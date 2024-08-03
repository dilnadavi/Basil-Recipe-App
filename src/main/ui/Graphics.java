package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import model.Database;

import model.Recipe;

public class Graphics extends JFrame implements ActionListener {
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private JPanel panel4;
    private JFrame frame;
    private Recipe newRecipe;
    private RecipeApp recipeApp;

    public Graphics() throws FileNotFoundException {
        recipeApp = new RecipeApp();
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
        panel4 = new JPanel();

        JTabbedPane tabbedpane = new JTabbedPane();
        tabbedpane.setBounds(0, 0, 800, 600);
        tabbedpane.add("Main", panel1);
        tabbedpane.add("Search Database", panel2);
        tabbedpane.add("Create Personal Recipe", panel3);
        tabbedpane.add("View Personal Recipes", panel4);

        this.add(tabbedpane);
        this.setVisible(true);

        addButtons();
        createRecipe();
        viewPersonalRecipes();
    }

    public void viewPersonalRecipes() {
        JButton srch = new JButton("Something");
        srch.setBounds(300, 10, 200, 40);
        panel4.add(srch);
    }

    public void addButtons() {
        JButton srch = new JButton("Search Database");
        srch.setBounds(300, 10, 200, 40);
        panel2.add(srch);
        ArrayList<Recipe> foods = new ArrayList<>();
        foods.add(new Recipe("title", "Author", 1));
        foods.add(new Recipe("title1", "Author1", 1));

        Recipe[] recipes = new Recipe[foods.size()];
        // Assuming there is data in your list
        JList<Recipe> list = new JList<>(foods.toArray(recipes));
        panel2.add(list);

        srch.addActionListener(new SearchActionListener());
    }

    public void createRecipe() {
        JLabel tlabel = new JLabel("Title:");
        panel3.add(tlabel);
        JTextField title = new JTextField(65);
        title.setBounds(200, 200, 200, 200);
        title.setText("Default Title");
        panel3.add(title);

        JLabel authorLabel = new JLabel("Author:");
        panel3.add(authorLabel);
        JTextField author = new JTextField(63);
        author.setBounds(200, 400, 200, 200);
        author.setText("Default Author");
        panel3.add(author);

        JLabel cookTimeLabel = new JLabel("Cooktime:");
        panel3.add(cookTimeLabel);
        JTextField cookTime = new JTextField(60);
        cookTime.setBounds(200, 400, 200, 200);
        cookTime.setText("0");
        panel3.add(cookTime);

        JLabel ingrLabel = new JLabel("Ingredients:");
        panel3.add(ingrLabel);
        JTextField ingredients = new JTextField(60);
        ingredients.setBounds(200, 400, 200, 200);
        ingredients.setText("Type ingredients separated by commas");
        panel3.add(ingredients);

        JLabel dirLabel = new JLabel("Directions:");
        panel3.add(dirLabel);
        JTextField directions = new JTextField(60);
        directions.setBounds(200, 400, 200, 200);
        directions.setText("Type directions separated by :");
        panel3.add(directions);

        JButton add = new JButton("Add to Database");
        add.setActionCommand("addRecipe");
        add.setBounds(300, 10, 200, 40);
        panel3.add(add);
        add.addActionListener(this);

        newRecipe = new Recipe(tlabel.getText(), authorLabel.getText(), Integer.parseInt(cookTime.getText()));
    }

    @Override
    // This is the method that is called when the the JButton btn is clicked
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("addRecipe")) {
            //
        }
    }
}
