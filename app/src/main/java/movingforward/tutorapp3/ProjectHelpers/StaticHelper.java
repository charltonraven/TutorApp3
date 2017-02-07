package movingforward.tutorapp3.ProjectHelpers;

import android.util.Log;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.util.Enumeration;

import movingforward.tutorapp3.R;

/**
 * Created by Jeebus Prime on 2/7/2017.
 */

public class StaticHelper {

    public static String getDeviceIP()
    {
        URL myIP;
        try {
            myIP = new URL("http://api.externalip.net/ip/");

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(myIP.openStream())
            );
            return in.readLine();
        } catch (Exception e)
        {
            try
            {
                myIP = new URL("http://myip.dnsomatic.com/");

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(myIP.openStream())
                );
                return in.readLine();
            } catch (Exception e1)
            {
                try {
                    myIP = new URL("http://icanhazip.com/");

                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(myIP.openStream())
                    );
                    return in.readLine();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }

        return null;
    }
}
