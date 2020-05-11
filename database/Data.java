import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Data {
    Connection con;

    public Data() {
        try {
            con = DriverManager.getConnection(Info.HOST, Info.USER, Info.PASSWORD);
            PreparedStatement st = con.prepareStatement("USE metropolis_db;");
            PreparedStatement st1 = con.prepareStatement("DROP TABLE IF EXISTS metropolises;");
            st.execute();
            st1.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void createTable() {
        try {
            PreparedStatement st = con.prepareStatement("CREATE TABLE metropolises (\n" +
                    "    metropolis CHAR(64),\n" +
                    "    continent CHAR(64),\n" +
                    "    population BIGINT\n" +
                    ");");
            st.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void insertSomeInfo(){
        PreparedStatement st = null;
        try {
            st = con.prepareStatement("INSERT INTO metropolises VALUES\n" +
                    "\t(\"Mumbai\",\"Asia\",20400000),\n" +
                    "        (\"New York\",\"North America\",21295000),\n" +
                    "\t(\"San Francisco\",\"North America\",5780000),\n" +
                    "\t(\"London\",\"Europe\",8580000),\n" +
                    "\t(\"Rome\",\"Europe\",2715000),\n" +
                    "\t(\"Melbourne\",\"Australia\",3900000),\n" +
                    "\t(\"San Jose\",\"North America\",7354555),\n" +
                    "\t(\"Rostov-on-Don\",\"Europe\",1052000);");
            st.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Connection getCon() {
        return con;
    }
    public void closeConn() {
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
