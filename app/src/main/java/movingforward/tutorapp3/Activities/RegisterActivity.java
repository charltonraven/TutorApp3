package movingforward.tutorapp3.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import movingforward.tutorapp3.Entities.class_Helper.HttpHandler;
import movingforward.tutorapp3.ProjectHelpers.StaticHelper;
import movingforward.tutorapp3.R;

public class RegisterActivity extends AppCompatActivity
{

    private EditText mFirstNameView;
    private EditText mLastNameView;
    private EditText mEmailView;
    private Spinner mMajorView;
    private CheckBox mTeacherCheckBox;
    private Button mRegisterButton;
    public String who = "";
    public FirebaseAuth.AuthStateListener mAuthListener;
    public FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;


    ArrayAdapter<CharSequence> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFirstNameView = (EditText) findViewById(R.id.et_firstName);
        mLastNameView = (EditText) findViewById(R.id.et_lastName);
        mEmailView = (EditText) findViewById(R.id.et_email);
        mMajorView = (Spinner) findViewById(R.id.spinner_majors);
        mTeacherCheckBox = (CheckBox) findViewById(R.id.cb_teacher);
        mRegisterButton = (Button) findViewById(R.id.btn_register);
// Create an ArrayAdapter using the string array and a default spinner layout
        adapter = ArrayAdapter.createFromResource(this,
                R.array.majors_array, android.R.layout.simple_spinner_item);

// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        mMajorView.setAdapter(adapter);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        mRegisterButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String email = mEmailView.getText().toString();
                String domain = email.split("@")[1];

                if (mTeacherCheckBox.isChecked() && domain.equals("fmarion.edu"))
                {
                    Register();
                }
                else if (domain.equals("g.fmarion.edu"))
                {
                    Register();
                }
                else
                {
                    Toast.makeText(RegisterActivity.this, "Use valid school email!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public void Register()
    {
        who = "Student";
        String email = mEmailView.getText().toString();
        String firstName = mFirstNameView.getText().toString();
        String lastName = mLastNameView.getText().toString();
        String major = mMajorView.getSelectedItem().toString();
        if (mTeacherCheckBox.isChecked())
        {
            who = "Teacher";
        }
        if (mMajorView.getSelectedItem() == "")
        {
            mMajorView = null;
        }

        RegisterUser registerUser = new RegisterUser(this);
        registerUser.execute(who, firstName, lastName, email, major);

        signIn(email, "password");

        mFirebaseUser = mFirebaseAuth.getCurrentUser();
    }

    private void signIn(final String Email, final String Password)
    {
        mFirebaseAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>()
        {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if (!task.isSuccessful())
                {
                    createAccount(Email, Password);
                }
            }
        });
    }

    private void createAccount(String Email, String Password)
    {
        mFirebaseAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>()
        {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                Log.d("RegActivity", "Create" + task.isSuccessful());
                if (task.isSuccessful())
                {
                    mFirebaseUser.sendEmailVerification();
                }
                if (!task.isSuccessful())
                {
                    Toast.makeText(RegisterActivity.this, "Account Creation Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static class RegisterUser extends AsyncTask<String, Void, String>
    {

        AlertDialog alertDialog;
        Context context;
        public String who = "";
        public String firstname = "";
        public String lastname = "";
        public String Email = "";
        public String major = "";

        RegisterUser(Context context)
        {
            this.context = context;

        }

        @Override
        protected String doInBackground(String... params)
        {
            // TODO: attempt authentication against a network service.
            who = params[0];
            firstname = params[1];
            lastname = params[2];
            Email = params[3];
            major = params[4];
            String[] subID = Email.split("\\@");
            String id = subID[0];

            String[] UserInfo = {who, id, Email, firstname, lastname, major};

            String register_url = "http://" + StaticHelper.getDeviceIP() + "/android/Inserts/insert.php";
            HttpHandler Register = new HttpHandler();
            String response = Register.makeServiceCallPost(register_url, null, null, null, UserInfo);

            return response.trim();
        }

        @Override
        protected void onPreExecute()
        {
            alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle("Register Status");
        }

        @Override
        protected void onPostExecute(String result) {
            //super.onPostExecute(s);
            if (result != null) {

                if (result.equals("Email Already Exist")) {
                    alertDialog.setMessage(result);
                    alertDialog.show();
                }
            }
            else
            {
                alertDialog.setMessage("Something went wrong");
                alertDialog.show();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values)
        {
            super.onProgressUpdate(values);
        }
    }

}
