package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import model.Database;

import model.Recipe;
import persistence.JsonReader;
import persistence.JsonWriter;

// A class that is responsible for implementing all the Graphical User Interface features of the app.
// All references have been cited above the relevant methods, all stated code belong to their respective owners.
public class Graphics extends JFrame implements ActionListener {
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private JPanel panel4;

    private Recipe newRecipe;
    private Database database;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private JsonReader preReader;
    private static final String JSON_STORE = "./data/database.json";
    private static final String JSON_STORE2 = "./data/preloaded.json";

    private DefaultListModel<Recipe> model;

    private JTextField title;
    private JTextField author;
    private JTextField cookTime;
    private JTextField ingredients;
    private JTextField directions;

    private JLabel message;
    private JLabel loadMessage;

    private TableRowSorter<TableModel> sorter;
    private JTextField filter;

    // MODIFIES: this, database
    // EFFECTS: initializes main fields, builds the JFrame and all the tabbed panels on start-up and
    //          throws FileNotFoundException if a file is not found
    public Graphics() throws FileNotFoundException {
        init();
        buildFrame();
        makeTabs();
        loadMainPanel();
        loadPanel2();
        loadPanel3();
        loadPanel4();
    }

    // MODIFIES: this, database
    // EFFECTS: initializes the database, JSONReader and JSONWriter, and pre-loads the default recipes into
    //          the database.
    public void init() {
        database = new Database();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        preReader = new JsonReader(JSON_STORE2);
        preloadRecipe();
    }

    // ADAPTED FROM: UBC CPSC210's provided LabelChanger code.
    // MODIFIES: this
    // EFFECTS: sets all the settings of the JFrame.
    private void buildFrame() {
        this.setTitle("Basil");
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: sets up the tabbed panel layout with four panels
    // ADAPTED FROM: https://www.youtube.com/watch?v=RY3Hu4VYYVs&t=292s (YT: Career & Tech HQ)
    private void makeTabs() {
        panel1 = new JPanel();
        panel2 = new JPanel();
        panel3 = new JPanel();
        panel4 = new JPanel();

        panel1.setBackground(Color.WHITE);
        panel2.setBackground(Color.WHITE);
        panel3.setBackground(Color.WHITE);
        panel4.setBackground(Color.WHITE);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setBounds(0, 0, 800, 600);
        tabs.add("Main", panel1);
        tabs.add("Search Database", panel2);
        tabs.add("Create Personal Recipe", panel3);
        tabs.add("View All Recipes", panel4);

        this.add(tabs);
    }

    // MODIFIES: this
    // EFFECTS: paints the main panel with load and save buttons and a menu picture
    // REFERENCE: Button implementation adapted from https://www.youtube.com/watch?v=-IMys4PCkIA (YT: Bro Code) 
    // Image implementation adapted from https://coderanch.com/t/499598/java/add-image-jPanel
    public void loadMainPanel() {
        JButton load = new JButton("LOAD FILE");
        load.setBounds(300, 10, 200, 40);
        buttonCustom(load);
        panel1.add(load);
        load.setActionCommand("loadRecipes");
        load.addActionListener(this);

        JButton save = new JButton("SAVE FILE");
        save.setBounds(300, 10, 200, 40);
        buttonCustom(save);
        panel1.add(save);
        save.setActionCommand("saveRecipes");
        save.addActionListener(this);

        loadMessage = new JLabel("No user data loaded in.");
        panel1.add(loadMessage);

        JLabel menuImage = new JLabel();
        menuImage.setIcon(new ImageIcon("./data/basil menu.png"));
        menuImage.setMaximumSize(new Dimension(10, 10));
        panel1.add(menuImage);
    }

    // MODIFIES: this
    // EFFECTS: Adds the components of panel two, with a JTable filter system
    // ADAPTED FROM: https://stackoverflow.com/questions/22066387/how-to-search-an-element-in-a-jtable-java 
    //               (Author: Paul Samsotha)
    public void loadPanel2() {
        String[] columns = { "Title", "Author", "Cooktime", "Ingredients" };
        ArrayList<Recipe> data = database.getRecipeDatabase();
        Object[][] objectArray = recipe2dArray(data);

        DefaultTableModel sortermodel = new DefaultTableModel(objectArray, columns);
        JTable searchTable = new JTable(sortermodel);
        sorter = new TableRowSorter<>(sortermodel);
        filter = new JTextField(20);
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        searchTable.setOpaque(false);

        searchTable.setRowSorter(sorter);
        panel.add(new JLabel("Search database for:"),
                BorderLayout.NORTH);
        panel.add(filter, BorderLayout.CENTER);

        setLayout(new BorderLayout());
        add(panel, BorderLayout.SOUTH);
        panel.add(new JScrollPane(searchTable), BorderLayout.SOUTH);
        panel2.add(panel);

        filter.getDocument().addDocumentListener(new RecipeDocumentListener(filter, sorter));
    }

    // EFFECTS: Converts the array list of recipes into a two dimensional object array for the table
    // ADAPTED FROM: https://stackoverflow.com/questions/5501809/how-to-access-second-part-of-2d-array,
    // https://sentry.io/answers/how-can-i-create-a-two-dimensional-array-in-javascript/, and
    // https://runestone.academy/ns/books/published/apcsareview/Array2dBasics/a2dDAS.html
    public static Object[][] recipe2dArray(ArrayList<Recipe> recipes) {
        int rowNumber = recipes.size();
        Object[][] arrOfRecipes = new Object[rowNumber][4];

        for (int currentRow = 0; currentRow < rowNumber; currentRow++) {
            Recipe recipe = recipes.get(currentRow);
            arrOfRecipes[currentRow][0] = recipe.getTitle();
            arrOfRecipes[currentRow][1] = recipe.getAuthor();
            arrOfRecipes[currentRow][2] = recipe.getCookTime();
            arrOfRecipes[currentRow][3] = recipe.printList(recipe.getIngredients());
        }

        return arrOfRecipes;
    }

    // MODIFIES: this
    // EFFECTS: paints the panel three with a form to add personal recipe to database
    // REFERENCE: Button and TextField implementation adapted from https://www.youtube.com/watch?v=Kmgo00avvEw (YT: Bro Code) 
    // Image implementation adapted from https://coderanch.com/t/499598/java/add-image-jPanel
    public void loadPanel3() {
        getTlabel();
        getAuthorLabel();
        getCookTimeLabel();
        getIngrLabel();
        getDirLabel();

        JButton add = new JButton("Add to Database");
        add.setActionCommand("addRecipe");
        add.setBounds(300, 10, 200, 40);
        buttonCustom(add);
        panel3.add(add);
        add.addActionListener(this);

        message = new JLabel("No recipe added.");
        panel3.add(message);

        JLabel menuImage = new JLabel();
        menuImage.setIcon(new ImageIcon("./data/background.png"));
        menuImage.setMaximumSize(new Dimension(10, 10));
        panel3.add(menuImage);
    }

    // MODIFIES: this
    // EFFECTS: creates the Directions text-field for user's recipe direction input
    private void getDirLabel() {
        JLabel dirLabel = new JLabel("Directions:");
        panel3.add(dirLabel);
        directions = new JTextField(60);
        directions.setBounds(200, 400, 200, 200);
        directions.setText("Type directions separated by : and NO SPACES");
        panel3.add(directions);
    }

    // MODIFIES: this
    // EFFECTS: creates the Ingredients text-field for user's recipe ingredient input
    private void getIngrLabel() {
        JLabel ingrLabel = new JLabel("Ingredients:");
        panel3.add(ingrLabel);
        ingredients = new JTextField(60);
        ingredients.setBounds(200, 400, 200, 200);
        ingredients.setText("Type ingredients separated by commas and NO SPACES");
        panel3.add(ingredients);
    }

    // MODIFIES: this
    // EFFECTS: creates the cook-time text-field for user's recipe cook-time input
    private void getCookTimeLabel() {
        JLabel cookTimeLabel = new JLabel("Cooktime (mins):");
        panel3.add(cookTimeLabel);
        cookTime = new JTextField(57);
        cookTime.setBounds(200, 400, 200, 200);
        cookTime.setText("0");
        panel3.add(cookTime);
    }

    // MODIFIES: this
    // EFFECTS: creates the author text-field for user's recipe author input
    private void getAuthorLabel() {
        JLabel authorLabel = new JLabel("Author:");
        panel3.add(authorLabel);
        author = new JTextField(63);
        author.setBounds(200, 400, 200, 200);
        author.setText("Default Author");
        panel3.add(author);
    }

    // MODIFIES: this
    // EFFECTS: creates the Title text-field for user's recipe title input
    private void getTlabel() {
        JLabel tlabel = new JLabel("Title:");
        panel3.add(tlabel);
        title = new JTextField(65);
        title.setBounds(200, 200, 200, 200);
        title.setText("Default Title");
        panel3.add(title);
    }

    // EFFECTS: customizes the button's design
    public void buttonCustom(JButton button) {
        button.setBackground(Color.decode("#2C8F00"));
        button.setForeground(Color.WHITE);
        button.setBorderPainted(false);
        button.setFont(new Font("Dialog Input", Font.BOLD, 10));
    }

    
    // ADAPTED FROM: https://www.youtube.com/watch?v=KOI1WbkKUpQ&t=433s (Author: Lazic B)
    // MODIFIES: this
    // EFFECTS: sets up the components in panel four, creating a list view of all the recipes
    public void loadPanel4() {
        JPanel panel = new JPanel();
        JList<Recipe> personalRecipes = new JList<>();
        initializePanel4(personalRecipes);
        JLabel label = new JLabel();
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
        panel.setMinimumSize(new Dimension(100, 100));
        panel.setBackground(Color.WHITE);
        panel.add(label);
        panel4.add(splitPane);

        JButton remove = new JButton("Remove All User Recipes");
        remove.setActionCommand("removeRecipes");
        buttonCustom(remove);
        panel4.add(remove);
        remove.addActionListener(this);

    }

    // MODIFIES: this
    // EFFECTS: intializes DefaultListModel and sets it as the JTable's model
    private void initializePanel4(JList<Recipe> personalRecipes) {
        model = new DefaultListModel<>();
        personalRecipes.setModel(model);
    }

    // ADAPTED FROM: UBC CPSC210's provided LabelChanger code.
    // MODIFIES: this, database
    // EFFECTS: interprets user actions from ActionListener and responds with respective event
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("addRecipe")) {
            newRecipe = new Recipe(title.getText(), author.getText(), Integer.parseInt(cookTime.getText()));
            if (checkNewRecipe(newRecipe) && !model.contains(newRecipe)) {
                addRecipeGUI(newRecipe);
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
            for (Recipe r : database.getUserRecipeDatabase()) {
                model.removeElement(r);
            }
            database.getRecipeDatabase().removeAll(database.getUserRecipeDatabase());
            database.resetDatabase();
            panel2.removeAll();
            loadPanel2();
        }
    }

    // MODIFIES: this, database
    // EFFECTS: adds the recipe to the database and model, refreshes panel2
    public void addRecipeGUI(Recipe recipe) {
        addIngredientsToRecipe(recipe);
        addDirectionsToRecipe(recipe);
        database.addUserRecipeDatabase(recipe);
        model.addElement(recipe);
        panel2.removeAll();
        loadPanel2();
    }

    // EFFECTS: returns true if the inputted recipe has a customized title and author, and a cook-time > 0 mins.
    public boolean checkNewRecipe(Recipe recipe) {
        String author = recipe.getAuthor();
        String title = recipe.getTitle();
        int cookTime = recipe.getCookTime();
        boolean titleCheck = (title.contains("Default Title") || title.length() == 0);
        boolean authorCheck = (author.contains("Default Author") || author.length() == 0);

        if (titleCheck || authorCheck || cookTime < 1) {
            return false;
        }

        return true;
    }

    // MODIFIES: this, database
    // EFFECTS: prompts user for listed ingredients, and adds the ingredients to
    // the recipe, with splitting based on ','
    public void addIngredientsToRecipe(Recipe recipe) {
        String[] arrOfIngredient = ingredients.getText().split(",");
        for (String ingredient : arrOfIngredient) {
            recipe.addIngredient(ingredient);
        }
    }

    // MODIFIES: this, database
    // EFFECTS: prompts user for listed ingredients, and adds the ingredients to
    // the recipe, with splitting based on ','
    public void addDirectionsToRecipe(Recipe recipe) {
        String[] arrOfDirection = directions.getText().split(":");
        for (String direction : arrOfDirection) {
            recipe.addDirection(direction);
        }
    }

    // MODIFIES: this, database
    // EFFECTS: loads pre-loaded recipes from file into the database
    private void preloadRecipe() {
        try {
            database = preReader.read();
        } catch (IOException e) {
            System.out.println("Error in loading default data!");
        }
    }

    // MODIFIES: this, database
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
            panel2.removeAll();
            loadPanel2();
            repaint();
            revalidate();
            loadMessage.setText("Loaded in files from" + JSON_STORE);
        } catch (IOException e) {
            loadMessage.setText("Unable to load files from" + JSON_STORE);
        }
    }

    // EFFECTS: saves the current Database to file
    private void saveDatabase() {
        try {
            jsonWriter.open();
            jsonWriter.write(database);
            jsonWriter.close();
            loadMessage.setText("Saved files to" + JSON_STORE);
        } catch (FileNotFoundException e) {
            loadMessage.setText("Unable to save files" + JSON_STORE);
        }
    }
}
