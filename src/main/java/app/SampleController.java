package app; /**
 * Created by strapper on 12.09.15.
 */
import app.logic.Collage;
import app.logic.TwitterAPI;
import app.logic.TwitterUser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

@Controller
@SpringBootApplication
public class SampleController extends SpringBootServletInitializer {

    @RequestMapping(value = "/collage", method = RequestMethod.GET,  produces = "image/png" )
    @ResponseBody
    byte[] home(@RequestParam(value="size", required=false, defaultValue="100") String size, @RequestParam(value="login", required=false, defaultValue="100") String login) throws IOException {

        TwitterAPI twitterAPI = new TwitterAPI();
        ArrayList<TwitterUser> list = twitterAPI.getFriends(login, Integer.valueOf(size));
        if(list == null) {
            BufferedImage in = ImageIO.read(new File("Error.png"));
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(in, "png", outputStream);
            byte[] imageData = outputStream.toByteArray();
            return imageData;
        }
        list = twitterAPI.cutList(list, Integer.valueOf(size));
        list = twitterAPI.loadImages(list);
        ArrayList<BufferedImage> listImage = twitterAPI.getImages(list);

        BufferedImage result = Collage.createCollage(listImage);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(result, "png", outputStream);
        byte[] imageData = outputStream.toByteArray();
        return imageData;
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SampleController.class, args);
    }
}