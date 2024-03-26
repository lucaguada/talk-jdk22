import java.io.IOException;
import java.lang.classfile.ClassFile;
import java.lang.constant.ClassDesc;
import java.lang.constant.MethodTypeDesc;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.lang.reflect.AccessFlag.*;

void main() throws IOException {
  var codeModel = ClassFile.of().build(ClassDesc.of("HelloWorld"), clazz ->
    clazz
      .withField("name", ClassDesc.ofDescriptor("Ljava/lang/String;"), field ->
        field
          .withFlags(PRIVATE)
      )
      .withMethod("whoever", MethodTypeDesc.of(ClassDesc.ofDescriptor("V")), PUBLIC.ordinal(), method ->
        method
          .withFlags(PRIVATE)
          .withCode(code ->
            code
              .aload(0)
              .getstatic(ClassDesc.of("java.lang", "System"), "out", ClassDesc.ofDescriptor("Ljava/io/PrintStream;"))
              .aload(1)
              .invokevirtual(ClassDesc.of("java.io", "PrintStream"), "println", MethodTypeDesc.of(ClassDesc.ofDescriptor("V"), ClassDesc.ofDescriptor("Ljava/lang/String;")))
              .return_()
          )
      )
  );

  Files.write(Path.of("HelloWorld.class"), codeModel);
}
