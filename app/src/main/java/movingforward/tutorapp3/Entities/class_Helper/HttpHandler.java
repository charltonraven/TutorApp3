package movingforward.tutorapp3.Entities.class_Helper;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

public class HttpHandler {

    private static final String TAG = HttpHandler.class.getSimpleName();

    public HttpHandler() {

    }

    public String makeServiceCallPost(String reqUrl,String ClassName, String Username, String Password,String [] registerInfo) {
        String response = null;

        //Runs the Tutor_List to generate Tutors
        if(ClassName!=null &&Username==null && Password==null) {
            try {
                URL url = new URL(reqUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setDoInput(true);

                OutputStream out = conn.getOutputStream();

                response = PutInVariables(out, conn, ClassName,null,null,null);
            } catch (MalformedURLException e) {
                Log.e(TAG, "MalformedURLException: " + e.getMessage());
            } catch (ProtocolException e) {
                Log.e(TAG, "ProtocolException: " + e.getMessage());
            } catch (IOException e) {
                Log.e(TAG, "IOException: " + e.getMessage());
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
            return response;
        }
        //Runs the Login
        else if(Username!=null && Password!=null){

            try {
                URL url = new URL(reqUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setDoInput(true);
                OutputStream out = conn.getOutputStream();
                response = PutInVariables(out, conn, ClassName,Username,Password,null);
            } catch (MalformedURLException e) {
                Log.e(TAG, "MalformedURLException: " + e.getMessage());
            } catch (ProtocolException e) {
                Log.e(TAG, "ProtocolException: " + e.getMessage());
            } catch (IOException e) {
                Log.e(TAG, "IOException: " + e.getMessage());
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }



        }
        //Register new users to application
        else if(registerInfo!= null){

            /*(id, firstName,LastName,Email,major)*/

            try {
                URL url = new URL(reqUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setDoInput(true);
                OutputStream out = conn.getOutputStream();
                response = PutInVariables(out, conn, null,null,null,registerInfo);
            } catch (MalformedURLException e) {
                Log.e(TAG, "MalformedURLException: " + e.getMessage());
            } catch (ProtocolException e) {
                Log.e(TAG, "ProtocolException: " + e.getMessage());
            } catch (IOException e) {
                Log.e(TAG, "IOException: " + e.getMessage());
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }

        }
        return response;
    }
    public String makeServiceCallGet(String reqUrl) {
        String response = null;
        try {
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            // read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());
            response = convertStreamToString(in);
        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
        return response;
    }


    private String PutInVariables(OutputStream out,HttpURLConnection conn,String department,String Username, String Password,String [] RegisterInfo) {
        String response = "";
        if (Username == null && Password == null && department != null){
            try {
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
                String post_data = URLEncoder.encode("department", "UTF-8") + "=" + URLEncoder.encode(department, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                out.close();
                InputStream in = conn.getInputStream();
                response = convertStreamToString(in);
            } catch (Exception e) {
                e.printStackTrace();
            }

        return response;
    }

    else if(Username!=null && Password!=null &&department==null){
            try {
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
                String post_data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(Username, "UTF-8") + "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(Password, "UTF-8");;
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                InputStream in = conn.getInputStream();
                response = convertStreamToString(in);
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
        else if (RegisterInfo!=null){
            try {
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
                String post_data = URLEncoder.encode("who", "UTF-8") + "=" + URLEncoder.encode(RegisterInfo[0], "UTF-8") + "&"
                        + URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(RegisterInfo[1], "UTF-8") + "&"
                        + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(RegisterInfo[2], "UTF-8") + "&"
                        + URLEncoder.encode("firstname", "UTF-8") + "=" + URLEncoder.encode(RegisterInfo[3], "UTF-8") + "&"
                        + URLEncoder.encode("lastname", "UTF-8") + "=" + URLEncoder.encode(RegisterInfo[4], "UTF-8") + "&"
                        + URLEncoder.encode("major", "UTF-8") + "=" + URLEncoder.encode(RegisterInfo[5], "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                InputStream in = conn.getInputStream();
                response = convertStreamToString(in);
            } catch (Exception e) {
                e.printStackTrace();
                response=e.toString();
            }
        }
        return response;


    }
    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }
}