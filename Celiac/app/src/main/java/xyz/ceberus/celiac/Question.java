package xyz.ceberus.celiac;

/**
 * Created by Eli on 11/6/2017.
 */

public class Question {
    String strQuestion;
    String strTitle = null;
    String strInfo;
    String strLink;
    String arrAnswer[];
    Question(String strQuestion,String strAnswer,String strInfo){
        this.strQuestion = strQuestion;
        this.arrAnswer = strAnswer.split(",");

        try {
            String strInfoTemp = strInfo;
            this.strTitle = strInfoTemp.split("__")[0];
            strInfoTemp = strInfoTemp.split("__")[1];
            this.strInfo = strInfoTemp.split("hyperlink")[0];
            this.strLink = strInfoTemp.split("hyperlink")[1];
        }catch (Exception e){
            this.strInfo = strInfo;
            this.strLink = null;
            e.printStackTrace();
        }
    }
    public String getStrQuestion(){
        return strQuestion;
    }

    public String[] getArrAnswer(){
        return arrAnswer;
    }

    public String getStrInfo(){
        return strInfo;
    }
    public String getStrLink(){
        return strLink;
    }

    public void setStrQuestion(String strQuestion){

    }

    public void setArrAnswer(String strAnswer){
        this.arrAnswer = strAnswer.split(",");
    }

}
