package movingforward.tutorapp3.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import movingforward.tutorapp3.Entities.User;
import movingforward.tutorapp3.Entities.class_Helper.DepartCourseListAdapter;
import movingforward.tutorapp3.Entities.class_Helper.HttpListHandler;
import movingforward.tutorapp3.Fragments.Sessions;
import movingforward.tutorapp3.ProjectHelpers.StaticHelper;
import movingforward.tutorapp3.R;

/**
 * Created by Jeebus on 3/28/2017.
 */

public class EditClasses extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,Sessions.OnFragmentInteractionListener, View.OnClickListener,AdapterView.OnItemClickListener
{
    public User mUser;
    private ScrollView sv_depart;
    private ScrollView sv_course;
    private Button bt_accept;
    private Button bt_cancel;
    public ArrayList<String> departments=new ArrayList<>();
    LinearLayout departLinear;
    LinearLayout courseLinear;
    TextView tv_departItem;
    ListView lv_departList;
    ListView lv_courseList;
    View item;


    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_tutor_classes);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
        courseLinear= (LinearLayout) findViewById(R.id.ll_courseList);

        lv_departList= (ListView) findViewById(R.id.lv_department);


        try {
            departments=new generateDepartmentTask().execute("department").get();
            DepartCourseListAdapter DepartAdapter=new DepartCourseListAdapter(this, android.R.layout.simple_list_item_1,departments);
            lv_departList.setAdapter(DepartAdapter);


        }catch (Exception e){
            e.printStackTrace();
        }
        lv_departList.setOnItemClickListener(EditClasses.this);


        /*tv_departItem.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()== MotionEvent.ACTION_DOWN){
                    v.setBackgroundColor(Color.BLUE);

                }else if(event.getAction()==MotionEvent.ACTION_UP){
                    v.setBackgroundColor(Color.WHITE);

                }
                return true;
            }
        });*/


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView = navigationView.getHeaderView(0);

        toggle.syncState();



        navigationView.setNavigationItemSelectedListener(this);
        bt_cancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent navIntent = new Intent(EditClasses.this, Nav_MainActivity.class);
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
            super.onBackPressed();
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
                Intent mIntent = new Intent(EditClasses.this, EditAccountActivity.class);
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


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onFragmentInteraction(Uri uri)
    {

    }

    @Override
    public void onClick(View v) {


    }

    //DepartmentList
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        tv_departItem= (TextView) findViewById(R.id.tv_departCourseItem);
        lv_courseList=(ListView) findViewById(R.id.lv_course) ;

        String course=departments.get(position);

        try {
            ArrayList<String> courses = new generateCourseTask().execute("course", course).get();
            DepartCourseListAdapter DepartAdapter = new DepartCourseListAdapter(this, android.R.layout.simple_list_item_1, courses);
            lv_courseList.setAdapter(DepartAdapter);
        }catch (Exception e){
            e.printStackTrace();
        }




    }

    public static class generateDepartmentTask extends AsyncTask<String,String, ArrayList<String>>{

        ArrayList<String> departments=new ArrayList<>();


        @Override
        protected ArrayList<String> doInBackground(String... params) {

            String what=params[0];
            String register_url = "http://" + StaticHelper.getDeviceIP() + "/android/CreateListorGrids/generateDepartList.php";
            HttpListHandler departmentList = new HttpListHandler();
            String response = departmentList.makeServiceCallPost(register_url, null, null,what,null);



            if(response!=null){

                try {
                    JSONArray departJSONarray = new JSONArray(response);
                    for (int i = 0; i < departJSONarray.length(); i++) {
                        JSONObject departObject = departJSONarray.getJSONObject(i);
                        departments.add(departObject.getString("departName"));

                        String Test = "Test";
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
            return departments;
}

        @Override
        protected void onPostExecute(ArrayList<String> departments) {

            super.onPostExecute(departments);
        }
    }
    public static class generateCourseTask extends AsyncTask<String,String, ArrayList<String>>{

        ArrayList<String> courses=new ArrayList<>();


        @Override
        protected ArrayList<String> doInBackground(String... params) {

            String what=params[0];
            String  departmentName=params[1];
            String [] CourseList={what,departmentName};
            String register_url = "http://" + StaticHelper.getDeviceIP() + "/android/CreateListorGrids/generateCourseList.php";
            HttpListHandler departmentList = new HttpListHandler();
            String response = departmentList.makeServiceCallPost(register_url, null, null,null,CourseList);

            if(response!=null){

                try {
                    JSONArray CourseJSONarray = new JSONArray(response);
                    for (int i = 0; i < CourseJSONarray.length(); i++) {
                        JSONObject courseObject = CourseJSONarray.getJSONObject(i);
                        courses.add(courseObject.getString("departAbbr")+" "+courseObject.getString("courseNumber"));

                        String Test = "Test";
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
            return courses;
        }

        @Override
        protected void onPostExecute(ArrayList<String> courses) {

            super.onPostExecute(courses);
        }
    }
}
