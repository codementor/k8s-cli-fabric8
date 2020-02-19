package com.d2iq.kubectl;

import picocli.CommandLine;

@CommandLine.Command(
        name = "pod",
        description = "Programmatically accesses pods in a k8s with 'add', 'delete' and 'list'",
        subcommands = {
            PodAddCommand.class,
            PodDeleteCommand.class,
            PodListCommand.class
        }
)
public class PodCommand {
}
