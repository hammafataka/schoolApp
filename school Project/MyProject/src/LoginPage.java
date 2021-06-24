import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class LoginPage extends JFrame{
    private JTextField userNameTxt;
    JPanel panel1;
    private JPasswordField passwordTxt;
    private JButton LoginBtn;
    private JLabel registerLbl;
    private JCheckBox checkbox;
    private JLabel timeLbl;
    public static JFrame frame;
    static RegisterPage registerPage;
    static User user;
    Teacher teacher;
    StudentPage studentPage;

    public LoginPage() {
        Helpers.setTimeDate(timeLbl);
        user=new User();
        LoginBtn.addActionListener(e -> {
            try{
                ResultSet rs= SqlService.GetResultSet("SELECT  * FROM  User_Table");
                while(rs.next()){
                    user.username=rs.getString(1);
                    user.password=rs.getString(2);
                    user.userType=rs.getString(3);
                    user.userEmail=rs.getString(4);
                    user.id=rs.getString(5);
                    if(userNameTxt.getText().equals(rs.getString(1)) && RegisterPage.Hash(passwordTxt.getText()).equals(rs.getString(2))){
                        if(user.userType.equals("teacher")){
                            teacher=new Teacher();
                            teacher.setMinimumSize(new Dimension(400,400));
                            teacher.setContentPane(teacher.panel1);
                            teacher.pack();
                            teacher.setVisible(true);
                            frame.dispose();
                        }
                        else if(user.userType.equals("student")){
                            studentPage=new StudentPage();
                            studentPage.setMinimumSize(new Dimension(400,400));
                            studentPage.setContentPane(studentPage.studentPanel);
                            studentPage.pack();
                            studentPage.setVisible(true);
                            frame.dispose();
                        }
                    }

                }
            }
            catch(SQLException | NoSuchAlgorithmException s){
                System.out.println(s.getMessage());
            }

        });
        registerLbl.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                super.mouseClicked(e);
                registerPage =new RegisterPage();
                registerPage.setMinimumSize(new Dimension(400,400));
                registerPage.setContentPane(registerPage.myPanel);
                registerPage.pack();
                registerPage.setVisible(true);
                frame.dispose();
            }
        });
        checkbox.addActionListener(e -> {
            if(checkbox.isSelected()){
                passwordTxt.setEchoChar((char)0);
            }else {
                passwordTxt.setEchoChar('*');
            }
        });
    }
    public static void RegisterDisposer(){
        registerPage.dispose();
    }

    public static void main(String[] args) {
        frame = new JFrame("LoginPage");
        frame.setContentPane(new LoginPage().panel1);
        frame.setMinimumSize(new Dimension(400,400));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }
}
