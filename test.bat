@echo off
if not exist out-test mkdir out-test
javac -encoding UTF-8 -cp "..\lib\junit-platform-console-standalone-1.13.0-M3.jar;src\main\java" -d out-test src\main\java\*.java src\test\java\*.java
java -jar "..\lib\junit-platform-console-standalone-1.13.0-M3.jar" --class-path out-test --scan-class-path
