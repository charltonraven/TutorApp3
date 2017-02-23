package movingforward.tutorapp3.TutChat;

/**
 * Created by Jeebus Prime on 2/9/2017.
 */

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
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

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.appindexing.Action;
import com.google.firebase.appindexing.FirebaseAppIndex;
import com.google.firebase.appindexing.FirebaseUserActions;
import com.google.firebase.appindexing.Indexable;
import com.google.firebase.appindexing.builders.Indexables;
import com.google.firebase.appindexing.builders.PersonBuilder;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    public static String MESSAGES_CHILD;
    private static final int REQUEST_INVITE = 1;
    public static final int DEFAULT_MSG_LENGTH_LIMIT = 100;
    public static final String ANONYMOUS = "anonymous";
    private static final String MESSAGE_SENT_EVENT = "message_sent";
    private FirebaseUser mFirebaseUser;
    private String mPhotoUrl;
    private SharedPreferences mSharedPreferences;
    public FirebaseAuth.AuthStateListener mAuthListener;
    public FirebaseAuth mFirebaseAuth;
    private static String MESSAGE_URL = "https://tutitup-71061.firebaseio.com/";

    private DatabaseReference mFirebaseDatabaseReference;
    private FirebaseRecyclerAdapter<FriendlyMessage, MessageViewHolder> mFirebaseAdapter;

    private Button mSendButton;
    private RecyclerView mMessageRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private ProgressBar mProgressBar;
    private EditText mMessageEditText;
    public User mUser;
    private String mUsername;
    public User nUser;
    private String nUsername;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);



        Intent i = getIntent();
        mUser = (User) i.getSerializableExtra("mUser");
        mUsername = mUser.getEmail();

        nUser = (User) i.getSerializableExtra("nUser");
        nUsername = nUser.getEmail();

        MESSAGES_CHILD = mUsername.compareTo(mUsername) < 0 ? (mUsername + "_" + nUsername) : (nUsername + "_" + nUsername);

        MESSAGE_URL += MESSAGES_CHILD + "/";

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        mAuthListener = new FirebaseAuth.AuthStateListener()
        {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth)
            {
                mFirebaseUser = firebaseAuth.getCurrentUser();
                if (mFirebaseUser != null)
                {
                    mUsername = mFirebaseUser.getDisplayName();
                    mPhotoUrl = mFirebaseUser.getPhotoUrl().toString();
                    // User is signed in
                    Log.d("TAG", "onAuthStateChanged:signed_in:" + mFirebaseUser.getUid());

                    // Authenticated successfully with authData
                    Intent intent = new Intent(ChatActivity.this, Nav_MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                }
                else
                {
                    // User is signed out
                    Log.d("TAG", "onAuthStateChanged:signed_out");
                }
            }
        };

        signIn(mUser.getEmail(), mUser.getPassword());

        mUsername = mFirebaseUser.getDisplayName();
        mPhotoUrl = mFirebaseUser.getPhotoUrl() == null ? "n/a" : mFirebaseUser.getPhotoUrl().toString();

        // Initialize ProgressBar and RecyclerView.
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mMessageRecyclerView = (RecyclerView) findViewById(R.id.messageRecyclerView);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setStackFromEnd(true);
        mMessageRecyclerView.setLayoutManager(mLinearLayoutManager);


        // New child entries
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mFirebaseAdapter = new FirebaseRecyclerAdapter<FriendlyMessage,
                MessageViewHolder>(
                FriendlyMessage.class,
                R.layout.item_message,
                MessageViewHolder.class,
                mFirebaseDatabaseReference.child(MESSAGES_CHILD))
        {

            @Override
            protected FriendlyMessage parseSnapshot(DataSnapshot snapshot)
            {
                FriendlyMessage friendlyMessage = super.parseSnapshot(snapshot);
                if (friendlyMessage != null)
                {
                    friendlyMessage.setId(snapshot.getKey());
                }
                return friendlyMessage;
            }

            @Override
            protected void populateViewHolder(MessageViewHolder viewHolder,
                                              FriendlyMessage friendlyMessage, int position)
            {
                mProgressBar.setVisibility(ProgressBar.INVISIBLE);
                viewHolder.messageTextView.setText(friendlyMessage.getText());
                viewHolder.messengerTextView.setText(friendlyMessage.getName());
                if (friendlyMessage.getPhotoUrl() == null)
                {
                    viewHolder.messengerImageView
                            .setImageDrawable(ContextCompat
                                    .getDrawable(ChatActivity.this,
                                            R.drawable.ic_account_circle_black_36dp));
                }
                else
                {
                    Glide.with(ChatActivity.this)
                            .load(friendlyMessage.getPhotoUrl())
                            .into(viewHolder.messengerImageView);
                }

                // write this message to the on-device index
                FirebaseAppIndex.getInstance().update(getMessageIndexable(friendlyMessage));

                // log a view action on it
                FirebaseUserActions.getInstance().end(getMessageViewAction(friendlyMessage));
            }
        };

        mFirebaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver()
        {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount)
            {
                super.onItemRangeInserted(positionStart, itemCount);
                int friendlyMessageCount = mFirebaseAdapter.getItemCount();
                int lastVisiblePosition =
                        mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
                // If the recycler view is initially being loaded or the
                // user is at the bottom of the list, scroll to the bottom
                // of the list to show the newly added message.
                if (lastVisiblePosition == -1 ||
                        (positionStart >= (friendlyMessageCount - 1) &&
                                lastVisiblePosition == (positionStart - 1)))
                {
                    mMessageRecyclerView.scrollToPosition(positionStart);
                }
            }
        });

        mMessageRecyclerView.setLayoutManager(mLinearLayoutManager);
        mMessageRecyclerView.setAdapter(mFirebaseAdapter);


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
                FriendlyMessage friendlyMessage = new
                        FriendlyMessage(mMessageEditText.getText().toString(),
                        mUsername,
                        mPhotoUrl);
                mFirebaseDatabaseReference.child(MESSAGES_CHILD)
                        .push().setValue(friendlyMessage);
                mMessageEditText.setText("");
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

    private Indexable getMessageIndexable(FriendlyMessage friendlyMessage)
    {
        mUsername = mUser.getEmail();
        friendlyMessage.setName(mUsername);

        PersonBuilder sender = Indexables.personBuilder()
                .setIsSelf(mUsername == friendlyMessage.getName())
                .setName(friendlyMessage.getName())
                .setUrl(MESSAGE_URL.concat(friendlyMessage.getId() + "/sender"));

        PersonBuilder recipient = Indexables.personBuilder()
                .setName(mUsername)
                .setUrl(MESSAGE_URL.concat(friendlyMessage.getId() + "/recipient"));

        Indexable messageToIndex = Indexables.messageBuilder()
                .setName(friendlyMessage.getText())
                .setUrl(MESSAGE_URL.concat(friendlyMessage.getId()))
                .setSender(sender)
                .setRecipient(recipient)
                .build();

        return messageToIndex;
    }

    private Action getMessageViewAction(FriendlyMessage friendlyMessage)
    {
        return new Action.Builder(Action.Builder.VIEW_ACTION)
                .setObject(friendlyMessage.getName(), MESSAGE_URL.concat(friendlyMessage.getId()))
                .setMetadata(new Action.Metadata.Builder().setUpload(false))
                .build();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult)
    {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }

    private void createAccount(String Email, String Password)
    {

        mFirebaseAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(ChatActivity.this, new OnCompleteListener<AuthResult>()
        {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                Log.d(TAG, "Create" + task.isSuccessful());
                if (task.isSuccessful())
                {
                    Toast.makeText(ChatActivity.this, "IT WORKED !", Toast.LENGTH_SHORT).show();
                }
                if (!task.isSuccessful())
                {
                    Toast.makeText(ChatActivity.this, "it failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void signIn(final String Email, final String Password)
    {

        mFirebaseAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(ChatActivity.this, new OnCompleteListener<AuthResult>()
        {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                Toast.makeText(ChatActivity.this, "Sign in Successful", Toast.LENGTH_SHORT).show();

                if (!task.isSuccessful())
                {
                    Toast.makeText(ChatActivity.this, "Sign in NOT Successful", Toast.LENGTH_SHORT).show();
                    Toast.makeText(ChatActivity.this, "Attempting to create user", Toast.LENGTH_SHORT).show();
                    createAccount(Email, Password);
                }
            }
        });

    }

    private void signOut()
    {
        mFirebaseAuth.signOut();
    }

}