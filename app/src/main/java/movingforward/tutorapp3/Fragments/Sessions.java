package movingforward.tutorapp3.Fragments;

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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import movingforward.tutorapp3.Activities.ChatActivity;
import movingforward.tutorapp3.Entities.ListItemHolder;
import movingforward.tutorapp3.Entities.Role;
import movingforward.tutorapp3.Entities.User;
import movingforward.tutorapp3.Entities.class_Helper.HttpHandler2;
import movingforward.tutorapp3.Entities.class_Helper.HttpListHandler;
import movingforward.tutorapp3.Entities.class_Helper.SessionListAdapter;
import movingforward.tutorapp3.ProjectHelpers.StaticHelper;
import movingforward.tutorapp3.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Sessions.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Sessions#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Sessions extends Fragment implements AdapterView.OnItemClickListener
{

    TextView tv_names;
    TextView header;
    TextView type;
    ListView lv;
    SessionListAdapter adapter;
    ArrayAdapter<String> sadapter;
    ArrayList<ListItemHolder> TutorsAndClasses = new ArrayList<>();
    ArrayList<String> TutorsAndClassesStrings = new ArrayList<>();
    User mUser;//Logged In user
    User nUser;
    ArrayList<ListItemHolder> firstLastNames = new ArrayList<>();
    ArrayList<String> firstLastNamesStrings = new ArrayList<>();

    String who = "";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Sessions()
    {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static Sessions newInstance(User user, String who, String header)
    {
        Sessions fragment = new Sessions();
        Bundle args = new Bundle();
        args.putSerializable("mUser", user);
        args.putString("who", who);
        args.putString("header", header);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);


        if (getArguments() != null)
        {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        final View rootview = inflater.inflate(R.layout.fragment_sessions, container, false);
        Bundle bundle = getArguments();

        mUser = (User) bundle.getSerializable("mUser");
        who = bundle.getString("who");
        String headerString = bundle.getString("header");
        lv = (ListView) rootview.findViewById(R.id.lvSessionList);
        header = (TextView) rootview.findViewById(R.id.session_name);
        type = (TextView) rootview.findViewById(R.id.tv_tutor);

        header.setText(headerString);

        if (headerString.contains("Students"))
        {
            type.setText("Students:");
        }
        else if (headerString.contains("Teachers"))
        {
            type.setText("Teachers:");
        }

        //get ClassName from BySubject
        lv.setAdapter(sadapter);

        lv.setOnItemClickListener(Sessions.this);

        try
        {
            new SessionItemList().execute().get();
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        } catch (ExecutionException e)
        {
            e.printStackTrace();
        }

        return rootview;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri)
    {
        if (mListener != null)
        {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener)
        {
            mListener = (OnFragmentInteractionListener) context;
        }
        else
        {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {

        tv_names = (TextView) view.findViewById(R.id.class_and_name);
        String LastName = "";
        String FirstName = "";

        if (who.equals("TutorTeachers") || who.equals("TeacherTutors"))
        {
            String[] names = tv_names.toString().split(" ");
            FirstName = names[0];
            LastName = names[names.length - 1];
        }
        else
        {
            String tutorname = tv_names.getText().toString();
            String[] FirstLastName = tutorname.split(" ");
            FirstName = FirstLastName[FirstLastName.length - 2];
            LastName = FirstLastName[FirstLastName.length - 1];
        }
        try
        {
            new getInformation().execute(FirstName, LastName, who).get();

        } catch (InterruptedException e)
        {
            e.printStackTrace();
        } catch (ExecutionException e)
        {
            e.printStackTrace();
        }
    }

    public interface OnFragmentInteractionListener
    {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    private class SessionItemList extends AsyncTask<String, String, String>
    {

        @Override
        protected String doInBackground(String... params)
        {
            HttpListHandler sh = new HttpListHandler();
            String jsonStr = "";

            if (who.equals("student"))
            {
                String TutorList_url = "http://" + StaticHelper.getDeviceIP() + "/android/CreateListorGrids/generateList.php";


                //Making a request to url and getting response
                jsonStr  = sh.makeServiceCallPost(TutorList_url, mUser.getID(), who,null,null);
            }
            if (who.equals("tutor"))
            {
                String TutorList_url = "http://" + StaticHelper.getDeviceIP() + "/android/CreateListorGrids/generateList.php";


                //Making a request to url and getting response
                jsonStr  = sh.makeServiceCallPost(TutorList_url, mUser.getID(), who,null,null);
            }
            if (who.equals("TutorTeachers"))
            {
                String TutorList_url = "http://" + StaticHelper.getDeviceIP() + "/android/CreateListorGrids/generateList.php";


                //Making a request to url and getting response
                jsonStr  = sh.makeServiceCallPost(TutorList_url, mUser.getID(), who,null,null);
            }
            if (who.equals("TeacherTutors"))
            {
                String TutorList_url = "http://" + StaticHelper.getDeviceIP() + "/android/CreateListorGrids/generateList.php";


                //Making a request to url and getting response
                jsonStr  = sh.makeServiceCallPost(TutorList_url, mUser.getID(), who,null,null);
            }

            if (jsonStr != null)
            {
                try
                {
                    //JSONObject jsonObject = new JSONObject(jsonStr);

                    String Name = "";
                    String Class = "";
                    String first = "";
                    String last = "";


                    //getting JSON Array node
                    JSONArray TutorList = new JSONArray(jsonStr);
                    //JSONArray TutorList2 = new JSONArray(new JSONObject(jsonStr));
                    for (int i = 0; i < TutorList.length(); i++)
                    {
                        JSONObject T = TutorList.getJSONObject(i);

                        if (T.isNull("classname"))
                        {
                            first = T.getString("name").split(" ")[0];
                            last = T.getString("name").split(" ")[1];
                            //firstLastNames.add(new ListItemHolder(first, last));
                            firstLastNamesStrings.add(first + "                 " + last);
                        }
                        else
                        {
                            Class = T.getString("classname");
                            Name = T.getString("name");

                            if (Class.length() == 5)
                                Class += "    ";
                            else if (Class.length() == 6)
                                Class += "  ";

                            //TutorsAndClasses.add(new ListItemHolder(Class, Name));
                            TutorsAndClassesStrings.add(Class + "                 " +Name);
                        }
                    }

                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            //Toast.makeText(getActivity(), "Generating Tutors", Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onPostExecute(String s)
        {
            /*if (!TutorsAndClasses.isEmpty())
            {
                adapter = new SessionListAdapter(getActivity(), R.layout.mylist, TutorsAndClasses);
                lv.setAdapter(adapter);
            }
            else
            {
                adapter = new SessionListAdapter(getActivity(), R.layout.mylist, firstLastNames);
                lv.setAdapter(adapter);
            }*/
            if (!TutorsAndClassesStrings.isEmpty())
            {

                sadapter = new ArrayAdapter<String> (getActivity(), R.layout.mylist, TutorsAndClassesStrings);
                lv.setAdapter(sadapter);
            }
            else
            {
                sadapter = new ArrayAdapter<String> (getActivity(), R.layout.mylist, firstLastNamesStrings);
                lv.setAdapter(sadapter);
            }
        }
    }

    private class getInformation extends AsyncTask<String, String, String>
    {

        @Override
        protected String doInBackground(String... params)
        {
            String who = params[2];
            String firstName = params[0];
            String lastName = params[1];
            String[] info = {firstName, lastName, who};
            String jsonStr = "";
            HttpHandler2 sh = new HttpHandler2();

            String getInfo_URL = "http://" + StaticHelper.getDeviceIP() + "/android/inserts/getInfo.php";

            //Making a request to url and getting response
            jsonStr = sh.makeServiceCallPost(getInfo_URL, null, null, info, null);

            return jsonStr;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String responseJson)
        {
            nUser = null;

            String ID = "";

            String major = "";
            String email = "";
            String firstName = "";
            String lastName = "";
            String courses = "";
            Role role = null;
            int registered = 0;
            int tutor = 0;

            if (responseJson != null)
            {

                try
                {
                    JSONArray LoggedUser = new JSONArray(responseJson);
                    for (int i = 0; i < LoggedUser.length(); i++)
                    {
                        JSONObject UserObj = LoggedUser.getJSONObject(i);

                        if (UserObj.isNull("studentID"))
                        {
                            ID = UserObj.getString("teacherID");
                            major = UserObj.getString("department");
                            role = Role.Teacher;
                        }
                        else
                        {
                            ID = UserObj.getString("studentID");
                            major = UserObj.getString("major");
                            courses = UserObj.getString("courses");
                            role = Role.Student;
                            tutor = UserObj.getInt("tutor");
                        }
                        email = UserObj.getString("email");
                        firstName = UserObj.getString("firstName");
                        lastName = UserObj.getString("lastName");
                        // registered=UserObj.getInt("registered");
                        String password = UserObj.getString("password");

                        nUser = new User(ID, email, firstName, lastName, major, courses, registered, tutor, password, role);
                    }

                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
            Intent chatIntent = new Intent(getActivity(), ChatActivity.class);
            chatIntent.putExtra("mUser", mUser);
            chatIntent.putExtra("nUser", nUser);
            chatIntent.putExtra("className", tv_names.getText().toString().split(" ")[0]);
            startActivity(chatIntent);
        }
    }
}
