package code;


import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Minesweeper extends JFrame {

	//Instance Variables
    private JPanel statusbar;
 
    //Constructor 
       public Minesweeper() {

        initUI();
    }
       
    //This method creates the main JPanel 
    private void initUI() {

        statusbar = new JPanel();
        add(statusbar, BorderLayout.SOUTH);

        add(new Board(statusbar));

        setResizable(false);
        pack();

        setTitle("BombSweeper");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    
    //This method is the main run method
    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {

            Minesweeper ex = new Minesweeper();
            ex.setVisible(true);
        });
    }
}