package com.yu.rmi;

import java.rmi.MarshalledObject;
import java.rmi.Remote;
import java.rmi.activation.Activatable;
import java.rmi.activation.ActivationDesc;
import java.rmi.activation.ActivationGroup;
import java.rmi.activation.ActivationGroupDesc;
import java.rmi.activation.ActivationGroupID;
import java.rmi.registry.LocateRegistry;
import java.util.Properties;

public class Setup {

    /**
     * Prevents instantiation.
     */
    private Setup() {}

    public static void main(String[] args) throws Exception {

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        /*
         * Get the impl class argument from the command line.
         */
        String implClass = "";
        if (args.length < 1) {
            System.err.println(
                "usage: java [options] examples.activation.Setup <implClass>");
            System.exit(1);
        } else {
            implClass = args[0];
        }

        String policy =
            System.getProperty("examples.activation.policy", "group.policy");
        String implCodebase =
            System.getProperty("examples.activation.impl.codebase");
        String filename =
            System.getProperty("examples.activation.file", "");

        Properties props = new Properties();
        props.put("java.security.policy", policy);
        props.put("java.class.path", "no_classpath");
        props.put("examples.activation.impl.codebase", implCodebase);
        if (filename != null && !filename.equals("")) {
            props.put("examples.activation.file", filename);
        }

        ActivationGroupDesc groupDesc = new ActivationGroupDesc(props, null);

        ActivationGroupID groupID =
           ActivationGroup.getSystem().registerGroup(groupDesc);
        System.err.println("Activation group descriptor registered.");

        MarshalledObject<String> data = null;
        if (filename != null && !filename.equals("")) {
            data = new MarshalledObject<>(filename);
        }

        ActivationDesc desc =
            new ActivationDesc(groupID, implClass, implCodebase, data);

        Remote stub = Activatable.register(desc);
        System.err.println("Activation descriptor registered.");

        String name = System.getProperty("examples.activation.name");
        LocateRegistry.getRegistry().rebind(name, stub);
        System.err.println("Stub bound in registry.");
    }
}
