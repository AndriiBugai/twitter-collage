package app.integration.github;

import app.exceptions.GitHubAvatarFetchException;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

@Service
public class AvatarLoader {

    public BufferedImage loadAvatar(String avatarUrl, int size) {
        try {
            URL url = URI.create(avatarUrl + "&s=" + size).toURL();
            return ImageIO.read(url);
        } catch (IOException e) {
            throw new GitHubAvatarFetchException(e);
        }
    }
}
