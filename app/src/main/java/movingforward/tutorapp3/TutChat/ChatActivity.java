package movingforward.tutorapp3.TutChat;

/**
 * Created by Jeebus Prime on 2/9/2017.
 */

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import de.hdodenhof.circleimageview.CircleImageView;
import movingforward.tutorapp3.Activities.Nav_MainActivity;
import movingforward.tutorapp3.Entities.User;
import movingforward.tutorapp3.R;

//import com.google.android.gms.auth.api.Auth;

public class ChatActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener
{


    public static class MessageViewHolder extends RecyclerView.ViewHolder
    {
        public TextView messageTextView;
        public TextView messengerTextView;
        public CircleImageView messengerImageView;

        public MessageViewHolder(View v)
        {
            super(v);
            messageTextView = (TextView) itemView.findViewById(R.id.messageTextView);
            messengerTextView = (TextView) itemView.findViewById(R.id.messengerTextView);
            messengerImageView = (CircleImageView) itemView.findViewById(R.id.messengerImageView);
        }
    }

    private static final String TAG = "MainActivity";
    public static final String MESSAGES_CHILD = "messages";
    private static final int REQUEST_INVITE = 1;
    public static final int DEFAULT_MSG_LENGTH_LIMIT = 10;
    public static final String ANONYMOUS = "anonymous";
    private static final String MESSAGE_SENT_EVENT = "message_sent";
    private FirebaseUser mFirebaseUser;
    private String mUsername;
    private String mPhotoUrl;
    private SharedPreferences mSharedPreferences;
    public FirebaseAuth.AuthStateListener mAuthListener;
    public FirebaseAuth mFirebaseAuth;
    //  private GoogleApiClient mGoogleApiClient;
    private static final String MESSAGE_URL = "http://friendlychat.firebase.google.com/message/";

    private Button mSendButton;
    private RecyclerView mMessageRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private ProgressBar mProgressBar;
    private EditText mMessageEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        Intent i = getIntent();
        final User mUser = (User) i.getSerializableExtra("mUser");

        mFirebaseAuth = FirebaseAuth.getInstance();


        mAuthListener=new FirebaseAuth.AuthStateListener(){

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                mFirebaseUser=firebaseAuth.getCurrentUser();
                if (mFirebaseUser != null) {
                    // User is signed in
                    Log.d("TAG", "onAuthStateChanged:signed_in:" + mFirebaseUser.getUid());

                    // Authenticated successfully with authData
                    Intent intent = new Intent(ChatActivity.this, Nav_MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                } else {
                    // User is signed out
                    Log.d("TAG", "onAuthStateChanged:signed_out");
                }
            }
        };


       // final DatabaseReference ref= FirebaseDatabase.getInstance().getReference();

        signIn(mUser.getEmail(),mUser.getPassword());







//        mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
//                .addApi(Auth.GOOGLE_SIGN_IN_API)
//                .build();

        // Initialize ProgressBar and RecyclerView.
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mMessageRecyclerView = (RecyclerView) findViewById(R.id.messageRecyclerView);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setStackFromEnd(true);
        mMessageRecyclerView.setLayoutManager(mLinearLayoutManager);

        mProgressBar.setVisibility(ProgressBar.INVISIBLE);

        mMessageEditText = (EditText) findViewById(R.id.messageEditText);
        mMessageEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(mSharedPreferences
                .getInt(CodelabPreferences.FRIENDLY_MSG_LENGTH, DEFAULT_MSG_LENGTH_LIMIT))});
        mMessageEditText.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                if (charSequence.toString().trim().length() > 0)
                {
                    mSendButton.setEnabled(true);
                }
                else
                {
                    mSendButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable)
            {
            }
        });

        mSendButton = (Button) findViewById(R.id.sendButton);
        mSendButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // Send messages on click.
            }
        });
    }

    @Override
    public void onStart()
    {
        super.onStart();
        // Check if user is signed in.
        // TODO: Add code to check if user is signed in.
    }

    @Override
    public void onPause()
    {
        super.onPause();
    }

    @Override
    public void onResume()
    {
        super.onResume();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.chat_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult)
    {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }
    private void createAccount(String Email, String Password){

        mFirebaseAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(ChatActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(TAG,"Create"+task.isSuccessful());
                if(task.isSuccessful()){
                    Toast.makeText(ChatActivity.this,"HOLY SHIT IT WORKED !",Toast.LENGTH_SHORT).show();
                }
                if(!task.isSuccessful()){
                    Toast.makeText(ChatActivity.this,"it failed",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private void signIn(final String Email, final String Password){

        mFirebaseAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(ChatActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Toast.makeText(ChatActivity.this,"Sign in Successful",Toast.LENGTH_SHORT).show();

                if(!task.isSuccessful()){
                    Toast.makeText(ChatActivity.this,"Sign in NOT Successful",Toast.LENGTH_SHORT).show();
                    Toast.makeText(ChatActivity.this,"Attempting to create user",Toast.LENGTH_SHORT).show();
                    createAccount(Email,Password);
                }
            }
        });

    }
    private void signOut(){
        mFirebaseAuth.signOut();
    }

}