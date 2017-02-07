package movingforward.tutorapp3.Find_Class;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutionException;

import movingforward.tutorapp3.Entities.TutorList;
import movingforward.tutorapp3.Entities.class_Helper.HttpHandler;
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
public class Tutor_list extends Fragment {

    ListView lv;
    InputStream content;
    TutorListAdapter adapter;
    ArrayList<TutorList> TutorsAndClasses=new ArrayList<>();

    //String TutorList_url = "http://10.10.103.165/android/CreateListorGrids/TutorList.php";
    //String TutorList_url = "http://192.168.1.10/android/CreateListorGrids/TutorList.php";


    String line = null;
    String result;
    public String[] data;
    public String[] data2;
    String ClassName="";


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
        final View listview = inflater.inflate(R.layout.fragment_tutor_list, container, false);
        lv = (ListView) listview.findViewById(R.id.lvTutorList);

        //get ClassName from BySubject


        try {
            String blah=new TutorListTask().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        return listview;
    }

    private void getData(String ClassName) {


    }
    /*public void updateInfo(String ClassName){
        Toast.makeText(getActivity(),ClassName,Toast.LENGTH_SHORT);

    }*/

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

 /*   @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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

    public void StartTask() {
        try {
            new TutorListTask().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private class TutorListTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
                  HttpHandler sh = new HttpHandler();

            //String TutorList_url = "http://10.10.103.74/android/CreateListorGrids/TutorList.php";
            //String TutorList_url = "http://172.19.10.128/android/CreateListorGrids/TutorList.php";
            //String TutorList_url = "http://192.168.1.10/android/CreateListorGrids/TutorList.php";
            //String TutorList_url = "http://192.168.1.10/android/CreateListorGrids/TutorList.php";
            //  String TutorList_url = "http://192.168.1.10/android/CreateListorGrids/TutorList.php";


<<<<<<< HEAD
          //  String TutorList_url = "http://192.168.1.10/android/CreateListorGrids/TutorList.php";


            String TutorList_url = "http://172.19.10.48/android/CreateListorGrids/TutorList.php";
=======
            String TutorList_url = "http://" + StaticHelper.getDeviceIP() + "/android/CreateListorGrids/TutorList.php";
>>>>>>> origin/master

            //Making a request to url and getting response
            String jsonStr = sh.makeServiceCallPost(TutorList_url,ClassName,null,null,null);



            if (jsonStr != null) {
                try {
                    //JSONObject jsonObject = new JSONObject(jsonStr);


                    //getting JSON Array node
                    JSONArray TutorList = new JSONArray(jsonStr);
                    //JSONArray TutorList2 = new JSONArray(new JSONObject(jsonStr));
                    for(int i=0;i<TutorList.length();i++){
                        JSONObject T=TutorList.getJSONObject(i);
                        String Class=T.getString("ClassTutor");
                        String FName=T.getString("firstName");
                        String LName=T.getString("lastName");
                        TutorsAndClasses.add(new TutorList(Class,FName,LName));

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
            Toast.makeText(getActivity(),"Generating Tutors",Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onPostExecute(String s) {


            adapter = new TutorListAdapter(getActivity(), android.R.layout.simple_list_item_1,TutorsAndClasses);
            lv.setAdapter(adapter);
        }
    }
    //--------------------------------------------------------------------------------------------------------------
    public static class TutorListAdapter extends ArrayAdapter<Tutor_list>{


        ArrayList<TutorList> values;

        public TutorListAdapter(Context context, int resource, ArrayList<TutorList> values) {
            super(context,resource);
            this.values=values;
        }

        public TutorListAdapter(Context context, int resource) {
            super(context, resource);
        }

        @Override
        public void add(Tutor_list object) {
            super.add(object);
        }

        @Override
        public void addAll(Collection<? extends Tutor_list> collection) {
            super.addAll(collection);
        }

        @Override
        public int getCount() {
            return values.size();
        }



        @Override
        public int getPosition(Tutor_list item) {
            return super.getPosition(item);
        }

        @Override
        public long getItemId(int position) {
            return super.getItemId(position);
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v=convertView;
            TextView tvClassName;
            TextView tvTutorName;


            if(v==null){
                LayoutInflater vi;
                vi = LayoutInflater.from(getContext());
                v = vi.inflate(R.layout.txt_txt_template, null);
            }else{
                v=(View) convertView;
            }

            tvClassName=(TextView) v.findViewById(R.id.tv_Class_Name);
            tvTutorName=(TextView) v.findViewById(R.id.tv_TutorName);

            tvClassName.setText(values.get(position).ClassTutored);
            tvTutorName.setText(values.get(position).getfName()+" "+values.get(position).getlName());


            return v;
        }
    }
}
