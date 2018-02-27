package xyz.ceberus.celiac;

import android.util.Log;

import java.util.HashMap;

/**
 * Created by Eli on 12/9/2017.
 */

public class CeliacType {
    int arrIntContribution[];
    String strResult;
    int intType;
    UserData userData;
    HashMap<Integer,String[]>arrHashMapInfo = new HashMap<>();
    CeliacType(){
        initInfo();
    }
    CeliacType(UserData userData){
        this.userData = userData;
        initInfo();

        getResultMessage();
    }

    private void initInfo() {
        String strInput[] = new String[2];
        strInput[0] = "Silent Celiac Disease";
        strInput[1] ="Silent celiac disease or the Type 1 Celiac Disease exists if you don’t experience any known symptoms of celiac disease but test positive for it. This is usually discovered by a doctor who is examining you for another condition and discovers that you’re at risk for celiac disease (family history, perhaps).";
        arrHashMapInfo.put(1,strInput);
        String strInput2[] = new String[2];
        strInput2[0]="Typical Celiac Disease";
        strInput2[1]="“Typical” celiac disease or the Type 2 Celiac Disease produces all of the symptoms most stereotypically associated with gluten intolerance and celiac disease: gas, bloating, diarrhea, andconstipation. Basically, it is where celiac disease clearly manifests itself along your intestinal tract with its most well-known symptoms.";
        arrHashMapInfo.put(2,strInput2);
        String strInput3[] = new String[2];
        strInput3[0]="Atypical Celiac Disease";
        strInput3[1]="Atypical celiac disease or the Type 3 Celiac Disease occurs when patients test positive for celiac disease but they don’t have the obvious gastrointestinal symptoms. Instead, when someone has atypical celiac disease they tend to develop symptoms extra-intestinally, which means they develop symptoms beyond their gut. This includes migraines, ataxia, neuropathy, joint pain and more. Some researchers even believe celiac disease may manifest neurological symptoms more often than gastrointestinal symptoms";
        arrHashMapInfo.put(3,strInput3);
    }


    int[] getStrContribution(){
        return arrIntContribution;
    }
    String getStrResult(){
        return strResult;
    }

    void getResultMessage(){
        strResult = "The result is Negative based from the test conducted";
        Log.e("DATAVIEW","user result: "+userData.getIntResult());
        if (userData.getIntResult() != -1)
            strResult ="The result is Positive, we advice you to go to the Doctor " +
                    "(Gastroenterologist) and show this result to confirm if you have Celiac Disease";

        if (userData.getIntResult() == 1) {

            intType = TestResult(userData.getDataSet());

            String strType = "";
            if (intType == 1) {
                strType = " silent";
            } else if (intType == 2) {
                strType = " typical";
            } else if(intType == 3){
                strType = " atypical";
            }
            strResult += "\nCeliac Type: "+intType+" "+strType;
        }
        try {
            getContributionMessage();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void getContributionMessage() {
        String arrStrData[] = userData.getDataSet().split("\\|");
        int i = 0;
        int arrInt[] = new int[16];
        //String strMessage="The following symptoms have contributed to the result";

        for(String strData: arrStrData){
            if(i == 0||i==1||i==3||i==4||i==8||i==11){
                if(strData.equals("1")){
                    //strMessage += "\n"+"\u2022 "+(i+1);
                    //arrayList.add(i);
                    arrInt[i] = 1;
                }else{
                    arrInt[i] = 0;
                }
            }else{
                if(Integer.parseInt(strData)>2){
                    //strMessage += "\n"+"\u2022 "+(i+1);
                    //arrayList.add(i);
                    arrInt[i] = 1;
                }else{
                    arrInt[i] = 0;
                }
            }
            i++;
        }
        /*for(int j: arrayList){
            Log.e(TAG,"symptom number: "+j);
        }*/

        this.arrIntContribution = arrInt;
        //this.strContribution = strMessage;
    }

    int TestResult(String strResults){

        String arrStrResults[] = strResults.split("\\|");
        int intType1 = testType1(arrStrResults[3],arrStrResults[9]);
        int intType2 = testType2(arrStrResults[0],arrStrResults[1],arrStrResults[2],arrStrResults[9],arrStrResults[10]);
        int intType3 = testType3(arrStrResults[6],arrStrResults[12],
                arrStrResults[4],arrStrResults[5],arrStrResults[6],arrStrResults[7],
                arrStrResults[8],arrStrResults[9],arrStrResults[11]);
        if(intType1==0&&intType2==0&&intType3==0){
            return 0;
        }/*
        int arrIntTypes[] = {intType1,intType2,intType3};
        int winnerScore = -1, winner = 0;

        for (int i = 0; i < arrIntTypes.length; i++) {
            if (arrIntTypes[i] > winnerScore) {
                winner = i;
                winnerScore = arrIntTypes[i];
            }
        }
        winner++;*/
        if(intType1>0)
            return 1;
        else if(intType2>0)
            return 2;
        else
            return 3;
    }

    private int testType1(String strSymptoms4, String strSymptoms10) {
        int intPass = 0;
        if(Integer.parseInt(strSymptoms4)==1){
            intPass++;
        }
        if(Integer.parseInt(strSymptoms10)==1){
            intPass++;
        }
        return intPass;
    }
    private int testType2(String strSymptoms1, String strSymptoms2, String strSymptoms3,String strSymptoms10, String strSymptoms11) {
        int intPass = 0;
        if(Integer.parseInt(strSymptoms1)==1){
            intPass++;
        }
        if(Integer.parseInt(strSymptoms2)==1){
            intPass++;
        }
        if(Integer.parseInt(strSymptoms3)==1){
            intPass++;
        }
        if(Integer.parseInt(strSymptoms10)==1){
            intPass++;
        }
        if(Integer.parseInt(strSymptoms11)>2){
            intPass++;
        }
        return intPass;
    }
    private int testType3(String strSymptoms7, String strSymptoms13, String strSymptoms5,
                          String strSymptoms6, String strSymptoms8, String strSymptoms9, String strSymptoms10,
                          String strSymptoms12) {
        int intPass = 0;
        if(Integer.parseInt(strSymptoms4)==1){
            intPass++;
        }
        if(Integer.parseInt(strSymptoms5)==1){
            intPass++;
        }
        if(Integer.parseInt(strSymptoms7)>2){
            intPass++;
        }
        if(Integer.parseInt(strSymptoms8)>2){
            intPass++;
        }
        if(Integer.parseInt(strSymptoms9)==1){
            intPass++;
        }
        if(Integer.parseInt(strSymptoms10)>2){
            intPass++;
        }
        if(Integer.parseInt(strSymptoms12)==1){
            intPass++;
        }
        if(Integer.parseInt(strSymptoms13)>2){
            intPass++;
        }
        if(Integer.parseInt(strSymptoms14)>2){
            intPass++;
        }
        if(Integer.parseInt(strSymptoms15)>2){
            intPass++;
        }
        if(Integer.parseInt(strSymptoms16)>2){
            intPass++;
        }
        return intPass;
    }



}
