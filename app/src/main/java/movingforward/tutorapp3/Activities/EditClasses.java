package movingforward.tutorapp3.Activities;

import android.support.v7.app.ActionBar;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

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

public class EditClasses extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemClickListener
{
    public User mUser;
    public ActionBar actionBar;
    public Button bt_return;
    public Button bt_accept;
    ListView lvTutoredClasses;

    ArrayList<String> classes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_tutor_classes);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);

        LayoutInflater inflater = LayoutInflater.from(this);
        View customView = inflater.inflate(R.layout.custom_appbar, null);

        TextView titleTV = (TextView) customView.findViewById(R.id.action_custom_title);
        titleTV.setTypeface(Typeface.createFromAsset(EditClasses.this
                .getAssets(), String.format("fonts/TACOTAC.ttf")));

        actionBar.setCustomView(customView);
        actionBar.setDisplayShowCustomEnabled(true);

        Intent mIntent = getIntent();
        mUser = (User) mIntent.getSerializableExtra("mUser");

        lvTutoredClasses=(ListView) findViewById(R.id.lvTutoredClasses);
        bt_return = (Button) findViewById(R.id.bt_return);
        bt_accept = (Button) findViewById(R.id.bt_accept);

        bt_return.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent navIntent = new Intent(EditClasses.this, Nav_MainActivity.class);
                navIntent.putExtra("mUser", mUser);
                startActivity(navIntent);
            }
        });
        bt_accept.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent navIntent = new Intent(EditClasses.this, AddClasses.class);
                navIntent.putExtra("mUser", mUser);
                startActivity(navIntent);
            }
        });

        try {
            classes = new generateTutoredClasses().execute(mUser.getID()).get();
            btnNameAdapter btnNameAdapter=new btnNameAdapter(this, android.R.layout.simple_list_item_1,classes,mUser);
            lvTutoredClasses.setAdapter(btnNameAdapter);
        }catch (Exception e){
            e.printStackTrace();
        }
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
