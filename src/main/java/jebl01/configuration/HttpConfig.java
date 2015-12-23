package jebl01.configuration;

import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by jesblo on 15-12-22.
 */
@Configuration
public class HttpConfig {

    @Autowired
    private CredentialsProvider credentialsProvider;

    @Bean
    public HttpClient httpClient() {
        return HttpClients.custom()
            .setConnectionManager(new PoolingHttpClientConnectionManager())
            .setDefaultCredentialsProvider(credentialsProvider)
            .build();
    }
}
