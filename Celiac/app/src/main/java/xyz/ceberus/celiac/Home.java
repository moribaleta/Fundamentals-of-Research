package xyz.ceberus.celiac;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Home extends AppCompatActivity {
    Button btnTest, btnHistory, btnAbouts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();
        btnTest = (Button)findViewById(R.id.btnTest);
        btnHistory = (Button)findViewById(R.id.btnHistory);
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
    }

    public void openTest(View v){
        Intent intent = new Intent(this,Test.class);
        this.startActivity(intent);
    }

    public void openHistory(View v){
        Intent intent = new Intent(this,History.class);
        this.startActivity(intent);
    }

    public void openAbout(View v){
        Intent intent = new Intent(this,Abouts.class);
        this.startActivity(intent);
    }

}
