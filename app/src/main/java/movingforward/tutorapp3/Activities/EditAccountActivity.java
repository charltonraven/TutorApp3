package movingforward.tutorapp3.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;

import movingforward.tutorapp3.Entities.User;
import movingforward.tutorapp3.Entities.class_Helper.HttpChangeHandler;
import movingforward.tutorapp3.ProjectHelpers.StaticHelper;
import movingforward.tutorapp3.R;

/**
 * Created by Jeebus on 3/26/2017.
 */

public class EditAccountActivity extends AppCompatActivity
{
    public User mUser;
    private Button bt_accept;
    private Button bt_cancel;
    private CheckBox cb_pass;
    private CheckBox cb_first;
    private CheckBox cb_last;
    private CheckBox cb_major;
    private EditText et_pass;
    private EditText et_first;
    private EditText et_last;
    private Spinner sp_major;
    private ArrayAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_account_student);

        Intent mIntent = getIntent();

        mUser = (User) mIntent.getSerializableExtra("mUser");

        bt_accept = (Button) findViewById(R.id.bt_accept);
        bt_cancel = (Button) findViewById(R.id.bt_cancel);
        et_pass = (EditText) findViewById(R.id.et_pass);
        et_first = (EditText) findViewById(R.id.et_first);
        et_last = (EditText) findViewById(R.id.et_last);
        cb_pass = (CheckBox) findViewById(R.id.cb_pass);
        cb_first = (CheckBox) findViewById(R.id.cb_first);
        cb_last = (CheckBox) findViewById(R.id.cb_last);
        cb_major = (CheckBox) findViewById(R.id.cb_majors);
        sp_major = (Spinner) findViewById(R.id.spinner_majors);

        adapter = ArrayAdapter.createFromResource(this,
                R.array.majors_array, android.R.layout.simple_spinner_item);

        sp_major.setAdapter(adapter);

        et_pass.setEnabled(false);
        et_pass.setText(mUser.getPassword());
        et_first.setEnabled(false);
        et_first.setText(mUser.getFirstName());
        et_last.setEnabled(false);
        et_last.setText(mUser.getLastName());
        sp_major.setEnabled(false);
        sp_major.setSelection(getMajorInt(mUser.getMajor()));
        bt_accept.setEnabled(false);

        cb_pass.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (cb_pass.isChecked())
                {
                    et_pass.setEnabled(true);
                    bt_accept.setEnabled(true);
                }
                else
                {
                    et_pass.setEnabled(false);
                }
            }
        });

        cb_first.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (cb_first.isChecked())
                {
                    et_first.setEnabled(true);
                    bt_accept.setEnabled(true);
                }
                else
                {
                    et_first.setEnabled(false);
                }
            }
        });

        cb_last.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (cb_last.isChecked())
                {
                    et_last.setEnabled(true);
                    bt_accept.setEnabled(true);
                }
                else
                {
                    et_last.setEnabled(false);
                }
            }
        });

        cb_major.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (cb_major.isChecked())
                {
                    sp_major.setEnabled(true);
                    bt_accept.setEnabled(true);
                }
                else
                {
                    sp_major.setEnabled(false);
                    sp_major.clearFocus();
                }
            }
        });

        bt_accept.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try
                {
                    if (cb_first.isEnabled())
                    {
                        String firstFixed = et_first.getText().toString().substring(0, 1).toUpperCase() + et_first.getText().toString().substring(1).toLowerCase();
                        new UpdateTask().execute(mUser.getPermission().name(), mUser.getID(), "firstName", firstFixed).get();
                        mUser.setFirstName(firstFixed);
                    }
                    if (cb_last.isEnabled())
                    {
                        String lastFixed = et_last.getText().toString().substring(0, 1).toUpperCase() + et_last.getText().toString().substring(1).toLowerCase();
                        new UpdateTask().execute(mUser.getPermission().name(), mUser.getID(), "lastName", lastFixed).get();
                        mUser.setLastName(lastFixed);
                    }
                    if (cb_major.isEnabled())
                    {
                        new UpdateTask().execute(mUser.getPermission().name(), mUser.getID(), "major", sp_major.getSelectedItem().toString()).get();
                        mUser.setMajor(sp_major.getSelectedItem().toString());
                    }
                    if (cb_pass.isEnabled())
                    {
                        new UpdateTask().execute(mUser.getPermission().name(), mUser.getID(), "password", et_pass.getText().toString()).get();
                        mUser.setPassword(et_pass.getText().toString());
                    }
                } catch (Exception e)
                {
                    e.printStackTrace();
                }

                Intent navIntent = new Intent(EditAccountActivity.this, Nav_MainActivity.class);
                navIntent.putExtra("mUser", mUser);
                startActivity(navIntent);
            }
        });

        bt_cancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent navIntent = new Intent(EditAccountActivity.this, Nav_MainActivity.class);
                navIntent.putExtra("mUser", mUser);
                startActivity(navIntent);
            }
        });
    }

    private int getMajorInt(String major)
    {
        String[] majorArray =
                {
                        "Biology", "Business", "Chemistry", "Computer Science",
                        "Education", "English", "General Studies", "History",
                        "Mass Comm.", "Math", "Modern Lang.", "Music Industry",
                        "Nursing", "Physics", "Political Science"
                };

        ArrayList<String> majorAL = new ArrayList<>(Arrays.asList(majorArray));

        if (majorAL.contains(major))
        {
            return majorAL.indexOf(major);
        }

        return 0;
    }

    public static class UpdateTask extends AsyncTask<String, Void, String>
    {

        @Override
        protected String doInBackground(String... params)
        {
            String who = params[0];
            String UserID = params[1];
            String what = params[2];
            String changeTo = params[3];
            String responseJson = "";


            String updateInfoURL = "http://" + StaticHelper.getDeviceIP() + "/android/Change/changeInformation.php";
            HttpChangeHandler UpdateInfo = new HttpChangeHandler();
            responseJson = UpdateInfo.makeServiceCallPost(updateInfoURL, who, UserID, what, changeTo);


            return responseJson;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);
        }
    }
}
