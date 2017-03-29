package movingforward.tutorapp3.Fragments;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import movingforward.tutorapp3.Entities.User;
import movingforward.tutorapp3.Entities.class_Helper.HttpHandler;
import movingforward.tutorapp3.ProjectHelpers.StaticHelper;
import movingforward.tutorapp3.ProjectHelpers.firstLastName;
import movingforward.tutorapp3.ProjectHelpers.nameBtnListAdapter;
import movingforward.tutorapp3.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link User_list_Button.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link User_list_Button#newInstance} factory method to
 * create an instance of this fragment.
 */
public class User_list_Button extends Fragment implements AdapterView.OnItemClickListener{
    User mUser;
    User nUser;
    TextView tvFirstName;
   TextView tvLastName;
    CheckBox CheckTutor;
    ListView lv;
    InputStream content;
    nameBtnListAdapter adapter;
    ArrayList<firstLastName> firstLastNames = new ArrayList<>();

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

    public User_list_Button() {
        // Required empty public constructor
    }

    public void setClassName(final String ClassName) {
        this.ClassName = ClassName;
    }



    // TODO: Rename and change types and number of parameters
    public static User_list_Button newInstance(User mUser) {
        User_list_Button fragment = new User_list_Button();
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

        lv.setOnItemClickListener(User_list_Button.this);
        mUser=(User) getArguments().getSerializable("mUser");


        try {
            String blah = new ListTask().execute().get();
            String test=blah;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        CheckTutor= (CheckBox) rootview.findViewById(R.id.cb_Tutor);





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


        tvFirstName = (TextView) view.findViewById(R.id.tv_FirstName);
        tvLastName = (TextView) view.findViewById(R.id.tv_LastName);


        String FirstName=tvFirstName.getText().toString();
        String LastName=tvLastName.getText().toString();



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
                            boolean isTutor=false;
                            String FName = T.getString("firstName");
                            String LName = T.getString("lastName");
                            int tutor=T.getInt("tutor");

                            if(tutor==1){
                                isTutor=true;

                            }
                            firstLastNames.add(new firstLastName(FName, LName,isTutor));

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


            adapter = new nameBtnListAdapter(getActivity(), android.R.layout.simple_list_item_1, firstLastNames);
            lv.setAdapter(adapter);
        }
    }



}
