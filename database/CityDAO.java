import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class CityDAO {
    private Connection con;

    public CityDAO(Connection con) throws SQLException {
        this.con = con;
            PreparedStatement st = con.prepareStatement("USE metropolis_db;");
            boolean used = st.execute();
    }

    public void addCity(City city) {
        try {
            PreparedStatement st = con.prepareStatement("INSERT INTO metropolises VALUES(?, ?, ?);");
            setInfoToStFromCity(st, city);
            boolean added = st.execute();
        } catch (SQLException e) {e.printStackTrace();}
    }

    /*
            this method uses switch to create sql statement
            invariant is that populationOption is in {-1,0,1} set
            which indents operator for Population where statement
     */
    public List<City> searchCity(City city, int populationOption, boolean matchOption) {
        List<City> cities = new ArrayList<>();
        String statementText = "SELECT* FROM metropolises WHERE ";
        switch (populationOption) {
            case 1:
                statementText += "Population > ?  AND ";
                break;
            case 0:
                statementText += "Population = ? AND ";
                break;
            case -1:
                statementText += "Population < ? AND ";
                break;
        }
        if (matchOption) statementText += "Continent = ? AND Metropolis = ? ;";
        //checks if either one of string is substring of another's
        else
            statementText += "LOCATE(?,Continent)!= 0 AND LOCATE(?,Metropolis)!= 0 OR LOCATE(Continent,?)!= 0 AND LOCATE(Metropolis,?)!= 0 OR LOCATE(?,Continent)!= 0 AND LOCATE(Metropolis,?)!= 0 OR  LOCATE(Continent,?)!= 0 AND LOCATE(?,Metropolis)!= 0 ;";
        try {
            PreparedStatement st = con.prepareStatement(statementText);
            st.setInt(1, city.getPopulation());
            st.setString(2, city.getContinent());
            st.setString(3, city.getMetropolis());
            if (!matchOption) {
                st.setString(4, city.getContinent());
                st.setString(5, city.getMetropolis());
                st.setString(6, city.getContinent());
                st.setString(7, city.getMetropolis());
                st.setString(8, city.getContinent());
                st.setString(9, city.getMetropolis());
            }
            ResultSet res = st.executeQuery();
            while (res.next()) {
                cities.add(new City(res.getString(1),
                        res.getString(2), res.getInt(3)));
            }
        } catch (SQLException e) {e.printStackTrace();}
        return cities;
    }

    //these was not in original assignment but let's make it fun
    //delete method and getAll method
    public void deleteCity(City city) {
        try {
            PreparedStatement st = con.prepareStatement("DELETE FROM metropolises WHERE Metropolis = ? " +
                    "AND Continent = ? AND Population = ?");
            setInfoToStFromCity(st, city);
            boolean deleted = st.execute();
        } catch (SQLException e) {e.printStackTrace();}
    }

    public List<City> getALLCity() {
        List<City> cities = new ArrayList<>();
        try {
            PreparedStatement st = con.prepareStatement("SELECT* FROM metropolises");
            ResultSet res = st.executeQuery();
            while (res.next()) {
                cities.add(new City(res.getString(1),
                        res.getString(2), res.getInt(3)));
            }
        } catch (SQLException e) {e.printStackTrace();}
        return cities;
    }

    //used for decomposition
    private void setInfoToStFromCity(PreparedStatement st, City city) {
        try {
            st.setString(1, city.getMetropolis());
            st.setString(2, city.getContinent());
            st.setInt(3, city.getPopulation());
        } catch (SQLException e) {e.printStackTrace();}

    }
}
