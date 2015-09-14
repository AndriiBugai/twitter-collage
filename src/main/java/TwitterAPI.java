import com.twitter.hbc.httpclient.auth.OAuth1;
import twitter4j.*;
import twitter4j.auth.AccessToken;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by strapper on 13.09.15.
 */
public class TwitterAPI {

    private Twitter twitter;

    TwitterAPI() {
        twitter = getTwitterInstance();
    }

    private Twitter getTwitterInstance() {
        final String consumerKey = "MgRLR20IDo7N0NqzmDhPhR00L";
        final String consumerSecret = "FjeKESD7Yb0iK064FGkBMpoxHQXvm8jjWdURTwEgDT2GwJVUWB";
        final String accessToken = "526655300-aC2DqwI0ztgeyahoFCuwoNKiDJfIoRl6DODLSZZl";
        final String accessTokenSecret = "5NYxQXjYYtvViIxDn9uAjfuh5HbjuqGQ3ot52vhNdvcTN";

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

    public ArrayList<TwitterUser> getFriends(String userLogin, int size) {
        System.out.println("Twitter API getFriends");
        ArrayList<TwitterUser> list = new ArrayList<TwitterUser>();
        int counter = 0;

        User u1 = null ;
        long cursor = -1;
        IDs ids;
        do {
            try {
                ids = twitter.getFriendsIDs(userLogin, cursor);
            } catch (TwitterException e) {
                return null;
            }
            for (long id : ids.getIDs()) {
                try{
                    System.out.println(id);

                    counter++;
                    System.out.println("count " + counter);
                    User user = twitter.showUser(id);
                    int tweetsNumber = user.getStatusesCount();

                    TwitterUser tUser = new TwitterUser();
                    tUser.setId(id);
                    tUser.setTweetsNumber(tweetsNumber);
                    tUser.setUser(user);

                    System.out.println(tUser);



                    list.add(tUser);

                    if(counter > size) {
                        System.out.println("counter" + counter);
                        return list;
                    }



                } catch (Exception e) {

                }
            }
        } while ((cursor = ids.getNextCursor()) != 0);

        System.out.println("counter" + counter);
        return list;
    }

    public ArrayList<TwitterUser> cutList( ArrayList<TwitterUser> list, int wantedSize) {
        int oldSize = list.size();

        if(oldSize <= wantedSize) {
            return list;
        }

        ArrayList<TwitterUser> newList = new ArrayList<TwitterUser>();
        for(int i = 0; i < wantedSize; i++) {
            TwitterUser item = list.get(i);
            newList.add(item);
        }
        return newList;
    }

    public ArrayList<TwitterUser> loadImages(ArrayList<TwitterUser> list) throws IOException {
        for(TwitterUser item: list) {
            User user = item.getUser();
            String urlText = user.getBiggerProfileImageURLHttps()  ;
            URL url = new URL(urlText);
            BufferedImage img = ImageIO.read(url);
            item.setImg(img);
        }
        return list;
    }

    public ArrayList<BufferedImage> getImages(ArrayList<TwitterUser> list) {
        Collections.sort(list);
        ArrayList<BufferedImage> listImage = new ArrayList<BufferedImage>();
        for(TwitterUser item: list) {
            listImage.add(item.getImg());
        }
        return listImage;
    }



    public ArrayList<BufferedImage> getFriendsAvatars(String userLogin) {
        ArrayList<BufferedImage> list = new ArrayList<BufferedImage>();
        int counter = 0;

        User u1 = null ;
        long cursor = -1;
        IDs ids;
        do {
            try {
                ids = twitter.getFriendsIDs(userLogin, cursor);
            } catch (TwitterException e) {
                return null;
            }
            for (long id : ids.getIDs()) {
                try{

                    System.out.println(id);
                    User user = twitter.showUser(id);
                    String urlText = user.getBiggerProfileImageURLHttps()  ;
                    URL url = new URL(urlText);
                    BufferedImage img = ImageIO.read(url);
                    list.add(img);

                } catch (Exception e) {

                }
            }
        } while ((cursor = ids.getNextCursor()) != 0);

        return list;
    }

}
