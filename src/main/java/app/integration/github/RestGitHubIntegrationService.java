package app.integration.github;

import app.exceptions.GitHubIntegrationException;
import app.exceptions.GitHubResourceNotFoundException;
import app.integration.github.dto.GitHubUserResponse;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Primary()
@Service
public class RestGitHubIntegrationService implements GitHubIntegrationService {

    private final RestClient restClient;

    private final AvatarLoader avatarLoader;

    public RestGitHubIntegrationService(RestClient restClient,
                                        AvatarLoader avatarLoader) {
        this.restClient = restClient;
        this.avatarLoader = avatarLoader;
    }

    @Override
    public List<BufferedImage> getAvatars(String username, int collageSize, int tileSize) {
        GitHubUserResponse[] users = this.restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/users/{username}/followers")
                        .queryParam("per_page", collageSize)
                        .build(username))
                .retrieve()
                .onStatus(HttpStatusCode::isError, (request, response) -> {
                    throw new GitHubIntegrationException(null);
                })
                .body(GitHubUserResponse[].class);

        if (users == null || users.length == 0) {
            throw new GitHubResourceNotFoundException(null);
        }

        return Arrays.stream(users).parallel()
                .map(GitHubUserResponse::avatar_url)
                .map(avatarUrl -> avatarLoader.loadAvatar(avatarUrl, tileSize))
                .collect(Collectors.toList());
    }
}
