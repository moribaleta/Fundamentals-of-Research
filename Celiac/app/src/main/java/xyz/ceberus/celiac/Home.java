package xyz.ceberus.celiac;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import java.util.ArrayList;

public class Home extends AppCompatActivity {
    Button  btnAbouts;
    Button btnTest; /*btnHistory*/;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();
        btnTest = (Button)findViewById(R.id.btnTest);
        //btnHistory = (Button)findViewById(R.id.btnHistory);
        btnAbouts = (Button)findViewById(R.id.btnAbout);
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTest(view);
            }
        });
       /* btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHistory(view);
            }
        });*/
        btnAbouts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAbout(view);
            }
        });
        getTraining();
        final Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.clockwise);
        btnTest.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                btnTest.startAnimation(animation);
                return true;
            }
        });

        btnAbouts.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                btnAbouts.startAnimation(animation);
                return true;
            }
        });

        final Animation entrance = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slidein);

        btnTest.startAnimation(entrance);
        btnAbouts.startAnimation(entrance);


       /* btnHistory.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.clockwise);
                btnHistory.startAnimation(animation);
                return true;
            }
        });*/

    }

    public void openTest(View v){
        Intent intent = new Intent(this,UserView.class);
        intent.putExtra("Test","Test");
        this.startActivity(intent);
    }

    public void openHistory(View v){
        Intent intent = new Intent(this,UserView.class);
        intent.putExtra("Test","History");
        this.startActivity(intent);
    }

    public void openAbout(View v){
        Intent intent = new Intent(this,Abouts.class);
        this.startActivity(intent);
    }

    public void getTraining() {
        FileStorage fileStorage;
        try {
            fileStorage = new FileStorage(this);
            ArrayList<TrainingData>arrTraining = fileStorage.GetTraining();
            Log.e("Training Data","training: "+arrTraining.size());
            /*for(TrainingData trainingData: arrTraining){
                Log.e("Training Data",trainingData.getStrDataSet()+" ... "+trainingData.getIntResult());
            }*/
        }catch (Exception e){

        }
    }
}
