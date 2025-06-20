package io.hainenber.vulpes.opensearch;

import org.apache.hc.client5.http.auth.AuthScope;
import org.apache.hc.client5.http.auth.UsernamePasswordCredentials;
import org.apache.hc.client5.http.impl.auth.BasicCredentialsProvider;
import org.apache.hc.client5.http.impl.nio.PoolingAsyncClientConnectionManager;
import org.apache.hc.client5.http.impl.nio.PoolingAsyncClientConnectionManagerBuilder;
import org.apache.hc.client5.http.ssl.DefaultClientTlsStrategy;
import org.apache.hc.client5.http.ssl.HostnameVerificationPolicy;
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.nio.ssl.TlsStrategy;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch._types.OpenSearchException;
import org.opensearch.client.opensearch._types.mapping.DateProperty;
import org.opensearch.client.opensearch._types.mapping.Property;
import org.opensearch.client.opensearch._types.mapping.TypeMapping;
import org.opensearch.client.opensearch.indices.CreateIndexRequest;
import org.opensearch.client.opensearch.indices.GetIndexRequest;
import org.opensearch.client.transport.OpenSearchTransport;
import org.opensearch.client.transport.httpclient5.ApacheHttpClient5TransportBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLContext;
import java.util.Objects;

@Component
public class OpensearchClientFactory {
    private static final Logger log = LoggerFactory.getLogger(OpensearchClientFactory.class);

    @Value("${vulpes.opensearch.url}")
    private String opensearchClusterUrl;

    @Autowired
    private Environment environment;

    public void createIndex(String indexName) throws Exception {
        final OpenSearchClient openSearchClient = getOpensearchClient();
        final GetIndexRequest getIndexRequest = new GetIndexRequest.Builder()
                .index(indexName)
                .build();
        try {
            openSearchClient.indices().get(getIndexRequest);
        } catch (OpenSearchException getIndexException) {
            if (Objects.equals(getIndexException.error().type(), "index_not_found_exception")) {
                log.info("Index {} not yet created in OpenSearch cluster, creating one", indexName);
                // Have to do explicit mapping to `date` type for "modified", "published" and "withdrawn".
                final TypeMapping explicitDateMapping = new TypeMapping.Builder()
                        .properties("modified", new Property.Builder().date(
                                new DateProperty.Builder().build()).build())
                        .properties("published", new Property.Builder().date(
                                new DateProperty.Builder().build()).build())
                        .properties("withdrawn", new Property.Builder().date(
                                new DateProperty.Builder().build()).build())
                        .build();
                final CreateIndexRequest createIndexRequest = new CreateIndexRequest.Builder()
                        .index(indexName)
                        .mappings(explicitDateMapping)
                        .build();
                openSearchClient.indices().create(createIndexRequest);
                log.info("Index {} created", indexName);
            } else {
                throw getIndexException;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public OpenSearchClient getOpensearchClient() throws Exception {
        // Configure credentials for authentication.
        final HttpHost opensearchHost = HttpHost.create(opensearchClusterUrl);
        String opensearchUsername = environment.getProperty("OPENSEARCH_ADMIN_USERNAME");
        String opensearchPassword = environment.getProperty("OPENSEARCH_ADMIN_PASSWORD");

        if (opensearchUsername == null || opensearchPassword == null)  {
            throw new Exception("OpenSearch cluster credentials are null");
        }

        // Remove single quotes intended for shell escape.
        opensearchUsername = opensearchUsername.replace("'", "");
        opensearchPassword = opensearchPassword.replace("'", "");

        final BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(new AuthScope(opensearchHost),
                new UsernamePasswordCredentials(opensearchUsername, opensearchPassword.toCharArray()));

        // SSL verification
        final SSLContext SSLContext = SSLContextBuilder
                .create()
                .loadTrustMaterial(null, (chains, authType) -> true)
                .build();

        // Initialize transport and client with proper SSL and hostname verification
        final ApacheHttpClient5TransportBuilder builder = ApacheHttpClient5TransportBuilder.builder(opensearchHost);
        builder.setHttpClientConfigCallback(httpAsyncClientBuilder -> {
            final TlsStrategy tlsStrategy = new DefaultClientTlsStrategy(
                    SSLContext,
                    HostnameVerificationPolicy.CLIENT,
                    NoopHostnameVerifier.INSTANCE
            );
            final PoolingAsyncClientConnectionManager connectionManager = PoolingAsyncClientConnectionManagerBuilder
                    .create()
                    .setTlsStrategy(tlsStrategy)
                    .build();
            return httpAsyncClientBuilder
                    .setConnectionManager(connectionManager)
                    .setDefaultCredentialsProvider(credentialsProvider);
        });
        final OpenSearchTransport transport = builder.build();

        // Get overall cluster info as a quick health check
        try {
            final OpenSearchClient openSearchClient = new OpenSearchClient(transport);
            openSearchClient.info();
            log.debug("OpenSearch cluster connected successfully");
            return openSearchClient;
        } catch (Exception e) {
            log.error("Error initializing OpenSearch cluster: {}", e.toString());
            throw e;
        }
    }
}
