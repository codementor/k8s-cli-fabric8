package com.d2iq.kubectl;

import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

public abstract class KubernetesClientCommand implements Runnable {

    abstract void runWithClient(KubernetesClient kc);

    @Override
    public void run() {
        try (KubernetesClient kc = new DefaultKubernetesClient()) {
            runWithClient(kc);
        } catch (Exception ex) {
            System.out.println("Unable to get cluster configuration");
            System.err.println(ex);
        }
    }
}
