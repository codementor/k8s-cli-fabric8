package com.d2iq.kubectl;


import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.api.model.PodBuilder;
import io.fabric8.kubernetes.client.KubernetesClient;
import picocli.CommandLine;

import java.util.HashMap;
import java.util.Map;

@CommandLine.Command(
        name = "add",
        description = "Adds a pod to a k8s cluster"
)
public class PodAddCommand extends KubernetesClientCommand {

    @CommandLine.Parameters(index = "0")
    String name;

    @CommandLine.Option(names = {"-i", "--image"}, description = "image to use for pod")
    String image = "nginx";

    @CommandLine.Option(names = {"-n", "--namespace"}, description = "namespace to use for pod")
    String namespace = "default";

    @Override
    public void runWithClient(KubernetesClient kc) {
        try {
            final Map<String, String> labels = new HashMap<>();
            labels.put("app", "demo");
            final Pod pod = new PodBuilder()
                .withNewMetadata()
                .withName(name)
                .withLabels(labels)
                .endMetadata()
                .withNewSpec()
                .addNewContainer()
                .withName(name)
                .withImage(image)
                .endContainer()
                .endSpec()
                .build();
            kc.pods().inNamespace(namespace).create(pod);
        } catch (Exception e) {
            System.out.println("Unable to add pod");
            System.err.println(e);
            return;
        }
    }
}
