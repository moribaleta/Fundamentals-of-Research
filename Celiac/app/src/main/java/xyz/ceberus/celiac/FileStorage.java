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

    public ArrayList<UserData> GetHistory() {
        ArrayList<UserData> arrayList = dictionary.getHistory();
        dictionary.close();
        return arrayList;
    }

    public ArrayList<Question> GetQuestions(){
        ArrayList<Question> arrayList = dictionary.getQuestion();
        dictionary.close();
        return arrayList;
    }

    public void InsertData(UserData userData) {
        dictionary.InsertData(userData);
        dictionary.close();
    }

}
