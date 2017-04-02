package movingforward.tutorapp3.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import movingforward.tutorapp3.Entities.Role;
import movingforward.tutorapp3.Entities.User;
import movingforward.tutorapp3.Find_Class.BySubjectFragmentOne;
import movingforward.tutorapp3.Fragments.Sessions;
import movingforward.tutorapp3.Fragments.User_list;
import movingforward.tutorapp3.Fragments.User_list_Button;
import movingforward.tutorapp3.R;

import static movingforward.tutorapp3.Find_Class.BySubjectFragmentOne.newInstance;
import static movingforward.tutorapp3.R.id.StudentSessions;
import static movingforward.tutorapp3.R.id.TutorSessions;

public class Nav_MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Sessions.OnFragmentInteractionListener
{
    User mUser;
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

        Intent navIntent = getIntent();
        mUser = (User) navIntent.getSerializableExtra("mUser");
        mUser.setEmail(mUser.getEmail().trim());

        toggle.syncState();

        String UserType = mUser.getPermission().toString();
        mEmail = mUser.getEmail();
        mPassword = mUser.getPassword();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView = navigationView.getHeaderView(0);
        TextView tvType = (TextView) hView.findViewById(R.id.tvType);
        TextView tvEmail = (TextView) hView.findViewById(R.id.tvEmail);
        tvEmail.setText(mUser.getID());
        tvType.setText("Logged in as a " + mUser.getPermission().name().toUpperCase());

        switch (mUser.getPermission().toString())
        {
            case "Tutor":
                navigationView.getMenu().findItem(R.id.MyClasses).setVisible(false);
                navigationView.getMenu().findItem(R.id.PostBulletin).setVisible(false);
                navigationView.getMenu().findItem(R.id.Find_ClassesT).setVisible(false);
                navigationView.getMenu().findItem(R.id.StudentList).setVisible(false);
                mUser.setPermission(Role.Tutor);

                break;
            case "Student":
                navigationView.getMenu().findItem(R.id.MyClasses).setVisible(false);
                navigationView.getMenu().findItem(R.id.EditClasses).setVisible(false);
                navigationView.getMenu().findItem(R.id.PostBulletin).setVisible(false);
                navigationView.getMenu().findItem(R.id.TeacherSessions).setVisible(false);
                navigationView.getMenu().findItem(StudentSessions).setVisible(false);
                navigationView.getMenu().findItem(R.id.TeacherList).setVisible(false);
                navigationView.getMenu().findItem(R.id.Find_ClassesT).setVisible(false);
                navigationView.getMenu().findItem(R.id.StudentList).setVisible(false);
                navigationView.getMenu().findItem(R.id.EditClasses).setVisible(false);
                mUser.setPermission(Role.Student);


                break;
            case "Teacher":
                navigationView.getMenu().findItem(R.id.EditClasses).setVisible(false);
                navigationView.getMenu().findItem(R.id.FindClass).setVisible(false);
                navigationView.getMenu().findItem(TutorSessions).setVisible(false);
                navigationView.getMenu().findItem(StudentSessions).setVisible(false);
                navigationView.getMenu().findItem(R.id.TeacherSessions).setVisible(false);
                navigationView.getMenu().findItem(R.id.TeacherList).setVisible(false);
                navigationView.getMenu().findItem(R.id.PostBulletin).setVisible(false);
                navigationView.getMenu().findItem(R.id.EditClasses).setVisible(false);
                mUser.setPermission(Role.Teacher);

                break;
        }

        navigationView.setNavigationItemSelectedListener(this);
        drawer.openDrawer(Gravity.LEFT);
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
            drawer.openDrawer(GravityCompat.START);
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
        switch (item.getItemId())
        {
            case R.id.sign_out_menu:
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
                return true;
            }
            case R.id.action_settings:
            {
                Intent mIntent = new Intent(Nav_MainActivity.this, EditAccountActivity.class);
                mIntent.putExtra("mUser", mUser);
                startActivity(mIntent);
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        Bundle bundle = new Bundle();
        bundle.putSerializable("mUser", mUser);

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.FindClass)
        {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            BySubjectFragmentOne subjectFragmentOne = newInstance(mUser);
            android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.relativeLayout_for_fragmentOnes, subjectFragmentOne, subjectFragmentOne.getTag()).commit();

        }
        else if (id == TutorSessions)
        {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            Sessions TutorSessions = Sessions.newInstance(mUser, "student", "Past Sessions with Tutors");
            android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.relativeLayout_for_fragmentOnes, TutorSessions, TutorSessions.getTag()).commit();
        }
        else if (id == StudentSessions)
        {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            Sessions StudentSessions = Sessions.newInstance(mUser, "tutor", "Past Sessions with Students");
            android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.relativeLayout_for_fragmentOnes, StudentSessions, StudentSessions.getTag()).commit();
        }
        else if (id == R.id.TeacherSessions)
        {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            //Sessions TeacherSession = Sessions.newInstance(mUser, "TutorTeachers");
            Sessions TeacherSession = Sessions.newInstance(mUser, "TutorTeachers", "Past Sessions with Teachers");
            android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.relativeLayout_for_fragmentOnes, TeacherSession, TeacherSession.getTag()).commit();
        }
        else if (id == R.id.MyClasses)
        {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            Sessions TeacherTutorSessions = Sessions.newInstance(mUser, "TeacherTutors", "Past Sessions with Tutors" );
            android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.relativeLayout_for_fragmentOnes, TeacherTutorSessions, TeacherTutorSessions.getTag()).commit();
        }
        else if (id == R.id.AddClasses)
        {
            Intent mIntent = new Intent(Nav_MainActivity.this, AddClasses.class);
            mIntent.putExtra("mUser", mUser);
            startActivity(mIntent);
        }
        else if (id == R.id.EditClasses)//------------------
        {
            Intent mIntent = new Intent(Nav_MainActivity.this, EditClasses.class);
            mIntent.putExtra("mUser", mUser);
            startActivity(mIntent);
        }
        else if (id == R.id.AppointTutor)
        {
        }
        else if (id == R.id.TeacherList)
        {
            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            User_list user_list = new User_list().newInstance(mUser);
            user_list.setArguments(bundle);
            android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.relativeLayout_for_fragmentOnes, user_list, user_list.getTag()).commit();

        }
        else if(id==R.id.StudentList){

            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            User_list_Button user_list = new User_list_Button().newInstance(mUser);
            android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.relativeLayout_for_fragmentOnes, user_list, user_list.getTag()).commit();
        }
        else if (id == R.id.Find_ClassesT)
        {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            BySubjectFragmentOne subjectFragmentOne = newInstance(mUser);
            android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.relativeLayout_for_fragmentOnes, subjectFragmentOne, subjectFragmentOne.getTag()).commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri)
    {

    }
}
