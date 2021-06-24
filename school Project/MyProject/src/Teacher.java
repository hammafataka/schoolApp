import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Teacher extends JFrame {
    private JTabbedPane tabbedpane;
    JPanel panel1;
    private JPanel homepane;
    private JPanel exampane;
    private JPanel gradePane;
    private JPanel settingPane;
    private  JLabel wlcLbl;
    private JLabel idLbl;
    private JButton addBtn;
    private JButton editBtn;
    private JButton clearBtn;
    private JSplitPane splittedPane;
    private JButton deleteBtn;

    private JButton addGradeBtn;
    private JButton removeGradeBtn;
    private JSplitPane GradesSplittedPane;
    private JLabel username_lbl;
    private  JTextField user_name_text;
    private final JTextField password_text;
    private  JTextField email_text;
    private JButton updateButton;
    private JPasswordField passwordField_text;
    private JTextField subjectnameTextField;
    private JTextField examNameTextField;
    private JLabel timeLbl;
    private JButton logoutBtn;
    private JTextArea courseArea;
    private final JList<Question> questionJlist;
    private final JList<Grade> gradeJList;
    private ArrayList<Question> questions;
    private ArrayList<Grade> grades;
    private final DefaultListModel<Question> defaultListModel;
    private final DefaultListModel<Grade> GradesListModel;
    private DefaultComboBoxModel<String> subjectModel;
    private DefaultComboBoxModel<String> examnametModel;
    private final JTextField qtext;
    private final JTextField ans1text;
    private final JTextField ans2text;
    private final JTextField ans3text;
    private final JTextField ans4text;
    private final JTextField studentIdtext;
    private final JTextField studentNametext;
    private final JTextField studentGradetext;
    private final JTextField studentsubjectgrade;
    private final JTextField studentexamgrade;
    private ArrayList<String> courses;
    Question tempQestion;
    Grade tempGrade;


    public Teacher() throws SQLException {
        Helpers.setUPDetails(LoginPage.user,wlcLbl,idLbl);
        Helpers.setTimeDate(timeLbl);
        loadQuestions();
        LoadGrades();
        Helpers.loadUserinfo(user_name_text,email_text);
        LoadCourses();
        JPanel qaPane = new JPanel();
        JPanel gradePanel = new JPanel();
        questionJlist = new JList<>();
        gradeJList=new JList<>();
        wlcLbl.setText("Welcome back "+LoginPage.user.username);
        password_text=new JTextField();
        qtext =new JTextField();
        ans1text=new JTextField();
        ans2text=new JTextField();
        ans3text=new JTextField();
        ans4text=new JTextField();
        qtext.setHorizontalAlignment(JTextField.CENTER);
        ans1text.setHorizontalAlignment(JTextField.CENTER);
        ans2text.setHorizontalAlignment(JTextField.CENTER);
        ans3text.setHorizontalAlignment(JTextField.CENTER);
        ans4text.setHorizontalAlignment(JTextField.CENTER);
        qtext.setDisabledTextColor(Color.BLACK);
        ans1text.setDisabledTextColor(Color.BLACK);
        ans2text.setDisabledTextColor(Color.BLACK);
        ans3text.setDisabledTextColor(Color.BLACK);
        ans4text.setDisabledTextColor(Color.BLACK);
        subjectnameTextField.setText("Enter Subject..");
        examNameTextField.setText("Enter Exam..");
        studentIdtext =new JTextField();
        studentNametext=new JTextField();
        studentGradetext =new JTextField();
        studentsubjectgrade=new JTextField();
        studentexamgrade=new JTextField();
        studentIdtext.setHorizontalAlignment(JTextField.CENTER);
        studentNametext.setHorizontalAlignment(JTextField.CENTER);
        studentGradetext.setHorizontalAlignment(JTextField.CENTER);
        studentsubjectgrade.setHorizontalAlignment(JTextField.CENTER);
        studentexamgrade.setHorizontalAlignment(JTextField.CENTER);
        studentIdtext.setDisabledTextColor(Color.BLACK);
        studentNametext.setDisabledTextColor(Color.BLACK);
        studentGradetext.setDisabledTextColor(Color.BLACK);
        studentsubjectgrade.setDisabledTextColor(Color.BLACK);
        studentexamgrade.setDisabledTextColor(Color.BLACK);


        defaultListModel= new DefaultListModel<>();
        questionJlist.setModel(defaultListModel);
        defaultListModel.addAll(questions);
        splittedPane.setLeftComponent(new JScrollPane(questionJlist));
        qaPane.setLayout(new GridLayout(5,1,10,10));
        qaPane.add(qtext);
        qaPane.add(ans1text);
        qaPane.add(ans2text);
        qaPane.add(ans3text);
        qaPane.add(ans4text);
        splittedPane.setRightComponent(qaPane);

        GradesListModel=new DefaultListModel<>();
        gradeJList.setModel(GradesListModel);
        GradesListModel.addAll(grades);
        GradesSplittedPane.setLeftComponent(new JScrollPane(gradeJList));
        gradePanel.setLayout(new GridLayout(5,1,10,10));
        gradePanel.add(studentIdtext);
        gradePanel.add(studentNametext);
        gradePanel.add(studentGradetext);
        gradePanel.add(studentsubjectgrade);
        gradePanel.add(studentexamgrade);
        GradesSplittedPane.setRightComponent(gradePanel);


        questionJlist.getSelectionModel().addListSelectionListener(e->{
            try {

                qtext.setEnabled(false);
                ans1text.setEnabled(false);
                ans2text.setEnabled(false);
                ans3text.setEnabled(false);
                ans4text.setEnabled(false);
                qtext.setText(questionJlist.getSelectedValue().question);
                ans1text.setText(questionJlist.getSelectedValue().answer1);
                ans2text.setText(questionJlist.getSelectedValue().answer2);
                ans3text.setText(questionJlist.getSelectedValue().answer3);
                ans4text.setText(questionJlist.getSelectedValue().answer4);
            }catch (Exception t){
                System.out.println(t.getMessage());
            }

        });
        gradeJList.getSelectionModel().addListSelectionListener(e->{
            try{
                studentIdtext.setEnabled(false);
                studentNametext.setEnabled(false);
                studentGradetext.setEnabled(false);
                studentsubjectgrade.setEnabled(false);
                studentexamgrade.setEnabled(false);
                studentIdtext.setText(gradeJList.getSelectedValue().Id);
                studentNametext.setText(gradeJList.getSelectedValue().StudentName);
                studentGradetext.setText(gradeJList.getSelectedValue().Grade);
                studentsubjectgrade.setText(gradeJList.getSelectedValue().subject);
                studentexamgrade.setText(gradeJList.getSelectedValue().exam);

            }
            catch (Exception t){
                System.out.println(t.getMessage());
            }
        });
        addBtn.addActionListener(e -> addQuestion());

        clearBtn.addActionListener(e-> defaultListModel.clear());
        deleteBtn.addActionListener(e-> deleteQuestion());
        addGradeBtn.addActionListener(e-> addGrade());
        removeGradeBtn.addActionListener(e-> deleteGrade());
        updateButton.addActionListener(e-> {
            try {
                Helpers helpers=new Helpers();
                helpers.UpdateUser(passwordField_text,user_name_text,email_text);
            } catch (Exception v) {
                System.out.println(v.getMessage());
            }
        });
        logoutBtn.addActionListener(e->
                {
                 this.dispose();
                 LoginPage loginPage=new LoginPage();
                    loginPage.setMinimumSize(new Dimension(400,400));
                    loginPage.setContentPane(loginPage.panel1);
                    loginPage.pack();
                    loginPage.setVisible(true);
                });
    }


    private void deleteQuestion(){
        setSelectedQuestion();
        boolean result=SqlService.deleteQuestion(tempQestion);
        if(result){
            int index= questionJlist.getSelectedIndex();
            if(index!=-1){
                defaultListModel.removeElementAt(index);
                clearQuestionTexts();
            }else {
                JOptionPane.showMessageDialog(this,"there is no item to be deleted");
            }

        }else {
            JOptionPane.showMessageDialog(this,"could not Remove item to database");
        }
    }

    private void clearQuestionTexts(){
        Helpers.ClearFields(qtext, ans1text, ans2text, ans3text, ans4text);
    }


    private void clearGradetexts(){
        Helpers.ClearFields(studentIdtext, studentNametext, studentGradetext, studentsubjectgrade, studentexamgrade);
    }
    private void addQuestion(){
        setSelectedQuestion();
        boolean output=SqlService.addQuestion(tempQestion);
        if(output){
            questionJlist.setModel(defaultListModel);
            defaultListModel.addElement(tempQestion);
            clearQuestionTexts();
        }
        else {
            JOptionPane.showMessageDialog(this,"could not add item to database");
        }
    }
    private void addGrade(){
        setSelectedGrade();
        boolean output=SqlService.addGrade(tempGrade);
        if(output){
            gradeJList.setModel(GradesListModel);
            GradesListModel.addElement(tempGrade);
            clearGradetexts();
        }
        else {
            JOptionPane.showMessageDialog(this,"could not add item to database");
        }
    }
    private void setSelectedQuestion(){
        tempQestion =new Question();
        tempQestion.question=qtext.getText();
        tempQestion.answer1=ans1text.getText();
        tempQestion.answer2=ans2text.getText();
        tempQestion.answer3=ans3text.getText();
        tempQestion.answer4=ans4text.getText();
        tempQestion.subject=subjectnameTextField.getText();
        tempQestion.examName=examNameTextField.getText();
    }
    private void setSelectedGrade(){
        tempGrade =new Grade();
        tempGrade.Id=studentIdtext.getText();
        tempGrade.StudentName=studentNametext.getText();
        tempGrade.Grade=studentGradetext.getText();
        tempGrade.subject=studentsubjectgrade.getText();
        tempGrade.exam=studentexamgrade.getText();
    }


    private void loadQuestions() throws SQLException {
        Question q=new Question();
        questions=new ArrayList<>();
        subjectModel= new DefaultComboBoxModel<>();
        examnametModel= new DefaultComboBoxModel<>();
        ResultSet rs= SqlService.GetResultSet("SELECT * from questions");
        while (rs.next()){
            Helpers.SetRsQuestions(q, rs, questions);
            if(subjectModel.getIndexOf(q.subject)==-1){
                subjectModel.addElement(q.subject);
            }
            if(examnametModel.getIndexOf(q.examName)==-1){
                examnametModel.addElement(q.examName);
            }

        }
    }
    private  void LoadGrades() throws SQLException {
        grades=new ArrayList<>();
        Grade grade=new Grade();
        ResultSet rs=SqlService.GetResultSet("SELECT * from Grades");
        while (rs.next()){
            grade.Id=rs.getString(1);
            grade.StudentName=rs.getString(2);
            grade.Grade=rs.getString(3);
            grade.subject=rs.getString(4);
            grade.exam=rs.getString(5);
            grades.add(grade);
        }
    }
    private void deleteGrade(){
        setSelectedGrade();
        boolean result=SqlService.deleteGrade(tempGrade);
        if(result){
            int index= gradeJList.getSelectedIndex();
            if(index!=-1){
                GradesListModel.removeElementAt(index);
                clearGradetexts();
            }else {
                JOptionPane.showMessageDialog(this,"there is no item to be deleted");
            }

        }else {
            JOptionPane.showMessageDialog(this,"could not Remove item to database");
        }
    }
    private void LoadCourses(){
        courses=new ArrayList<>();
        try{
            ResultSet rs=SqlService.GetResultSet(String.format("select * from Courses where teacher='%s'",LoginPage.user.username));
            while (rs.next()){
                courses.add(rs.getString(3));
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        for(int i=0;i<courses.size();i++){
            courseArea.setText("\n"+courses.get(i));
        }
    }
    public void main(String[] args){

        JFrame frame = new JFrame("Teacher");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }



}
