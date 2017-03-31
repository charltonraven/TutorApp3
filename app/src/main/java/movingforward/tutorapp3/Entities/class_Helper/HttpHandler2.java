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

public class HttpHandler2 {

    private static final String TAG = HttpHandler.class.getSimpleName();

    public HttpHandler2() {

    }



    public String makeServiceCallPost(String reqUrl,String [] FL,String [] saveInfo,String [] getInformation,String [] TeacherTutor) {
        String response = null;

        //Runs the Tutor_List to generate Tutors

        if(FL != null) {
            try {
                URL url = new URL(reqUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setDoInput(true);

                OutputStream out = conn.getOutputStream();

                response = PutInVariables(out, conn, FL,null,null,null);
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
        if(saveInfo!=null){
            try{
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            OutputStream out = conn.getOutputStream();
            response = PutInVariables(out, conn, null,saveInfo,null,null);
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
        if(getInformation!=null){
            try{
                URL url = new URL(reqUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setDoInput(true);

                OutputStream out = conn.getOutputStream();
                response = PutInVariables(out, conn, null,null,getInformation,null);
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
        if(TeacherTutor!=null){
            try{
                URL url = new URL(reqUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setDoInput(true);

                OutputStream out = conn.getOutputStream();
                response = PutInVariables(out, conn, null,null,null,TeacherTutor);
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


    private String PutInVariables(OutputStream out,HttpURLConnection conn,String [] FL,String [] saveInfo,String [] getinformation,String [] TeacherTutor) {
        String response = "";

        if(FL!=null) {
            try {
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
                String post_data = URLEncoder.encode("who", "UTF-8") + "=" + URLEncoder.encode(FL[2], "UTF-8") + "&" + URLEncoder.encode("firstName", "UTF-8") + "=" + URLEncoder.encode(FL[0], "UTF-8")+ "&" + URLEncoder.encode("lastName", "UTF-8") + "=" + URLEncoder.encode(FL[1], "UTF-8");
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

        if(saveInfo!=null){
            try {
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
                String post_data = URLEncoder.encode("who", "UTF-8") + "=" + URLEncoder.encode(saveInfo[0], "UTF-8") + "&" +URLEncoder.encode("toID", "UTF-8") + "=" + URLEncoder.encode(saveInfo[1], "UTF-8") + "&" + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(saveInfo[2].trim(), "UTF-8") + "&" + URLEncoder.encode("tutorName", "UTF-8") + "=" + URLEncoder.encode(saveInfo[3], "UTF-8")+ "&" + URLEncoder.encode("classtutorName", "UTF-8") + "=" + URLEncoder.encode(saveInfo[4], "UTF-8")+ "&" + URLEncoder.encode("fromID", "UTF-8") + "=" + URLEncoder.encode(saveInfo[5], "UTF-8")+ "&" + URLEncoder.encode("studentName", "UTF-8") + "=" + URLEncoder.encode(saveInfo[6], "UTF-8");
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


        if(getinformation!=null){
            try {
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
                String post_data = URLEncoder.encode("who", "UTF-8") + "=" + URLEncoder.encode(getinformation[2], "UTF-8") + "&" +URLEncoder.encode("firstName", "UTF-8") + "=" + URLEncoder.encode(getinformation[0], "UTF-8") + "&" + URLEncoder.encode("lastName", "UTF-8") + "=" + URLEncoder.encode(getinformation[1].trim(), "UTF-8");
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
        if(TeacherTutor!=null){
            try {
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
                String post_data = URLEncoder.encode("who", "UTF-8") + "=" + URLEncoder.encode(TeacherTutor[0], "UTF-8") + "&" +URLEncoder.encode("toID", "UTF-8") + "=" + URLEncoder.encode(TeacherTutor[1], "UTF-8") + "&" + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(TeacherTutor[2].trim(), "UTF-8") + "&" + URLEncoder.encode("tutorName", "UTF-8") + "=" + URLEncoder.encode(TeacherTutor[3], "UTF-8")+ "&" + URLEncoder.encode("classtutorName", "UTF-8") + "=" + URLEncoder.encode(TeacherTutor[4], "UTF-8")+ "&" + URLEncoder.encode("fromID", "UTF-8") + "=" + URLEncoder.encode(TeacherTutor[4], "UTF-8")+ "&" + URLEncoder.encode("studentName", "UTF-8") + "=" + URLEncoder.encode(TeacherTutor[5], "UTF-8");
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