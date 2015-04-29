@echo off
setlocal

rem if "%JAVA_HOME%"=="" (
rem   set JAVA_HOME="c:\Program Files (x86)\Java\jdk1.8.0_25"
rem )

"%JAVA_HOME%"\bin\java.exe -jar jarfinder.jar %*
rem %JAVA_HOME%\bin\java.exe -cp bin jp.hashiwa.jarfinder.JarFinderMain %*

