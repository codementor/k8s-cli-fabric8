package com.d2iq.kubectl;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import dnl.utils.text.table.TextTable;
import io.fabric8.kubernetes.api.model.Doneable;
import io.fabric8.kubernetes.api.model.KubernetesList;
import io.fabric8.kubernetes.client.BaseClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.dsl.Resource;
import io.fabric8.kubernetes.client.dsl.base.BaseOperation;
import io.fabric8.kubernetes.client.dsl.base.OperationContext;
import picocli.CommandLine;

import java.util.List;

@CommandLine.Command(
        name = "resources",
        description = "lists resources in the k8s cluster"
)
public class ResourcesCommand extends KubernetesClientCommand {

    @Override
    void runWithClient(KubernetesClient kc) {

        final APIResourceList resourceList = new APIResourceListOperation((BaseClient)kc).list();
        if (resourceList.resources.isEmpty()) {
            System.out.println("No resources found");
        } else {
            printTable(resourceList.resources);
        }
    }

    private void printTable(List<APIResource> resources) {
        final Object[][] data = resources.stream()
            .map(apiResource -> new Object[]{
                apiResource.name,
                apiResource.namespaced,
                apiResource.kind
            })
            .toArray(Object[][]::new);
        final String[] columnNames = {"Resource", "Namespaced", "Kind"};
        new TextTable(columnNames, data).printTable();
    }

    /**
     * Temporary work-around to allow query api-resources
     * (current fabric8io/kubernetes-client doesn't have this in the model)
     */
    public static class APIResourceListOperation
        extends BaseOperation<APIResource, APIResourceList, Doneable<APIResource>, Resource<APIResource, Doneable<APIResource>>> {

        public APIResourceListOperation(BaseClient kc) {
            super(new OperationContext()
                .withConfig(kc.getConfiguration())
                .withOkhttpClient(kc.getHttpClient())
                .withPlural(""));

            this.apiVersion = "v1";
            this.type = APIResource.class;
            this.listType = APIResourceList.class;
        }

        @Override
        public boolean isResourceNamespaced() {
            return false;
        }
    }

    public static class APIResourceList extends KubernetesList {
        @JsonProperty("resources")
        private List<APIResource> resources;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class APIResource {
        @JsonProperty("name")
        private String name;
        @JsonProperty("singularName")
        private String singularName;
        @JsonProperty("namespaced")
        private Boolean namespaced;
        @JsonProperty("kind")
        private String kind;
    }
}
