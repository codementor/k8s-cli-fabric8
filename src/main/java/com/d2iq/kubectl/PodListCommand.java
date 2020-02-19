package com.d2iq.kubectl;

import dnl.utils.text.table.TextTable;
import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.client.KubernetesClient;
import picocli.CommandLine;

import java.util.List;


/**
 * PodListCommand is a command for listing pods in a kubernetes cluster.  This class is an example and requires
 * standard kubeconfig setup to work.
 *
 */
@CommandLine.Command(
        name = "list",
        description = "lists pods in the cluster using a structured approach"
)
public class PodListCommand extends KubernetesClientCommand {

    @Override
    void runWithClient(KubernetesClient kc) {
        final List<Pod> list = kc.pods().list().getItems();
        if (list.isEmpty()) {
            System.out.println("No Pods found");
        } else {
            printTable(list);
        }
    }

    /**
     * Prints the table of pods discovered
     *
     * @param list PodList of pods
     */
    private static void printTable(List<Pod> list) {
        final Object[][] tableData = list.stream()
            .map(pod -> new Object[]{
                pod.getMetadata().getName(),
                pod.getMetadata().getNamespace()
            })
            .toArray(Object[][]::new);
        String[] columnNames = {"Pod Name", "namespace"};
        new TextTable(columnNames, tableData).printTable();
    }
}
