import jdk.incubator.vector.IntVector;

import java.util.Arrays;

void main() {
  int[] array1 = new int[1_000_000_000];
  int[] array2 = new int[1_000_000_000];
  int[] resultArray1 = new int[1_000_000_000];
  int[] resultArray2 = new int[1_000_000_000];

  for (int i = 0; i < array1.length; i++) {
    array1[i] = i * 2;
    array2[i] = i * 3;
  }

  // Scalar computation
  for (int i = 0; i < array1.length; i++) {
    resultArray1[i] = array1[i] + array2[i];
  }

  for (int offset = 0; offset < array1.length; offset++) {
    var vector1 = IntVector.fromArray(IntVector.SPECIES_128, array1, offset);
    var vector2 = IntVector.fromArray(IntVector.SPECIES_128, array2, offset);

    vector1.add(vector2).intoArray(resultArray2, offset);
  }

  System.out.println(STR."Arrays are equal: \{Arrays.equals(resultArray1, resultArray2)}");
}
