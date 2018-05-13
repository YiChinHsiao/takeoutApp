package cmpe275eat.takeoutapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.util.Log;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by yichinhsiao on 5/7/18.
 */

public class AdminIndexActivity extends AppCompatActivity {

    private Button pending_order;
    private Button add_menu;
    private Button remove_menu;
    private Button status_report;
    private Button popularity_report;
    private Button reset_order;
    private Button log_out;

    private FirebaseAuth auth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseRference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_index);

        pending_order = (Button) findViewById(R.id.admin_index_pending_order);
        add_menu = (Button) findViewById(R.id.admin_index_add_menu);
        remove_menu = (Button) findViewById(R.id.admin_index_remove_menu);
        status_report = (Button) findViewById(R.id.admin_index_status_report);
        popularity_report = (Button) findViewById(R.id.admin_index_popularity_report);
        reset_order = (Button) findViewById(R.id.admin_index_reset_order);
        log_out = (Button) findViewById(R.id.admin_index_log_out);

        pending_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final GMailSender sender = new GMailSender("garyhsiao1219@gmail.com",
                        "yichin0091");
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    public Void doInBackground(Void... arg) {
                        try {
                            sender.sendMail("Test Mail",
                                    "Hi, this is a test mail from TakeoutApp",
                                    "garyhsiao1219@gmail.com",
                                    "garyhsiao1219@gmail.com");
                        } catch (Exception e) {
                            Log.e("SendMail", e.getMessage(), e);
                        }
                        return null;
                    }
                }.execute();

            }
        });

        add_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminIndexActivity.this, AdminAddMenuActivity.class);
                startActivity(intent);
            }
        });

        remove_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminIndexActivity.this, AdminRemoveMenuActivity.class);
                startActivity(intent);
            }
        });

        status_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        popularity_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        reset_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Firebase.setAndroidContext(getApplicationContext());
                FirebaseApp.initializeApp(getApplicationContext());
                auth = FirebaseAuth.getInstance();
                mFirebaseDatabase = FirebaseDatabase.getInstance();
                mDatabaseRference = mFirebaseDatabase.getReference();
                mDatabaseRference.child("test").removeValue();
                Toast.makeText(getApplicationContext(), "Remove test", Toast.LENGTH_LONG).show();
            }
        });

        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminIndexActivity.this, LogoutActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
