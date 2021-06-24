import javax.mail.*;
import javax.mail.internet.MimeMessage;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;

public class  RegisterPage extends JFrame {


    private JTextField emailTxt;
    private JTextField userNmaeTxt;
    private JPasswordField passwordTxt;
    private JPasswordField re_passwordtxt;
    private JButton registerButton;
    JPanel myPanel;
    private JLabel backLbl;
    private JLabel timeLbl;
    static JFrame rFrame;
    Properties prop;
    Session session;
    String email="fataka.demo@gmail.com";
    String pass="hamma312";
    String randomNum;
    SqlService sqlService;


    public RegisterPage(){
        Helpers.setTimeDate(timeLbl);
        registerButton.addActionListener(e -> {
            if(userNmaeTxt.getDocument().getLength()>1&&emailTxt.getDocument().getLength()>1&&passwordTxt.getDocument().getLength()>1&&re_passwordtxt.getDocument().getLength()>1){
                if(isEmail(emailTxt.getText())){
                    if(passwordTxt.getText().equals(re_passwordtxt.getText())){
                        if(passwordTxt.getDocument().getLength()>5){
                            prop=new Properties();
                            prop.put("mail.smtp.auth", true);
                            prop.put("mail.smtp.starttls.enable", "true");
                            prop.put("mail.smtp.host", "smtp.mailtrap.io");
                            prop.put("mail.smtp.port", "25");
                            prop.put("mail.smtp.ssl.trust", "smtp.mailtrap.io");
                            session=Session.getInstance(prop, new Authenticator() {
                                @Override
                                protected PasswordAuthentication getPasswordAuthentication() {
                                    return new PasswordAuthentication(email,pass);
                                }
                            });
                       /* try {
                            Message message=emailVerification(session,emailTxt.getText());
                            assert message != null;
                            Transport.send(message);
                        } catch (MessagingException messagingException) {
                            messagingException.printStackTrace();
                        }
                        String code=JOptionPane.showInputDialog(this,"Enter the code that has been sent to your Email..");
                        if(code.equals(randomNum)){

                        }*/
                            try {
                                String pass = Hash(passwordTxt.getText());
                                boolean result=sqlService.ExecuteSqlCommand(String.format("insert into User_Table values ('%s','%s','student','%s')",userNmaeTxt.getText(),pass,emailTxt.getText()));
                                if(result){
                                    JOptionPane.showMessageDialog(this,"Registered successfully");
                                    LoginPage.frame.setVisible(true);
                                    LoginPage.RegisterDisposer();
                                }
                                else {
                                    JOptionPane.showMessageDialog(this,"Failed to Register");
                                }
                            } catch (NoSuchAlgorithmException n) {
                                JOptionPane.showMessageDialog(this,"Error occurred :(");
                            }
                        }
                        else {
                            JOptionPane.showMessageDialog(this,"Minimum password Length is six.");
                        }
                    }
                    else {
                       JOptionPane.showMessageDialog(this,"Please try again, Password Fields must be same");
                    }
                }else {
                   JOptionPane.showMessageDialog(this,"Please enter a valid email address");
                }
            }
            else{
               JOptionPane.showMessageDialog(this,"All fields are REQUIRED");
            }

        });

        backLbl.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                LoginPage.frame.setVisible(true);
                LoginPage.RegisterDisposer();
            }
        });
    }
    public static boolean isEmail(String email){
        Matcher m=Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE).matcher(email);
        return m.find();
    }
    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
    public static String Hash  (String input ) throws NoSuchAlgorithmException {
        final MessageDigest digest = MessageDigest.getInstance("SHA3-512");
        final byte[] hashbytes = digest.digest(
                input.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(hashbytes);
    }
    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    private Message emailVerification(Session s,String recipient){
        randomNum=String.valueOf(getRandomNumber(100000, 100000));
        try{
            Message m=new MimeMessage(s);
            m.setFrom(new InternetAddress(email));
            m.setRecipient(Message.RecipientType.TO,new InternetAddress(recipient));
            m.setSubject("Verification Code");
            m.setText(randomNum);
            return m;
        }
        catch (Exception e){
           JOptionPane.showMessageDialog(this,e.getMessage());
        }
        return null;
    }
    public static void main(String[] args) {
        rFrame = new JFrame("RegisterPage");
        rFrame.setContentPane(new RegisterPage().myPanel);
        rFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        rFrame.pack();
        rFrame.setVisible(true);
    }

}
