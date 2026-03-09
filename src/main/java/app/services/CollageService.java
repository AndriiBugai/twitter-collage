package app.services;

import app.collage.image.CollageBuilder;
import app.dto.CollageRequest;
import app.services.github.integration.Hub4jClientService;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.util.List;

@Service
public class CollageService {

    private final Hub4jClientService gitHubClient;

    public CollageService(Hub4jClientService gitHubClient) {
        this.gitHubClient = gitHubClient;
    }

    public BufferedImage generateCollage(CollageRequest request) {
        List<BufferedImage> images = this.gitHubClient.getAvatars(request.login(), request.size(), request.tileSizeOrDefault());
        CollageBuilder collageBuilder = new CollageBuilder(images, request.tileSizeOrDefault());
        return collageBuilder.createCollage();
    }

}
