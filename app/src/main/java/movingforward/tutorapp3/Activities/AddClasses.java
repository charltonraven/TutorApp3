package movingforward.tutorapp3.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import movingforward.tutorapp3.Entities.Role;
import movingforward.tutorapp3.Entities.User;
import movingforward.tutorapp3.Entities.class_Helper.DepartAdapter;
import movingforward.tutorapp3.Entities.class_Helper.HttpHandlerInsertUser;
import movingforward.tutorapp3.Entities.class_Helper.HttpListHandler;
import movingforward.tutorapp3.Find_Class.BySubjectFragmentOne;
import movingforward.tutorapp3.Fragments.Sessions;
import movingforward.tutorapp3.Fragments.User_list;
import movingforward.tutorapp3.Fragments.User_list_Button;
import movingforward.tutorapp3.ProjectHelpers.StaticHelper;
import movingforward.tutorapp3.R;

import static movingforward.tutorapp3.Find_Class.BySubjectFragmentOne.newInstance;
import static movingforward.tutorapp3.R.id.StudentSessions;
import static movingforward.tutorapp3.R.id.TutorSessions;

/**
 * Created by Jeebus on 3/28/2017.
 */

public class AddClasses extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Sessions.OnFragmentInteractionListener, View.OnClickListener, AdapterView.OnItemClickListener
{
    public User mUser;
    private ScrollView sv_depart;
    private ScrollView sv_course;
    private Button bt_accept;
    private Button bt_cancel;
    public ArrayList<String> departments = new ArrayList<>();
    LinearLayout departLinear;
    LinearLayout courseLinear;
    TextView tv_departItem;
    TextView tv_courseItem;
    ListView lv_departList;
    ListView lv_courseList;
    View item;
    ArrayList<String> courses;


    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_tutor_classes);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);

        LayoutInflater inflater = LayoutInflater.from(this);
        View customView = inflater.inflate(R.layout.custom_appbar, null);

        TextView titleTV = (TextView) customView.findViewById(R.id.action_custom_title);
        titleTV.setTypeface(Typeface.createFromAsset(AddClasses.this
                .getAssets(), String.format("fonts/TACOTAC.ttf")));

        actionBar.setCustomView(customView);
        actionBar.setDisplayShowCustomEnabled(true);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        Intent mIntent = getIntent();
        mUser = (User) mIntent.getSerializableExtra("mUser");

        sv_depart = (ScrollView) findViewById(R.id.sv_depart);
        sv_course = (ScrollView) findViewById(R.id.sv_course);
        bt_accept = (Button) findViewById(R.id.bt_accept);
        bt_cancel = (Button) findViewById(R.id.bt_cancel);
        departLinear = (LinearLayout) findViewById(R.id.ll_departList);
        courseLinear = (LinearLayout) findViewById(R.id.ll_courseList);
        tv_courseItem = (TextView) findViewById(R.id.tv_course);
        lv_courseList = (ListView) findViewById(R.id.lv_course);
        lv_departList = (ListView) findViewById(R.id.lv_department);

        try
        {
            departments = new generateDepartmentTask().execute("department").get();
            DepartAdapter DepartAdapter = new DepartAdapter(this, android.R.layout.simple_list_item_1, departments);
            lv_departList.setAdapter(DepartAdapter);

        } catch (Exception e)
        {
            Toast.makeText(AddClasses.this, "Error", Toast.LENGTH_SHORT).show();
        }

        lv_departList.setOnItemClickListener(AddClasses.this);

        lv_courseList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {

                final String departAbbr = courses.get(position).split(" ")[0];
                final String courseNumber = courses.get(position).split(" ")[1];

                AlertDialog.Builder addClassesDialog = new AlertDialog.Builder(AddClasses.this);
                addClassesDialog.setMessage("Do you want to add this class?");
                addClassesDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        try
                        {
                            new InsertTutorClasses().execute(mUser.getID(), departAbbr, courseNumber).get();

                            System.out.println();
                        } catch (Exception e)
                        {
                            Toast.makeText(AddClasses.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                addClassesDialog.setNegativeButton("No", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        finish();
                    }
                });
                AlertDialog alertDialog = addClassesDialog.create();
                alertDialog.show();

            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView = navigationView.getHeaderView(0);

        toggle.syncState();

        TextView tvType = (TextView) hView.findViewById(R.id.tvType);
        TextView tvType2 = (TextView) hView.findViewById(R.id.tvType2);
        TextView tvEmail = (TextView) hView.findViewById(R.id.tvEmail);
        tvEmail.setText(mUser.getID());
        tvType.setText("Logged in as a ");
        tvType2.setText(mUser.getPermission().name().toUpperCase());

        switch (mUser.getPermission().toString())
        {
            case "Tutor":
                navigationView.getMenu().findItem(R.id.MyClasses).setVisible(false);
                navigationView.getMenu().findItem(R.id.Find_ClassesT).setVisible(false);
                navigationView.getMenu().findItem(R.id.StudentList).setVisible(false);
                navigationView.getMenu().findItem(R.id.AddClasses).setVisible(false);
                mUser.setPermission(Role.Tutor);

                break;
            case "Student":
                navigationView.getMenu().findItem(R.id.MyClasses).setVisible(false);
                navigationView.getMenu().findItem(R.id.TeacherSessions).setVisible(false);
                navigationView.getMenu().findItem(StudentSessions).setVisible(false);
                navigationView.getMenu().findItem(R.id.TeacherList).setVisible(false);
                navigationView.getMenu().findItem(R.id.Find_ClassesT).setVisible(false);
                navigationView.getMenu().findItem(R.id.StudentList).setVisible(false);
                navigationView.getMenu().findItem(R.id.AddClasses).setVisible(false);
                mUser.setPermission(Role.Student);

                break;
            case "Teacher":
                navigationView.getMenu().findItem(R.id.FindClass).setVisible(false);
                navigationView.getMenu().findItem(TutorSessions).setVisible(false);
                navigationView.getMenu().findItem(StudentSessions).setVisible(false);
                navigationView.getMenu().findItem(R.id.TeacherSessions).setVisible(false);
                navigationView.getMenu().findItem(R.id.TeacherList).setVisible(false);
                navigationView.getMenu().findItem(R.id.AddClasses).setVisible(false);
                mUser.setPermission(Role.Teacher);
                break;
        }

        navigationView.setNavigationItemSelectedListener(this);
        bt_cancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent navIntent = new Intent(AddClasses.this, Nav_MainActivity.class);
                navIntent.putExtra("mUser", mUser);
                startActivity(navIntent);
            }
        });
    }

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
                startActivity(new Intent(this, LoginActivity.class));
                return true;
            }
            case R.id.action_settings:
            {
                Intent mIntent = new Intent(AddClasses.this, EditAccountActivity.class);
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

    @Override
    public void onClick(View v)
    {

    }

    //DepartmentList
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        tv_departItem = (TextView) findViewById(R.id.tv_departCourseItem);


        String course = departments.get(position);

        try
        {
            courses = new generateCourseTask().execute("course", course).get();
            DepartAdapter DepartAdapter = new DepartAdapter(this, android.R.layout.simple_list_item_1, courses);
            lv_courseList.setAdapter(DepartAdapter);
        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public static class generateDepartmentTask extends AsyncTask<String, String, ArrayList<String>>
    {

        ArrayList<String> departments = new ArrayList<>();


        @Override
        protected ArrayList<String> doInBackground(String... params)
        {

            String what = params[0];
            String register_url = "http://" + StaticHelper.getDeviceIP() + "/android/CreateListorGrids/generateDepartList.php";
            HttpListHandler departmentList = new HttpListHandler();
            String response = departmentList.makeServiceCallPost(register_url, null, null, what, null);

            if (response != null)
            {
                try
                {
                    JSONArray departJSONarray = new JSONArray(response);
                    for (int i = 0; i < departJSONarray.length(); i++)
                    {
                        JSONObject departObject = departJSONarray.getJSONObject(i);
                        departments.add(departObject.getString("departName"));

                        String Test = "Test";
                    }
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
            return departments;
        }

        @Override
        protected void onPostExecute(ArrayList<String> departments)
        {

            super.onPostExecute(departments);
        }
    }

    public static class generateCourseTask extends AsyncTask<String, String, ArrayList<String>>
    {
        ArrayList<String> courses = new ArrayList<>();

        @Override
        protected ArrayList<String> doInBackground(String... params)
        {
            String what = params[0];
            String departmentName = params[1];
            String[] CourseList = {what, departmentName};
            String register_url = "http://" + StaticHelper.getDeviceIP() + "/android/CreateListorGrids/generateCourseList.php";
            HttpListHandler departmentList = new HttpListHandler();
            String response = departmentList.makeServiceCallPost(register_url, null, null, null, CourseList);

            if (response != null)
            {

                try
                {
                    JSONArray CourseJSONarray = new JSONArray(response);
                    for (int i = 0; i < CourseJSONarray.length(); i++)
                    {
                        JSONObject courseObject = CourseJSONarray.getJSONObject(i);
                        courses.add(courseObject.getString("departAbbr") + " " + courseObject.getString("courseNumber"));

                        String Test = "Test";
                    }
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
            return courses;
        }

        @Override
        protected void onPostExecute(ArrayList<String> courses)
        {

            super.onPostExecute(courses);
        }
    }

    private class InsertTutorClasses extends AsyncTask<String, String, String>
    {

        @Override
        protected String doInBackground(String... params)
        {
            String id = params[0];
            String departmentAbbr = params[1];
            String courseNumber = params[2];

            String result = "";
            String[] idCourseNumber = {id, departmentAbbr, courseNumber};

            HttpHandlerInsertUser sh = new HttpHandlerInsertUser();

            String InsertTutorClassesURL = "http://" + StaticHelper.getDeviceIP() + "/android/inserts/InsertTutorClass.php";
            result = sh.makeServiceCallPost(InsertTutorClassesURL, idCourseNumber);

            System.out.println(result);
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
        }
    }
}
