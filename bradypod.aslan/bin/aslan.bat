@echo off

echo "Welcome to use bradypod's aslan tool"

SET AGENT_LIB=../aslan-agent/target/aslan-agent.jar

SET JAR_FILE=../aslan-client/target/aslan-client.jar

SET CORE_DIR=../aslan-client/target/lib

:: 因为有空格需使用""
set BOOT_CLASSPATH="-Xbootclasspath/a:%JAVA_HOME%/lib/tools.jar"

setlocal enabledelayedexpansion

for /r %CORE_DIR% %%i in (*) do (
	set "BOOT_CLASSPATH=!BOOT_CLASSPATH!;%%i"
)

echo %BOOT_CLASSPATH%

::::
::::	main_start()
::::
:: call java -classpath %CLASS_PATH% -jar %JAR_FILE% bradypod.framework.client.MainLauncher -pid %1 -agent %AGENT_LIB% -core %CORE_DIR%
call java %BOOT_CLASSPATH% -jar %JAR_FILE% bradypod.framework.client.MainLauncher -pid %1 -agent %AGENT_LIB% -core %CORE_DIR%

@pause