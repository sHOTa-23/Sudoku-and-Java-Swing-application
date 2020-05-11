import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public class MVCModel extends CityDAO {
    private Data data;
    public MVCModel(Data data) throws SQLException {
        super(data.getCon());
        this.data = data;
        data.createTable();
        data.insertSomeInfo();
    }
    public void safeDelete(City city) {
        boolean nothingToDelete = true;
        List<City> cities = getALLCity();
        for(int i = 0; i < cities.size(); i++) {
            if(cities.get(i).equals(city)) {
                deleteCity(city);
                nothingToDelete = false;
            }
        }
        if(nothingToDelete) System.out.println("Nothing To Delete");
    }
    public void closeTheConnection() {
        data.closeConn();
    }

}
