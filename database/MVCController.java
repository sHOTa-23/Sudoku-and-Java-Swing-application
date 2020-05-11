

/*
    this class implements InputListener interface which includes fire signal methods which
    connects view,model via controller class
 */
public class MVCController implements InputListener {
    private MVCModel model;
    private MVCView view;


    public MVCController(MVCModel model, MVCView view) {
        this.model = model;
        this.view = view;
        view.setListener(this);
    }

    /*
      used to identify when to start view
    */
    public void start() {
        view.start();
    }

    /*
        just adds the city into the table
     */
    @Override
    public void fireAddButton(City city) {
        model.addCity(city);
    }

    /*
       clears the JTable using view method
       then changes the JTable with the returned list of city objects
    */
    @Override
    public void fireSearchButton(City city, int populationOption, boolean matchOption) {
        view.clearTable();
        view.changeTable(model.searchCity(city, populationOption, matchOption));
    }

    /*
       clears the JTable using view method
      then changes the JTable with the returned list of city objects
      but this time there is full list
    */
    @Override
    public void fireGetAllButton() {
        view.clearTable();
        view.changeTable(model.getALLCity());
    }

    /*
        deletes the city from table
    */
    @Override
    public void fireDeleteButton(City city) {
        model.safeDelete(city);
    }

    @Override
    public void fireConnectionCLose() {
        model.closeTheConnection();
    }
}
