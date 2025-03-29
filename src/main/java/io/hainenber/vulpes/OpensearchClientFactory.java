package io.hainenber.vulpes;

import org.apache.hc.client5.http.auth.AuthScope;
import org.apache.hc.client5.http.auth.UsernamePasswordCredentials;
import org.apache.hc.client5.http.impl.auth.BasicCredentialsProvider;
import org.apache.hc.client5.http.impl.nio.PoolingAsyncClientConnectionManager;
import org.apache.hc.client5.http.impl.nio.PoolingAsyncClientConnectionManagerBuilder;
import org.apache.hc.client5.http.ssl.ClientTlsStrategyBuilder;
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.apache.hc.client5.http.ssl.TrustAllStrategy;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.nio.ssl.TlsStrategy;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.transport.OpenSearchTransport;
import org.opensearch.client.transport.httpclient5.ApacheHttpClient5TransportBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;

public class OpensearchClientFactory {
    private static final Logger log = LoggerFactory.getLogger(OpensearchClientFactory.class);
    private final String opensearchClusterUrl;
    private String opensearchUsername;
    private String opensearchPassword;

    public OpensearchClientFactory(String opensearchClusterUrl, String inputOpensearchUsername, String inputOpensearchPassword) throws Exception {
        if (inputOpensearchPassword == null || inputOpensearchUsername == null)  {
            throw new Exception("OpenSearch cluster credentials are null");
        }
        this.opensearchClusterUrl = opensearchClusterUrl;
        this.opensearchUsername = inputOpensearchUsername;
        this.opensearchPassword = inputOpensearchPassword;
    }

    public OpenSearchClient getOpensearchClient() throws Exception {
        // Configure credentials for authentication.
        final HttpHost opensearchHost = HttpHost.create(opensearchClusterUrl);

        // Remove single quotes intended for shell escape.
        opensearchUsername = opensearchUsername.replace("'", "");
        opensearchPassword = opensearchPassword.replace("'", "");

        BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(new AuthScope(opensearchHost),
                new UsernamePasswordCredentials(opensearchUsername, opensearchPassword.toCharArray()));

        // Disable SSL verification.
        // TODO: remove this part for production deployment.
        final SSLContext bypassedSSLContext = SSLContextBuilder
                .create()
                .loadTrustMaterial(TrustAllStrategy.INSTANCE)
                .build();

        // Initialize transport and client with disabled SSL and hostname verification
        // TODO: remove this part for production deployment.
        final ApacheHttpClient5TransportBuilder builder = ApacheHttpClient5TransportBuilder.builder(opensearchHost);
        builder.setHttpClientConfigCallback(httpAsyncClientBuilder -> {
            final TlsStrategy tlsStrategy = ClientTlsStrategyBuilder.create()
                    .setSslContext(bypassedSSLContext)
                    .setHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                    .build();
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
            OpenSearchClient openSearchClient = new OpenSearchClient(transport);
            openSearchClient.info();
            log.info("OpenSearch cluster connected successfully");
            return openSearchClient;
        } catch (Exception e) {
            log.error("Error initializing OpenSearch cluster: {1}", e);
            throw e;
        }
    }
}
