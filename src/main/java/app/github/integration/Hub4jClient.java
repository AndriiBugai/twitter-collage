package app.github.integration;

import org.kohsuke.github.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Hub4jClient {

    private BufferedImage loadImage(String avatarUrl) {
        try {
            return ImageIO.read(new URL(avatarUrl + "&s=90"));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public List<BufferedImage> getAvatars(int collageSize,
                                          String gitHubName) throws MalformedURLException, IOException {

        final String token = System.getenv("GITHUB_TOKEN");

        final GitHub github = new GitHubBuilder().withOAuthToken(token).build();

        final GHUser user = github.getUser(gitHubName);

        return StreamSupport
                .stream(user.listFollowers().spliterator(), false)
                .limit(collageSize)
                .map(GHPerson::getAvatarUrl)
                .map(this::loadImage)
                .collect(Collectors.toList());
    }

}
