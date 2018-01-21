package xyz.ceberus.celiac;

/**
 * Created by Eli on 11/6/2017.
 */

public class Question {
    String strQuestion;
    String strInfo;
    String strLink;
    String arrAnswer[];
    Question(String strQuestion,String strAnswer,String strInfo){
        this.strQuestion = strQuestion;
        this.arrAnswer = strAnswer.split(",");

        try {
            this.strInfo = strInfo.split("hyperlink")[0];
            this.strLink = strInfo.split("hyperlink")[1];
        }catch (Exception e){
            this.strInfo = null;
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
