package xyz.ceberus.celiac;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class DataView extends AppCompatActivity {
    UserData userData;
    TextView txtName, txtAge, txtResult,txtDate;
    //ListView listViewData;
    LinearLayout linearData;
    ArrayList<Question> arrQuestion = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
       /* String strName = intent.getStringExtra("NAME");
        String strDataSet = intent.getStringExtra("DATASET");
        int intAge = Integer.parseInt(intent.getStringExtra("AGE"));
        int intResult = Integer.parseInt(intent.getStringExtra("RESULT"));*/
        /*userData = new UserData(strDataSet, strName, intAge, intResult);*/
        String strId = intent.getStringExtra("ID");
        getUserData(strId);
        txtName = (TextView) findViewById(R.id.textName);
        txtAge = (TextView) findViewById(R.id.textAge);
        txtResult = (TextView) findViewById(R.id.txtResult);
        txtDate = (TextView)findViewById(R.id.txtDate);
        //listViewData = (ListView) findViewById(R.id.listViewData);
        linearData = (LinearLayout)findViewById(R.id.linearData);
        processData();
    }

    private void getUserData(String strId) {
        FileStorage fileStorage;
        try{
            fileStorage = new FileStorage(this);
            userData = fileStorage.getUserDataById(strId);
            userData.showDataFull();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private void processData() {
        txtName.setText("Name: " + userData.getStrName());
        txtAge.setText("Age: " + userData.getIntAge());
        txtDate.setText("Date: "+userData.getDate());
        String strResult = "NEGATIVE";
        if (userData.getIntResult() > -1)
            strResult = "POSITIVE";
        txtResult.setText("Result: " + strResult);
        String strData = userData.getDataSet();
        strData = strData.replace("|",",");
        //int arrIntData[] = new int[10];
        ArrayList<Integer>arrIntData = new ArrayList<>();
        for (String strDataItem : strData.split(",")) {
            int intData = Integer.parseInt(strDataItem);
            Log.e("DATAVIEW",""+strDataItem+": "+intData);
            arrIntData.add(intData);
        }
        FileStorage fileStorage;

        try {
            fileStorage = new FileStorage(getApplicationContext());
            arrQuestion = fileStorage.GetQuestions();
            //ArrayList<String> arrListData = new ArrayList<>();
            int intCount = 0;

            for (Question question : arrQuestion) {
                Log.e("DATAVIEW", question.getStrQuestion());
                String arrAnswer[] = question.getArrAnswer();
                int intIndex = arrIntData.get(intCount);
                View viewToLoad = LayoutInflater.from(
                        getApplicationContext()).inflate(
                        R.layout.data_item, null);
                //String strBuilder = (intCount + 1) + ".  " + question.getStrQuestion() + "\n    " + arrAnswer[intIndex-1];
                //arrListData.add(strBuilder);
                //Log.e("DataView", "data: " + strBuilder);
                TextView txtCount = (TextView)viewToLoad.findViewById(R.id.txtCount);
                TextView txtQuestion = (TextView)viewToLoad.findViewById(R.id.txtQuestion);
                TextView txtAnswer = (TextView)viewToLoad.findViewById(R.id.txtAnswer);
                txtCount.setText((intCount + 1) + ".  ");
                txtQuestion.setText(question.getStrQuestion());
                txtAnswer.setText(arrAnswer[intIndex-1]);
                intCount++;
                linearData.addView(viewToLoad);
            }
            //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, arrListData);
            //listViewData.setAdapter(arrayAdapter);


        } catch (Exception e) {
            e.printStackTrace();
        }
        //strDataset = strDataset.replace("|","\n");
        //txtData.setText("DATA:\n"+strDataset);
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
