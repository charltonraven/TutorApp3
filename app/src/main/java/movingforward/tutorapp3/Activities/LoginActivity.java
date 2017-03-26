package movingforward.tutorapp3.Activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import movingforward.tutorapp3.Entities.Role;
import movingforward.tutorapp3.Entities.User;
import movingforward.tutorapp3.Entities.class_Helper.HttpHandler;
import movingforward.tutorapp3.ProjectHelpers.StaticHelper;
import movingforward.tutorapp3.R;

import static android.Manifest.permission.READ_CONTACTS;
import static movingforward.tutorapp3.R.id.email;

public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor>
{

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    String FirstName="";
    String LastName="";
    User mUser=null;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(email);
        populateAutoComplete();

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent)
            {
                if (id == R.id.login || id == EditorInfo.IME_NULL)
                {
                    // attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        Button debugStudentButton = (Button) findViewById(R.id.debugStudent_Button);
        Button debugTutorButton = (Button) findViewById(R.id.debugTutor_button);
        Button debugTeacherButton = (Button) findViewById(R.id.debugteacher_button);

        mEmailSignInButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                attemptLogin();
            }
        });

        debugStudentButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                attemptLogin("dleon6779@g.fmarion.edu", "password");
            }
        });

        debugTutorButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                attemptLogin("CWilliams2638@g.fmarion.edu", "password");
            }
        });

        debugTeacherButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                attemptLogin("FinalTest@g.fmarion.edu", "password");
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);


    }

    private void populateAutoComplete()
    {
        if (!mayRequestContacts())
        {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts()
    {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
        {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED)
        {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS))
        {
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener()
                    {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v)
                        {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        }
        else
        {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        if (requestCode == REQUEST_READ_CONTACTS)
        {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                populateAutoComplete();
            }
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin()
    {

        try
        {
            String email = mEmailView.getText().toString();
            String password = mPasswordView.getText().toString();
            String who = "login";
            boolean verified = false;
            if(email.equals("") && password.equals("")){


                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }

            mFirebaseAuth = FirebaseAuth.getInstance();
            mFirebaseUser = mFirebaseAuth.getCurrentUser();

            mFirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>()
            {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task)
                {
                    if (!task.isSuccessful())
                    {
                        final Context context = LoginActivity.this;

                        AlertDialog.Builder RegorTry = new AlertDialog.Builder(context);
                        RegorTry.setTitle("Incorrect! Would you like to register");

                        RegorTry.setPositiveButton(R.string.register, new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int id)
                            {
                                context.startActivity(new Intent(context, RegisterActivity.class));
                            }
                        });
                        RegorTry.setNeutralButton(R.string.try_again, new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int id)
                            {
                                dialog.dismiss();

                            }
                        });

                        RegorTry.create();
                        RegorTry.show();
                    }
                    else
                    {
                        mFirebaseUser = mFirebaseAuth.getCurrentUser();

                        if(!mFirebaseUser.isEmailVerified())
                        {
                            Toast.makeText(LoginActivity.this, "Must verify first! Resending email...", Toast.LENGTH_SHORT).show();
                            mFirebaseUser.sendEmailVerification();
                        }
                    }
                }
            });

            if(mFirebaseUser.isEmailVerified())
            {
                mAuthTask = new UserLoginTask(this);
                mAuthTask.execute(who, email, password);
            }

        } catch (Exception e)
        {
            System.out.println(e);
        }
    }

    private void attemptLogin(String email, String password)
    {

        try
        {
            String who = "login";

            mAuthTask = new UserLoginTask(this);
            mAuthTask.execute(who, email, password);

        } catch (Exception e)
        {
            System.out.println(e);
        }
    }


    private boolean isEmailValid(String email)
    {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password)
    {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show)
    {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2)
        {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter()
            {
                @Override
                public void onAnimationEnd(Animator animation)
                {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter()
            {
                @Override
                public void onAnimationEnd(Animator animation)
                {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        }
        else
        {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle)
    {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor)
    {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader)
    {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection)
    {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }


    private interface ProfileQuery
    {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public static class UserLoginTask extends AsyncTask<String, Void, String>
    {
        AlertDialog.Builder RegorTry;
        AlertDialog alertDialog;
        Context context;
        String username;
        String password;

        UserLoginTask(Context context)
        {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... params)
        {
            // TODO: attempt authentication against a network service.
            String responseJson = "";
            if (params[1].equals("") && params[2].equals(""))
            {
                responseJson = "";
            }
            else
            {
                String type = params[0];
                username = params[1];
                password = params[2];

                String login_url = "http://" + StaticHelper.getDeviceIP() + "/android/Inserts/login2.php";

                HttpHandler LoginHandler = new HttpHandler();
                responseJson = LoginHandler.makeServiceCallPost(login_url, null, username, password, null);
            }

            return responseJson;
        }

        @Override
        protected void onPreExecute()
        {
            alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle("Login Status");
        }

        @Override
        protected void onPostExecute(String responseJson)
        {
            User mUser=null;
            String id="";
            String TeacherStudent="student";
            String major_department="";
            String courses="";
            int tutor;

            if(!responseJson.isEmpty()){
                try {
                    JSONArray LoggedUser=new JSONArray(responseJson);
                    for(int i=0;i<LoggedUser.length();i++){
                        JSONObject UserObj = LoggedUser.getJSONObject(i);

                        if(UserObj.isNull("studentID")){
                            id=UserObj.getString("teacherID");
                            major_department=UserObj.getString("department");
                            courses=null;
                            //TeacherStudent="teacher";
                            tutor=3;
                        }else {
                            id = UserObj.getString("studentID");
                            major_department=UserObj.getString("major");
                            courses=UserObj.getString("courses");
                            tutor=UserObj.getInt("tutor");
                        }
                        String email=UserObj.getString("email");
                        String firstName=UserObj.getString("firstName");
                        String lastName=UserObj.getString("lastName");
                        int registered=UserObj.getInt("registered");
                        String password=UserObj.getString("password");

                        mUser = new User(id, email, firstName, lastName, major_department, courses, registered, tutor, password, null);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                alertDialog.setMessage("Logged IN");
                String Type="";
                String Email;

                if(responseJson!=null){
                    if (!responseJson.equals(""))
                    {

                        if(mUser.getTutor()==1){
                            Type="Tutor";
                        }else if(mUser.getTutor()==0){
                            Type="Student";

                        }else {
                            Type="Teacher";
                        }

                        if (Type.contains("Student"))
                        {
                            mUser.setPermission(Role.Student);
                        }
                        else if (Type.contains("Teacher"))
                        {
                            mUser.setPermission(Role.Teacher);

                        }
                        else if (Type.contains("Tutor"))
                        {
                            mUser.setPermission(Role.Tutor);
                        }

                        Intent navIntent = new Intent(context, Nav_MainActivity.class);
                        navIntent.putExtra("mUser", mUser);

                        alertDialog.setMessage("Login Successful");
                        alertDialog.show();

                        context.startActivity(navIntent);
                    }
                }
            }else{
                RegorTry = new AlertDialog.Builder(context);
                RegorTry.setTitle("Incorrect! Would you like to register");

                RegorTry.setPositiveButton(R.string.register, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        context.startActivity(new Intent(context, RegisterActivity.class));

                    }
                });

                RegorTry.setNeutralButton(R.string.try_again, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        dialog.dismiss();
                    }
                });

                RegorTry.create();
                RegorTry.show();


            }
        }
    }
}







