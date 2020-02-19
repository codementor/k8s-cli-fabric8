package com.d2iq.kubectl;

import io.fabric8.kubernetes.client.KubernetesClient;
import picocli.CommandLine;

@CommandLine.Command(
    name = "delete",
    aliases = "del",
    description = "Deletes a pod from a k8s cluster"
)
public class PodDeleteCommand extends KubernetesClientCommand {
    @CommandLine.Parameters(index = "0")
    String name;

    @CommandLine.Option(names = {"-n", "--namespace"}, description = "namespace to use for pod")
    String namespace = "default";

    @Override
    public void runWithClient(KubernetesClient kc) {
        try {
            final boolean result = kc.pods().inNamespace(namespace).withName(name).delete();
            if (!result) {
                System.out.printf("Pod with name %s was not found%n", name);
            }
        } catch (Exception e) {
            System.out.println("Unable to delete pod");
            System.err.println(e);
        }
    }
}
