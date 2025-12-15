#!/bin/bash
mkdir -p build/classes
javac -d build/classes ./src/main/java/ru/nsu/kutsenko/task111/*.java
javadoc -d docs ./src/main/java/ru/nsu/kutsenko/task111/*.java
echo "Main-Class: ru.nsu.kutsenko.task111.Sort" > manifest.txt
jar cvfm sort.jar manifest.txt -C build/classes .
java -jar sort.jar
