package xyz.ceberus.celiac;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.concurrent.BrokenBarrierException;

public class History extends AppCompatActivity {
    private static final String TAG = "History";
    ListView listView;
    ArrayList<UserData>arrUserData = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("History");
        listView = (ListView)findViewById(R.id.listView);
        FileStorage fileStorage;
        try {
            fileStorage = new FileStorage(getApplicationContext());
            arrUserData = fileStorage.GetHistory();
            Log.e(TAG,"userdata: "+arrUserData.size());
            ArrayList<String>arrayList = new ArrayList<>();
            for(int i = 0; i<arrUserData.size(); i++){
                arrayList.add((i+1)+". "+arrUserData.get(i).getStrName());
            }
            ArrayAdapter<String>arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,android.R.id.text1,arrayList);
            listView.setAdapter(arrayAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                   openItem(i);
                }
            });
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void openItem(int position){
        Log.e(TAG,"open item: "+position);
        Intent intent = new Intent(this,DataView.class);
        UserData userData = arrUserData.get(position);
        intent.putExtra("NAME",userData.getStrName());
        intent.putExtra("DATASET",userData.getDataSet());
        intent.putExtra("AGE",""+userData.getIntAge());
        intent.putExtra("RESULT",""+userData.getIntResult());
        startActivity(intent);
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
