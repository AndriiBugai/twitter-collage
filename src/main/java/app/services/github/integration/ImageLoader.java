package app.services.github.integration;

import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.net.URL;

@Service
public class ImageLoader {

    public BufferedImage loadImage(String avatarUrl, int size) {
        try {
            URL url = URI.create(avatarUrl + "&s=" + size).toURL();
            return ImageIO.read(url);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
