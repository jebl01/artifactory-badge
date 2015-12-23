package jebl01;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.aftonbladet.utils.functional.Either;

import javax.imageio.ImageIO;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.StreamingOutput;

/**
 * Created by jesblo on 15-12-22.
 */
@Component
@Path("artifacts")
public class ArtifactsEndpoint {
    private final ArtifactoryRepository repository;

    @Autowired
    public ArtifactsEndpoint(ArtifactoryRepository repository) {
        this.repository = repository;
    }

    @GET
    @Path("/{repo}/{groupId}/{artifactId}")
    @Produces("image/png")
    public StreamingOutput badge(
        @PathParam("repo") String repo,
        @PathParam("groupId") String groupId,
        @PathParam("artifactId") String artifactId) {

        final Either<String, String> result = repository.getVersionString(groupId, artifactId, repo);

        return output -> ImageIO.write(
            BadgeGenerator.generate(
                "artifactory",
                result.getRight().orElseGet(() -> result.getLeft().get()),
                result.isRight()),
            "png",
            output);
    }
}
