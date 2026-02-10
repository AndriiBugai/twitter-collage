package app;

import app.collage.image.CollageBuilder;
import app.github.integration.Hub4jClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Controller
@SpringBootApplication
public class SampleController extends SpringBootServletInitializer {

    @RequestMapping(value = "/collage", method = RequestMethod.GET,  produces = "image/png" )
    @ResponseBody
    byte[] home(@RequestParam(value="size", required=false, defaultValue="100") int size,
                @RequestParam(value="login", required=false, defaultValue="100") String login,
                @RequestParam(value="tileSize", required=false, defaultValue="50") int tileSize) throws IOException {

        Hub4jClient client = new Hub4jClient();
        List<BufferedImage> images = client.getAvatars(size, login);
        CollageBuilder collageBuilder = new CollageBuilder(images, tileSize);
        BufferedImage collage = collageBuilder.createCollage();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(collage, "png", out);

        return out.toByteArray();
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SampleController.class, args);
    }
}