@echo off

echo "Welcome to use bradypod's aslan tool"

set AGENT_LIB=..\aslan-agent\target\aslan-agent.jar

set JAR_FILE=..\aslan-client\target\aslan-client.jar

set CORE_DIR=..\aslan-client\target\lib

:: 因为有空格需使用""
set BOOT_CLASSPATH="-Xbootclasspath/a:%JAVA_HOME%/lib/tools.jar"   

::::
::::	main_start()
::::
call java %BOOT_CLASSPATH% -jar %JAR_FILE% bradypod.framework.client.MainLauncher -pid %1 -agent %AGENT_LIB% -core %CORE_DIR%

@pause