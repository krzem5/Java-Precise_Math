echo off
echo NUL>_.class&&del /s /f /q *.class
cls
javac com/krzem/precise_math/Main.java&&java com/krzem/precise_math/Main
start /min cmd /c "echo NUL>_.class&&del /s /f /q *.class"