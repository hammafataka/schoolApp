import java.util.List;

public class Question {
    public String question;
    public String answer1;
    public String answer2;
    public String answer3;
    public String answer4;
    public String subject;
    public String examName;

    public Question() {

    }

    public String getQuestion(){
        return question;
    }
    public String getAnswer1(){
        return answer1;
    }
    public String getAnswer2(){
        return answer2;
    }
    public String getAnswer3(){
        return answer3;
    }
    public String getAnswer4(){
        return answer4;
    }

    @Override
    public String toString() {
        return question;
    }
}
