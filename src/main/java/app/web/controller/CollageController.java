package app.web.controller;

import app.web.dto.CollageRequest;
import app.services.CollageService;
import app.services.ImageEncodingService;
import jakarta.validation.Valid;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.awt.image.BufferedImage;

@Controller
@Validated
public class CollageController extends SpringBootServletInitializer {

    private final CollageService collageService;
    private final ImageEncodingService imageEncodingService;

    public CollageController(CollageService collageService,
                             ImageEncodingService imageEncodingService) {
        this.collageService = collageService;
        this.imageEncodingService = imageEncodingService;
    }

    @GetMapping(value = "/collage", produces = "image/png")
    public ResponseEntity<byte[]> generateCollage(@Valid CollageRequest request) {
        BufferedImage collage = collageService.generateCollage(request);
        byte[] pngImage = imageEncodingService.toPngImage(collage);

        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(pngImage);
    }
}