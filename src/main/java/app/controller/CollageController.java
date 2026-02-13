package app.controller;

import app.collage.image.CollageBuilder;
import app.dto.CollageRequest;
import app.github.integration.Hub4jClient;
import jakarta.validation.Valid;
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
            @Valid CollageRequest request
    ) throws IOException {
        Hub4jClient client = new Hub4jClient();
        List<BufferedImage> images = client.getAvatars(request.size(), request.login());
        CollageBuilder collageBuilder = new CollageBuilder(images, request.tileSizeOrDefault());
        BufferedImage collage = collageBuilder.createCollage();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(collage, "png", out);

        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(out.toByteArray());
    }
}