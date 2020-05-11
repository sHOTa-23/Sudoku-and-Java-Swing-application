
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import java.util.List;


public class MVCView {

    private JFrame frame;
    private JPanel mainPanel;
    private JTable table;
    private JButton add,remove,getAll,search;
    private JTextField metropolis,continent,population;
    private InputListener listener;
    private JComboBox pop,match;

    public MVCView() {
        frame = new JFrame();
        frame.setTitle("Metropolis Viewer");
        frame.setSize(500,500);
        mainPanel = new JPanel(new BorderLayout());
        frame.add(mainPanel);
        setUpTextFields();
        setUpButtons();
        setComboBoxes();
        setUpTable();
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(frame,
                        "Are You Sure You Want To Close This Awesome App?", "Really Bro?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                    listener.fireConnectionCLose();  //fire method which will close the connection
                    System.exit(0);
                }
            }
        });
    }
    public void start() {
        frame.setVisible(true);
    }

    public void setListener(InputListener listener) {
        this.listener = listener;
    }

    /*
    takes defaultTableModel from JTable and the add rows given by argument
     */
    public void changeTable(List<City> cities) {
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        for (City city : cities) {
            model.addRow(city.getInfoForRow());
        }
    }
    /*
       takes defaultTableModel from JTable and removes first row until row count becomes 1
    */
    public void clearTable() {
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        while(model.getRowCount() >= 1) model.removeRow(0);
    }

    private void setUpTable() {
        String[] collNames = {"Metropolis","Continent","Population"};
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnCount(3);
        model.setColumnIdentifiers(collNames);
        table = new JTable(model);
        mainPanel.add(new JScrollPane(table),BorderLayout.CENTER);
    }

    private void setComboBoxes() {
        JPanel comboPanel = new JPanel();
        comboPanel.setLayout(new GridLayout(5,1));
        comboPanel.add(new JPanel());
        JPanel mid = new JPanel();
        mid.setLayout(new GridLayout(2,1,10,5));
        String[] popNames = {"Population Smaller Than","Population Exactly: ","Population Larger Than: "};
        pop = new JComboBox(popNames);
        mid.add(pop);
        String[]  matchNames = {"Exact Match","Partial Match"};
        match = new JComboBox(matchNames);
        mid.add(match);
        mid.setBorder(new TitledBorder("Search Options"));
        comboPanel.add(mid);
        mainPanel.add(comboPanel,BorderLayout.EAST);
    }
    /*
            creates each button and then adds action listeners individually
            clicking add causes to call listener interface's fireAddButt
            same happens to other listener bodies
    */
    private void setUpButtons() {
        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(1, 4, 20, 20));
        add = new JButton("ADD");
        add.addActionListener(e -> {
            int num = getIntSafely();
            listener.fireAddButton(new City(metropolis.getText(),continent.getText(),num));
            setTextsEmpty();
        });
        buttons.add(add);
        search = new JButton("Search");
        search.addActionListener(e -> {
            int num = getIntSafely();
            boolean matchOption = false;
            if(match.getSelectedIndex() == 0) matchOption = true;
            listener.fireSearchButton(new City(metropolis.getText(),continent.getText(),num),pop.getSelectedIndex()-1,matchOption);
            setTextsEmpty();
        });
        buttons.add(search);
        remove = new JButton("Remove");
        remove.addActionListener(e -> {
            int num = getIntSafely();
            listener.fireDeleteButton(new City(metropolis.getText(),continent.getText(),num));
            setTextsEmpty();
        });
        buttons.add(remove);
        getAll = new JButton("Get ALL");
        getAll.addActionListener(e -> listener.fireGetAllButton());
        buttons.add(getAll);
        mainPanel.add(buttons,BorderLayout.SOUTH);
    }
    private void setUpTextFields() {
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new GridLayout(1,6,10,5));
        JLabel metroLabel = new JLabel("Metropolis: ");
        textPanel.add(metroLabel);
        metropolis = new JTextField();
        textPanel.add(metropolis);
        JLabel contLabel = new JLabel("Continent: ");
        textPanel.add(contLabel);
        continent = new JTextField();
        textPanel.add(continent);
        JLabel popLabel = new JLabel("Population: ");
        textPanel.add(popLabel);
        population = new JTextField();
        textPanel.add(population);
        mainPanel.add(textPanel,BorderLayout.NORTH);

    }
    private void setTextsEmpty() {
        metropolis.setText("");
        continent.setText("");
        population.setText("");

    }
    /*
            tries to parse int from population text field
            and catches NumberFormatException
     */
    private int getIntSafely() {
        int num = 0;
        try {
            num = Integer.parseInt(population.getText());
        }
        catch (NumberFormatException n) {
            System.out.println("parse problem");
        }
        return num;
    }
}
