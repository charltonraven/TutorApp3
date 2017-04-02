package movingforward.tutorapp3.ProjectHelpers;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;

import movingforward.tutorapp3.Entities.class_Helper.HttpCheckBoxHandler;
import movingforward.tutorapp3.Fragments.User_list_Button;
import movingforward.tutorapp3.R;

/**
 * Created by raven on 3/2/2017.
 */

public class nameBtnListAdapter extends ArrayAdapter<User_list_Button> implements AdapterView.OnItemClickListener {



    ArrayList<firstLastName> values;
    TextView tvFirstName;
    TextView tvLastName;

    public nameBtnListAdapter(Context context, int resource, ArrayList<firstLastName> values) {
        super(context, resource);
        this.values = values;
    }

    public nameBtnListAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public void add(User_list_Button object) {
        super.add(object);
    }

    @Override
    public void addAll(Collection<? extends User_list_Button> collection) {
        super.addAll(collection);
    }

    @Override
    public int getCount() {
        return values.size();
    }


    @Override
    public int getPosition(User_list_Button item) {
        return super.getPosition(item);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;


        CheckBox cbTutor;


        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.txt_txt__btn_template, null);
        } else {
            v = (View) convertView;
        }


        tvFirstName = (TextView) v.findViewById(R.id.tv_FirstName);
        tvLastName = (TextView) v.findViewById(R.id.tv_LastName);
        cbTutor=(CheckBox) v.findViewById(R.id.cb_Tutor);

        tvFirstName.setText(values.get(position).getfName());
        tvLastName.setText(values.get(position).getlName());
        if(values.get(position).isTutor()==true){
            cbTutor.setChecked(true);
        }



        CompoundButton.OnCheckedChangeListener checkListener=new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                String checked="false";
                if(isChecked){
                    checked="true";
                }

                String firstName=values.get(position).getfName();
                String lastName=values.get(position).getlName();

                try {
                    new AssignTutors().execute(checked, firstName, lastName).get();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        };


        cbTutor.setOnCheckedChangeListener(checkListener);
        return v;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }




    public static class AssignTutors extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... params) {
            String promote= params[0];
            String firstName= params[1];
            String lastName= params[2];
            String responseJson="";



            String promoteURL = "http://" + StaticHelper.getDeviceIP() + "/android/tutors/PromoteDemoteTutors.php";
            HttpCheckBoxHandler UpdateInfo=new HttpCheckBoxHandler();
            responseJson=UpdateInfo.makeServiceCallPost(promoteURL,promote,firstName,lastName);



            return responseJson;
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
    }