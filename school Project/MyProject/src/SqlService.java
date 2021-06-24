import java.sql.*;

public class SqlService {
    static String stringconnection = "Your_SQL_Server_Connection";
    static Connection co;
    static Statement sql;

    public static Statement CreateStatement() throws SQLException {
        co= DriverManager.getConnection(stringconnection);
        sql=co.createStatement();
        return sql;
    }
    public static ResultSet GetResultSet (String sqlCommand) throws SQLException {
        CreateStatement();
        return sql.executeQuery(sqlCommand);
    }
    public static boolean ExecuteSqlCommand(String command){
        boolean output;
        try {
            CreateStatement();
            sql.execute(command);
            output =true;
        } catch (Exception e) {
            output =false;
        }
        return output;
    }
    public static boolean addQuestion(Question q){
        try{
            CreateStatement();
            sql.execute(String.format("insert into questions values ('%s','%s','%s','%s','%s','%s','%s')",
                    q.subject,q.examName,q.question,q.answer1,q.answer2,q.answer3,q.answer4
            ));
            return true;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    public static boolean addGrade(Grade g){
        try{
            CreateStatement();
            sql.execute(String.format("insert into Grades values ('%s','%s','%s','%s','%s')",
                    g.Id,g.StudentName,g.Grade,g.subject,g.exam
            ));
            return true;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    public static void addToBeGraded(ToBeGraded tb){
        try{
            CreateStatement();
            sql.execute(String.format("insert into ToGrade values ('%s','%s','%s','%s','%s')",tb.question
            ,tb.Answer,tb.studentuser,tb.subject,tb.examName));
        } catch (SQLException t) {
            System.out.println(t.getMessage());
        }
    }
    public static boolean deleteQuestion(Question q){
        try{
            CreateStatement();
            sql.execute(String.format("delete from questions where question ='%s'",
                    q.question
            ));
            return true;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    public static boolean UpdateUser(User u,User currentUser){
        try{
            CreateStatement();
            sql.execute(String.format("UPDATE User_Table" +
                    "SET username = '%s'  ,password = '%s' , Email='%s'" +
                    "WHERE username ='%s';",u.username,u.password,u.userEmail,currentUser.username));
            return true;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }
    public static boolean deleteGrade(Grade g){
        try{
            CreateStatement();
            sql.execute(String.format("delete from Grades where Grade='%S' and exam='%s'",
                    g.Grade,g.exam
            ));
            return true;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

}
