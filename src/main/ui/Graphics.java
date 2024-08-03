package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import model.Recipe;

public class Graphics extends JFrame {
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private JFrame frame;

    public Graphics() {
        this.setTitle("Basil");
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        
        panel1 = new JPanel();
        panel1.setBackground(Color.white);
        panel2 = new JPanel();
        panel3 = new JPanel();
        JPanel panel4 = new JPanel();
        
        JTabbedPane tabbedpane = new JTabbedPane();
        tabbedpane.setBounds(0,0,800,600);
        tabbedpane.add("Main", panel1);
        tabbedpane.add("Search Database", panel2);
        tabbedpane.add("Create Personal Recipe", panel3);
        tabbedpane.add("View Personal Recipes", panel4);
        
        this.add(tabbedpane);
        this.setVisible(true);

        addButtons();
    }

    public void addButtons() {
        JButton srch = new JButton("Search Database");
        srch.setBounds(300, 10, 200, 40);
        panel2.add(srch);
        ArrayList<Recipe> foods = new ArrayList<>();
        foods.add(new Recipe("title", "Author", 1));
        foods.add(new Recipe("title1", "Author1", 1));

        Recipe[] recipes = new Recipe[foods.size()];
        //Assuming there is data in your list
        JList<Recipe> list = new JList<>(foods.toArray(recipes));
        panel2.add(list);

        srch.addActionListener(new SearchActionListener(list));
    }

    public void createRecipe() {

    }
}
