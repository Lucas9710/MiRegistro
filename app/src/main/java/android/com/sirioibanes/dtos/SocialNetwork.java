package android.com.sirioibanes.dtos;

import java.util.HashMap;

public class SocialNetwork extends HashMap {
    public static final String FACEBOOK = "facebook";
    public static final String TWITTER = "twitter";
    public static final String INSTAGRAM = "instagram";

    public String getKey() {
        return (String) get("key");
    }

    public String getName() {
        return (String) ((HashMap) get("value")).get("name");
    }

    public String getLink() {
        return (String) ((HashMap) get("value")).get("link");
    }
}
