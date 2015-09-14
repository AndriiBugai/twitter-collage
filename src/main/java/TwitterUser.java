import twitter4j.User;

import java.awt.image.BufferedImage;

/**
 * Created by strapper on 13.09.15.
 */
public class TwitterUser implements Comparable<TwitterUser> {

    private BufferedImage img;
    private long id;
    private int tweetsNumber;
    private User user;

    TwitterUser() {

    }
    @Override
    public String toString() {
        return "TwitterUser{" +
                "tweetsNumber=" + tweetsNumber +
                ", id='" + id + '\'' +
                '}';
    }

    @Override
    public int compareTo(TwitterUser o) {
        if(this.tweetsNumber > o.tweetsNumber) {
            return -1;
        } else if(this.tweetsNumber < o.tweetsNumber) {
            return 1;
        }
        return 0;
    }

    public BufferedImage getImg() {
        return img;
    }

    public void setImg(BufferedImage img) {
        this.img = img;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getTweetsNumber() {
        return tweetsNumber;
    }

    public void setTweetsNumber(int tweetsNumber) {
        this.tweetsNumber = tweetsNumber;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
