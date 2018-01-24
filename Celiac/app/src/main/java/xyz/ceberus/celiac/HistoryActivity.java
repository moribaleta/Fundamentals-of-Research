package xyz.ceberus.celiac;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {
    private static final String TAG = "History";
    ProgressDialog dialogTraining;
    //ListView listView;
    LinearLayout linearLayoutHistory;
    ArrayList<UserData> arrUserData = new ArrayList<>();
    UserData userData;
    FloatingActionButton fabAddItem;
    TextView textViewNoItem;
    LinearLayout linearLayoutResult;
    CoordinatorLayout coordinatorLayout;
    AppBarLayout appBar;
    //NestedScrollView nestedScroll;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String strName = intent.getStringExtra("NAME");
        String strID = intent.getStringExtra("ID");
        int intAge = Integer.parseInt(intent.getStringExtra("AGE"));
        //Log.e("UserData","id: "+strID+" name: "+strName+" age: "+intAge);
        userData = new UserData(strID, strName, intAge);
        setTitle("History");
        //setTitle("History");
        linearLayoutResult = (LinearLayout) findViewById(R.id.linearLayout_Result);
        //listView = (ListView)findViewById(R.id.listViewHistory);
        linearLayoutHistory = (LinearLayout) findViewById(R.id.linearLayout_History);
        fabAddItem = (FloatingActionButton) findViewById(R.id.fabAddTest);
        fabAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTest();
            }
        });
        textViewNoItem = (TextView) findViewById(R.id.textViewItemNone);
        init();
    }


    void startTest() {
        dialogTraining = ProgressDialog.show(this, "",
                "Loading. Please wait...", true);


        final Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    if(FileStorage.threadAdaboost.isAlive())
                        sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                /*while (FileStorage.threadAdaboost.isAlive()){
                    Log.e(TAG, "adaboost training is still running: " + FileStorage.threadAdaboost.isAlive());
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                dialogTraining.dismiss();
                Log.e(TAG, "adaboost training thread ended");*/
                openTest();
            }
        };
        thread.start();
    }


    void openTest() {
        Intent intent = new Intent(HistoryActivity.this, Test.class);
        //UserData userData = arrUserData.get(i);
        userData.showData();
        intent.putExtra("ID", userData.getStrUserId());
        intent.putExtra("NAME", userData.getStrName());
        intent.putExtra("AGE", userData.getIntAge() + "");
        startActivity(intent);
        finish();
    }


    public void init() {
        linearLayoutResult.startAnimation(AnimationUtils.loadAnimation(getBaseContext(), R.anim.fadein));

        FileStorage fileStorage;
        String strMonth[] = {"Jan", "Feb", "March", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

        try {
            fileStorage = new FileStorage(this);
            arrUserData = fileStorage.GetHistory(userData);
            Log.e(TAG, "userdata: " + arrUserData.size());
            ArrayList<String> arrayList = new ArrayList<>();
            for (int i = 0; i < arrUserData.size(); i++) {
                String strResult = "Negative";
                if (arrUserData.get(i).getIntResult() == 1) {
                    strResult = "Positive";
                }
                String strDate = arrUserData.get(i).getDate();
                String strTime = strDate.split(" ")[1];
                String strAmPm = strDate.split(" ")[2];
                strDate = strDate.split(" ")[0];
                String strDateSegment[] = strDate.split("-");
                String strTimeSegment[] = strTime.split(":");
                String strBuilder = "";
                try {
                    strBuilder = strMonth[Integer.parseInt(strDateSegment[1]) - 1] + ", " + strDateSegment[2] + ", " + strDateSegment[0] + " - " + strTimeSegment[0] + ":" + strTimeSegment[1] + " " + strAmPm;
                } catch (Exception e) {
                    Log.e("History", "date: " + strDate);
                    e.printStackTrace();
                    strBuilder = strMonth[Integer.parseInt(strDateSegment[1]) - 1] + ", " + strDateSegment[2] + ", " + strDateSegment[0] + " - " + strTimeSegment[0] + ":" + strTimeSegment[1];
                }
                arrayList.add("\t" + (i + 1) + ". " + strBuilder + " - " + strResult);
            }
            //Collections.reverse(arrayList);
            processArrayList(arrayList);
            /*ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,android.R.id.text1,arrayList);
            listView.setAdapter(arrayAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    openItem(i);
                }
            });*/
            if (arrUserData.size() > 0) {
                textViewNoItem.setVisibility(View.GONE);
                UserData currUserData = arrUserData.get(arrUserData.size()-1);
                TextView textViewName = (TextView) findViewById(R.id.textViewUserData);
                TextView textViewResult = (TextView) findViewById(R.id.textViewResult);
                TextView textViewDate = (TextView) findViewById(R.id.textViewDate);

                textViewName.setText("Name: " + currUserData.getStrName() + "\n" + "Age: " + currUserData.getIntAge());

                String strDate = currUserData.getDate();
                String strTime = strDate.split(" ")[1];
                String strAmPm = strDate.split(" ")[2];
                strDate = strDate.split(" ")[0];
                String strDateSegment[] = strDate.split("-");
                String strTimeSegment[] = strTime.split(":");
                String strBuilder = "";
                try {
                    strBuilder = strMonth[Integer.parseInt(strDateSegment[1]) - 1] + ", " + strDateSegment[2] + ", " + strDateSegment[0] + " - " + strTimeSegment[0] + ":" + strTimeSegment[1] + " " + strAmPm;
                } catch (Exception e) {
                    Log.e("History", "date: " + strDate);
                    e.printStackTrace();
                    strBuilder = strMonth[Integer.parseInt(strDateSegment[1]) - 1] + ", " + strDateSegment[2] + ", " + strDateSegment[0] + " - " + strTimeSegment[0] + ":" + strTimeSegment[1];
                }
                textViewDate.setText(strBuilder);

                CeliacType celiacType = new CeliacType(currUserData);
                textViewResult.setText(celiacType.getStrResult());

                Button btnViewCurrent = (Button) findViewById(R.id.btnCurrent);
                btnViewCurrent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openItem(arrUserData.size() - 1);
                    }
                });
            } else {
                linearLayoutResult.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void processArrayList(ArrayList<String> arrayList) {
        int i = 0;
        for (String strListItem : arrayList) {
            View viewToLoad = LayoutInflater.from(
                    getApplicationContext()).inflate(android.R.layout.simple_list_item_1, null);
            linearLayoutHistory.addView(viewToLoad);
            TextView textViewItem = (TextView) viewToLoad.findViewById(android.R.id.text1);
            textViewItem.setText(strListItem);
            final int finalI = i;
            textViewItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openItem(finalI);
                }
            });

            i++;

        }
    }

    public void openItem(int position) {
        Log.e(TAG, "open item: " + position);
        Intent intent = new Intent(this, DataView.class);
        UserData userData = arrUserData.get(position);
        intent.putExtra("ID", userData.getStrUserId());
        intent.putExtra("POSITION", position);
        /*intent.putExtra("NAME",userData.getStrName());
        intent.putExtra("DATASET",userData.getDataSet());
        intent.putExtra("AGE",""+userData.getIntAge());
        intent.putExtra("RESULT",""+userData.getIntResult());*/
        startActivity(intent);
        finish();
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
