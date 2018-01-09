package xyz.ceberus.celiac;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Eli on 11/3/2017.
 */

public class UserData {
    public String strUserId;
    public String strDataset;
    public String strName;
    public String strDate;
    public int intAge;
    public int intResult;


    public UserData(){

    }

    public UserData(String strUserId, String strName, int intAge){
        this.strUserId = strUserId;
        this.strName = strName;
        this.intAge = intAge;
    }

    /*public UserData( String strDataset,String strName, int intAge){
        this.strDataset = strDataset;
        this.strName = strName;
        this.intAge = intAge;
    }*/

    public UserData( String strDataset,String strName, int intAge, int intResult){
        this.strDataset = strDataset;
        this.strName = strName;
        this.intAge = intAge;
        this.intResult = intResult;
    }

    public void setStrUserId(String strUserId){
        this.strUserId = strUserId;
    }
    public String getStrUserId(){
        return  strUserId;
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

    public void showData() {
        Log.e("UserData","id: "+strUserId+" name: "+strName+" age: "+intAge);
    }

    public void showDataFull() {
        Log.e("UserData","id: "+strUserId+" name: "+strName+" age: "+intAge+" dataset:"+strDataset+" result:"+intResult);
    }

    public void setDate(){
        this.strDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a").format(Calendar.getInstance().getTime());
    }

    public String getDate(){
        return strDate;
    }

    public void setDate(String strDate) {
        this.strDate = strDate;
    }
}
