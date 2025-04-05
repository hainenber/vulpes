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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLContext;

@Component
public class OpensearchClientFactory {
    private static final Logger log = LoggerFactory.getLogger(OpensearchClientFactory.class);

    @Value("${vulpes.opensearch.url}")
    private String opensearchClusterUrl;

    @Autowired
    private Environment environment;

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
            final OpenSearchClient openSearchClient = new OpenSearchClient(transport);
            openSearchClient.info();
            log.info("OpenSearch cluster connected successfully");
            return openSearchClient;
        } catch (Exception e) {
            log.error("Error initializing OpenSearch cluster: {0}", e);
            throw e;
        }
    }
}
