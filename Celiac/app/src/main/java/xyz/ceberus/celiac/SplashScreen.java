package xyz.ceberus.celiac;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();

        /*Thread thread = new Thread() {
            @Override
            public void run() {
                FileStorage.generateAdaboost(getBaseContext());
                openHome();
            }
        };

        thread.start();*/
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                FileStorage.runAdaboostGeneratorThread(getBaseContext());
                openHome();
            }
        }, 5000);
    }

    void openHome() {
        Intent mainIntent = new Intent(SplashScreen.this, Home.class);
        SplashScreen.this.startActivity(mainIntent);
        SplashScreen.this.finish();
    }
}
