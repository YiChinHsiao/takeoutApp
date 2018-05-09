package cmpe275eat.takeoutapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.text.TextUtils;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

import static android.content.ContentValues.TAG;

public class RegisterActivity extends Activity{
    private EditText reg_password;
    private EditText reg_email;
    private Button reg_register;
    private Button reg_goSignIn;
    private RadioButton btn_reg_admin;
    private RadioButton btn_reg_customer;
    private RadioGroup btnGroup_register;
    FirebaseAuth mAuth;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseRference;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
//        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

//        Firebase.setAndroidContext(this);
//        mRef = new Firebase("https://takeoutapp-277.firebaseio.com/");

        mAuth = FirebaseAuth.getInstance();

        initFirebase();
        initButton();
        RegisterButton();
        GoSignInButton();
    }

    private void initButton() {
        reg_password = (EditText)findViewById(R.id.register_password);
        reg_email = (EditText)findViewById(R.id.register_email);

        reg_register = (Button) findViewById(R.id.btn_register);
        reg_goSignIn = (Button)findViewById(R.id.btn_goToSignIn);

        btn_reg_admin = (RadioButton) findViewById(R.id.rbtn_admin);
        btn_reg_customer = (RadioButton)findViewById(R.id.rbtn_cus);
        btnGroup_register = (RadioGroup)findViewById(R.id.radioGroup_register);

    }


    private void initFirebase() {
        FirebaseApp.initializeApp(this);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseRference = mFirebaseDatabase.getReference();
//        mDatabaseRference.keepSynced(true);
    }

    protected void RegisterButton(){
        reg_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = reg_email.getText().toString().trim();
                String password = reg_password.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Please enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Please enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 4) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 4 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // check radio button which is selected
                if(checkAnswer() == null) {
                    Toast.makeText(getApplicationContext(), "Please choose register as Admin or Customer", Toast.LENGTH_SHORT).show();
                    return;
                }

                //check user email exist or not
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    // Name, email address
    //                String name = user.getDisplayName();
                    String getEmail = user.getEmail();

                    // Check if user's email is verified
    //                boolean emailVerified = user.isEmailVerified();

                    // The user's ID, unique to the Firebase project. Do NOT use this value to
                    // authenticate with your backend server, if you have one. Use
                    // FirebaseUser.getToken() instead.
                    String uid = user.getUid();
                    Toast.makeText(RegisterActivity.this, getEmail, Toast.LENGTH_SHORT).show();
                    Toast.makeText(RegisterActivity.this, "Email ready exist",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                //Sign up new users with firebase auth
                mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            // save data to database.
                            User newUser = new User(UUID.randomUUID().toString(),reg_email.getText().toString(),reg_password.getText().toString(), checkAnswer());
                            updateUser(newUser);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());

    //                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    //                        if (user != null) {
    //                            String getEmail = user.getEmail();
    //                            Toast.makeText(RegisterActivity.this, getEmail, Toast.LENGTH_LONG).show();
    //                            Toast.makeText(RegisterActivity.this, "auth fail",
    //                                    Toast.LENGTH_SHORT).show();
    //                        }

                            Toast.makeText(RegisterActivity.this, "auth fail",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private String checkAnswer() {
        if (btn_reg_admin.isChecked()) {
            return btn_reg_admin.getText().toString();
        }
        if (btn_reg_customer.isChecked()) {
            return btn_reg_customer.getText().toString();
        }
        return null;
    }

    private void updateUser(User user) {
        mDatabaseRference.child("users").child(user.getUid()).child("email").setValue(user.getEmail());
        mDatabaseRference.child("users").child(user.getUid()).child("password").setValue(user.getPassword());
        mDatabaseRference.child("users").child(user.getUid()).child("type").setValue(user.getType());
        Toast.makeText(RegisterActivity.this, "Register Success!", Toast.LENGTH_SHORT).show();
        clearEditText();
    }

    private void clearEditText() {
        reg_email.setText("");
        reg_password.setText("");
    }

    protected void GoSignInButton(){
        reg_goSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // go to sign in page
                Intent signInIntent = new Intent(RegisterActivity.this, SigninActivity.class);
                startActivity(signInIntent);
            }
        });
    }

}
