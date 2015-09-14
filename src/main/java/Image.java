import com.twitter.hbc.httpclient.auth.OAuth1;
import twitter4j.*;
import twitter4j.auth.AccessToken;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.TreeSet;

/**
 * Created by strapper on 12.09.15.
 */
public class Image {

    public static ArrayList<BufferedImage> getImages(String login) throws TwitterException, IOException {
        Twitter twitter = getTwitterInstance();
        ArrayList<BufferedImage> list = new ArrayList<BufferedImage>();


        User u1 = null;
        long cursor = -1;
        IDs ids;
        System.out.println("Listing followers's ids.");
        do {
            //    ids = twitter.getFollowersIDs("AStrapper", cursor);
            ids = twitter.getFriendsIDs(login, cursor);
            for (long id : ids.getIDs()) {
                try{
                    System.out.println(id);
                    User user = twitter.showUser(id);
                    //System.out.println(user.getScreenName());

                    String urlText = user.getBiggerProfileImageURLHttps()  ;
//                    System.out.println(item + "  " +  urlText);
                    URL url = new URL(urlText);
                    BufferedImage img = ImageIO.read(url);
                    list.add(img);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        } while ((cursor = ids.getNextCursor()) != 0);


//        for(String item: setOfNames) {
//            User user = twitter.showUser(item);
//            String urlText = user.getBiggerProfileImageURLHttps()  ;
//            System.out.println(item + "  " +  urlText);
//
//            URL url = new URL(urlText);
//            BufferedImage img = ImageIO.read(url);
//            list.add(img);
//        }
        return list;
    }

    public static int[] getDimensions(int number) {
        int size = (int) Math.ceil( Math.sqrt(number)) ;

        int width = size;
        int length = size;
        int square = length * (width - 1);

        if(square >= number) {
            length--;
        }
        int[] dimension = new int[2];
        dimension[0] = width;
        dimension[1] = length;
        return dimension;
    }



    public static BufferedImage createCollage(ArrayList<BufferedImage> list) throws IOException {

        double size = list.size();
        int numberOfImages = (int) Math.ceil( Math.sqrt(size)) ;

        int widthOfPicture = list.get(0).getWidth();
        System.out.println("widthOfPicture" + widthOfPicture);

        int dimension[] = getDimensions(list.size());
        int width = dimension[0] * widthOfPicture;
        int length = dimension[1] * widthOfPicture;

        BufferedImage result = new BufferedImage(
                width, length, //work these out
                BufferedImage.TYPE_INT_RGB);
        Graphics g = result.getGraphics();

        int x = 0;
        int y = 0;



        for(BufferedImage image : list){

            System.out.println("pic pic");

            File outputfile = new File("pic.png");
            ImageIO.write(image, "png", outputfile);


            BufferedImage bi = image;
            g.drawImage(bi, x, y, null);
            x += widthOfPicture;
            if(x >= result.getWidth()){
                x = 0;
                y += bi.getHeight();
            }
        }
        File outputfile = new File("webapp/saved.png");
        ImageIO.write(result, "png", outputfile);
        return result;
    }



    public static Twitter getTwitterInstance() {
        String key = "MgRLR20IDo7N0NqzmDhPhR00L";
        String secret = "FjeKESD7Yb0iK064FGkBMpoxHQXvm8jjWdURTwEgDT2GwJVUWB";
        String token = "526655300-aC2DqwI0ztgeyahoFCuwoNKiDJfIoRl6DODLSZZl";
        String tokenSecret = "5NYxQXjYYtvViIxDn9uAjfuh5HbjuqGQ3ot52vhNdvcTN";


        OAuth1 auth = new OAuth1(key, secret, token, tokenSecret);

        final String consumerKey = key;
        final String consumerSecret = secret;
        final String accessToken = token;
        final String accessTokenSecret = tokenSecret;

        System.out.println("hello");

        //Instantiate a re-usable and thread-safe factory
        TwitterFactory twitterFactory = new TwitterFactory();

        //Instantiate a new Twitter instance
        Twitter twitter = twitterFactory.getInstance();

        //setup OAuth Consumer Credentials
        twitter.setOAuthConsumer(consumerKey, consumerSecret);

        //setup OAuth Access Token
        twitter.setOAuthAccessToken(new AccessToken(accessToken, accessTokenSecret));
        return twitter;
    }

}
