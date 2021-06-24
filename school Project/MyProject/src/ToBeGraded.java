public class ToBeGraded {
    String subject;
    String examName;
    String question;
    String Answer;
    String studentuser;

    public ToBeGraded(String subject, String examName, String question, String answer ,String StudentUSer) {
        this.studentuser=StudentUSer;
        this.subject = subject;
        this.examName = examName;
        this.question = question;
        this.Answer = answer;
    }

    public ToBeGraded() {
    }

    @Override
    public String toString() {
        return "ToBeGraded{" +
                "subject='" + subject + '\'' +
                ", examName='" + examName + '\'' +
                ", question='" + question + '\'' +
                ", Answer='" + Answer + '\'' +
                ",studentUser'="+studentuser+'\''+
                '}';
    }
}
