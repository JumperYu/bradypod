@echo on

echo "Welcome to use bradypod's agent tool"

java -jar E:\\agent.jar -pid 3892

java -Xbootclasspath/a:%JAVA_HOME%\lib\tools.jar -jar client-jar-with-dependencies.jar -pid 4796 -agent E://work/new-life/bradypod/bradypod.framework/bradypod.framework.agent/target/agent.jar

java -cp tools.jar;client-jar-with-dependencies.jar bradypod.framework.client.MainLauncher -pid 4796 -agent E://work/new-life\bradypod\bradypod.framework\bradypod.framework.agent\target\agent.jar

java -jar client-jar-with-dependencies.jar -pid 4796 -agent E://work/new-life\bradypod\bradypod.framework\bradypod.framework.agent\target\agent.jar

@pause