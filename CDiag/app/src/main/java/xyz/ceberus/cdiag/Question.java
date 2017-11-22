package xyz.ceberus.cdiag;

/**
 * Created by Eli on 11/6/2017.
 */

public class Question {
    String strQuestion;
    String arrAnswer[];
    Question(String strQuestion,String strAnswer){
        this.strQuestion = strQuestion;
        this.arrAnswer = strAnswer.split(",");
    }
    public String getStrQuestion(){
        return strQuestion;
    }

    public String[] getArrAnswer(){
        return arrAnswer;
    }

    public void setStrQuestion(String strQuestion){

    }

    public void setArrAnswer(String strAnswer){
        this.arrAnswer = strAnswer.split(",");
    }

}
