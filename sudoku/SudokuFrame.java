import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.*;


public class SudokuFrame extends JFrame {
    private JButton check;
    private JCheckBox autoCheck;
    private Sudoku sudo;
    private JTextArea leftText;
    private JTextArea rightText;
    private JPanel panel;

    public SudokuFrame() {
        super("Sudoku Solver");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(getParent(),
                        "Are You Sure You Want To Close This Aweseome Game? ", "Sudo it Bro",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                    System.exit(0);
                }
            }
        });
        setResizable(false);
        setSize(400, 400);
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        add(panel);
        setButtons();
        setTexts();
        setVisible(true);
    }

    private void setTexts() {
        JPanel leftPanel = new JPanel();
        leftText = new JTextArea(15, 20);
        leftText.setBorder(new TitledBorder("Puzzle"));
        leftPanel.add(leftText);
        leftText.setLineWrap(true);
        panel.add(leftPanel, BorderLayout.WEST);
        JPanel rightPanel = new JPanel();
        rightText = new JTextArea(15, 20);
        rightText.setLineWrap(true);
        rightText.setEditable(false);
        rightPanel.add(rightText);
        rightText.setBorder(new TitledBorder("Solution"));
        leftText.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                setDocumentEventCode();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                setDocumentEventCode();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                setDocumentEventCode();
            }
        });
        panel.add(rightPanel, BorderLayout.EAST);
    }

    private void setButtons() {
        JPanel buttPanel = new JPanel();
        check = new JButton("Check");
        buttPanel.add(check);
        autoCheck = new JCheckBox("Auto Check");
        buttPanel.add(autoCheck);
        autoCheck.setSelected(false);
        check.addActionListener(e -> setListenerCode());
        JPanel pan = new JPanel();
        pan.setLayout(new BorderLayout());
        pan.add(buttPanel, BorderLayout.WEST);
        panel.add(pan, BorderLayout.SOUTH);
    }

    private void setDocumentEventCode() {
        if (autoCheck.isSelected()) setListenerCode();
    }

    private void setListenerCode() {
        try {
            sudo = new Sudoku(leftText.getText());
            int num = sudo.solve();
            rightText.setText(sudo.getSolutionText() + System.getProperty("line.separator") +
                    "solutions num: " + num + System.getProperty("line.separator") +
                    "time elapsed: " + sudo.getElapsed());

        } catch (RuntimeException r) {
            System.out.println(r.getMessage());
            rightText.setText("Parsing error");
        }
    }

    public static void main(String[] args) {
        // GUI Look And Feel
        // Do this incantation at the start of main() to tell Swing
        // to use the GUI LookAndFeel of the native platform. It's ok
        // to ignore the exception.
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }
        SudokuFrame frame = new SudokuFrame();
    }

}
