package xyz.ceberus.celiac;

/**
 * Created by Eli on 12/9/2017.
 */

public class CeliacType {

    CeliacType(){

    }

    int TestResult(String strResults){

        String arrStrResults[] = strResults.split("\\|");
        int intType1 = testType1(arrStrResults[0],arrStrResults[1]);
        int intType2 = testType2(arrStrResults[2],arrStrResults[5],arrStrResults[10]);
        int intType3 = testType3(arrStrResults[3],arrStrResults[4],
                arrStrResults[6],arrStrResults[7],arrStrResults[8],arrStrResults[9],
                arrStrResults[11],arrStrResults[12],arrStrResults[13],arrStrResults[14],
                arrStrResults[15]);
        if(intType1==0&&intType2==0&&intType3==0){
            return 0;
        }
        int arrIntTypes[] = {intType1,intType2,intType3};
        int winnerScore = -1, winner = 0;
        for (int i = 0; i < arrIntTypes.length; i++) {
            if (arrIntTypes[i] > winnerScore) {
                winner = i;
                winnerScore = arrIntTypes[i];
            }
        }
        winner++;
        if(intType1>0)
            return 1;
        return winner;
    }

    private int testType1(String strSymptoms1, String strSymptoms2) {
        int intPass = 0;
        if(Integer.parseInt(strSymptoms1)==1){
            intPass++;
        }
        if(Integer.parseInt(strSymptoms2)==1){
            intPass++;
        }
        return intPass;
    }
    private int testType2(String strSymptoms3, String strSymptoms6, String strSymptoms11) {
        int intPass = 0;
        if(Integer.parseInt(strSymptoms3)>2){
            intPass++;
        }
        if(Integer.parseInt(strSymptoms6)>2){
            intPass++;
        }
        if(Integer.parseInt(strSymptoms11)>2){
            intPass++;
        }
        return intPass;
    }
    private int testType3(String strSymptoms4, String strSymptoms5, String strSymptoms7,
                          String strSymptoms8, String strSymptoms9, String strSymptoms10, String strSymptoms12,
                          String strSymptoms13, String strSymptoms14, String strSymptoms15, String strSymptoms16) {
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
