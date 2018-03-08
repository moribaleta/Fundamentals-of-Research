
public class CeliacType {

    int arrIntContribution[];
    String strResult;
    int intType;
    //UserData userData;
  
    String TestResult(String strResults){

        String arrStrResults[] = strResults.split("\\|");
        int intType1 = testType1(arrStrResults[3],arrStrResults[9]);
        float intType2 = testType2(arrStrResults[0],arrStrResults[1],arrStrResults[2],arrStrResults[9],arrStrResults[10]);
        float intType3 = testType3(arrStrResults[6],arrStrResults[12],
                arrStrResults[4],arrStrResults[5],arrStrResults[7],
                arrStrResults[8],arrStrResults[9],arrStrResults[11],arrStrResults[13],arrStrResults[14],arrStrResults[15]);
        if(intType1==0&&intType2==0&&intType3==0){
            return "null";
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
        //String strResult = " test "+intType+" "+intType2+" "+intType3;
        String strResult = "";
        if(intType1>0)
            return 1 + strResult;
        else if(intType2>=intType3)
            return 2 + strResult;
        else
            return 3 + strResult;
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
    private float testType2(String strSymptoms1, String strSymptoms2, String strSymptoms3,String strSymptoms10, String strSymptoms11) {
        float intPass = 0;
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
        }else if(Integer.parseInt(strSymptoms11)>1){
            intPass+=0.5;
        }

        return intPass;
    }
    private float testType3(String strSymptoms7, String strSymptoms13, String strSymptoms5,
                          String strSymptoms6, String strSymptoms8, String strSymptoms9, String strSymptoms10,
                          String strSymptoms12,String strSymptoms14,String strSymptoms15,String strSymptoms16) {
        float intPass = 0;
        if(Integer.parseInt(strSymptoms7)>2){
            intPass++;
        }
        else if(Integer.parseInt(strSymptoms7)>1){
            intPass+=0.5;
        }
        if(Integer.parseInt(strSymptoms13)>2){
            intPass++;
        }
        else if(Integer.parseInt(strSymptoms13)>1){
            intPass+=0.5;
        }
        if(Integer.parseInt(strSymptoms5)==1){
            intPass++;
        }
        if(Integer.parseInt(strSymptoms6)==1){
            intPass++;
        }
        if(Integer.parseInt(strSymptoms8)>2){
            intPass++;
        }
        else if(Integer.parseInt(strSymptoms8)>1){
            intPass+=0.5;
        }
        if(Integer.parseInt(strSymptoms9)==1){
            intPass++;
        }
        if(Integer.parseInt(strSymptoms10)==1){
            intPass++;
        }
        if(Integer.parseInt(strSymptoms12)==1){
            intPass++;
        }
        if(Integer.parseInt(strSymptoms14)>2){
            intPass++;
        }
        else if(Integer.parseInt(strSymptoms14)>1){
            intPass+=0.5;
        }
        if(Integer.parseInt(strSymptoms15)>2){
            intPass++;
        }
        else if(Integer.parseInt(strSymptoms15)>1){
            intPass+=0.5;
        }
        if(Integer.parseInt(strSymptoms16)>2){
            intPass++;
        }
        else if(Integer.parseInt(strSymptoms16)>1){
            intPass+=0.5;
        }
        return intPass;
    }



}
