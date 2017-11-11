package xyz.ceberus.celiac;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class DataView extends AppCompatActivity {
    UserData userData;
    TextView txtName, txtAge, txtResult;
    ListView listViewData;
    ArrayList<Question> arrQuestion = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        String strName = intent.getStringExtra("NAME");
        String strDataSet = intent.getStringExtra("DATASET");
        int intAge = Integer.parseInt(intent.getStringExtra("AGE"));
        int intResult = Integer.parseInt(intent.getStringExtra("RESULT"));
        userData = new UserData(strDataSet, strName, intAge, intResult);
        txtName = (TextView) findViewById(R.id.txtName);
        txtAge = (TextView) findViewById(R.id.txtAge);
        txtResult = (TextView) findViewById(R.id.txtResult);
        listViewData = (ListView) findViewById(R.id.listViewData);
        processData();
    }

    private void processData() {
        txtName.setText("Name: " + userData.getStrName());
        txtAge.setText("Age: " + userData.getIntAge());
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
            ArrayList<String> arrListData = new ArrayList<>();
            int intCount = 0;
            for (Question question : arrQuestion) {
                Log.e("DATAVIEW", question.getStrQuestion());
                String arrAnswer[] = question.getArrAnswer();
                int intIndex = arrIntData.get(intCount);
                String strBuilder = (intCount + 1) + ".  " + question.getStrQuestion() + "\n    " + arrAnswer[intIndex-1];
                arrListData.add(strBuilder);
                Log.e("DataView", "data: " + strBuilder);
                intCount++;
            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, arrListData);
            listViewData.setAdapter(arrayAdapter);
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
