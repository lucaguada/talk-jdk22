import java.lang.foreign.Arena;
import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

import static java.lang.foreign.ValueLayout.*;

class QSort {
  static int intcmp(MemorySegment address1, MemorySegment address2) {
    return address1.get(JAVA_INT, 0) - address2.get(JAVA_INT, 0);
  }
}

void main() {
  try (final var offHeap = Arena.ofConfined()) {

    var linker = Linker.nativeLinker();
    var stdlib = linker.defaultLookup();

    var qsort = stdlib.find("qsort")
      .map(function -> linker.downcallHandle(function, FunctionDescriptor.ofVoid(ADDRESS, JAVA_LONG, JAVA_LONG, ADDRESS)))
      .orElseThrow(() -> new IllegalCallerException("Can't find qsort"));

    var jdklib = MethodHandles.lookup();
    var intcmp = jdklib.findStatic(QSort.class, "intcmp", MethodType.methodType(int.class, MemorySegment.class, MemorySegment.class));

    var cmp = FunctionDescriptor.of(
      JAVA_INT,
      ADDRESS.withTargetLayout(JAVA_INT),
      ADDRESS.withTargetLayout(JAVA_INT)
    );

    var intcmpFunctionPointer = linker.upcallStub(intcmp, cmp, Arena.ofAuto());

    var integers = new int[]{12, 3, 4, 45, 21, 1, 5};

    var pointers = offHeap.allocate(JAVA_INT, integers.length);

    for (int index = 0; index < integers.length; index++) {
      var integer = integers[index];
      pointers.setAtIndex(JAVA_INT, index, integer);
    }

    qsort.invoke(pointers, integers.length, JAVA_INT.byteSize(), intcmpFunctionPointer);

    var array = pointers.toArray(JAVA_INT);
    for (int index = 0; index < integers.length; index++) {
      System.out.println(array[index]);
    }
  } catch (NoSuchMethodException | IllegalAccessException e) {
    throw new IllegalCallerException("Can't find intcmp", e);
  } catch (Throwable e) {
    throw new IllegalStateException("Can't invoke qsort", e);
  }
}

