package jebl01;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import se.aftonbladet.utils.functional.Either;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;

import static se.aftonbladet.utils.functional.Either.left;
import static se.aftonbladet.utils.functional.Either.right;

/**
 * Created by jesblo on 15-12-22.
 */
public class ArtifactoryRepository {
    private final static Logger log = LoggerFactory.getLogger(ArtifactoryRepository.class);

    private final HttpClient client;
    private final String artifactoryHost;
    private final int artifactoryPort;

    @Autowired
    public ArtifactoryRepository(HttpClient client, String artifactoryHost, int artifactoryPort) {
        this.client = client;
        this.artifactoryHost = artifactoryHost;
        this.artifactoryPort = artifactoryPort;
    }

    public Either<String, String> getVersionString(String groupId, String artifactId, String repo) {

        try {
            URI uri = new URIBuilder()
                .setScheme("http")
                .setHost(artifactoryHost)
                .setPort(artifactoryPort)
                .setPath("/artifactory/api/search/latestVersion")
                .addParameter("g", groupId)
                .addParameter("a", artifactId)
                .addParameter("repos", repo).build();

            final HttpResponse response = client.execute(new HttpGet(uri));

            if(response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                log.warn("failed to get data from artifactory, http status code: " + response.getStatusLine().getStatusCode());
                return left("...");
            }

            return right(new BufferedReader(new InputStreamReader(response.getEntity().getContent())).readLine());

        } catch(Exception e) {
            log.warn("failed to get data from artifactory", e);
            return left("...");
        }
    }
}
