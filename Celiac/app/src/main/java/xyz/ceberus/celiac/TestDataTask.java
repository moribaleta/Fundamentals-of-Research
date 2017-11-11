package xyz.ceberus.celiac;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import java.util.ArrayList;
import java.util.concurrent.BrokenBarrierException;

/**
 * Created by Eli on 11/10/2017.
 */

public class TestDataTask extends AsyncTask{
    //ProgressDialog progressDialog;
    Context context;
    TestDataTask(Context context){
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        /*progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Doing something, please wait.");
        progressDialog.show();*/
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        UserData userData = (UserData) objects[0];
        userData = generateResult(userData);
        //progressDialog.dismiss();
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(context);
        }
        FileStorage fileStorage = null;
        try {
            fileStorage = new FileStorage(context);
            fileStorage.InsertData(userData);
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        final UserData userOutput = userData;
        builder.setTitle("Success!")
                .setMessage("We will now generate the result")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(context,DataView.class);
                        intent.putExtra("NAME",userOutput.getStrName());
                        intent.putExtra("DATASET",userOutput.getDataSet());
                        intent.putExtra("AGE",""+userOutput.getIntAge());
                        intent.putExtra("RESULT",""+userOutput.getIntResult());
                        context.startActivity(intent);
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
        return null;
    }


    private UserData generateResult(UserData userData) {
        FileStorage fileStorage;
        try{
            fileStorage = new FileStorage(context);
            ArrayList<UserData> arrUserData = fileStorage.GetHistory();
            String strBuilder = "";
            for(UserData userDataRow: arrUserData){
                strBuilder += userDataRow.getDataSet()+"|"+userDataRow.getIntResult()+"\n";
            }
            strBuilder = strBuilder.substring(0,strBuilder.length()-1);
            Log.e("Test","Traindata: "+strBuilder);
            Adaboost boosting2 = Adaboost.train(strBuilder, 10, 10, 0);
            int output = boosting2.classify(userData.getDataSet().split("\\|"));
            //int label = boosting2.classify(strSample.split("\\|"));
            Log.e("Test","Result Data Label: "+userData.getDataSet()+" res: "+output);
            userData.setIntResult(output);
            return userData;
            //System.out.println("v2 Data Label"+strSample+" = "+label);

        }catch (Exception e){
            e.printStackTrace();
        }
        return userData;
    }
}
