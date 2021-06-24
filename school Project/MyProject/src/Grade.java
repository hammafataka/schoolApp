public class Grade {
    String StudentName;
    String Grade;
    String Id;
    String subject;
    String exam;

    public Grade(String studentName, String grade, String id, String subject, String exam) {
        StudentName = studentName;
        Grade = grade;
        Id = id;
        this.subject = subject;
        this.exam = exam;
    }

    public Grade() {
    }

    @Override
    public String toString() {
        return
                StudentName  +
                ", subject=" + subject+
                ", exam=" + exam;
    }
}
