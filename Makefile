
run-jep-463:
	java --source 22 --enable-preview JEP463.java

run-jep-447:
	java --source 22 --enable-preview JEP447.java

run-jep-454:
	java --source 22 --enable-preview JEP454.java

run-jep-456:
	java --source 22 --enable-preview JEP456.java

run-jep-457:
	java --source 22 --enable-preview JEP457.java

run-jep-458:
	java --class-path '*' --source 22 --enable-preview JEP458.java

run-jep-459:
	java --source 22 --enable-preview JEP459.java

run-jep-460:
	java --source 22 --enable-preview --add-modules=jdk.incubator.vector -Xmx12G JEP460.java
