package app.integration.github;

import app.exceptions.GitHubIntegrationException;
import app.exceptions.GitHubResourceNotFoundException;
import org.kohsuke.github.*;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class Hub4jClientService implements GitHubIntegrationService {

    private volatile GitHub github;

    private final AvatarLoader avatarLoader;

    public Hub4jClientService(AvatarLoader avatarLoader) {
        this.avatarLoader = avatarLoader;
    }

    @Override
    public List<BufferedImage> getAvatars(String username,
                                          int collageSize,
                                          int tileSize) {
        if (github == null) {
            loginToGitHub();
        }

        final GHUser githubUser;
        try {
            githubUser = github.getUser(username);
        } catch (IOException e) {
            throw new GitHubResourceNotFoundException(e);
        }

        List<String> avatarsUrl = StreamSupport
                .stream(githubUser.listFollowers().spliterator(), false)
                .limit(collageSize)
                .map(GHPerson::getAvatarUrl)
                .toList();

        if (avatarsUrl.isEmpty()) {
            throw new GitHubResourceNotFoundException(null);
        }

        // load images in parallel
        return avatarsUrl.parallelStream()
                .map(avatarUrl -> avatarLoader.loadAvatar(avatarUrl, tileSize))
                .collect(Collectors.toList());
    }

    // TODO check on synchronized and volatile options
    private synchronized void loginToGitHub() {
        if (github == null) {
            final String githubToken = System.getenv("GITHUB_TOKEN");

            try {
                github = new GitHubBuilder().withOAuthToken(githubToken).build();
            } catch (IOException e) {
                throw new GitHubIntegrationException(e);
            }
        }
    }

}
