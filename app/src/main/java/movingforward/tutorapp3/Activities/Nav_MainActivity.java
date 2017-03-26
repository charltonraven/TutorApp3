package movingforward.tutorapp3.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import movingforward.tutorapp3.Entities.Role;
import movingforward.tutorapp3.Entities.User;
import movingforward.tutorapp3.Find_Class.BySubjectFragmentOne;
import movingforward.tutorapp3.R;
import movingforward.tutorapp3.Sessions.Sessions;
import movingforward.tutorapp3.Teacher_list;
import movingforward.tutorapp3.TutChat.ChatActivity;

import static movingforward.tutorapp3.Find_Class.BySubjectFragmentOne.newInstance;
import static movingforward.tutorapp3.R.id.StudentSessions;
import static movingforward.tutorapp3.R.id.TutorSessions;
//import static movingforward.tutorapp3.Sessions.Sessions.newInstance;

public class Nav_MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,Sessions.OnFragmentInteractionListener
{

    int day, month, year, hour, minute;
    int dayFinal, monthFinal, yearFinal, hourFinal, minuteFinal;
    NavigationView navigationView;
    User mUser;
    User nUser;
    String mEmail;
    String mPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav__main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        if (getIntent().getExtras() != null)
        {
            for (String key : getIntent().getExtras().keySet())
            {
                String value = getIntent().getExtras().getString(key);

                if (key.equals("Nav_MainActivity") && value.equals("True"))
                {
                    Intent intent = new Intent(this, ChatActivity.class);
                    intent.putExtra("value", value);
                    startActivity(intent);
                    finish();
                }
            }
        }

        subscribeToPushService();

        Intent navIntent = getIntent();
        mUser = (User) navIntent.getSerializableExtra("mUser");
        mUser.setEmail(mUser.getEmail().trim());

        TextView tvType;
        TextView tvEmail;
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        String UserType = mUser.getPermission().toString();
        mEmail = mUser.getEmail();
        mPassword = mUser.getPassword();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView = navigationView.getHeaderView(0);
        tvType = (TextView) hView.findViewById(R.id.tvType);
        tvEmail = (TextView) hView.findViewById(R.id.tvEmail);
        tvEmail.setText(mEmail);
        tvType.setText("Logged in as " + UserType.toUpperCase());

        switch (mUser.getPermission().toString())
        {
            case "Tutor":
                navigationView.getMenu().findItem(R.id.MyClasses).setVisible(false);

                navigationView.getMenu().findItem(R.id.PostBulletin).setVisible(false);
                navigationView.getMenu().findItem(R.id.Find_ClassesT).setVisible(false);
                mUser.setPermission(Role.Tutor);
                break;
            case "Student":
                navigationView.getMenu().findItem(R.id.MyClasses).setVisible(false);
                navigationView.getMenu().findItem(R.id.PostBulletin).setVisible(false);
                navigationView.getMenu().findItem(R.id.TeacherSessions).setVisible(false);
                navigationView.getMenu().findItem(StudentSessions).setVisible(false);
                navigationView.getMenu().findItem(R.id.TeacherList).setVisible(false);
                navigationView.getMenu().findItem(R.id.Find_ClassesT).setVisible(false);
                mUser.setPermission(Role.Student);

                break;
            case "Teacher":
                navigationView.getMenu().findItem(R.id.FindClass).setVisible(false);
                navigationView.getMenu().findItem(TutorSessions).setVisible(false);
                navigationView.getMenu().findItem(R.id.BulletinBoard).setVisible(false);
                navigationView.getMenu().findItem(StudentSessions).setVisible(false);
                navigationView.getMenu().findItem(R.id.TeacherSessions).setVisible(false);
                navigationView.getMenu().findItem(R.id.TeacherList).setVisible(false);
                mUser.setPermission(Role.Teacher);

                break;
        }

        navigationView.setNavigationItemSelectedListener(this);
        drawer.openDrawer(Gravity.LEFT);
    }

    private void subscribeToPushService()
    {
        FirebaseMessaging.getInstance().subscribeToTopic("news");

        Log.d("AndroidBash", "Subscribed");
        Toast.makeText(Nav_MainActivity.this, "Subscribed", Toast.LENGTH_SHORT).show();

        String token = FirebaseInstanceId.getInstance().getToken();

        // Log and toast
        Log.d("AndroidBash", token);
        Toast.makeText(Nav_MainActivity.this, token, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            AlertDialog.Builder RegorTry = new AlertDialog.Builder(Nav_MainActivity.this);
            RegorTry.setTitle("Are you sure you want to sign out?");

            RegorTry.setPositiveButton("Yes", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int id)
                {
                    startActivity(new Intent(Nav_MainActivity.this, LoginActivity.class));
                }
            });
            RegorTry.setNeutralButton("Cancel", new DialogInterface.OnClickListener()
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav__main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        switch (item.getItemId())
        {
            case R.id.sign_out_menu:
                //mFirebaseAuth.signOut();
                //Auth.GoogleSignInApi.signOut(mGoogleApiClient);
                //mUsername = ANONYMOUS;
                startActivity(new Intent(this, LoginActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        //User nUser = new User(mEmail, mPassword);
        //mUser = nUser;
        Bundle bundle=new Bundle();
        bundle.putSerializable("mUser",mUser);

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.FindClass)
        {

            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            BySubjectFragmentOne subjectFragmentOne = newInstance(mUser);
            android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.relativeLayout_for_fragmentOnes, subjectFragmentOne, subjectFragmentOne.getTag()).commit();

        }
        else if (id == TutorSessions)
        {


            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            Sessions TutorSessions = Sessions.newInstance(mUser,"student");
            android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.relativeLayout_for_fragmentOnes, TutorSessions, TutorSessions.getTag()).commit();


        }
        else if (id == R.id.BulletinBoard)
        {

        }
        else if (id == StudentSessions)
        {
            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            Sessions StudentSessions = Sessions.newInstance(mUser,"tutor");
            android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.relativeLayout_for_fragmentOnes, StudentSessions, StudentSessions.getTag()).commit();

        }

        else if (id == R.id.TeacherSessions)
        {
            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            Sessions TeacherSession = Sessions.newInstance(mUser,"teacher");
            android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.relativeLayout_for_fragmentOnes, TeacherSession, TeacherSession.getTag()).commit();


        }
        else if (id == R.id.MyClasses)
        {
            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            Sessions TeacherTutorSessions = Sessions.newInstance(mUser,"TeacherTutors");
            android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.relativeLayout_for_fragmentOnes, TeacherTutorSessions, TeacherTutorSessions.getTag()).commit();

        }
        else if (id == R.id.PostBulletin)
        {


        }
        else if (id == R.id.AppointTutor)
        {
        }
        else if(id==R.id.TeacherList){

            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            Teacher_list teacher_list = new Teacher_list();
            teacher_list.setArguments(bundle);
            android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.relativeLayout_for_fragmentOnes, teacher_list, teacher_list.getTag()).commit();

        }
        else if(id==R.id.TeacherList){

            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            Teacher_list teacher_list = new Teacher_list();
            android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.relativeLayout_for_fragmentOnes, teacher_list, teacher_list.getTag()).commit();
        }
        else if (id==R.id.Find_ClassesT){
            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            BySubjectFragmentOne subjectFragmentOne = newInstance(mUser);
            android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.relativeLayout_for_fragmentOnes, subjectFragmentOne, subjectFragmentOne.getTag()).commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }





    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public static class InserUserTask extends AsyncTask<String, Void, String>
    {


        @Override
        protected String doInBackground(String... params)
        {


                return null;



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
