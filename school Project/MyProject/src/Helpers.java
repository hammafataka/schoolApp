import javax.swing.*;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;


public  class Helpers extends JFrame {
    static void SetRsQuestions(Question q, ResultSet rs, ArrayList<Question> questions){
        try{
            q.subject=rs.getString(1);
            q.examName=rs.getString(2);
            q.question=rs.getString(3);
            q.answer1=rs.getString(4);
            q.answer2=rs.getString(5);
            q.answer3=rs.getString(6);
            q.answer4=rs.getString(7);
            questions.add(q);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void UpdateUser(JPasswordField passwordField_text,JTextField user_name_text, JTextField email_text) {
        try {
            if(RegisterPage.Hash(passwordField_text.getText()).equals(passwordField_text.getText())){
                User tempUser=new User();
                tempUser.username=user_name_text.getText();
                tempUser.password=passwordField_text.getText();
                tempUser.userEmail=email_text.getText();
                boolean result=SqlService.UpdateUser(tempUser,LoginPage.user);
                if(result){
                    JOptionPane.showMessageDialog(this,"Information Updated to database");
                }else {
                    JOptionPane.showMessageDialog(this,"could not Update information to database");
                }
            }
            else{
                JOptionPane.showMessageDialog(this,"Password was incorrect");
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
    static void ClearFields(JTextField qtext, JTextField ans1text, JTextField ans2text, JTextField ans3text, JTextField ans4text) {
        qtext.setEnabled(true);
        ans1text.setEnabled(true);
        ans2text.setEnabled(true);
        ans3text.setEnabled(true);
        ans4text.setEnabled(true);
        qtext.setText(null);
        ans1text.setText(null);
        ans2text.setText(null);
        ans3text.setText(null);
        ans4text.setText(null);
    }
    static void loadUserinfo(JTextField user_name_text,JTextField email_text){
        try{
            ResultSet rs=SqlService.GetResultSet(String.format("SELECT * from User_Table where username ='%s'",LoginPage.user.username));
            while(rs.next()){
                user_name_text.setText(rs.getString(1));
                email_text.setText(rs.getString(4));

            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
    static void setTimeDate(JLabel label){
        Thread timeAndDate= new Thread(() -> {
            for(;;){
                SimpleDateFormat formatter = new SimpleDateFormat(" HH:mm:ss dd/MM/yyyy");
                Date date = new Date();
                label.setText(formatter.format(date));
            }

        });
        timeAndDate.start();
    }
    static void setUPDetails(User user,JLabel userNameLbl,JLabel idLbl){
        userNameLbl.setText("Welcome Back "+user.username);
        idLbl.setText("ID: "+user.id);
    }
}
