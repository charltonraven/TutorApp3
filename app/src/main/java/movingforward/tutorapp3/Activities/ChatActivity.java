package movingforward.tutorapp3.Activities;

/**
 * Created by Jeebus Prime on 2/9/2017.
 */

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import de.hdodenhof.circleimageview.CircleImageView;
import movingforward.tutorapp3.Entities.User;
import movingforward.tutorapp3.R;
import movingforward.tutorapp3.TutChat.CodelabPreferences;
import movingforward.tutorapp3.TutChat.FriendlyMessage;

/*import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;*/

public class ChatActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener
{

    public static class MessageViewHolder extends RecyclerView.ViewHolder
    {
        public TextView messageTextView;
        public TextView messengerTextView;
        public CircleImageView messengerImageView;
        public ImageView messengerPicMessage;

        public MessageViewHolder(View v)
        {
            super(v);
            messageTextView = (TextView) itemView.findViewById(R.id.messageTextView);
            messengerTextView = (TextView) itemView.findViewById(R.id.messengerTextView);
            messengerImageView = (CircleImageView) itemView.findViewById(R.id.messengerImageView);
            messengerPicMessage = (ImageView) itemView.findViewById(R.id.pic_message);
        }
    }

    private static final String TAG = "MainActivity";
    public static String MESSAGES_CHILD;
    public static final int DEFAULT_MSG_LENGTH_LIMIT = Integer.MAX_VALUE;
    private static final int RC_TAKE_PICTURE = 101;
    private ContentValues values;
    private Uri mFileUri = null;
    public static final String ANONYMOUS = "anonymous";
    private static final String MESSAGE_SENT_EVENT = "message_sent";
    private FirebaseUser mFirebaseUser;
    private String mPhotoUrl;
    private SharedPreferences mSharedPreferences;
    public FirebaseAuth.AuthStateListener mAuthListener;
    public FirebaseAuth mFirebaseAuth;
    public boolean amITutor;
    private static String MESSAGE_URL = "https://tutitup-71061.firebaseio.com/";

    private DatabaseReference mFirebaseDatabaseReference;
    private FirebaseRecyclerAdapter<FriendlyMessage, MessageViewHolder> mFirebaseAdapter;

    private TextView classReminder;


    private Button mSendButton;
    private ImageButton bt_clock;
    private ImageButton mImageButton;
    private RecyclerView mMessageRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private ProgressBar mProgressBar;
    private EditText mMessageEditText;
    public User mUser;
    private String mUsername;
    public User nUser;
    private String nUsername;

    /*private SimpleDateFormat mFormatter = new SimpleDateFormat("MMMM dd yyyy hh:mm aa");

    private SlideDateTimeListener listener = new SlideDateTimeListener() {

        @Override
        public void onDateTimeSet(Date date)
        {
            sendEmail(mFormatter.format(date));
        }

        // Optional cancel listener
        @Override
        public void onDateTimeCancel()
        {
            Toast.makeText(ChatActivity.this,
                    "Canceled", Toast.LENGTH_SHORT).show();
        }
    };*/

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        classReminder = (TextView) findViewById(R.id.ClassReminder);

        Intent i = getIntent();

        mUser = (User) i.getSerializableExtra("mUser");
        mUsername = mUser.getID().toLowerCase();

        amITutor = mUser.getTutor() == 1 ? true : false;

        nUser = (User) i.getSerializableExtra("nUser");
        mUsername = mUser.getID().toLowerCase();

        nUser = (User) i.getSerializableExtra("nUser");
        nUsername = nUser.getID().toLowerCase();

        String className = i.getStringExtra("className").toLowerCase();

        User tUser = (nUser.getTutor() == 1 ? nUser : mUser);

        classReminder.setText("Tutor: " + tUser.getFirstName() + " " + tUser.getLastName() + ", " + className);


        if(mUser.getPermission().name()=="Teacher"|| mUser.getPermission().name()=="Tutor" && nUser.getPermission().name()=="Teacher"){
            className="teacher";
        }
        MESSAGES_CHILD = (mUsername.compareTo(nUsername) < 0 ? (mUsername + "_" + nUsername) : (nUsername + "_" + mUsername)) + "_" + className;

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
                    mPhotoUrl = mFirebaseUser.getPhotoUrl().toString() == null ? "n/a" : mFirebaseUser.getPhotoUrl().toString();
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
                if (friendlyMessage.getTutor() == 1)
                {
                    viewHolder.messageTextView.setTextColor(Color.BLUE);
                }
                else
                {
                    viewHolder.messageTextView.setTextColor(Color.BLACK);
                }

                try
                {
                    String returnString = friendlyMessage.getText();

                    viewHolder.messengerPicMessage.setImageDrawable(null);
                    viewHolder.messageTextView.setText("");

                    if (returnString.length() > 400)
                    {
                        Bitmap bitImage = decodeBase64(returnString);

                        viewHolder.messengerPicMessage.setImageBitmap(Bitmap.createScaledBitmap(bitImage, 1944, 2592, false));
                    }
                    else
                    {
                        viewHolder.messageTextView.setText(friendlyMessage.getText());
                    }
                } catch (Exception ex)
                {
                    //viewHolder.messageTextView.setText(friendlyMessage.getText());
                }

                viewHolder.messengerTextView.setText(friendlyMessage.getName());

                if (friendlyMessage.getPhotoUrl() == null || friendlyMessage.getPhotoUrl().equals("n/a"))
                {
                    if (friendlyMessage.getTutor() == 1)
                    {
                        viewHolder.messengerImageView
                                .setImageDrawable(ContextCompat
                                        .getDrawable(ChatActivity.this,
                                                R.drawable.ic_launcher));
                    }
                    else
                    {
                        viewHolder.messengerImageView
                                .setImageDrawable(ContextCompat
                                        .getDrawable(ChatActivity.this,
                                                R.drawable.ic_account_circle_black_36dp));
                    }
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
        bt_clock = (ImageButton) findViewById(R.id.bt_clock);
        mSendButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                FriendlyMessage friendlyMessage = new
                        FriendlyMessage(mMessageEditText.getText().toString(),
                        mUsername,
                        mPhotoUrl,
                        amITutor ? 1 : 0);
                mFirebaseDatabaseReference.child(MESSAGES_CHILD)
                        .push().setValue(friendlyMessage);
                mMessageEditText.setText("");
            }
        });

        bt_clock.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (amITutor)
                {
                    Calendar c = Calendar .getInstance();
                    Date today = new Date();
                    Date inOneYear = new Date();
                    TypedValue typedValue = new TypedValue();
                    getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
                    int color = typedValue.data;
                    try
                    {
                        today = mFormatter.parse(mFormatter.format(c.getTime()));
                        c.add(Calendar.YEAR, 1);
                        inOneYear = mFormatter.parse(mFormatter.format(c.getTime()));
                    }
                    catch (Exception ex)
                    {
                    }

                    new SlideDateTimePicker.Builder(getSupportFragmentManager())
                            .setListener(listener)
                            .setInitialDate(new Date())
                            .setMinDate(today)
                            .setMaxDate(inOneYear)
                            //.setTheme(SlideDateTimePicker.HOLO_DARK)
                            .setIndicatorColor(color)
                            .build()
                            .show();
                }
                else
                {
                    Toast.makeText(ChatActivity.this, "Ask your tutor to email an Appointment.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mImageButton = (ImageButton) findViewById(R.id.imagebutton);
        mImageButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                checkPermission();
            }
        });
    }

    private void checkPermission(){
        int permissionCheck = ContextCompat.checkSelfPermission(ChatActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    ChatActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            launchCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {

            case 1:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    launchCamera();
                }
                break;

            default:
                break;
        }
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

    private void launchCamera()
    {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(ChatActivity.this);

        builder.setTitle("Pic It Up!");

        builder.setItems(options, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int item)
            {
                if (options[item].equals("Take Photo"))
                {
//                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//
//                    startActivityForResult(intent, 1);

                    values = new ContentValues();
                    values.put(MediaStore.Images.Media.TITLE, "New Picture");
                    values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
                    mFileUri = getContentResolver().insert(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, mFileUri);
                    startActivityForResult(intent, 1);
                }
                else if (options[item].equals("Choose from Gallery"))
                {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    startActivityForResult(intent, 2);
                }
                else if (options[item].equals("Cancel"))
                {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if ((requestCode == 1 || requestCode == RC_TAKE_PICTURE) && resultCode == RESULT_OK)
        {
            //Bitmap picture = (Bitmap) data.getExtras().get("data");

            try
            {

                Bitmap thumbnail = MediaStore.Images.Media.getBitmap(
                        getContentResolver(), mFileUri);

                thumbnail = rotateBitmap(mFileUri.getPath(), thumbnail);
                String encodedImage = encodeImage(thumbnail);

                FriendlyMessage friendlyMessage = new
                        FriendlyMessage(encodedImage,
                        mUsername,
                        mPhotoUrl,
                        amITutor ? 1 : 0);
                mFirebaseDatabaseReference.child(MESSAGES_CHILD)
                        .push().setValue(friendlyMessage);


                mMessageEditText.setText("");
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        if ((requestCode == 2 || requestCode == RC_TAKE_PICTURE) && resultCode == RESULT_OK)
        {
            final Uri imageUri = data.getData();
            InputStream imageStream = null;
            try
            {
                imageStream = getContentResolver().openInputStream(imageUri);
            } catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }
            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

            Bitmap rotatedMap = rotateBitmap(imageUri.getPath(), selectedImage);

            String encodedImage = encodeImage(rotatedMap);

            FriendlyMessage friendlyMessage = new
                    FriendlyMessage(encodedImage,
                    mUsername,
                    mPhotoUrl,
                    amITutor ? 1 : 0);
            mFirebaseDatabaseReference.child(MESSAGES_CHILD)
                    .push().setValue(friendlyMessage);


            mMessageEditText.setText("");
        }
    }

    private String encodeImage(Bitmap bm)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 65, baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);

        return encImage;
    }

    public static Bitmap decodeBase64(String input)
    {
        byte[] decodedBytes = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    @Override
    public void onNewIntent(Intent intent)
    {
        super.onNewIntent(intent);
    }

    public static Bitmap rotateBitmap(String src, Bitmap bitmap)
    {
        try
        {
            int orientation = getExifOrientation(src);

            if (orientation == 1)
            {
                return bitmap;
            }

            Matrix matrix = new Matrix();
            switch (orientation)
            {
                case 0:
                    matrix.setRotate(-90);
                    break;
                case 2:
                    matrix.setScale(-1, 1);
                    break;
                case 3:
                    matrix.setRotate(180);
                    break;
                case 4:
                    matrix.setRotate(180);
                    matrix.postScale(-1, 1);
                    break;
                case 5:
                    matrix.setRotate(90);
                    matrix.postScale(-1, 1);
                    break;
                case 6:
                    matrix.setRotate(90);
                    break;
                case 7:
                    matrix.setRotate(-90);
                    matrix.postScale(-1, 1);
                    break;
                case 8:
                    matrix.setRotate(-90);
                    break;
                default:
                    return bitmap;
            }

            try
            {
                Bitmap oriented = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                bitmap.recycle();
                return oriented;
            } catch (OutOfMemoryError e)
            {
                e.printStackTrace();
                return bitmap;
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        return bitmap;
    }

    private static int getExifOrientation(String src) throws IOException
    {
        int orientation = 1;

        try
        {

            ExifInterface exif = new ExifInterface(src);
            orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);

            if (Build.VERSION.SDK_INT <= 5)
            {
                Class<?> exifClass = Class.forName("android.media.ExifInterface");
                Constructor<?> exifConstructor = exifClass.getConstructor(new Class[]{String.class});
                Object exifInstance = exifConstructor.newInstance(new Object[]{src});
                Method getAttributeInt = exifClass.getMethod("getAttributeInt", new Class[]{String.class, int.class});
                Field tagOrientationField = exifClass.getField("TAG_ORIENTATION");
                String tagOrientation = (String) tagOrientationField.get(null);
                orientation = (Integer) getAttributeInt.invoke(exifInstance, new Object[]{tagOrientation, 1});
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return orientation;
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
        switch (item.getItemId())
        {
            case R.id.sign_out_menu:
                mFirebaseAuth.signOut();
                mUsername = ANONYMOUS;
                startActivity(new Intent(this, LoginActivity.class));
                return true;
            case R.id.invite_menu:
            {

            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void sendEmail(String date)
    {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:" + nUser.getEmail()));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Tut It Up! Reminder");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Your tutor '" + nUsername + "' has scheduled a " +
                "tutoring appointment for you on " + date + "!");

        try
        {
            startActivity(Intent.createChooser(emailIntent, "Send email using..."));
        } catch (android.content.ActivityNotFoundException ex)
        {
            Toast.makeText(ChatActivity.this, "No email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    private Indexable getMessageIndexable(FriendlyMessage friendlyMessage)
    {
        mUsername = mUser.getEmail();
        String[] parts = mUsername.split("@");
        mUsername = parts[0];

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
                }
                if (!task.isSuccessful())
                {
                    mFirebaseUser.updatePassword(mUser.getPassword());
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

                if (!task.isSuccessful())
                {
                    createAccount(Email, Password);
                }
            }
        });
    }

}