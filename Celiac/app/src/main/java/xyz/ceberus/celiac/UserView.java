package xyz.ceberus.celiac;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class UserView extends AppCompatActivity {
    UserData userData;
    ListView listViewUser;
    String strIntentSend;
    ArrayList<UserData>arrUserData = new ArrayList<>();
    Boolean blTest =false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        strIntentSend = intent.getStringExtra("Test");
        setTitle("History");
        if(strIntentSend.equals("Test")){
            blTest = true;
            setTitle("Test");
        }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                createUser();
            }
        });

        listViewUser = (ListView)findViewById(R.id.listUser);
        init();
    }
    EditText inputName;
    EditText inputAge;
    Button btnProceed;
    Button btnCancel;
    private void createUser() {
        userData = new UserData();

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.alert_dialog_user);
        dialog.setTitle("Add User");
        inputName = (EditText)dialog.findViewById(R.id.editName);
        inputAge = (EditText)dialog.findViewById(R.id.editAge);
        btnCancel = (Button)dialog.findViewById(R.id.btnCancel);
        btnProceed = (Button)dialog.findViewById(R.id.btnProceed);
        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userData.setStrName(inputName.getText().toString());
                userData.setIntAge(Integer.parseInt(inputAge.getText().toString()));
                if(userData.getIntAge()>=16) {
                    saveUser(userData);
                    Log.e("USERDATA", "name: " + userData.getStrName() + " age: " + userData.getIntAge());
                    Intent intent = getIntent();
                    intent.putExtra("Test", strIntentSend);
                    finish();
                    startActivity(intent);
                }else{
                    Snackbar snackbar = Snackbar
                            .make(listViewUser, "User must be 14 or above", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        dialog.show();


        /*AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add User");

// Set up the input
        inputName = new EditText(this);
        inputAge = new EditText(this);
        inputName.setInputType(InputType.TYPE_CLASS_TEXT);
        inputAge.setInputType(InputType.TYPE_CLASS_NUMBER);
        inputName.setHint("Fullname");
        inputAge.setHint("Age");
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(inputName);
        layout.addView(inputAge);
        layout.setPadding(16,10,16,10);
        builder.setView(layout);

// Set up the buttons
        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                userData.setStrName(inputName.getText().toString());
                userData.setIntAge(Integer.parseInt(inputAge.getText().toString()));
                saveUser(userData);
                Log.e("USERDATA","name: "+userData.getStrName()+" age: "+userData.getIntAge());
                Intent intent = getIntent();
                intent.putExtra("Test",strIntentSend);
                finish();
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();*/
    }

    private void saveUser(UserData userData) {
        FileStorage fileStorage;
        try{
            fileStorage = new FileStorage(this);
            fileStorage.createUser(userData);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void init() {
        try {
            arrUserData = getUserList();
            if(arrUserData.size()>0) {
                ArrayList<String> arrStrUser = new ArrayList<>();
                int intCount = 1;
                for (UserData userData : arrUserData) {
                    arrStrUser.add("\t"+intCount + ". " + userData.getStrName());
                    intCount++;
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrStrUser);
                listViewUser.setAdapter(arrayAdapter);
                listViewUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        if(blTest) {//test
                            Intent intent = new Intent(UserView.this, Test.class);
                            UserData userData = arrUserData.get(i);
                            userData.showData();
                            intent.putExtra("ID", userData.getStrUserId());
                            intent.putExtra("NAME", userData.getStrName());
                            intent.putExtra("AGE", userData.getIntAge() + "");
                            startActivity(intent);
                        }else {//history
                            Intent intent = new Intent(UserView.this, History.class);
                            UserData userData = arrUserData.get(i);
                            userData.showData();
                            intent.putExtra("ID", userData.getStrUserId());
                            intent.putExtra("NAME", userData.getStrName());
                            intent.putExtra("AGE", userData.getIntAge() + "");
                            startActivity(intent);
                        }
                    }
                });
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private ArrayList<UserData> getUserList(){
        FileStorage fileStorage;
        try{
            fileStorage = new FileStorage(this);
            return  fileStorage.GetUser();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
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
