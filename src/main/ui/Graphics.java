package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import model.Database;

import model.Recipe;
import persistence.JsonReader;
import persistence.JsonWriter;

public class Graphics extends JFrame implements ActionListener {
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private JPanel panel4;
    private JFrame frame;
    private Recipe newRecipe;
    private Database database;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private JsonReader preReader;
    private DefaultListModel<Recipe> model;
    private static final String JSON_STORE = "./data/database.json";
    private static final String JSON_STORE2 = "./data/preloaded.json";

    public Graphics() throws FileNotFoundException {
        init();
        this.setTitle("Basil");
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);

        panel1 = new JPanel();
        panel2 = new JPanel();
        panel3 = new JPanel();
        panel4 = new JPanel();

        JTabbedPane tabbedpane = new JTabbedPane();
        tabbedpane.setBounds(0, 0, 800, 600);
        tabbedpane.add("Main", panel1);
        tabbedpane.add("Search Database", panel2);
        tabbedpane.add("Create Personal Recipe", panel3);
        tabbedpane.add("View All Recipes", panel4);

        this.add(tabbedpane);
        this.setVisible(true);

        mainMenu();
        addButtons();
        createRecipe();
        testScroll();
    }

    public void mainMenu() {
        JButton load = new JButton("Load File");
        load.setBounds(300, 10, 200, 40);
        panel1.add(load);
        load.setActionCommand("loadRecipes");
        load.addActionListener(this);

        JButton save = new JButton("Save File");
        save.setBounds(300, 10, 200, 40);
        panel1.add(save);
        save.setActionCommand("saveRecipes");
        save.addActionListener(this);
    }

    public void init() {
        database = new Database();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        preReader = new JsonReader(JSON_STORE2);
        preloadRecipe();
    }

    public void addButtons() {
        JButton srch = new JButton("Search Database");
        srch.setBounds(300, 10, 200, 40);
        panel2.add(srch);
        ArrayList<Recipe> foods = database.getRecipeDatabase();

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

        newRecipe = new Recipe(title.getText(), author.getText(), Integer.parseInt(cookTime.getText()));
    }

    public void testScroll() {
        JPanel panel = new JPanel();
        JList<Recipe> personalRecipes = new JList<>();
        model = new DefaultListModel<>();
        JLabel label = new JLabel();
        personalRecipes.setModel(model);
        JSplitPane splitPane = new JSplitPane();

        for (Recipe r: database.getRecipeDatabase()) {
            model.addElement(r);
        }

        personalRecipes.getSelectionModel().addListSelectionListener(e -> {
            Recipe p = personalRecipes.getSelectedValue();
            label.setText(p.printRecipe());
        });

        splitPane.setLeftComponent(new JScrollPane(personalRecipes));
        splitPane.setRightComponent(panel);
        panel.add(label);
        panel4.add(splitPane);
    }

    @Override
    // This is the method that is called when the the JButton btn is clicked
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("addRecipe")) {
            database.addDefaultRecipeDatabase(newRecipe);
            database.addUserRecipeDatabase(newRecipe);
            model.addElement(newRecipe);
            JLabel success = new JLabel();
            success.setText("Success!");
            panel3.add(success);
            repaint();
            panel2.repaint();
            panel2.revalidate();
        } else if (e.getActionCommand().equals("loadRecipes")) {
            loadDatabase();
        } else if (e.getActionCommand().equals("saveRecipes")) {
            saveDatabase();
        }
    }

    // MODIFIES: this
    // EFFECTS: loads pre-loaded recipes from file into the database
    private void preloadRecipe() {
        try {
            database = preReader.read();
        } catch (IOException e) {
            //
        }
    }

    // MODIFIES: this
    // EFFECTS: loads the previously saved Database from file
    private void loadDatabase() {
        try {
            database = jsonReader.read();
            ArrayList<Recipe> loadedUserRecipes = database.getUserRecipeDatabase();
            for (Recipe r: loadedUserRecipes) {
                if (!model.contains(r)) {
                    model.addElement(r);
                }
            }
            repaint();
            revalidate();
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: saves the current Database to file
    private void saveDatabase() {
        try {
            jsonWriter.open();
            jsonWriter.write(database);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }
}
