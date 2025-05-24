package io.hainenber.vulpes.testcontainers;

import org.opensearch.testcontainers.OpensearchContainer;
import org.testcontainers.utility.DockerImageName;

// Source: https://www.baeldung.com/spring-boot-testcontainers-integration-test#common-configuration
public class OpensearchReusableContainer extends OpensearchContainer<OpensearchReusableContainer> {
    private static final String IMAGE_VERSION = "opensearchproject/opensearch:2.11.0";
    private static OpensearchReusableContainer container;

    private OpensearchReusableContainer() {
        super(DockerImageName.parse(IMAGE_VERSION));
    }

    public static OpensearchReusableContainer getInstance() {
        if (container == null) {
            container = new OpensearchReusableContainer().withSecurityEnabled();
        }
        return container;
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("OPENSEARCH_ADMIN_USERNAME", container.getUsername());
        System.setProperty("OPENSEARCH_ADMIN_PASSWORD", container.getPassword());
    }

    // Do nothing, let JVM handles the container shutdown.
    @Override
    public void stop() { }
}
