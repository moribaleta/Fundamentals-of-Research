package xyz.ceberus.celiac;

/**
 * Created by Eli on 11/3/2017.
 */

public class UserData {

    public String strDataset;
    public String strName;
    public int intAge;
    public int intResult;

    public UserData( String strDataset,String strName, int intAge){
        this.strDataset = strDataset;
        this.strName = strName;
        this.intAge = intAge;
    }

    public UserData( String strDataset,String strName, int intAge, int intResult){
        this.strDataset = strDataset;
        this.strName = strName;
        this.intAge = intAge;
        this.intResult = intResult;
    }

    public void setStrDataset(String strDataset){
        this.strDataset = strDataset;
    }
    public void setStrName(String strName){
        this.strName = strName;
    }
    public void setIntAge(int intAge){
        this.intAge = intAge;
    }
    public void setIntResult(int intResult){
        this.intResult = intResult;
    }
    public String getDataSet(){
        return strDataset;
    }

    public String getStrName(){
        return strName;
    }

    public int getIntAge(){
        return intAge;
    }

    public int getIntResult(){return intResult;}
}
