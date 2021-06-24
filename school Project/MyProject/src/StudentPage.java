import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class StudentPage extends JFrame implements ActionListener {
    private JList<Grade> gradesJlist;
    private JTabbedPane TabbedPane;
    JPanel studentPanel;
    private JPanel examspane;
    private JLabel wlcLbl;
    private JLabel idLbl;
    private JPanel homepane;
    private JTextField textfield;
    private JButton buttonA;
    private JButton buttonB;
    private JButton ButtonC;
    private JButton buttonD;
    private JComboBox<String> subjectsComboBox;
    private ArrayList<String> subjects;
    private ArrayList<String> exams;
    private ArrayList<Grade> grades;
    private JLabel username_lbl;
    private JTextField user_name_text;
    private JTextField email_text;
    private JButton updateButton;
    private DefaultComboBoxModel<String> subjectsModel;
    private DefaultComboBoxModel<String> examsModel;
    private JPasswordField passwordField_text;
    private JPanel settingPane;
    private JComboBox<String> examsComboBox;
    private JPanel gradePane;
    private JSplitPane GradesSplittedPane;
    private JLabel timeLbl;
    private JButton logOutBtn;
    private JTextArea courseArea;
    private ArrayList<Question> questions;
    String TempAnswer;
    int index=0;
    JLabel subjectLbl=new JLabel("Subject Name");
    JLabel examLbl=new JLabel("Exam");
    JLabel gradeLbl=new JLabel("Grade");

    public StudentPage() throws SQLException {
        Helpers.setTimeDate(timeLbl);
        Helpers.setUPDetails(LoginPage.user,wlcLbl,idLbl);
        LoadStudentGrades();
        DefaultListModel<Grade> gradeListModel = new DefaultListModel<Grade>();
        JPanel gradePanel = new JPanel();
        loadExams();
        LoadCourses();
        Helpers.loadUserinfo(user_name_text,email_text);
        textfield.setHorizontalAlignment(JTextField.CENTER);
        subjectsModel =new DefaultComboBoxModel<>();
        subjectsComboBox.setModel(subjectsModel);
        subjectsModel.addAll(subjects);
        examsModel=new DefaultComboBoxModel<>();
        examsComboBox.setModel(examsModel);
        examsModel.addAll(exams);
        examsComboBox.addActionListener(e-> {
                    loadQuestions();
                    if(questions.size()>0){
                        nextQuestions();

                    }
                }
        );

        buttonA.addActionListener(this::ButtonsListner);
        buttonB.addActionListener(this::ButtonsListner);
        ButtonC.addActionListener(this::ButtonsListner);
        buttonD.addActionListener(this::ButtonsListner);
        gradesJlist=new JList<>();
        gradesJlist.setModel(gradeListModel);
        gradeListModel.addAll(grades);
        GradesSplittedPane.setLeftComponent(new JScrollPane(gradesJlist));
        gradePanel.setLayout(new GridLayout(3,1,10,10));
        subjectLbl.setHorizontalAlignment(0);
        examLbl.setHorizontalAlignment(0);
        gradeLbl.setHorizontalAlignment(0);
        gradePanel.add(subjectLbl);
        gradePanel.add(examLbl);
        gradePanel.add(gradeLbl);
        GradesSplittedPane.setRightComponent(gradePanel);
        gradesJlist.getSelectionModel().addListSelectionListener(e->{

            try {
                subjectLbl.setText(gradesJlist.getSelectedValue().subject);
                examLbl.setText(gradesJlist.getSelectedValue().exam);
                gradeLbl.setText(gradesJlist.getSelectedValue().Grade);
            }catch (Exception t){
                System.out.println(t.getMessage());
            }
        });

        updateButton.addActionListener(e->{
            Helpers helpers=new Helpers();
            helpers.UpdateUser(passwordField_text,user_name_text,email_text);
        });
        logOutBtn.addActionListener(e->
        {
            this.dispose();
            LoginPage loginPage=new LoginPage();
            loginPage.setMinimumSize(new Dimension(400,400));
            loginPage.setContentPane(loginPage.panel1);
            loginPage.pack();
            loginPage.setVisible(true);
        });

    }
    public void ButtonsListner(ActionEvent e){
        Object source=e.getSource();
        if(source instanceof JButton){
            index++;
            TempAnswer=((JButton) source).getText();
            nextQuestions();
            setToBeGraded();

        }
    }
    private void setToBeGraded(){
        try {
            ToBeGraded toBeGraded=new ToBeGraded();
            toBeGraded.studentuser=LoginPage.user.username;
            toBeGraded.question=textfield.getText();
            toBeGraded.examName=examsComboBox.getSelectedItem().toString();
            toBeGraded.subject=subjectsComboBox.getSelectedItem().toString();
            toBeGraded.Answer=TempAnswer;
            SqlService.addToBeGraded(toBeGraded);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    private void LoadStudentGrades() throws SQLException {
        grades =new ArrayList<>();
        Grade grade =new Grade();
        ResultSet rs=SqlService.GetResultSet(String.format("Select * from Grades where Student='%s'",LoginPage.user.username));
        while(rs.next()){
            grade.StudentName=rs.getString(2);
            grade.subject=rs.getString(4);
            grade.exam=rs.getString(5);
            grade.Grade=rs.getString(3);
            grades.add(grade);
        }


    }

    private void loadQuestions() {
        try {
            Question q=new Question();
            questions=new ArrayList<>();
            String subject= subjectsComboBox.getSelectedItem().toString();
            String exam= examsComboBox.getSelectedItem().toString();
            ResultSet rs=SqlService.GetResultSet(String.format
                    ("Select * from questions where subject='%s' and examName='%s'",subject,exam));
            while(rs.next()){
                Helpers.SetRsQuestions(q, rs, questions);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
    private void LoadCourses(){
        ArrayList<String> courses = new ArrayList<>();
        try{
            ResultSet rs=SqlService.GetResultSet(String.format("select * from Courses where student='%s'",LoginPage.user.username));
            while (rs.next()){
                courses.add(rs.getString(3));
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        for(int i = 0; i< courses.size(); i++){
            courseArea.setText("\n"+ courses.get(i));
        }
    }
    private void ClearAllFields(){
        subjectsComboBox.setSelectedItem(null);
        examsComboBox.setSelectedItem(null);
        textfield.setText(" ");
        buttonA.setText(" ");
        buttonB.setText(" ");
        ButtonC.setText(" ");
        buttonD.setText(" ");

    }



    private void nextQuestions(){
        if(index>=questions.size()){
            JOptionPane.showMessageDialog(this,"Exam Finished..");
            ClearAllFields();
        }
        else {
            textfield.setText(questions.get(index).question);
            buttonA.setText(questions.get(index).answer1);
            buttonB.setText(questions.get(index).answer2);
            ButtonC.setText(questions.get(index).answer3);
            buttonD.setText(questions.get(index).answer4);
        }


    }

    private void loadExams() throws SQLException {
        subjects =new ArrayList<>();
        exams=new ArrayList<>();
        ResultSet rs=SqlService.GetResultSet("select * from questions");
        while(rs.next()){
            if(!subjects.contains(rs.getString(1))){
                subjects.add(rs.getString(1));
            }
            if(!exams.contains(rs.getString(2))){
                exams.add(rs.getString(2));
            }
        }

    }
    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
