package movingforward.tutorapp3.ProjectHelpers;

import movingforward.tutorapp3.BuildConfig;

/**
 * Created by Jeebus Prime on 2/7/2017.
 */

public class StaticHelper {

    public static String getDeviceIP() {
        String test = BuildConfig.URL_SERVER;
       /* String[] split = test.split(",");
        int n = split[0].length() - 1;*/
        String ip = test.replace("/","");

        return ip;
    }
}