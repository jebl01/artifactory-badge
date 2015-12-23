package jebl01.configuration;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import jebl01.ArtifactoryRepository;

/**
 * Created by jesblo on 15-12-22.
 */
@Configuration
public class ArtifactoryConfig {
    private static final Logger log = LoggerFactory.getLogger(ArtifactoryConfig.class);

    @Value("${artifactory.host}")
    private String artifactoryHost;

    @Value("${artifactory.port}")
    private String artifactoryPort;

    @Value("${artifactory.user.name}")
    private String artifactoryUserName;

    @Value("${artifactory.user.pw}")
    private String artifactoryUserPW;

    @Bean
    public CredentialsProvider credentialsProvider() {
        log.warn("creating artifactory credentialsprovider for host: {}:{}", artifactoryHost, artifactoryPort);

        CredentialsProvider credsProvider = new BasicCredentialsProvider();

        credsProvider.setCredentials(
            new AuthScope(artifactoryHost, Integer.parseInt(artifactoryPort)),
            new UsernamePasswordCredentials(artifactoryUserName, artifactoryUserPW));

        return credsProvider;
    }

    @Bean
    public ArtifactoryRepository artifactoryRepository(HttpClient client) {
        return new ArtifactoryRepository(client, artifactoryHost, Integer.parseInt(artifactoryPort));
    }
}
