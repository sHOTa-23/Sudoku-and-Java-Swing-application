import java.sql.SQLException;


public class Main {

    public static void main(String[] args) throws SQLException {
            Data data = new Data();
            MVCView view = new  MVCView();
            MVCModel model = new MVCModel(data);
            MVCController controller = new MVCController(model,view);
            controller.start();
    }
}
