package app.api;

import app.collage.image.CollageBuilder;
import app.github.integration.Hub4jClient;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Controller
@Validated
public class CollageController extends SpringBootServletInitializer {

    @GetMapping(value = "/collage", produces = "image/png")
    public ResponseEntity<byte[]> generateCollage(
            @RequestParam
            @NotBlank
            String login,

            @RequestParam
            @Min(4) @Max(100)
            int size,

            @RequestParam(defaultValue = "64")
            @Min(25) @Max(100)
            int tileSize
    ) throws IOException {
        Hub4jClient client = new Hub4jClient();
        List<BufferedImage> images = client.getAvatars(size, login);
        CollageBuilder collageBuilder = new CollageBuilder(images, tileSize);
        BufferedImage collage = collageBuilder.createCollage();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(collage, "png", out);

        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(out.toByteArray());
    }
}