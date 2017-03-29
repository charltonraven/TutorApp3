package movingforward.tutorapp3.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import movingforward.tutorapp3.Entities.User;
import movingforward.tutorapp3.Entities.class_Helper.HttpHandler;
import movingforward.tutorapp3.Entities.class_Helper.HttpHandler2;
import movingforward.tutorapp3.ProjectHelpers.StaticHelper;
import movingforward.tutorapp3.R;
import movingforward.tutorapp3.Activities.ChatActivity;
import movingforward.tutorapp3.ProjectHelpers.firstLastName;
import movingforward.tutorapp3.ProjectHelpers.nameListAdapter;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link User_list.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link User_list#newInstance} factory method to
 * create an instance of this fragment.
 */
public class User_list extends Fragment implements AdapterView.OnItemClickListener{
    User mUser;
    User nUser;
    TextView tvClassName;
   TextView tvTutorName;
    ListView lv;
    InputStream content;
    nameListAdapter adapter;
    ArrayList<firstLastName> firstLastNames = new ArrayList<>();
    private SaveHistoryTask saveHistoryTask=null;

    String line = null;
    String result;
    public String[] data;
    public String[] data2;
    String ClassName = "";
    private static final String TAG = "Tutor_list";


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public User_list() {
        // Required empty public constructor
    }

    public void setClassName(final String ClassName) {
        this.ClassName = ClassName;
    }



    // TODO: Rename and change types and number of parameters
    public static User_list newInstance(User mUser) {
        User_list fragment = new User_list();
        Bundle args = new Bundle();
        args.putSerializable("mUser", mUser);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootview = inflater.inflate(R.layout.fragment_tutor_list, container, false);

        lv = (ListView) rootview.findViewById(R.id.lvTutorList);

        //get ClassName from BySubject
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(User_list.this);
        mUser=(User) getArguments().getSerializable("mUser");


        try {
            String blah = new ListTask().execute().get();
            String test=blah;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }



        return rootview;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        tvClassName = (TextView) view.findViewById(R.id.tv_Class_Name);
        tvTutorName = (TextView) view.findViewById(R.id.tv_TutorName);


        String FirstName=tvClassName.getText().toString();
        String LastName=tvTutorName.getText().toString();



        try {
            new SaveHistoryTask().execute(FirstName,LastName).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void StartTask() {
        try {
            new ListTask().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private class SaveHistoryTask extends  AsyncTask<String,String,String>{


        public SaveHistoryTask() {
            super();
        }

        @Override
        protected String doInBackground(String... params) {
            String tutorFirstName=params[0];
            String tutorLastName=params[1];




            String result="";
            String [] FLW={tutorFirstName,tutorLastName,"teacher"};

            HttpHandler2 sh=new HttpHandler2();

            String getInformation = "http://" + StaticHelper.getDeviceIP() + "/android/getInfo/getinformation.php";
            result=sh.makeServiceCallPost(getInformation,FLW,null,null,null);
            String toID="";
            String Email="";

            if(result!=null){

                try {
                    JSONArray LoggedUser=new JSONArray(result);
                    for(int i=0;i<LoggedUser.length();i++){
                        JSONObject UserObj = LoggedUser.getJSONObject(i);
                        String StudentID=UserObj.getString("teacherID");
                        String email=UserObj.getString("email");
                        String firstName=UserObj.getString("firstName");
                        String lastName=UserObj.getString("lastName");
                        String major=UserObj.getString("department");
                        int registered=UserObj.getInt("registered");
                        //  String password=UserObj.getString("password");


                        nUser=new User(StudentID,email,firstName,lastName,major,null,registered,3,null,null);

                        String Test="Test";
                    }
                    toID=nUser.getID();
                    Email=nUser.getEmail();



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }




            String fromID=mUser.getID();
            String [] saveInfo={"Tutor2Teacher",nUser.getID(),Email,nUser.getFirstName()+" "+nUser.getLastName(),mUser.getID(),mUser.getFirstName()+" "+mUser.getLastName()};
            String SaveHistory_URL="http://" + StaticHelper.getDeviceIP() + "/android/inserts/InsertHistory.php";
           String results =  sh.makeServiceCallPost(SaveHistory_URL,null,null,null,saveInfo);

            System.out.println("");
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            Intent chatIntent = new Intent(getActivity(), ChatActivity.class);
            chatIntent.putExtra("mUser", mUser);
            chatIntent.putExtra("nUser",nUser);
            chatIntent.putExtra("className",tvClassName.getText());
            startActivity(chatIntent);
        }
    }

    private class ListTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            HttpHandler sh = new HttpHandler();

            if (mUser.getPermission().name().equals("Tutor")) {
                String TutorList_url = "http://" + StaticHelper.getDeviceIP() + "/android/CreateListorGrids/generateTeacherlist.php";

                //Making a request to url and getting response
                String jsonStr = sh.makeServiceCallGet(TutorList_url);


                if (jsonStr != null) {
                    try {
                        //JSONObject jsonObject = new JSONObject(jsonStr);


                        //getting JSON Array node
                        JSONArray TutorList = new JSONArray(jsonStr);
                        //JSONArray TutorList2 = new JSONArray(new JSONObject(jsonStr));
                        for (int i = 0; i < TutorList.length(); i++) {
                            JSONObject T = TutorList.getJSONObject(i);
                            String FName = T.getString("firstName");
                            String LName = T.getString("lastName");
                            firstLastNames.add(new firstLastName(FName, LName));

                            String Test = "Test";
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }
            if (mUser.getPermission().name().equals("Teacher")) {
                String TutorList_url = "http://" + StaticHelper.getDeviceIP() + "/android/CreateListorGrids/generateStudentList.php";

                //Making a request to url and getting response
                String jsonStr = sh.makeServiceCallGet(TutorList_url);


                if (jsonStr != null) {
                    try {
                        //JSONObject jsonObject = new JSONObject(jsonStr);


                        //getting JSON Array node
                        JSONArray TutorList = new JSONArray(jsonStr);
                        //JSONArray TutorList2 = new JSONArray(new JSONObject(jsonStr));
                        for (int i = 0; i < TutorList.length(); i++) {
                            JSONObject T = TutorList.getJSONObject(i);
                            String FName = T.getString("firstName");
                            String LName = T.getString("lastName");
                            firstLastNames.add(new firstLastName(FName, LName));

                            String Test = "Test";
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }
            return null;
        }




        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Toast.makeText(getActivity(), "Generating Tutors", Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onPostExecute(String s) {


            adapter = new nameListAdapter(getActivity(), android.R.layout.simple_list_item_1, firstLastNames);
            lv.setAdapter(adapter);
        }
    }



}
