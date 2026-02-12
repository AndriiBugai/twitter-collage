package app.github.integration;

import org.kohsuke.github.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UncheckedIOException;
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
                                          String username) throws IOException {
        final String githubToken = System.getenv("GITHUB_TOKEN");
        final GitHub github = new GitHubBuilder().withOAuthToken(githubToken).build();
        final GHUser githubUser = github.getUser(username);

        List<String> avatarsUrl = StreamSupport
                .stream(githubUser.listFollowers().spliterator(), false)
                .limit(collageSize)
                .map(GHPerson::getAvatarUrl)
                .toList();

        // load images in parallel
        return avatarsUrl.parallelStream()
                .map(this::loadImage)
                .collect(Collectors.toList());
    }

}
