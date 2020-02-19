package com.d2iq.kubectl;


import picocli.CommandLine;

@CommandLine.Command(
        name = "kubectl-java",
        description = "An example kubectl cli written in Java",
        mixinStandardHelpOptions = true,
        subcommands = {
                PodCommand.class,
                ResourcesCommand.class
        }
)
public class JavaKubeCtlCommand implements Runnable {

        @Override
        public void run() {
            new CommandLine(this).usage(System.out);
        }
}
