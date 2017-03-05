package movingforward.tutorapp3.Find_Class;

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
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import movingforward.tutorapp3.Entities.TutorList;
import movingforward.tutorapp3.Entities.User;
import movingforward.tutorapp3.Entities.class_Helper.HttpHandler;
import movingforward.tutorapp3.Entities.class_Helper.HttpHandler2;
import movingforward.tutorapp3.Entities.class_Helper.TutorListAdapter;
import movingforward.tutorapp3.ProjectHelpers.StaticHelper;
import movingforward.tutorapp3.R;


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
    TextView tvClassName;
   TextView tvTutorName;
    ListView lv;
    InputStream content;
    TutorListAdapter adapter;
    ArrayList<TutorList> TutorsAndClasses = new ArrayList<>();
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
            String blah = new TutorListTask().execute().get();
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


        try {
            new SaveHistoryTask().execute(Abbr,ClassName,FirstName,LastName).get();
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
            new TutorListTask().execute().get();
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
            String FirstName=params[2];
            String LastName=params[3];
            String role=mUser.getPermission().toString();

            String Email="";
            String [] ACFL={Abbr,ClassName,FirstName,LastName};

            HttpHandler2 sh=new HttpHandler2();

            String getEmail = "http://" + StaticHelper.getDeviceIP() + "/android/getInfo/getEmailAddress.php";
             Email=sh.makeServiceCallPost(getEmail,ACFL,null);
            String toID=Email.split("\\@")[0];
            String fromID=mUser.getEmail().split("\\@")[0];




            String [] saveInfo={role,toID,Email,FirstName+" "+LastName,Abbr+ClassName,fromID};
            String SaveHistory_URL="http://" + StaticHelper.getDeviceIP() + "/android/inserts/InsertHistory.php";
           String results =  sh.makeServiceCallPost(SaveHistory_URL,null,saveInfo);

            System.out.println("");
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

    private class TutorListTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            HttpHandler sh = new HttpHandler();

            String TutorList_url = "http://" + StaticHelper.getDeviceIP() + "/android/CreateListorGrids/TutorList.php";

            //Making a request to url and getting response
            String jsonStr = sh.makeServiceCallPost(TutorList_url, ClassName, null, null, null);


            if (jsonStr != null) {
                try {
                    //JSONObject jsonObject = new JSONObject(jsonStr);


                    //getting JSON Array node
                    JSONArray TutorList = new JSONArray(jsonStr);
                    //JSONArray TutorList2 = new JSONArray(new JSONObject(jsonStr));
                    for (int i = 0; i < TutorList.length(); i++) {
                        JSONObject T = TutorList.getJSONObject(i);
                        String Class = T.getString("ClassTutor");
                        String FName = T.getString("firstName");
                        String LName = T.getString("lastName");
                        TutorsAndClasses.add(new TutorList(Class, FName, LName));

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


            adapter = new TutorListAdapter(getActivity(), android.R.layout.simple_list_item_1, TutorsAndClasses);
            lv.setAdapter(adapter);
        }
    }


}
