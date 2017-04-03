package movingforward.tutorapp3.ProjectHelpers;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;

import movingforward.tutorapp3.Entities.User;
import movingforward.tutorapp3.Entities.class_Helper.HttpTutorHandler;
import movingforward.tutorapp3.R;

/**
 * Created by raven on 3/2/2017.
 */

public class btnNameAdapter extends ArrayAdapter<String> implements AdapterView.OnItemClickListener {



    ArrayList<String> values;
    User mUser;

    public btnNameAdapter(Context context, int resource, ArrayList<String> values, User mUser) {
        super(context, resource);
        this.values = values;
        this.mUser=mUser;
    }

    public btnNameAdapter(Context context, int resource) {
        super(context, resource);
    }


    @Override
    public void addAll(Collection<? extends String> collection) {
        super.addAll(collection);
    }

    @Override
    public void remove(@Nullable String object) {
        super.remove(object);
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return values.size();
    }


    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;


        final TextView textView;
        Button Remove;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.btn_txt_template, null);
        } else {
            v = (View) convertView;
        }



        textView = (TextView) v.findViewById(R.id.tv_ClassItems);
        Remove=(Button) v.findViewById(R.id.btn_removeTutoredClass);

        Remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String coursNumber=values.get(position).split(" ")[1];
               final  String departAbbr=values.get(position).split(" ")[0];
                    AlertDialog.Builder removeClassDialog=new AlertDialog.Builder(getContext());
                    removeClassDialog.setMessage("Do you want to drop this class");
                    removeClassDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                new RemoveClass().execute(mUser.getID(),coursNumber,departAbbr).get();
                                values.remove(position);
                                btnNameAdapter.this.notifyDataSetChanged();




                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }
                    });
                removeClassDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                removeClassDialog.create();
                removeClassDialog.show();






            }
        });


        textView.setText(values.get(position));

        return v;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }




    public static class RemoveClass extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... params) {
            String id= params[0];
            String courseNumber= params[1];
            String departAbbr= params[2];
            String responseJson="";

            String [] ClassTutored={id,courseNumber,departAbbr};



            String removeURLClassTutored = "http://" + StaticHelper.getDeviceIP() + "/android/tutors/DeleteTutoredClass.php";
            HttpTutorHandler removeClassTutored=new HttpTutorHandler();
            responseJson=removeClassTutored.makeServiceCallPost(removeURLClassTutored,null,ClassTutored);



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