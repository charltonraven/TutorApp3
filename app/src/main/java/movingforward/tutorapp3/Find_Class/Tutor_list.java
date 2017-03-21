package movingforward.tutorapp3.Find_Class;

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

import movingforward.tutorapp3.Entities.ListItemHolder;
import movingforward.tutorapp3.Entities.User;
import movingforward.tutorapp3.Entities.class_Helper.HttpHandler;
import movingforward.tutorapp3.Entities.class_Helper.HttpHandler2;
import movingforward.tutorapp3.Entities.class_Helper.TutorListAdapter;
import movingforward.tutorapp3.ProjectHelpers.StaticHelper;
import movingforward.tutorapp3.R;
import movingforward.tutorapp3.TutChat.ChatActivity;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Tutor_list.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Tutor_list#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tutor_list extends Fragment implements AdapterView.OnItemClickListener{
    User mUser;
    User nUser;
    TextView tvClassName;
   TextView tvTutorName;
    ListView lv;
    InputStream content;
    TutorListAdapter adapter;
    ArrayList<ListItemHolder> TutorsAndClasses = new ArrayList<>();
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

    public Tutor_list() {
        // Required empty public constructor
    }

    public void setClassName(final String ClassName) {
        this.ClassName = ClassName;
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tutor_list.
     */
    // TODO: Rename and change types and number of parameters
    public static Tutor_list newInstance(String param1, String param2) {
        Tutor_list fragment = new Tutor_list();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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
        Bundle bundle=getArguments();
        mUser= (User) bundle.getSerializable("mUser");

        lv = (ListView) rootview.findViewById(R.id.lvTutorList);

        //get ClassName from BySubject
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(Tutor_list.this);


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

        String classname=tvClassName.getText().toString();
        String tutorname=tvTutorName.getText().toString();

        String [] classAbbrName=classname.split("(?<=\\D)(?=\\d)");
        String Abbr=classAbbrName[0];
        String ClassName=classAbbrName[1];


        String [] FirstLastName=tutorname.split(" ");
        String FirstName=FirstLastName[0];
        String LastName=FirstLastName[1];
        //String studentPermission="Student";


        try {
            new SaveHistoryTask().execute(Abbr,ClassName,FirstName,LastName,"Student").get();
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
            String Abbr=params[0];
            String ClassName=params[1];
            String tutorFirstName=params[2];
            String tutorLastName=params[3];
            //String role=mUser.getPermission().toString();
            String role=params[4];
            String studnetFirstName;
            String studentLastName;

            String result="";
            String [] FLW={tutorFirstName,tutorLastName,"student"};

            HttpHandler2 sh=new HttpHandler2();

            String getInformation = "http://" + StaticHelper.getDeviceIP() + "/android/getInfo/getinformation.php";
            result=sh.makeServiceCallPost(getInformation,FLW,null,null);
            String toID="";
            String Email="";

            if(result!=null){

                try {
                    JSONArray LoggedUser=new JSONArray(result);
                    for(int i=0;i<LoggedUser.length();i++){
                        JSONObject UserObj = LoggedUser.getJSONObject(i);
                        String StudentID=UserObj.getString("studentID");
                        String email=UserObj.getString("email");
                        String firstName=UserObj.getString("firstName");
                        String lastName=UserObj.getString("lastName");
                        String major=UserObj.getString("major");
                        String courses=UserObj.getString("courses");
                        int registered=UserObj.getInt("registered");
                        int tutor=UserObj.getInt("tutor");
                        //  String password=UserObj.getString("password");


                        nUser=new User(StudentID,email,firstName,lastName,major,courses,registered,tutor,null,null);

                        String Test="Test";
                    }
                    toID=nUser.getStudentID();
                    Email=nUser.getEmail();



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }




            String fromID=mUser.getStudentID();
            String [] saveInfo={role,nUser.getStudentID(),Email,nUser.getFirstName()+" "+nUser.getLastName(),Abbr+ClassName,mUser.getStudentID(),mUser.getFirstName()+" "+mUser.getLastName()};
            String SaveHistory_URL="http://" + StaticHelper.getDeviceIP() + "/android/inserts/InsertHistory.php";
           String results =  sh.makeServiceCallPost(SaveHistory_URL,null,saveInfo,null);//saves to Student and Tutor

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
            if(mUser.getTutor()==3 || nUser.getTutor()==3){
                chatIntent.putExtra("className","teacher");
            }else {
                chatIntent.putExtra("className", tvClassName.getText());

            }
            startActivity(chatIntent);
        }
    }

    private class ListTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            HttpHandler sh = new HttpHandler();

            String TutorList_url = "http://" + StaticHelper.getDeviceIP() + "/android/CreateListorGrids/TutorList.php";

            //Making a request to url and getting response
            String jsonStr = sh.makeServiceCallPost(TutorList_url,ClassName,null,null,null);



            if (jsonStr != null) {
                try {
                    //JSONObject jsonObject = new JSONObject(jsonStr);


                    //getting JSON Array node
                    JSONArray TutorList = new JSONArray(jsonStr);
                    //JSONArray TutorList2 = new JSONArray(new JSONObject(jsonStr));
                    for(int i=0;i<TutorList.length();i++){
                        JSONObject T = TutorList.getJSONObject(i);
                        String Class=T.getString("ClassTutor");
                        String FName=T.getString("firstName");
                        String LName=T.getString("lastName");
                        TutorsAndClasses.add(new ListItemHolder(Class,FName,LName));

                        String Test="Test";
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
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


            adapter = new TutorListAdapter(getActivity(), android.R.layout.simple_list_item_1, TutorsAndClasses);
            lv.setAdapter(adapter);
        }
    }



}
