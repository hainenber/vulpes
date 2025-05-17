package io.hainenber.vulpes.opensearch;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.testcontainers.OpensearchContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.utility.DockerImageName;

@TestComponent
@SpringBootTest(classes = OpensearchClientFactory.class)
class OpensearchClientFactoryTest {
    @Autowired
    OpensearchClientFactory opensearchClientFactory;

    static OpensearchContainer<?> opensearchContainer = new OpensearchContainer<>(DockerImageName.parse("opensearchproject/opensearch:2.11.0")).withSecurityEnabled();

    @BeforeAll
    static void setup() {
        System.setProperty("OPENSEARCH_ADMIN_USERNAME", opensearchContainer.getUsername());
        System.setProperty("OPENSEARCH_ADMIN_PASSWORD", opensearchContainer.getPassword());
        opensearchContainer.start();
    }

    @AfterAll
    static void tearDown() {
        opensearchContainer.stop();
    }

    @DynamicPropertySource
    static void dynamicProperties(DynamicPropertyRegistry registry) {
        registry.add("vulpes.opensearch.url", opensearchContainer::getHttpHostAddress);
    }

    @Test
    public void testOpensearchClient() {
        assertTrue(opensearchContainer.isRunning());
        assertNotNull(opensearchClientFactory);
        assertDoesNotThrow(() -> {
            OpenSearchClient client = opensearchClientFactory.getOpensearchClient();
            assertNotNull(client);
        });
    }
}
