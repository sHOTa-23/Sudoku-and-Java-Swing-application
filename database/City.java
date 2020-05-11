
/*
      this class is used to save all Metropolis info in one Object
 */
public class City {

    private String metropolis, continent;
    private int population;

    public City(String metropolis, String continent, int population) {
        this.metropolis = metropolis;
        this.continent = continent;
        this.population = population;
    }

    /*
        returns the array of strings, which is helpful for the table changes in the view class
     */
    public String[] getInfoForRow() {
        String[] res = new String[3];
        res[0] = metropolis;
        res[1] = continent;
        res[2] = Integer.toString(population);
        return res;
    }

    /*
        used in MVCmodel
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof City)) return false;
        City city = (City) o;
        return population == city.population &&
                metropolis.equals(city.metropolis) &&
                continent.equals(city.continent);
    }

    //getters
    public String getMetropolis() {
        return metropolis;
    }


    public String getContinent() {
        return continent;
    }


    public int getPopulation() {
        return population;
    }


}
