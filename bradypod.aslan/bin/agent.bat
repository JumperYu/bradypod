@echo off

echo "Welcome to use bradypod's aslan tool"

set AGENT_LIB=E://work/new-life\bradypod\bradypod.framework\bradypod.framework.agent\target\agent-jar-with-dependencies.jar

set JAR_FILE=E:\work\new-life\bradypod\bradypod.framework\bradypod.framework.client\target\client-jar-with-dependencies.jar

:: 因为有空格需使用""
set BOOT_CLASSPATH="-Xbootclasspath/a:%JAVA_HOME%/lib/tools.jar"   

::::
::::	main_start()
::::
call java %BOOT_CLASSPATH% -jar %JAR_FILE% bradypod.framework.client.MainLauncher -pid %1 -agent %AGENT_LIB%

@pause