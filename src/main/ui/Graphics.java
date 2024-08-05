package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

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

    private JTextField title;
    private JTextField author;
    private JTextField cookTime;
    private JTextField ingredients;
    private JTextField directions;
    private JLabel message;

    private DefaultTableModel sortermodel;
    private JTable searchTable;
    private TableRowSorter<TableModel> rowSorter;
    private JTextField jtfFilter;
    private JButton jbtFilter;

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
        createRecipe();
        testScroll();
        addButtons();
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

        JLabel image = new JLabel();
        image.setIcon(new ImageIcon("./data/icon.png"));
        image.setMinimumSize(new Dimension(10, 20));
        panel1.add(image);
    }

    public void init() {
        database = new Database();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        preReader = new JsonReader(JSON_STORE2);
        preloadRecipe();
    }

    // FROM TestTableSortFilter
    public void addButtons() {
        String[] columnNames = { "Title", "Author", "Cooktime", "Ingredients" };
        ArrayList<Recipe> data = database.getRecipeDatabase();
        Object[][] objectArray = recipe2dArray(data);
        DefaultTableModel sortermodel = new DefaultTableModel(objectArray, columnNames);
        JTable searchTable = new JTable(sortermodel);
        rowSorter = new TableRowSorter<>(searchTable.getModel());
        jtfFilter = new JTextField(20);

        searchTable.setRowSorter(rowSorter);
        JPanel panel = new JPanel(new BorderLayout());

        panel.add(new JLabel("Search database for:"),
                BorderLayout.WEST);
        panel.add(jtfFilter, BorderLayout.CENTER);

        setLayout(new BorderLayout());
        add(panel, BorderLayout.SOUTH);
        panel.add(new JScrollPane(searchTable), BorderLayout.SOUTH);
        panel2.add(panel);

        jtfFilter.getDocument().addDocumentListener(new RecipeDocumentListener(jtfFilter, rowSorter));
    }

    public static Object[][] recipe2dArray(ArrayList<Recipe> recipes) {
        Object[][] arrOfRecipes = new Object[recipes.size()][4];

        for (int current = 0; current < recipes.size(); current++) {
            Recipe recipe = recipes.get(current);
            arrOfRecipes[current][0] = recipe.getTitle();
            arrOfRecipes[current][1] = recipe.getAuthor();
            arrOfRecipes[current][2] = recipe.getCookTime();
            arrOfRecipes[current][3] = recipe.printList(recipe.getIngredients());
        }

        return arrOfRecipes;
    }

    public void createRecipe() {
        JLabel tlabel = new JLabel("Title:");
        panel3.add(tlabel);
        title = new JTextField(65);
        title.setBounds(200, 200, 200, 200);
        title.setText("Default Title");
        panel3.add(title);

        JLabel authorLabel = new JLabel("Author:");
        panel3.add(authorLabel);
        author = new JTextField(63);
        author.setBounds(200, 400, 200, 200);
        author.setText("Default Author");
        panel3.add(author);

        JLabel cookTimeLabel = new JLabel("Cooktime:");
        panel3.add(cookTimeLabel);
        cookTime = new JTextField(60);
        cookTime.setBounds(200, 400, 200, 200);
        cookTime.setText("0");
        panel3.add(cookTime);

        JLabel ingrLabel = new JLabel("Ingredients:");
        panel3.add(ingrLabel);
        ingredients = new JTextField(60);
        ingredients.setBounds(200, 400, 200, 200);
        ingredients.setText("Type ingredients separated by commas");
        panel3.add(ingredients);

        JLabel dirLabel = new JLabel("Directions:");
        panel3.add(dirLabel);
        directions = new JTextField(60);
        directions.setBounds(200, 400, 200, 200);
        directions.setText("Type directions separated by : here");
        panel3.add(directions);

        JButton add = new JButton("Add to Database");
        add.setActionCommand("addRecipe");
        add.setBounds(300, 10, 200, 40);
        panel3.add(add);
        add.addActionListener(this);

        message = new JLabel("No recipe added.");
        panel3.add(message);
    }

    public void testScroll() {
        JPanel panel = new JPanel();
        JList<Recipe> personalRecipes = new JList<>();
        model = new DefaultListModel<>();
        JLabel label = new JLabel();
        personalRecipes.setModel(model);
        JSplitPane splitPane = new JSplitPane();

        for (Recipe r : database.getRecipeDatabase()) {
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

        JButton add = new JButton("Remove all user recipes.");
        add.setActionCommand("removeRecipes");
        add.setBounds(300, 10, 200, 40);
        panel4.add(add);
        add.addActionListener(this);
        
    }

    @Override
    // This is the method that is called when the the JButton btn is clicked
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("addRecipe")) {
            newRecipe = new Recipe(title.getText(), author.getText(), Integer.parseInt(cookTime.getText()));
            if (checkNewRecipe(newRecipe) && !model.contains(newRecipe)) {
                addIngredientsToRecipe(newRecipe);
                addDirectionsToRecipe(newRecipe);
                database.addUserRecipeDatabase(newRecipe);
                model.addElement(newRecipe);
                panel2.removeAll();
                addButtons();
                message.setText("Success!");
            } else if (model.contains(newRecipe)) {
                message.setText("Duplicate recipe!");
            } else {
                message.setText("Please customize ALL fields and set the cook-time greater than 0.");
            }
        } else if (e.getActionCommand().equals("loadRecipes")) {
            loadDatabase();
        } else if (e.getActionCommand().equals("saveRecipes")) {
            saveDatabase();
        } else if (e.getActionCommand().equals("removeRecipes")) {
            for (Recipe r: database.getUserRecipeDatabase()) {
                model.removeElement(r);
            }
            database.getRecipeDatabase().removeAll(database.getUserRecipeDatabase());
            database.resetDatabase();
            panel2.removeAll();
            addButtons();
        }
    }

    public boolean checkNewRecipe(Recipe recipe) {
        String author = recipe.getAuthor();
        String title = recipe.getTitle();
        int cookTime = recipe.getCookTime();

        if (title.equals("Default Title") || author.equals("Default Author") || cookTime < 1) {
            return false;
        }

        return true;
    }

    // MODIFIES: this
    // EFFECTS: prompts user for listed ingredients, and adds the ingredients to
    // the recipe, with splitting based on ','
    public void addIngredientsToRecipe(Recipe recipe) {
        String[] arrOfIngredient = ingredients.getText().split(",");
        for (String ingredient : arrOfIngredient) {
            recipe.addIngredient(ingredient);
        }
    }

    // MODIFIES: this
    // EFFECTS: prompts user for listed ingredients, and adds the ingredients to
    // the recipe, with splitting based on ','
    public void addDirectionsToRecipe(Recipe recipe) {
        String[] arrOfDirection = directions.getText().split(":");
        for (String direction : arrOfDirection) {
            recipe.addDirection(direction);
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
            for (Recipe r : loadedUserRecipes) {
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
