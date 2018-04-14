import java.sql.ResultSet;
import java.sql.Statement;

public class Parent {

    public static void main(String[] args){
        try{
            while(true){
                DynamicEntryFetch dynamicEntryFetch = new DynamicEntryFetch();
                dynamicEntryFetch.run();
                CreateStatement createStatement = new CreateStatement();
                Statement stt = createStatement.getStatement();
                Thread.sleep(10000);
                ResultSet res = stt.executeQuery("SELECT time FROM timereqforlastslot");
                while(res.next()){
                    int time = res.getInt("time");
                }

                Thread.sleep(10 * 1000 - 10000);
                //deleteFromFlightInfoTable();
            }
        }
        catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

}
