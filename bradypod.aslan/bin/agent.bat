@echo off

echo "Welcome to use bradypod's aslan tool"

SET AGENT_LIB=..\aslan-agent\target\aslan-agent.jar

SET JAR_FILE=..\aslan-client\target\aslan-client.jar

SET CORE_DIR=..\aslan-client\target\lib

:: 因为有空格需使用""
:: set BOOT_CLASSPATH="-Xbootclasspath/a:%JAVA_HOME%/lib/tools.jar;%CORE_DIR%"

set CLASS_PATH="%JAVA_HOME%/lib/tools.jar"

cd %CORE_DIR%

setlocal enabledelayedexpansion
set aa=""
for /f "delims=" %%a in ('dir ..\aslan-client\target\lib ".\lib\*.jar"') do (
set "aa=!aa!%%a"
)

::for /R . %%s in (*) do (
::	set CLASS_PATH=%CLASS_PATH%;%%s
::)

echo %aa%

cd ../../../bin

::::
::::	main_start()
::::
call java -cp %CLASS_PATH% -jar %JAR_FILE% bradypod.framework.client.MainLauncher -pid %1 -agent %AGENT_LIB% -core %CORE_DIR%

@pause