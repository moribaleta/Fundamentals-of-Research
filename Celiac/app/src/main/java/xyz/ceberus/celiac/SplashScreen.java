package xyz.ceberus.celiac;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class SplashScreen extends AppCompatActivity {
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();
        getWindow().getAttributes().windowAnimations = R.style.Fade;
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.colorAccent), android.graphics.PorterDuff.Mode.MULTIPLY);
        final ImageView imageView = (ImageView)findViewById(R.id.imageView);
        Animation anim = AnimationUtils.loadAnimation(getBaseContext(), R.anim.fadein);
        anim.setDuration(1000);
        imageView.startAnimation(anim);


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
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        imageView.startAnimation(AnimationUtils.loadAnimation(getBaseContext(),R.anim.slidein2));
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                openHome();
                            }
                        },800);
                    }
                });

            }
        }, 7000);
    }

    void openHome() {
        Intent mainIntent = new Intent(SplashScreen.this, Home.class);
        /*Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();*/
        SplashScreen.this.startActivity(mainIntent);
        SplashScreen.this.finish();
    }
}
