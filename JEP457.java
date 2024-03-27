import java.io.IOException;
import java.lang.classfile.*;
import java.lang.classfile.attribute.*;
import java.lang.constant.ClassDesc;
import java.lang.constant.MethodTypeDesc;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.lang.reflect.AccessFlag.*;

void main() throws IOException {
  var helloWorld = Path.of("socraten", "HelloWorld.class");
  var socraten = helloWorld.getParent();
  Files.deleteIfExists(helloWorld);
  Files.deleteIfExists(socraten);

  var bytecode = ClassFile.of().build(ClassDesc.of("socraten", "HelloWorld"), clazz -> clazz
    .withField("name", ClassDesc.ofDescriptor("Ljava/lang/String;"), PRIVATE.mask())
    .withMethod("hello", MethodTypeDesc.of(ClassDesc.ofDescriptor("V")), PUBLIC.mask(), method -> method
      .withCode(code -> code
        .aload(0)
        .getstatic(ClassDesc.of("java.lang", "System"), "out", ClassDesc.ofDescriptor("Ljava/io/PrintStream;"))
        .ldc("Hello, World!")
        .invokevirtual(ClassDesc.of("java.io", "PrintStream"), "println", MethodTypeDesc.of(ClassDesc.ofDescriptor("V"), ClassDesc.ofDescriptor("Ljava/lang/String;")))
        .return_()
      )
    )
    .withMethod("main", MethodTypeDesc.of(ClassDesc.ofDescriptor("V"), ClassDesc.ofDescriptor("[Ljava/lang/String;")), PUBLIC.mask() | STATIC.mask(), method -> method
      .withCode(code -> code
        .aload(0)
        .getstatic(ClassDesc.of("java.lang", "System"), "out", ClassDesc.ofDescriptor("Ljava/io/PrintStream;"))
        .ldc("Hello, World!")
        .invokevirtual(ClassDesc.of("java.io", "PrintStream"), "println", MethodTypeDesc.of(ClassDesc.ofDescriptor("V"), ClassDesc.ofDescriptor("Ljava/lang/String;")))
        .return_()
      )
    )
  );

  Files.createDirectory(socraten);
  Files.write(helloWorld, bytecode);
}
