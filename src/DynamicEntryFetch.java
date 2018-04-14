import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DynamicEntryFetch{

    private static List<String> flightNo = new ArrayList<>();
    private static List<String> airline = new ArrayList<>();
    private static List<String> arrivalDeparture = new ArrayList<>();
    private static List<String> sourceDestination = new ArrayList<>();
    private static List<String> aircraftName = new ArrayList<>();
    private static List<String> terminalName = new ArrayList<>();
    private static List<String> timeStamp = new ArrayList<>();
    private static List<String> time = new ArrayList<>();
    private static List<Double> sourceDestinationDegree = new ArrayList<>();
    private static List<String> cat = new ArrayList<>();
    private static List<Double> lat = new ArrayList<>();
    private static List<Double> lon = new ArrayList<>();
    private static double mumlat = 19.088686;
    private static double mumlon = 72.867919;
    private static java.sql.Time startTime;
    private static java.sql.Time endTime;
    private static int noOfFlights = 0;

    public double bearing(double lat1, double lon1, double lat2, double lon2){
        double x = Math.cos(lat2 * 3.1412 / 180) * Math.sin(lon2 * 3.1412 / 180 - lon1 * 3.1412 / 180);
        double y = Math.cos(lat1 * 3.1412 / 180) * Math.sin(lat2 * 3.1412 / 180) -
                Math.sin(lat1 * 3.1412 / 180) * Math.cos(lat2 * 3.1412 / 180) *
                        Math.cos(lon2 * 3.1412 / 180- lon1 * 3.1412 / 180);

        double beta = Math.atan2(x, y);
        return beta * 180 / 3.1412;
    }

    public void runCode(){
        try{
            CreateStatement createStatement = new CreateStatement();
            Statement stt = createStatement.getStatement();
            stt.execute("USE flextracks");
            ResultSet res1 = stt.executeQuery("SELECT * FROM `flightlogtable` ORDER BY id DESC LIMIT 1");
            while(res1.next()){
                java.sql.Time lastTime = res1.getTime("actualTime");
                long t = lastTime.getTime();
                startTime = new java.sql.Time(t+ 60000);
                t = startTime.getTime();
                endTime = new java.sql.Time(t + 15 * 60000);
            }

            int i = 0;
            ResultSet res = stt.executeQuery("SELECT * FROM flightinfoatc WHERE `time` <= '"+endTime+"' LIMIT 10");
            while(res.next()){
                flightNo.add(res.getString("flightNo"));
                airline.add(res.getString("airline"));
                arrivalDeparture.add(res.getString("arrivalDeparture"));
                sourceDestination.add(res.getString("sourceDestination"));
                aircraftName.add(res.getString("aircraftName"));
                terminalName.add(res.getString("terminalName"));
                timeStamp.add(res.getString("timeStamp"));
                time.add(res.getString("time"));
                noOfFlights++;
                i++;
            }

            stt.execute("TRUNCATE TABLE  flightcurrenttable");

            for(int j = 0; j < noOfFlights; j++){
                res = stt.executeQuery("SELECT * FROM aircraft where aircraftName = '"+ aircraftName.get(j) +"'"); // '"+ +"' write variable in the blank
                while(res.next()){
                    cat.add(res.getString("cat"));
                }

                res = stt.executeQuery("SELECT * FROM airportsinfo where iataCode = '"+ sourceDestination.get(j) +"'");
                while(res.next()){
                    lat.add(res.getDouble("lattitude"));
                    lon.add(res.getDouble("longitude"));
                }
            }

            for(i = 0; i < noOfFlights; i++){
                sourceDestinationDegree.add(bearing( mumlat, mumlon, lat.get(i), lon.get(i)) + 180);
            }

            //Fill up flgihtCureentTable
            for(i = 0; i < noOfFlights; i++)
            {
                stt.execute("INSERT INTO `flightcurrenttable`(`flightNo`, `airline`, `cat`, `arrivalDeparture`, `sourceDestinationDegree`, `terminalName`, "
                        + "`timeStamp`, `aircraftName`) VALUES ('"+flightNo.get(i)+"','"+airline.get(i)+"','"+cat.get(i)+"','"+arrivalDeparture.get(i)+"',"
                        + "'"+sourceDestinationDegree.get(i)+"','"+terminalName.get(i)+"','"+timeStamp.get(i)+"','"+aircraftName.get(i)+"')");
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void clearTables(){
        flightNo.clear();
        aircraftName.clear();
        cat.clear();
        sourceDestination.clear();
        terminalName.clear();
        timeStamp.clear();
        aircraftName.clear();
        arrivalDeparture.clear();
        time.clear();
        sourceDestinationDegree.clear();
        lat.clear();
        lon.clear();
    }

    public void run(){
        this.runCode();
        this.clearTables();
        CalculateFuel cf = new CalculateFuel();
        cf.runCode(noOfFlights);
    }
}