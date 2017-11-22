package xyz.ceberus.cdiag;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class Test extends AppCompatActivity {
    LinearLayout linearLayoutQuestion;
    TextView txtName, txtAge;
    Button btnSubmit;
    ArrayList<Question>arrayListQuestion = new ArrayList<>();
    int arrIntAnswer[] = new int[16];
    int intCount = 0;
    UserData userData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Test");
        linearLayoutQuestion = (LinearLayout)findViewById(R.id.linearQuestion);
        txtName = (TextView)findViewById(R.id.textName);
        txtAge = (TextView)findViewById(R.id.textAge);
        btnSubmit = (Button)findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
            }
        });
        try {
            Intent intent = getIntent();

            String strName = intent.getStringExtra("NAME");
            String strID = intent.getStringExtra("ID");
            int intAge = Integer.parseInt(intent.getStringExtra("AGE"));
            //Log.e("UserData","id: "+strID+" name: "+strName+" age: "+intAge);
            userData = new UserData(strID, strName, intAge);
            txtName.setText(userData.getStrName());
            txtAge.setText("Age: "+userData.getIntAge()+"");
        }catch (Exception e){
            e.printStackTrace();
        }
        init();
    }

    void init(){
        FileStorage fileStorage;
        try {
            fileStorage = new FileStorage(getBaseContext());
            arrayListQuestion = fileStorage.GetQuestions();
            initListData();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    void initListData(){

        for(final Question question: arrayListQuestion) {
            View viewToLoad = LayoutInflater.from(
                    getApplicationContext()).inflate(
                    R.layout.test_item, null);
            TextView textView = (TextView)viewToLoad.findViewById(R.id.txtQuestion);
            textView.setText((intCount+1)+". "+question.getStrQuestion());
            Spinner spinner = (Spinner)viewToLoad.findViewById(R.id.spnAnswer);
            String arrStrAnswer[] = question.getArrAnswer();
            ArrayAdapter<String>arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrStrAnswer);
            arrayAdapter.setDropDownViewResource(android.R.layout
                    .simple_spinner_dropdown_item);
            spinner.setAdapter(arrayAdapter);
            final int index = intCount;
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    arrIntAnswer[index] = i+1;
                    Log.e("Test",question.getStrQuestion()+" : "+arrIntAnswer[index]);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });


            intCount++;
            linearLayoutQuestion.addView(viewToLoad);
        }

    }

    void submit(){
        FileStorage fileStorage;
        try {
            fileStorage = new FileStorage(getBaseContext());
            String strName = userData.getStrName();
            int intAge =  userData.getIntAge();
            if(intAge>=16) {
                String strAnswer = "";
                for (int intAnswer : arrIntAnswer) {
                    strAnswer += intAnswer + "|";
                }
                strAnswer = strAnswer.substring(0, strAnswer.length() - 1);
                Log.e("Test", "userdata: " + strAnswer + " , " + strName + " , " + intAge);
                //final UserData userData = new UserData(strAnswer, strName, intAge, -1);
                userData.setStrDataset(strAnswer);
                UserData userDataResult = generateResult(userData);
                userDataResult.setDate();
                fileStorage.insertUserHistory(userDataResult);

                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(this);
                }
                builder.setTitle("Success!")
                        .setMessage("We will now generate the result")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Test.this,DataView.class);
                                intent.putExtra("ID",userData.getStrUserId());
                                /*intent.putExtra("NAME",userData.getStrName());
                                intent.putExtra("DATASET",userData.getDataSet());
                                intent.putExtra("AGE",""+userData.getIntAge());
                                intent.putExtra("RESULT",""+userData.getIntResult());
                                intent.putExtra("DATE",userData.getDate());*/
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }else{
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(this);
                }
                builder.setTitle("Ooops!")
                        .setMessage("Age must be 16 above")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private UserData generateResult(UserData userData) {
        userData.showDataFull();
        FileStorage fileStorage;
        try{
            fileStorage = new FileStorage(this);
            ArrayList<TrainingData>arrTrainingData = fileStorage.GetTraining();
            String strBuilder = "";
            for(TrainingData trainingDataRow: arrTrainingData){
                strBuilder += trainingDataRow.getStrDataSet()+"|"+trainingDataRow.getIntResult()+"\n";
            }
            strBuilder = strBuilder.substring(0,strBuilder.length()-1);
            Log.e("Test","Traindata: "+strBuilder);
            Adaboost boosting = Adaboost.train(strBuilder, 10, 10, 0);
            int output = boosting.classify(userData.getDataSet().split("\\|"));
            Log.e("Test","Result Data Label: "+userData.getDataSet()+" res: "+output);
            userData.setIntResult(output);
            return userData;
            //System.out.println("v2 Data Label"+strSample+" = "+label);

        }catch (Exception e){
            e.printStackTrace();
        }
        return userData;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
}
