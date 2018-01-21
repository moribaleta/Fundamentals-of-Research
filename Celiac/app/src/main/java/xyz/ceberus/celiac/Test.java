package xyz.ceberus.celiac;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
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

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

public class Test extends AppCompatActivity {
    LinearLayout linearLayoutQuestion;
    TextView txtName, txtAge;
    Button btnSubmit;
    ArrayList<Question> arrayListQuestion = new ArrayList<>();
    int arrIntAnswer[] = new int[16];
    int intCount = 0;
    UserData userData;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Test");
        linearLayoutQuestion = (LinearLayout) findViewById(R.id.linearQuestion);
        txtName = (TextView) findViewById(R.id.textName);
        txtAge = (TextView) findViewById(R.id.textAge);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
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
            txtAge.setText("Age: " + userData.getIntAge() + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        init();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    void init() {
        FileStorage fileStorage;
        try {
            fileStorage = new FileStorage(getBaseContext());
            arrayListQuestion = fileStorage.GetQuestions();
            initListData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void initListData() {

        for (final Question question : arrayListQuestion) {
            View viewToLoad = LayoutInflater.from(
                    getApplicationContext()).inflate(
                    R.layout.test_item, null);
            TextView textViewCount = (TextView) viewToLoad.findViewById(R.id.textCount);
            TextView textView = (TextView) viewToLoad.findViewById(R.id.txtQuestion);
            TextView textMore = (TextView) viewToLoad.findViewById(R.id.textMore);

            textViewCount.setText((intCount + 1) + ". ");
            textView.setText(question.getStrQuestion());
            Spinner spinner = (Spinner) viewToLoad.findViewById(R.id.spnAnswer);
            String arrStrAnswer[] = question.getArrAnswer();
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_text_view, arrStrAnswer);
            arrayAdapter.setDropDownViewResource(R.layout.spinner_text_view);
            spinner.setAdapter(arrayAdapter);
           /* TextView tv = (TextView) spinner.getSelectedView();
            tv.setTextColor(Color.BLACK);*/
            final int index = intCount;
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    arrIntAnswer[index] = i + 1;
                    Log.e("Test", question.getStrQuestion() + " : " + arrIntAnswer[index]);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            if (question.getStrInfo() != null) {
                textMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Test.this);
// ...Irrelevant code for customizing the buttons and title
                        dialogBuilder.setCancelable(true);
                        dialogBuilder.setNegativeButton(
                                "Close",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        LayoutInflater inflater = (Test.this).getLayoutInflater();
                        View dialogView = inflater.inflate(R.layout.more_information, null);
                        dialogBuilder.setView(dialogView);
                        TextView textInfo = (TextView) dialogView.findViewById(R.id.textInfo);
                        TextView textLink = (TextView) dialogView.findViewById(R.id.textLink);
                        textInfo.setText(question.getStrInfo());
                        textLink.setText(question.getStrLink());
                        AlertDialog alertDialog = dialogBuilder.create();
                        alertDialog.setTitle("More Information");
                        alertDialog.show();
                        Log.e("ALERT", "alert " + question.getStrInfo() + " - " + question.getStrLink());
                       /* AlertDialog.Builder builder1 = new AlertDialog.Builder(Test.this);

                        builder1.setMessage(question.getStrInfo()+"\n");
                        builder1.setCancelable(true);
                        builder1.setNegativeButton(
                                "Close",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alert11 = builder1.create();
                        alert11.show();*/


                        /*TextView textInfo = (TextView) dialog.findViewById(R.id.textInfo);
                        TextView textLink = (TextView) dialog.findViewById(R.id.textLink);
                        textInfo.setText(question.getStrInfo());
                        textLink.setText(question.getStrLink());*/
                    }
                });
            } else {
                textMore.setVisibility(View.GONE);
            }


            intCount++;
            linearLayoutQuestion.addView(viewToLoad);
        }

    }

    void submit() {
        final FileStorage fileStorage;
        try {
            fileStorage = new FileStorage(getBaseContext());
            String strName = userData.getStrName();
            int intAge = userData.getIntAge();

            String strAnswer = "";
            for (int intAnswer : arrIntAnswer) {
                strAnswer += intAnswer + "|";
            }
            strAnswer = strAnswer.substring(0, strAnswer.length() - 1);
            Log.e("Test", "userdata: " + strAnswer + " , " + strName + " , " + intAge);
            //final UserData userData = new UserData(strAnswer, strName, intAge, -1);

            final ProgressDialog dialog = ProgressDialog.show(this, "",
                    "Loading. Please wait...", true);
            final String finalStrAnswer = strAnswer;
            Thread thread = new Thread() {
                @Override
                public void run() {
                    userData.setStrDataset(finalStrAnswer);

                    UserData userDataResult = generateResult(userData);
                    userDataResult.setDate();
                    fileStorage.insertUserHistory(userDataResult);
                    dialog.dismiss();
                    Snackbar snackbar = Snackbar
                            .make(linearLayoutQuestion, "Process complete", Snackbar.LENGTH_LONG);
                    snackbar.show();
                    snackbar.addCallback(new Snackbar.Callback() {

                        @Override
                        public void onDismissed(Snackbar snackbar, int event) {
                            Intent intent = new Intent(Test.this, DataView.class);
                            intent.putExtra("ID", userData.getStrUserId());
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onShown(Snackbar snackbar) {

                        }
                    });

                }
            };

            thread.start();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private UserData generateResult(UserData userData) {
        userData.showDataFull();
        FileStorage fileStorage;
        try {
            fileStorage = new FileStorage(this);
            ArrayList<TrainingData> arrTrainingData = fileStorage.GetTraining();
            String strBuilder = "";
            for (TrainingData trainingDataRow : arrTrainingData) {
                strBuilder += trainingDataRow.getStrDataSet() + "|" + trainingDataRow.getIntResult() + "\n";
            }
            strBuilder = strBuilder.substring(0, strBuilder.length() - 1);
            Log.e("Test", "Traindata: " + strBuilder);
            Adaboost boosting = Adaboost.train(strBuilder, 10, 20, 0);
            int output = boosting.classify(userData.getDataSet().split("\\|"));
            Log.e("Test", "Result Data Label: " + userData.getDataSet() + " res: " + output);
            userData.setIntResult(output);
            return userData;
            //System.out.println("v2 Data Label"+strSample+" = "+label);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return userData;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(Test.this, History.class);
            userData.showData();
            intent.putExtra("ID", userData.getStrUserId());
            intent.putExtra("NAME", userData.getStrName());
            intent.putExtra("AGE", userData.getIntAge() + "");
            startActivity(intent);
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(Test.this, History.class);
        userData.showData();
        intent.putExtra("ID", userData.getStrUserId());
        intent.putExtra("NAME", userData.getStrName());
        intent.putExtra("AGE", userData.getIntAge() + "");
        startActivity(intent);
        finish(); // close this activity and return to preview activity (if there is any)
        super.onBackPressed();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Test Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
