package ui;

import java.util.ArrayList;
import java.util.Scanner;

import model.Database;
import model.Recipe;
import model.UserCollection;

// Recipe application that allows user to search for recipes by ingredient, add recipes
// to collections and add their own recipes.
// REFERENCE: Scanner and menu behaviour is studied/referenced from UBC CPSC210's Teller App
public class RecipeApp {

    private Scanner userinput;
    private boolean programStatus;
    private Database database;

    //EFFECTS: Starts the recipe application
    public RecipeApp() {
        startApp();
    }

    //MODIFIES: this
    //EFFECTS: Initializes the database and controls behaviour of program,
    //         of run-time, based on user inputs
    public void startApp() {
        initialize();

        while (programStatus) {
            menu();
        }
        System.out.println("Thank you for using Basil! :)");
    }

    //MODIFIES: this
    //EFFECTS: intantiates the database
    public void initialize() {
        userinput = new Scanner(System.in);
        programStatus = true;
        database = new Database();
        userinput.useDelimiter("\r?\n|\r");
    }

    // EFFECTS: Prints the menu, prompts user to select from the options and 
    //          recognizes the user input to procceed accordingly. 
    public void menu() {
        System.out.println("Welcome to Basil! Select one of the options below.");
        System.out.println("\t(S) Search Database");
        System.out.println("\t(B) Browse Top Recipe");
        System.out.println("\t(C) Create Personal Recipe");
        System.out.println("\t(V) View Personal Recipes");
        System.out.println("\t(A) View All Collections");
        System.out.println("\t(Q) Quit App");

        String input = this.userinput.nextLine();
        input = input.toLowerCase();
        inputRecognition(input);
    }

    // MODIFIES: this
    // EFFECTS: recognizes the user input and performs respective task
    public void inputRecognition(String prompt) {
        if (prompt.equals("s")) {
            searchWithIngredient();
        } else if (prompt.equals("b"))  {
            browseTop();
        } else if (prompt.equals("c"))  {
            createUserRecipe();
        } else if (prompt.equals("v"))  {
            viewUserRecipes();
        } else if (prompt.equals("a"))  {
            viewCollections();
        } else if (prompt.equals("q"))  {
            programStatus = false;
        } else {
            wrongKey();
        }
    }

    // EFFECTS: Prints a message when the user input does not correspond
    //          to presented options
    public void wrongKey() {
        createDivider();
        System.out.println("Sorry, you pressed an invalid key.");
        returnToMenu();
    }

    // EFFECTS: prompts the user for an ingredient input and finds recipes that
    //          contain the ingredient in the database, presenting further 
    //          user options
    public void searchWithIngredient() {
        createDivider();
        System.out.println("Please enter the ingredient of interest.");
        String input = this.userinput.nextLine();
        ArrayList<Recipe> matches = database.searchByIngredient(input);
        if (matches.size() != 0) {
            recipesPreview(matches);
            System.out.println("(Q) Back to Menu");
            input = this.userinput.nextLine();
            recipesMenuSelection(input, matches);
        } else {
            createDivider();
            System.out.println("Sorry, no matches found.");
            returnToMenu();
        }
    }

    // EFFECTS: finds the recipe with the highest rating in the database and
    //          presents options for interaction
    public void browseTop() {
        Recipe toprecipe = database.findTopRecipe();
        createDivider();
        System.out.println("The top recipe is " + toprecipe.getTitle() + " by " + toprecipe.getAuthor() + "!");
        recipePreview(toprecipe);
        System.out.println("\t(V) View Full Recipe");
        System.out.println("\t(Q) Back to Menu");
        String input = genericLowercaseInput();
        if (input.equals("v")) {
            recipeSelection(toprecipe);
        } else if (input.equals("q")) {
            menu();
        } else {
            wrongKey();
        }
    }

    // MODIFIES: this
    // EFFECTS: Prompts the user with questions to create a recipe and add it
    //          to the database, then presents further options for interaction
    public void createUserRecipe() {
        System.out.println("Enter the title of your recipe:");
        String title = this.userinput.nextLine();
        System.out.println("Enter the name of the author of your recipe:");
        String author = this.userinput.nextLine();
        System.out.println("Enter the cook-time (in minutes) of your recipe:");
        String cooktime = this.userinput.nextLine();
        int time = Integer.valueOf(cooktime);


        Recipe recipe = new Recipe(title, author, time);
        addIngredientsToRecipe(recipe);
        addDirectionsToRecipe(recipe);

        database.addUserRecipeDatabase(recipe);
        createDivider();
        System.out.println("Your recipe has been sucessfully created!");
        System.out.println("(V) View Recipe");
        System.out.println("(B) Back to Menu");
        String input = genericLowercaseInput();

        if (input.equals("v")) {
            recipeSelection(recipe);
        } else if (input.equals("b")) {
            menu();
        } else {
            wrongKey();
        }
    }

    // EFFECTS: reads the next user input and changes it to lowercase
    public String genericLowercaseInput() {
        String input = this.userinput.nextLine();
        input = input.toLowerCase();
        return input;
    } 

    // EFFECTS: Presents all the created collections in the database and presents
    //          user interaction options (view or delete collections)
    public void viewCollections() {
        ArrayList<UserCollection> all = database.viewAllUserCollection();
        if (all.size() == 0) {
            createDivider();
            System.out.println("No collections made yet.");
            returnToMenu();
        } else {
            createDivider();
            System.out.println("Choose which collection to view:");
            createDivider();
            int index = 0;
            for (UserCollection uc: all) {
                System.out.println("(" + Integer.toString(index) + ") " + uc.getTitle());
                System.out.println(uc.getDescription());
                createDivider();
                index++;
            }
            String input = this.userinput.nextLine();
            int val = Integer.valueOf(input);
            UserCollection uc = all.get(val);
            viewCollection(uc);
        }
    }
    
    // MODIFIES: this
    // EFFECTS: Views a collection and presents all the recipes and further
    //          options for user interaction (view or remove recipe from collection)
    public void viewCollection(UserCollection uc) {
        if (uc.getRecipes().size() == 0) {
            System.out.println("No recipes in this collection.");
            returnToMenu();
        } else {
            createDivider();
            System.out.println("\n" + uc.getTitle());
            System.out.println("\n" + uc.getDescription());
            for (Recipe recipe: uc.getRecipes()) {
                recipePreview(recipe);
                createDivider();
            }
            collectionRecipeMenu();

            String firstinput = genericLowercaseInput();
            if (firstinput.equals("v") || firstinput.equals("r")) {
                recipeModification(firstinput, uc);
            } else if (firstinput.equals("u")) {
                resetCollection(uc);
            } else if (firstinput.equals("q")) {
                menu();
            } else {
                wrongKey();
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: removes all the recipe entries from the provided UserCollection
    public void resetCollection(UserCollection uc) {
        uc.resetCollection();
        createDivider();
        System.out.println("All recipes removed from collection.");
        returnToMenu();
    }

    // EFFECTS: Prints the options of the menu when viewing a collection
    public void collectionRecipeMenu() {
        System.out.println("(V) View a Recipe");
        System.out.println("(R) Remove a Recipe.");
        System.out.println("(U) Remove All Recipes");
        System.out.println("(Q) Back to Menu");
    }

    // MODIFIES: this
    // EFFECTS: prompts user for input and removes or deletes a recipe from
    //          a collection or returns to main menu based on input.
    public void recipeModification(String firstinput, UserCollection uc) {
        createDivider();
        System.out.println("Choose a recipe:");
        createDivider();
        int index = 0;
        for (Recipe recipe: uc.getRecipes()) {
            System.out.println("\t(" + Integer.toString(index) + ") " + recipe.getTitle());
            recipePreview(recipe);
            index++;
        }
        String input = genericLowercaseInput();
        if (input.equals("q")) {
            menu();
        } else {
            int val = Integer.valueOf(input);
            Recipe chosen = uc.getRecipes().get(val);
            if (firstinput.equals("r")) {
                removeRecipeOption(uc, chosen);
            } else if (firstinput.equals("v")) {
                recipeSelection(chosen);
            } else {
                wrongKey();
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: Removes recipe from collection if the recipe exists in the
    //          collection
    public void removeRecipeOption(UserCollection uc, Recipe chosen) {
        boolean success = uc.removeRecipe(chosen);
        if (success) {
            createDivider();
            System.out.println("Recipe successfully removed.");
            returnToMenu();
        } else {
            createDivider();
            System.out.println("Chosen recipe does not exist.");
            returnToMenu();
        }
    }

    // EFFECTS: presents a display of the user recipes, and does respective tasks
    //          (view recipe, return to menu) based on user input
    public void viewUserRecipes() {
        ArrayList<Recipe> all = database.getUserRecipeDatabase();
        if (all.size() == 0) {
            createDivider();
            System.out.println("No user recipes have been created.");
            returnToMenu();
        } else {
            recipesPreview(all);
            System.out.println("(R) Remove a recipe");
            System.out.println("(Q) Back to Menu");
            String input = genericLowercaseInput();
            if (input.equals("r")) {
                removeRecipeMenu(all);
            } else {
                recipesMenuSelection(input, all);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: removes the user's recipe from the database if user input
    //          confirms it
    public void removeRecipeMenu(ArrayList<Recipe> recipes) {
        System.out.println("Choose a recipe to remove:");
        int index = 0;
        for (Recipe recipe: recipes) {
            System.out.println("\t(" + Integer.toString(index) + ") " + recipe.getTitle());
            createDivider();
            recipePreview(recipe);
            createDivider();
            index++;
        }
        System.out.println("(Q) Return to Menu");
        String input = genericLowercaseInput();
        if (input.equals("q")) {
            menu();
        } else {
            int val = Integer.valueOf(input);
            Recipe chosen = recipes.get(val);
            database.removeUserRecipeDatabase(chosen);
            createDivider();
            System.out.println("Recipe sucessfully removed.");
            returnToMenu();
        }
    }

    // MODIFIES: this
    // EFFECTS: prompts user for listed ingredients, and adds the ingredients to
    //          the recipe, with splitting based on ','
    public void addIngredientsToRecipe(Recipe recipe) {
        createDivider();
        System.out.println("Please type the name of all your ingredients, separated by commas:");
        String input = this.userinput.nextLine();
        String[] arrOfIngredient = input.split(",");
        for (String ingredient: arrOfIngredient) {
            recipe.addIngredient(ingredient);
        }
    }

    // MODIFIES: this
    // EFFECTS: prompts user for listed directions, and adds the directions to
    //          the recipe, with splitting based on ':'
    public void addDirectionsToRecipe(Recipe recipe) {
        createDivider();
        System.out.println("Please type the directions and use ':' to separate each step.");
        String input = this.userinput.nextLine();
        String[] arrOfDirections = input.split(":");
        for (String direction: arrOfDirections) {
            recipe.addDirection(direction);
        }
    }

    // EFFECTS: Presents recipes and prompts user with option to choose from
    //          the listed recipes or return to menu
    public void recipesMenuSelection(String input, ArrayList<Recipe> recipes) {
        if (recipes.size() == 0) {
            createDivider();
            System.out.println("There are currently no recipes.");
            returnToMenu();
        } else {
            if (input.equals("q")) {
                menu();
            } else {
                int index = Integer.valueOf(input);
                Recipe chosen = recipes.get(index);
                recipeSelection(chosen); 
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: presents the user a menu for interactions with recipe
    //          and performs respective chosen task based on user input
    public void recipeSelection(Recipe recipe) {
        recipeFullView(recipe);
        createDivider();
        System.out.println("(A) Add Recipe to Collection");
        System.out.println("(C) Add a comment.");
        System.out.println("(S) View all comments.");
        System.out.println("(R) Rate this recipe.");
        System.out.println("(Q) Back to Menu");
        String input = genericLowercaseInput();
        if (input.equals("a")) {
            addToCollection(recipe);
        } else if (input.equals("c")) {
            addComment(recipe);
        } else if (input.equals("s")) {
            viewAllComments(recipe);
        } else if (input.equals("r")) {
            addRating(recipe);
        } else if (input.equals("q")) {
            menu();
        } else {
            wrongKey();
        }
    }

    // MODIFIES: this
    // EFFECTS: presents the user with option to rate the recipe positively and
    //          updates and presents the rating of recipe based on user decisions
    public void addRating(Recipe recipe) {
        System.out.println("Would you recommend this recipe?");
        createDivider();
        System.out.println("(Y) Yes.");
        System.out.println("(N) No.");
        String input = this.userinput.nextLine();
        input = input.toLowerCase();
        if (input.equals("y")) {
            recipe.addReccomend(true);
        } else if (input.equals("n")) {
            recipe.addReccomend(false);
        } else {
            wrongKey();
        }
        createDivider();
        System.out.println("The recipe's rating is now " + Double.toString(recipe.getRating()) + "%");
        returnToMenu();
        
    }

    // MODIFIES: this
    // EFFECTS: presents the user with option to add a comment and presents
    //          all comments on recipe based on user decisions
    public void addComment(Recipe recipe) {
        createDivider();
        System.out.println("Please type your comment below:");
        String input = this.userinput.nextLine();
        recipe.addComment(input);
        viewAllComments(recipe);
    }

    // EFFECTS: displays all the comments of the recipe, along with other
    //          options of interaction
    public void viewAllComments(Recipe recipe) {
        if (recipe.getComments().size() == 0) {
            createDivider();
            System.out.println("No comments yet.");
            addCommentMenu(recipe);
        } else {
            createDivider();
            System.out.println("All comments on this recipe:");
            createDivider();
            ArrayList<String> comments = recipe.getComments();
            for (String comment: comments) {
                System.out.println("Anonymous user commented: " + "'" + comment + "'");
            }
            addCommentMenu(recipe);
        }
    }

    // EFFECTS: prompts user with option of adding a comment or returning to menu
    //          and performs respective task
    public void addCommentMenu(Recipe recipe) {
        createDivider();
        System.out.println("(A) Add a comment.");
        System.out.println("(Q) Back to Menu.");
        String input = genericLowercaseInput();
        if (input.equals("a")) {
            addComment(recipe);
        } else if (input.equals("q")) {
            menu();
        } else {
            wrongKey();
        }
    }

    // EFFECTS: prints the title, author, cooktime and rating of recipe
    public void recipePreview(Recipe recipe) {
        System.out.println("\tTitle: " + recipe.getTitle());
        System.out.println("\tAuthor: " + recipe.getAuthor());
        System.out.println("\tCooktime: " + recipe.getCookTime());
        System.out.println("\tRating: " + recipe.getRating() + "%");
    }

    // EFFECTS: Presents all the recipes in the list next to their respective
    //          index number
    public void recipesPreview(ArrayList<Recipe> recipes) {
        int index = 0;
        for (Recipe r: recipes) {
            recipePreview(r);
            System.out.println("(" + Integer.toString(index) + ")" + " " + "View This Recipe");
            createDivider();
            index++;
        }
    }

    // EFFECTS: Prints the recipe's title, author, cook-time, rating, ingredients
    //          and directions
    public void recipeFullView(Recipe recipe) {
        recipePreview(recipe);
        createDivider();
        System.out.println("Ingredients:");
        for (String ingredient: recipe.getIngredients()) {
            System.out.println(ingredient);
        }
        createDivider();
        System.out.println("Directions:");
        int count = 1;
        for (String direction: recipe.getDirections()) {
            System.out.println(Integer.toString(count) + ". " + direction);
            count++;
        }
    }

    // EFFECTS: Prompts user to add the recipe to a new collection, an existing
    //          collection, or to go back to menu. Performs chosen task
    public void addToCollection(Recipe recipe) {
        createDivider();
        System.out.println("(N) Add to New Collection");
        System.out.println("(A) Add to Existing Collection");
        System.out.println("(Q) Back to Main Menu");
        createDivider();
        String input = this.userinput.nextLine();
        input = input.toLowerCase();
        if (input.equals("n")) {
            addToNewCollection(recipe);
        } else if (input.equals("a")) {
            addToExistingCollection(recipe);
        } else if (input.equals("q")) {
            menu();
        } else {
            wrongKey();
        }
    }

    // MODIFIES: this
    // EFFECTS: Prompts user with questions to choose a title and description
    //          when creating a new collection and performs respective chosen task.
    public void addToNewCollection(Recipe recipe) {
        System.out.println("Let's create a new collection!");
        UserCollection newcollection = new UserCollection();
        createDivider();
        System.out.println("Enter the title of your collection:");
        String title = this.userinput.nextLine();
        createDivider();
        System.out.println("Enter the description of your collection:");
        String description = this.userinput.nextLine();
        newcollection.setTitle(title);
        newcollection.setDescription(description);
        
        optionAddCollection(newcollection, recipe);
    }

    // MODIFIES: this
    // EFFECTS: Prompts user to confirm whether to add recipe to collection and
    //          performs the chosen task upon decision.
    public void optionAddCollection(UserCollection collection, Recipe recipe) {
        createDivider();
        System.out.println("Would you like to add your recipe to this collection?");
        createDivider();
        System.out.println("(Y) Yes");
        System.out.println("(N) No");
        String input = genericLowercaseInput();
        if (input.equals("y")) {
            confirmAddRecipeCollection(input, collection, recipe);
        } else if (input.equals("n")) {
            createDivider();
            System.out.println("The recipe was not added to the collection.");
            returnToMenu();
        } else {
            wrongKey();
        }
    }

    // MODIFIES: this
    // EFFECTS: Adds recipe to collection if the recipe does not already exist in collection
    //          and presents further options to view all collections or return to menu,
    //          by prompting user input.
    public void confirmAddRecipeCollection(String input, UserCollection collection, Recipe recipe) {
        database.addToExistingUserCollection(collection);
        boolean success = collection.addRecipe(recipe);
        if (success) {
            createDivider();
            System.out.println("The recipe was successfully added to your collection.");
            collectionSmallMenu(input);
        } else {
            createDivider();
            System.out.println("This recipe already exists in the collection.");
            collectionSmallMenu(input);
        }
    }

    // EFFECTS: displays a small menu for a collection, with option to view
    //          all menu or return to main menu
    public void collectionSmallMenu(String input) {
        System.out.println("(V) View all collections.");
        System.out.println("(Q) Back to Menu");
        input = genericLowercaseInput();
        if (input.equals("v")) {
            viewCollections();
        } else if (input.equals("q")) {
            menu();
        } else {
            wrongKey();
        }
    }

    // EFFECTS: prompts user to return to menu and returns to menu
    //          based on user input.
    public void returnToMenu() {
        System.out.println("(Q) Back to Menu");
        String input = genericLowercaseInput();
        if (input.equals("q")) {
            menu();
        } else {
            wrongKey();
        }
    }

    // MODIFIES: this
    // EFFECTS: Adds recipe to an existing collection from all the collections
    //          available
    public void addToExistingCollection(Recipe recipe) {
        ArrayList<UserCollection> all = database.viewAllUserCollection();
        if (all.size() == 0) {
            System.out.println("There are currently no existing collections.");
            returnToMenu();
        } else {
            System.err.println("Choose a collection to add the recipe to.");
            int index = 0;
            for (UserCollection uc: all) {
                System.out.println("(" + Integer.toString(index) + ") " + uc.getTitle());
                index++;;
            }
            String input = this.userinput.nextLine();
            int val = Integer.valueOf(input);
            UserCollection uc = all.get(val);
            optionAddCollection(uc, recipe);
        }
    }

    // EFFECTS: prints a divider
    public void createDivider() {
        System.out.println("------------------------");
    }

}
