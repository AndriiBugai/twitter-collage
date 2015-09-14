package app; /**
 * Created by strapper on 12.09.15.
 */
import app.app2.Collage;
import app.app2.TwitterAPI;
import app.app2.TwitterUser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

@Controller
//@EnableAutoConfiguration
@SpringBootApplication
public class SampleController extends SpringBootServletInitializer {


    @RequestMapping(value = "/collage", method = RequestMethod.GET,  produces = "image/png" )
    @ResponseBody
    byte[] home(@RequestParam(value="size", required=false, defaultValue="100") String size, @RequestParam(value="login", required=false, defaultValue="100") String login) throws IOException {

//        app.app2.TwitterAPI twitterAPI = new app.app2.TwitterAPI();
//        ArrayList<BufferedImage> listOfImages = twitterAPI.getFriendsAvatars(login);
//
//        BufferedImage result = app.app2.Collage.createCollage(listOfImages, Integer.valueOf(size));
//
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        ImageIO.write(result, "png", outputStream);
//        byte[] imageData = outputStream.toByteArray();

        TwitterAPI twitterAPI = new TwitterAPI();
        ArrayList<TwitterUser> list = twitterAPI.getFriends(login, Integer.valueOf(size)  );
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