package xyz.ceberus.celiac;

/**
 * Created by Eli on 11/11/2017.
 */

public class TrainingData {
    String strDataSet;
    int intResult;

    TrainingData(String strDataSet,int intResult){
        this.strDataSet = strDataSet;
        this.intResult = intResult;
    }

    public void setStrDataSet(String strDataSet){
        this.strDataSet = strDataSet;
    }

    public void setIntResult(int intResult){
        this.intResult = intResult;
    }

    public String getStrDataSet(){
        return strDataSet;
    }

    public int getIntResult(){
        return intResult;
    }


}
