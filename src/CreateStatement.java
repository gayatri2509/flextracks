import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class CreateStatement {

    public Statement getStatement(){
        Statement stt = null;
        try {
            String url = "jdbc:mysql://localhost:3306/";
            String user = "****";
            String password = "****";
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection(url, user, password);
            stt = con.createStatement();
            stt.execute("USE flextracks");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return stt;
    }

}
