package app.services;

import app.collage.builder.CollageBuilder;
import app.web.dto.CollageRequest;
import app.integration.github.GitHubIntegrationService;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.util.List;

@Service
public class CollageService {

    private final GitHubIntegrationService gitHubClient;

    public CollageService(GitHubIntegrationService gitHubClient) {
        this.gitHubClient = gitHubClient;
    }

    public BufferedImage generateCollage(CollageRequest request) {
        List<BufferedImage> images = this.gitHubClient.getAvatars(request.login(), request.size(), request.tileSizeOrDefault());
        CollageBuilder collageBuilder = new CollageBuilder(images, request.tileSizeOrDefault());
        return collageBuilder.createCollage();
    }

}
