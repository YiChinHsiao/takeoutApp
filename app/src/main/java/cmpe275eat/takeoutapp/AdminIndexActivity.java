package cmpe275eat.takeoutapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

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

            }
        });

        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminIndexActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}