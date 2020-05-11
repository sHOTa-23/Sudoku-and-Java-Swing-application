import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;


import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CityDAOTest {

    private CityDAO cityDAO;
    Data data;
    private static final int INIT_NUM_OF_CITIES = 8;
    @BeforeAll
    void setUp() throws SQLException {
            data = new Data();
            data.createTable();
            data.insertSomeInfo();
            cityDAO = new CityDAO(data.getCon());
    }

    @Test
    void testGetAllSimple() {
        assertEquals(INIT_NUM_OF_CITIES, cityDAO.getALLCity().size());
    }
    @Test
    void testAddAndRemoveSimple() {
        int num = INIT_NUM_OF_CITIES;
        for(int i = 0; i < 100; i++) {
            cityDAO.addCity(new City("random","modnar",i));
        }
        num+=100;
        assertEquals(num,cityDAO.getALLCity().size());
        for(int i = 0; i < 100; i++) {
            cityDAO.deleteCity(new City("random","modnar",i));
        }
        assertEquals(INIT_NUM_OF_CITIES, cityDAO.getALLCity().size());
    }
    @Test
    void testSearchSimple() {
        assertEquals(cityDAO.searchCity(new City("","",0),1,false)
        , cityDAO.getALLCity());
    }
    @Test
    void testAddAndDeleteMedium() {
        for(int i = 0; i < 100; i++) {
            cityDAO.addCity(new City("random","modnar",i));
        }

        for(int i = INIT_NUM_OF_CITIES; i < cityDAO.getALLCity().size(); i++) {
            assertTrue(new City("random","modnar",i-INIT_NUM_OF_CITIES).equals(cityDAO.getALLCity().get(i)));
        }

        for(int i = 0; i < 100; i++) {
            cityDAO.deleteCity(new City("random","modnar",i));
        }
        for(int i = 0; i < cityDAO.getALLCity().size(); i++) {
            assertFalse(new City("random","modnar",i).equals(cityDAO.getALLCity().get(i)));
        }
    }
    @Test
    void testSearchMedium() {
        for(int i = 0; i < 100; i++) {
            cityDAO.addCity(new City("random","modnar",i));
        }

        List<City> cities = cityDAO.searchCity(new City("rand","mo",-1),1,false);
        assertEquals(100,cities.size());
        for(int i = 0; i<100; i++) {
            assertTrue(cities.get(i).equals(cityDAO.getALLCity().get(i+INIT_NUM_OF_CITIES)));
        }

        for(int i = 0; i < 100; i++) {
            cityDAO.deleteCity(new City("random","modnar",i));
        }

    }
    @Test
    void testComplex() {
        for(int i = 0; i < 100; i++) {
            cityDAO.addCity(new City("random","modnar",i));
        }
        List<City> cities = cityDAO.searchCity(new City("","",0),
                0,false);
        assertEquals(1,cities.size());
        cities = cityDAO.searchCity(new City("","",20400000),
                0,false);
        assertEquals(1,cities.size());
        cities = cityDAO.searchCity(new City("","",0),
                1,false);
        assertEquals(99+INIT_NUM_OF_CITIES,cities.size());
        cities = cityDAO.searchCity(new City("","",1052000),
                1,false);
        assertEquals(7,cities.size());
        cities = cityDAO.searchCity(new City("","",1052000),
                -1,false);
        assertEquals(100,cities.size());
        cities = cityDAO.searchCity(new City("","",1052000),
                1,true);
        assertEquals(0,cities.size());
        cities = cityDAO.searchCity(new City("random","modnar",1052000),
                1,true);
        assertEquals(0,cities.size());
        cities = cityDAO.searchCity(new City("random","modnar",1052000),
                0,true);
        assertEquals(0,cities.size());
        cities = cityDAO.searchCity(new City("random","modnar",1052000),
                -1,true);
        assertEquals(100,cities.size());

        for(int i = 0; i < 100; i++) {
            cityDAO.deleteCity(new City("random","modnar",i));
        }
    }

    @Test
    void testCityEquals() {
        String s = "Tbilisi";
        City city = new City("Tbilisi","Europe", 1000000);
        assertFalse(city.equals(s));
        assertTrue((city.equals(city)));
        assertFalse(city.equals(new City("Tbilisi","Europe", 1000001)));
        assertFalse(city.equals(new City("Tbilisi","Europe2", 1000000)));
        assertFalse(city.equals(new City("Tbilisi2","Europe", 1000000)));
    }
    @Test
    void testCityGetRows() {
        String[] cityRow = {"Tbilisi","Europe","1000000"};
        City city = new City("Tbilisi","Europe", 1000000);
        assertTrue(Arrays.equals(city.getInfoForRow(),cityRow));
    }
    @AfterAll
    public void close() {
        data.closeConn();
    }


}