package xyz.ceberus.cdiag;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;

public class Home extends AppCompatActivity {
    Button  btnAbouts;
    ImageButton btnTest, btnHistory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();
        btnTest = (ImageButton)findViewById(R.id.btnTest);
        btnHistory = (ImageButton)findViewById(R.id.btnHistory);
        btnAbouts = (Button)findViewById(R.id.btnAbout);
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTest(view);
            }
        });
        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHistory(view);
            }
        });
        btnAbouts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAbout(view);
            }
        });
        getTraining();
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
