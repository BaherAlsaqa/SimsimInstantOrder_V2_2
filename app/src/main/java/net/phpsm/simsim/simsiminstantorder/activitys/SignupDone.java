package net.phpsm.simsim.simsiminstantorder.activitys;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.Button;

import net.phpsm.simsim.simsiminstantorder.R;

public class SignupDone extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_done);

        Thread mythread = new Thread(){
            @Override
            public void run() {
                try {

                    sleep(1000);
                    startActivity(new Intent(getApplicationContext(),ValidateSMS.class));
                    finish();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        mythread.start();

    }


}
