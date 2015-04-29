@echo off
setlocal

if "%JAVA_HOME%"=="" (
  set JAVA_HOME="c:\Program Files (x86)\Java\jdk1.8.0_25"
)

"%JAVA_HOME%"\bin\java.exe -jar jarfinder.jar %*

