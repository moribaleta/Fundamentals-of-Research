package xyz.ceberus.celiac;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

import static java.lang.Thread.sleep;

public class PrelimTest extends AppCompatActivity {
    private static final String TAG = "TEST";
    LinearLayout linearLayoutQuestion;
    TextView txtName, txtAge;
    Button btnSubmit;
    ArrayList<Question> arrayListQuestion = new ArrayList<>();
    int arrIntAnswer[] = new int[16];
    UserData userData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prelim_test);
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

        TextView textViewLink = (TextView)findViewById(R.id.textViewLink);
        textViewLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(PrelimTest.this);
// ...Irrelevant code for customizing the buttons and title
                dialogBuilder.setCancelable(true);
                dialogBuilder.setNegativeButton(
                        "Close",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                LayoutInflater inflater = (PrelimTest.this).getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.more_information, null);
                dialogBuilder.setView(dialogView);
                TextView textInfo = (TextView) dialogView.findViewById(R.id.textInfo);
                TextView textLink = (TextView) dialogView.findViewById(R.id.textLink);
                textInfo.setText("Gluten is a general name for the proteins found in wheat (wheatberries, durum, emmer, semolina, spelt, farina, farro, graham, KAMUT® khorasan wheat and einkorn), rye, barley and triticale – a cross between wheat and rye. Gluten helps foods maintain their shape, acting as a glue that holds food together. Gluten can be found in many types of foods, even ones that would not be expected.\n" +
                        "\n" +
                        "Common foods with gluten:\n" +
                        "•\tRice\n" +
                        "•\tBread\n" +
                        "•\tCereals\n" +
                        "•\tSnacks with Wheat\n");
                textLink.setText("https://celiac.org/live-gluten-free/glutenfreediet/what-is-gluten/#msV1FQp6eCLgrw2M.99");
                AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.setTitle("More Information about Gluten");
                alertDialog.show();
                //Log.e("ALERT", "alert " + question.getStrInfo() + " - " + question.getStrLink());
            }
        });

        init();

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
        int intCount = 0;
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
                textMore.append(question.strTitle);
                textMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(PrelimTest.this);
// ...Irrelevant code for customizing the buttons and title
                        dialogBuilder.setCancelable(true);
                        dialogBuilder.setNegativeButton(
                                "Close",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        LayoutInflater inflater = (PrelimTest.this).getLayoutInflater();
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
                    }
                });
            } else {
                textMore.setVisibility(View.GONE);
            }


            intCount++;
            linearLayoutQuestion.addView(viewToLoad);
            if(intCount>=3){
                break;
            }
        }

    }

    void submit() {
        final FileStorage fileStorage;
        try {
            fileStorage = new FileStorage(getBaseContext());
            String strName = userData.getStrName();
            int intAge = userData.getIntAge();
            String strAnswer = "";
            if(arrIntAnswer[0]==2&&arrIntAnswer[1]==2&&arrIntAnswer[2]==2) {
                strAnswer = "2|2|2|2|2|2|1|1|2|1|1|2|1|1|1|1|";
                startTest(fileStorage, strAnswer);
            }else {
                fileStorage.close();
                Intent intent = new Intent(PrelimTest.this,Test.class);
                //UserData userData = arrUserData.get(i);
                userData.showData();
                intent.putExtra("ID", userData.getStrUserId());
                intent.putExtra("NAME", userData.getStrName());
                intent.putExtra("AGE", userData.getIntAge() + "");
                intent.putExtra("Q1",arrIntAnswer[0]);
                intent.putExtra("Q2",arrIntAnswer[1]);
                intent.putExtra("Q3",arrIntAnswer[2]);
                startActivity(intent);
                finish();
                /*for (int intAnswer : arrIntAnswer) {
                    strAnswer += intAnswer + "|";
                }*/

            }
            strAnswer = strAnswer.substring(0, strAnswer.length() - 1);
            Log.e("Test", "userdata: " + strAnswer + " , " + strName + " , " + intAge);




            //final UserData userData = new UserData(strAnswer, strName, intAge, -1);

            /*final ProgressDialog dialog = ProgressDialog.show(this, "",
                    "Loading. Please wait...", true);
            final String finalStrAnswer = strAnswer;
            Thread thread = new Thread() {
                @Override
                public void run() {
                    userData.setStrDataset(finalStrAnswer);
                    UserData userDataResult = FileStorage.generateResult(userData);
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

            thread.start();*/


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startTest(final FileStorage fileStorage, String strAnswer) {

        final String finalStrAnswer = strAnswer;
        /*Thread thread = new Thread() {
            @Override
            public void run() {

                while (FileStorage.threadAdaboost.isAlive()) {
                    Log.e(TAG, "adaboost training is still running: " + FileStorage.threadAdaboost.isAlive());
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                userData.setStrDataset(finalStrAnswer);
                final UserData userDataResult = FileStorage.generateResult(userData);
                userDataResult.setDate();
                fileStorage.insertUserHistory(userDataResult);
                dialog.dismiss();

                    Snackbar snackbar = Snackbar
                            .make(linearLayoutQuestion, "Process complete", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                    snackbar.addCallback(new Snackbar.Callback() {

                        @Override
                        public void onDismissed(Snackbar snackbar, int event) {
                            showResult(userDataResult);
                        }

                        @Override
                        public void onShown(Snackbar snackbar) {

                        }
                    });
                }

            //}
        };*/
        final ProgressDialog dialog = ProgressDialog.show(this, "",
                "Generating result. Please wait...", true);

        new Thread(new Runnable() {
            @Override
            public void run() {

                while (FileStorage.threadAdaboost.isAlive()) {
                    Log.e(TAG, "adaboost training is still running: " + FileStorage.threadAdaboost.isAlive());
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                dialog.dismiss();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        userData.setStrDataset(finalStrAnswer);
                        final UserData userDataResult = FileStorage.generateResult(userData);
                        userDataResult.setDate();
                        fileStorage.insertUserHistory(userDataResult);
                        dialog.dismiss();
                        showResult(userDataResult);

                    }
                });
            }

        }).start();



        /*runOnUiThread(new Runnable() {
            @Override
            public void run() {

                while (FileStorage.threadAdaboost.isAlive()) {
                    Log.e(TAG, "adaboost training is still running: " + FileStorage.threadAdaboost.isAlive());
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                userData.setStrDataset(finalStrAnswer);
                final UserData userDataResult = FileStorage.generateResult(userData);
                userDataResult.setDate();
                fileStorage.insertUserHistory(userDataResult);
                dialog.dismiss();
                Snackbar snackbar = Snackbar
                        .make(linearLayoutQuestion, "Process complete", Snackbar.LENGTH_SHORT);
                snackbar.show();
                snackbar.addCallback(new Snackbar.Callback() {

                    @Override
                    public void onDismissed(Snackbar snackbar, int event) {
                        //
                        showResult(userDataResult);
                    }

                    @Override
                    public void onShown(Snackbar snackbar) {

                    }
                });

            }

        });*/

        //thread.start();


    }



    void showResult(UserData userDataResult) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(PrelimTest.this);

        dialogBuilder.setCancelable(false);
        dialogBuilder.setPositiveButton("proceed", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(PrelimTest.this, DataView.class);
                intent.putExtra("ID", userData.getStrUserId());
                startActivity(intent);
                dialog.cancel();
                finish();
            }
        });
        dialogBuilder.setTitle("Adaboost Log");
        String strBuilder = "";
        for (String strOut : userDataResult.getAdaboostData().getArrayListLog()) {
            strBuilder += "\u2022 " + strOut + "\n";
        }
        dialogBuilder.setMessage(strBuilder);
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }


   /* private UserData generateResult(UserData userData) {
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
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            goBack();
        }

        return super.onOptionsItemSelected(item);
    }

    void goBack() {
        Intent intent = new Intent(PrelimTest.this, HistoryActivity.class);
        userData.showData();
        intent.putExtra("ID", userData.getStrUserId());
        intent.putExtra("NAME", userData.getStrName());
        intent.putExtra("AGE", userData.getIntAge() + "");
        startActivity(intent);
        finish(); // close this activity and return to preview activity (if there is any)
    }

    @Override
    public void onBackPressed() {
        goBack();
        super.onBackPressed();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */


}
