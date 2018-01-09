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
    UserData userData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        String strName = intent.getStringExtra("NAME");
        String strID = intent.getStringExtra("ID");
        int intAge = Integer.parseInt(intent.getStringExtra("AGE"));
        //Log.e("UserData","id: "+strID+" name: "+strName+" age: "+intAge);
        userData = new UserData(strID, strName, intAge);
        setTitle(strName);
        //setTitle("History");
        listView = (ListView)findViewById(R.id.listView);
        init();
    }

    public void init(){
        FileStorage fileStorage;
        String  strMonth[] = {"Jan","Feb","March","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};

        try {
            fileStorage = new FileStorage(this);
            arrUserData = fileStorage.GetHistory(userData);
            Log.e(TAG,"userdata: "+arrUserData.size());
            ArrayList<String>arrayList = new ArrayList<>();
            for(int i = 0; i<arrUserData.size(); i++){
                String strResult = "Negative";
                if(arrUserData.get(i).getIntResult()==1){
                    strResult = "Positive";
                }
                String strDate = arrUserData.get(i).getDate();
                String strTime = strDate.split(" ")[1];
                String strAmPm = strDate.split(" ")[2];
                strDate = strDate.split(" ")[0];
                String strDateSegment[] = strDate.split("-");
                String strTimeSegment[] = strTime.split(":");
                String strBuilder = "";
                try{
                    strBuilder = strMonth[Integer.parseInt(strDateSegment[1])-1] + ", "+ strDateSegment[2]+", "+strDateSegment[0]+" - "+strTimeSegment[0]+":"+strTimeSegment[1]+" "+strAmPm;
                }catch(Exception e){
                    Log.e("History","date: "+strDate);
                    e.printStackTrace();
                    strBuilder = strMonth[Integer.parseInt(strDateSegment[1])-1] + ", "+ strDateSegment[2]+", "+strDateSegment[0]+" - "+strTimeSegment[0]+":"+strTimeSegment[1];
                }
                arrayList.add("\t"+(i+1)+". "+strResult+" - "+strBuilder);
            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,android.R.id.text1,arrayList);
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
        intent.putExtra("ID",userData.getStrUserId());
        intent.putExtra("POSITION",position);
        /*intent.putExtra("NAME",userData.getStrName());
        intent.putExtra("DATASET",userData.getDataSet());
        intent.putExtra("AGE",""+userData.getIntAge());
        intent.putExtra("RESULT",""+userData.getIntResult());*/
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
