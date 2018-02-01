package xyz.ceberus.celiac;

import java.util.ArrayList;

/**
 * Created by Eli on 1/31/2018.
 */
public class AdaboostData {
    int intResult;
    ArrayList<String>arrayListLog;

    AdaboostData(){

    }

    void setResult(int intResult){
        this.intResult = intResult;
    }

    int getIntResult(){
        return intResult;
    }

    void setArrayListLog(ArrayList<String>arrayListLog){
        this.arrayListLog = arrayListLog;
    }

    ArrayList<String>getArrayListLog(){
        return arrayListLog;
    }



}
