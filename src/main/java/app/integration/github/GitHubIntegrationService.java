package app.integration.github;

import java.awt.image.BufferedImage;
import java.util.List;

public interface GitHubIntegrationService {

    public List<BufferedImage> getAvatars(String username,
                                          int collageSize,
                                          int tileSize);
}
