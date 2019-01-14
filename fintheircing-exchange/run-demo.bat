@echo off
chcp 65001
set JAVA_HOME=E:\Java\jdk1.8.0_151_32bit
set PATH=%JAVA_HOME%/bin;%PATH%
java -jar exchange-0.0.1-SNAPSHOT.jar
pause
