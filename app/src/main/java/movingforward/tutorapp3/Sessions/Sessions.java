package movingforward.tutorapp3.Sessions;

import android.content.Context;
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

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import movingforward.tutorapp3.Entities.ListItemHolder;
import movingforward.tutorapp3.Entities.User;
import movingforward.tutorapp3.Entities.class_Helper.HttpHandler2;
import movingforward.tutorapp3.Entities.class_Helper.HttpListHandler;
import movingforward.tutorapp3.Entities.class_Helper.SessionListAdapter;
import movingforward.tutorapp3.ProjectHelpers.StaticHelper;
import movingforward.tutorapp3.R;
import movingforward.tutorapp3.TutChat.ChatActivity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Sessions.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Sessions#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Sessions extends Fragment implements AdapterView.OnItemClickListener {

    TextView tvClassName;
    TextView tvTutorName;
    ListView lv;
    SessionListAdapter adapter;
    ArrayList<ListItemHolder> TutorsAndClasses=new ArrayList<>();
    User mUser;//Logged In user
    User nUser;

    String who="";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Sessions() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static Sessions newInstance(User user, String who) {
        Sessions fragment = new Sessions();
        Bundle args = new Bundle();
        args.putSerializable("mUser",user);
        args.putString("who",who);
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


        final View rootview = inflater.inflate(R.layout.fragment_sessions, container, false);
        Bundle bundle=getArguments();

        mUser= (User) bundle.getSerializable("mUser");
        who=bundle.getString("who");

        lv = (ListView) rootview.findViewById(R.id.lvSessionList);

        //get ClassName from BySubject
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(Sessions.this);

        try {
            new SessionItemList().execute().get();
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
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
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

        String [] FirstLastName=tutorname.split(" ");
        String FirstName=FirstLastName[0];
        String LastName=FirstLastName[1];

        try {
            new getInformation().execute(FirstName,LastName,mUser.getPermission().toString()).get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }





    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    private class SessionItemList extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            HttpListHandler sh = new HttpListHandler();
            String jsonStr="";

            if(who=="student") {
                String TutorList_url = "http://" + StaticHelper.getDeviceIP() + "/android/CreateListorGrids/generateList.php";
                String userID = mUser.getEmail().split("\\@")[0];

                //Making a request to url and getting response
                jsonStr  = sh.makeServiceCallPost(TutorList_url, userID, who);
            }
            if(who=="tutor") {
                String TutorList_url = "http://" + StaticHelper.getDeviceIP() + "/android/CreateListorGrids/generateList.php";
                String userID = mUser.getEmail().split("\\@")[0];

                //Making a request to url and getting response
                jsonStr  = sh.makeServiceCallPost(TutorList_url, userID, who);
            }
            if(who=="teacher") {
                String TutorList_url = "http://" + StaticHelper.getDeviceIP() + "/android/CreateListorGrids/generateList.php";
                String userID = mUser.getEmail().split("\\@")[0];

                //Making a request to url and getting response
                jsonStr  = sh.makeServiceCallPost(TutorList_url, userID, who);
            }

            if (jsonStr != null) {
                try {
                    //JSONObject jsonObject = new JSONObject(jsonStr);


                    //getting JSON Array node
                    JSONArray TutorList = new JSONArray(jsonStr);
                    //JSONArray TutorList2 = new JSONArray(new JSONObject(jsonStr));
                    for (int i = 0; i < TutorList.length(); i++) {
                        JSONObject T = TutorList.getJSONObject(i);
                        String Class = T.getString("classname");
                        String Name = T.getString("name");

                        TutorsAndClasses.add(new ListItemHolder(Class, Name));

                        String Test = "Test";
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


            adapter = new SessionListAdapter(getActivity(), android.R.layout.simple_list_item_1, TutorsAndClasses);
            lv.setAdapter(adapter);
        }
    }
    private class getInformation extends  AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... params) {


            String who=params[2];
            String firstName=params[0];
            String lastName = params[1];
            String [] info= {who,firstName,lastName};
            String jsonStr="";
            HttpHandler2 sh=new HttpHandler2();


                String getInfo_URL = "http://" + StaticHelper.getDeviceIP() + "/android/inserts/getInfo.php";

                //Making a request to url and getting response
                jsonStr  = sh.makeServiceCallPost(getInfo_URL, null,null,info);




            return jsonStr;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String responseJson) {
            nUser=null;


            if(responseJson!=null){

                try {
                    JSONArray LoggedUser=new JSONArray(responseJson);
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

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            Intent chatIntent = new Intent(getActivity(), ChatActivity.class);
            chatIntent.putExtra("mUser", mUser);
            chatIntent.putExtra("nUser",nUser);
            startActivity(chatIntent);

        }
    }

}
