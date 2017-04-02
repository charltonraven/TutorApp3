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
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import movingforward.tutorapp3.Entities.User;
import movingforward.tutorapp3.Entities.class_Helper.HttpTutorHandler;
import movingforward.tutorapp3.Fragments.Sessions;
import movingforward.tutorapp3.ProjectHelpers.StaticHelper;
import movingforward.tutorapp3.ProjectHelpers.btnNameAdapter;
import movingforward.tutorapp3.R;

/**
 * Created by Jeebus on 3/28/2017.
 */

public class EditClasses extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,Sessions.OnFragmentInteractionListener, View.OnClickListener,AdapterView.OnItemClickListener
{
    public User mUser;
    ListView lvTutoredClasses;

    ArrayList<String> classes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_tutor_classes);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
     //   setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        Intent mIntent = getIntent();
        mUser = (User) mIntent.getSerializableExtra("mUser");

        lvTutoredClasses=(ListView) findViewById(R.id.lvTutoredClasses);

        try {
            classes = new generateTutoredClasses().execute(mUser.getID()).get();
            btnNameAdapter btnNameAdapter=new btnNameAdapter(this, android.R.layout.simple_list_item_1,classes,mUser);
            lvTutoredClasses.setAdapter(btnNameAdapter);
        }catch (Exception e){
            e.printStackTrace();
        }


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
       View hView = navigationView.getHeaderView(0);

        toggle.syncState();


        navigationView.setNavigationItemSelectedListener(this);
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

}


    public static class generateTutoredClasses extends AsyncTask<String,String, ArrayList<String>>{

        ArrayList<String> classes=new ArrayList<>();


        @Override
        protected ArrayList<String> doInBackground(String... params) {

            String id=params[0];
            String register_url = "http://" + StaticHelper.getDeviceIP() + "/android/CreateListorGrids/generateUserTutoredClasses.php";
            HttpTutorHandler TutoredList = new HttpTutorHandler();
            String response = TutoredList.makeServiceCallPost(register_url, id,null);

            if(response!=null){

                try {
                    JSONArray TutoredJSONarray = new JSONArray(response);
                    for (int i = 0; i < TutoredJSONarray.length(); i++) {
                        JSONObject TutoredClassObj = TutoredJSONarray.getJSONObject(i);
                        classes.add(TutoredClassObj.getString("departAbbr")+" "+TutoredClassObj.getString("courseNumber"));

                        String Test = "Test";
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
            return classes;
        }

        @Override
        protected void onPostExecute(ArrayList<String> courses) {

            super.onPostExecute(courses);
        }
    }

}
