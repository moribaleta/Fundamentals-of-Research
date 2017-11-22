package xyz.ceberus.celiac;

import android.content.Context;

import java.util.ArrayList;
import java.util.concurrent.BrokenBarrierException;

/**
 * Created by Eli on 11/3/2017.
 */

public class FileStorage {
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
