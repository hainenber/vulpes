package io.hainenber.vulpes.opensearch;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.hainenber.vulpes.testcontainers.OpensearchReusableContainer;
import org.junit.ClassRule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.testcontainers.OpensearchContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@TestComponent
@ActiveProfiles("test")
@SpringBootTest(classes = OpensearchClientFactory.class)
public class OpensearchClientFactoryTest {
    @Autowired
    private OpensearchClientFactory opensearchClientFactory;

    @ClassRule
    public static OpensearchContainer<OpensearchReusableContainer> opensearchContainer = OpensearchReusableContainer.getInstance();

    @BeforeAll
    public static void beforeAll() {
        opensearchContainer.start();
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
            final OpenSearchClient client = opensearchClientFactory.getOpensearchClient();
            assertNotNull(client);
        });
    }
}
