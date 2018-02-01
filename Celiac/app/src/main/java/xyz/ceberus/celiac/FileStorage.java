package xyz.ceberus.celiac;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.concurrent.BrokenBarrierException;

/**
 * Created by Eli on 11/3/2017.
 */

public class FileStorage {

    private static Adaboost adaboost;
    public static Thread threadAdaboost;

    public static void runAdaboostGeneratorThread(final Context context){
        threadAdaboost = new Thread() {
            @Override
            public void run() {
                generateAdaboost(context);
            }
        };
        threadAdaboost.start();
    }



    public static void generateAdaboost(Context context) {
        FileStorage fileStorage;
        try {
            fileStorage = new FileStorage(context);
            ArrayList<TrainingData> arrTrainingData = fileStorage.GetTraining();
            String strBuilder = "";
            for (TrainingData trainingDataRow : arrTrainingData) {
                strBuilder += trainingDataRow.getStrDataSet() + "|" + trainingDataRow.getIntResult() + "\n";
            }
            strBuilder = strBuilder.substring(0, strBuilder.length() - 1);
            Log.e("Test", "Traindata: " + strBuilder);
            adaboost = Adaboost.train(strBuilder, 10, 25, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static UserData generateResult(UserData userData) {
        userData.showDataFull();
        try {
            //int output = adaboost.classify(userData.getDataSet().split("\\|"));
            AdaboostData adaboostData = adaboost.classifyData(userData.getDataSet().split("\\|"));
            Log.e("Test", "Result Data Label: " + userData.getDataSet() + " res: " + adaboostData.getIntResult());
            userData.setIntResult(adaboostData.getIntResult());
            userData.setAdaboostData(adaboostData);
            return userData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userData;
    }


    DictionaryHelper dictionary;
    Context context;

    FileStorage(Context context) throws BrokenBarrierException, InterruptedException {
        this.context = context;
        dictionary = new DictionaryHelper(context);
        dictionary.createDatabase();
        dictionary.open();
    }

    public ArrayList<UserData> GetUser(){
        ArrayList<UserData>arrayList = dictionary.getUserdata();
        //arrayList = GetHistory(arrayList);
        dictionary.close();
        return  arrayList;
    }

    public ArrayList<UserData> GetHistory(UserData userData) {
        ArrayList<UserData>arrayList = dictionary.getUserHistory(userData);
        dictionary.close();
        return arrayList;
    }

    public ArrayList<TrainingData>GetTraining(){
        ArrayList<TrainingData>arrTrainingData = dictionary.getTrainingData();
        dictionary.close();
        return arrTrainingData;
    }


    public ArrayList<Question> GetQuestions(){
        ArrayList<Question> arrayList = dictionary.getQuestion();
        dictionary.close();
        return arrayList;
    }


    public void createUser(UserData userData) {
        dictionary.createUser(userData);
        dictionary.close();
    }

    public void insertUserHistory(UserData userData){
        dictionary.insertUserDataHistory(userData);
        dictionary.close();
    }

    public UserData getUserDataById(String strId){
        UserData userData = dictionary.getUserDataById(strId);
        userData = dictionary.getData(userData);
        dictionary.close();
        return userData;
    }
}
