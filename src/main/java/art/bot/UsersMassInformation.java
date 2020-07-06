package art.bot;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class UsersMassInformation {
    public String userId;
    public LinkedHashMap<Integer, String> userInfo;

    public UsersMassInformation(String userId) {
        this.userId = userId;
        this.userInfo = new LinkedHashMap<Integer, String>();
    }
}
