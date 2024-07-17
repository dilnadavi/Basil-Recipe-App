package ui;

import java.util.ArrayList;
import java.util.Scanner;

import model.Database;
import model.Recipe;
import model.UserCollection;

public class RecipeApp {

    private Scanner userinput;
    private boolean programStatus;
    private Database database;

    public RecipeApp() {
        startApp();
    }

    public void startApp() {
        initialize();

        while (programStatus) {
            menu();
        }
        System.out.println("Thank you for using Basil! :)");
    }

    public void initialize() {
        userinput = new Scanner(System.in);
        programStatus = true;
        database = new Database();
    }

    public void menu() {
        System.out.println("Welcome to Basil! Select one of the options below.");
        System.out.println("\t(S) Search Database");
        System.out.println("\t(B) Browse Top Recipe This Week");
        System.out.println("\t(C) Create Personal Recipe");
        System.out.println("\t(V) View Personal Recipes");
        System.out.println("\t(A) View All Collections");
        System.out.println("\t(Q) Quit App");

        String input = this.userinput.nextLine();
        input = input.toLowerCase();
        inputRecognition(input);
    }

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

    public void wrongKey() {
        System.out.println("Sorry, you pressed an invalid key.");
        returnToMenu();
    }

    public void searchWithIngredient() {
        System.out.println("Please enter the ingredient of interest.");
        String input = this.userinput.nextLine();
        ArrayList<Recipe> matches = database.searchByIngredient(input);
        if (matches.size() != 0) {
            recipesPreview(matches);
            System.out.println("(Q) Back to Menu");
            input = this.userinput.nextLine();
            recipesMenuSelection(input, matches);
            // TODO: Repetition
        } else {
            System.out.println("Sorry, no matches found.");
        }
    }

    public void browseTop() {
        Recipe toprecipe = database.findTopRecipe();
        System.out.println("The top recipe is" + toprecipe.getTitle() + "by" + toprecipe.getAuthor() + "!");
        recipePreview(toprecipe);
        System.out.println("\t(V) View Full Recipe");
        System.out.println("\t(Q) Back to Menu");

        // TODO: fix this

    }

    public void createUserRecipe() {
        Recipe recipe = new Recipe();
        System.out.println("Enter the title of your recipe:");
        String title = this.userinput.nextLine();
        System.out.println("Enter the name of the author of your recipe:");
        String author = this.userinput.nextLine();
        System.out.println("Enter the cook-time (in minutes) of your recipe:");
        String cooktime = this.userinput.nextLine();
        int time = Integer.valueOf(cooktime);


        setRecipe(recipe, title, author, time);
        addIngredientsToRecipe(recipe);
        addDirectionsToRecipe(recipe);

        database.addUserRecipeDatabase(recipe);

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

    public void setRecipe(Recipe recipe, String title, String author, int time) {
        recipe.setTitle(title);
        recipe.setAuthor(author);
        recipe.setCookTime(time);
    }

    public String genericLowercaseInput() {
        String input = this.userinput.nextLine();
        input = input.toLowerCase();
        return input;
    } 

    public void viewCollections() {
        ArrayList<UserCollection> all = database.viewAllUserCollection();
        if (all.size() == 0) {
            System.out.println("No collections made yet.");
            System.out.println("(Q) Back to Menu");
            String input = this.userinput.nextLine();
            input = input.toLowerCase();
            if (input.equals("q")) {
                menu();
            }
        } else {
            System.out.println("Choose which collection to view:");
            int index = 0;
            for (UserCollection uc: all) {
                System.out.println("(" + Integer.toString(index) + ")" + uc.getTitle());
                System.out.println(uc.getDescription());
                createDivider();
                index++;
            }
            String input = this.userinput.nextLine();
            // TODO REMOVED LOWERCASE
            int val = Integer.valueOf(input);
            UserCollection uc = all.get(val);
            viewCollection(uc);
        }
    }

    public void viewCollection(UserCollection uc) {
        if (uc.getRecipes().size() == 0) {
            System.out.println("No recipes in this collection.");
            returnToMenu();
        } else {
            for (Recipe recipe: uc.getRecipes()) {
                recipePreview(recipe);
                createDivider();
            }
            collectionRecipeMenu();

            String firstinput = genericLowercaseInput();
            if (firstinput.equals("v") || firstinput.equals("r")) {
                recipeModification(firstinput, uc);
            } else if (firstinput.equals("u")) {
                uc.resetCollection();
                System.out.println("All recipes removed from collection.");
                returnToMenu();
            } else if (firstinput.equals("q")) {
                menu();
            } else {
                wrongKey();
            }
        }
    }

    public void collectionRecipeMenu() {
        System.out.println("(V) View a Recipe");
        System.out.println("(R) Remove a Recipe.");
        System.out.println("(U) Remove All Recipes");
        System.out.println("(Q) Back to Menu");
    }

    public void recipeModification(String firstinput, UserCollection uc) {
        System.out.println("Choose a recipe:");
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
                recipeFullView(chosen);
            } else {
                wrongKey();
            }
        }
    }

    public void removeRecipeOption(UserCollection uc, Recipe chosen) {
        boolean success = uc.removeRecipe(chosen);
        if (success) {
            System.out.println("Recipe successfully removed.");
            returnToMenu();
        } else {
            System.out.println("Chosen recipe does not exist.");
            returnToMenu();
        }
    }

    public void viewUserRecipes() {
        ArrayList<Recipe> all = database.getUserRecipeDatabase();
        if (all.size() == 0) {
            System.out.println("No user recipes have been created.");
            returnToMenu();
        } else {
            recipesPreview(all);
            System.out.println("(Q) Back to Menu");
            String input = this.userinput.nextLine();
            recipesMenuSelection(input, all);
        }
        // TODO (Repetition)
    }

    public void addIngredientsToRecipe(Recipe recipe) {
        System.out.println("Please type the name of all your ingredients, separated by commas:");
        String input = this.userinput.nextLine();
        String[] arrOfIngredient = input.split(",");
        for (String ingredient: arrOfIngredient) {
            recipe.addIngredient(ingredient);
        }
    }

    public void addDirectionsToRecipe(Recipe recipe) {
        System.out.println("Please type the directions and use ':' to separate each step.");
        String input = this.userinput.nextLine();
        String[] arrOfDirections = input.split(":");
        for (String direction: arrOfDirections) {
            recipe.addDirection(direction);
        }
    }

    public void recipesMenuSelection(String input, ArrayList<Recipe> recipes) {
        if (recipes.size() == 0) {
            System.out.println("There are currently no recipes.");
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

    public void collectionMenuSelection(String input, ArrayList<UserCollection> uc, Recipe recipe) {
        if (input.equals("b")) {
            menu();
        } else {
            int index = Integer.valueOf(input);
            UserCollection chosen = uc.get(index);
            System.out.println("Would you like to add the recipe to this collection?");
            System.out.println("(Y) Yes.");
            System.out.println("(N) No.");
            input = this.userinput.nextLine();
            input = input.toLowerCase();
            if (input.equals("y")) {
                chosen.addRecipe(recipe);
            } else if (input.equals("n")) {
                chosen.addRecipe(recipe);
            } else {
                wrongKey();
            }

        }
    }

    public void recipeSelection(Recipe recipe) {
        recipeFullView(recipe);
        createDivider();
        System.out.println("(A) Add Recipe to Collection");
        System.out.println("(C) Add a comment.");
        System.out.println("(S) View all comments.");
        System.out.println("(R) Rate this recipe.");
        System.out.println("(Q) Back to Menu");
        String input = this.userinput.nextLine();
        input = input.toLowerCase();
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

    public void addRating(Recipe recipe) {
        System.out.println("Would you recommend this recipe?");
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
        System.out.println("The recipe's rating is now " + Double.toString(recipe.getRating()) + "%");
        returnToMenu();
        
    }

    public void addComment(Recipe recipe) {
        System.out.println("Please type your comment below:");
        String input = this.userinput.nextLine();
        recipe.addComment(input);
        viewAllComments(recipe);
        returnToMenu();
    }

    public void viewAllComments(Recipe recipe) {
        if (recipe.getComment().size() == 0) {
            System.out.println("No comments yet.");
            System.out.println("(A) Add a comment.");
            System.out.println("(Q) Back to Menu.");
            String input = userinput.nextLine();
            input = input.toLowerCase();
            if (input.equals("a")) {
                addComment(recipe);
            } else if (input.equals("q")) {
                menu();
            } else {
                wrongKey();
            }
        } else {
            System.out.println("All comments on this recipe:");
            ArrayList<String> comments = recipe.getComment();
            for (String comment: comments) {
                System.out.println("Anonymous user commented: " + "'" + comment + "'");
            }
        }
    }

    public void recipePreview(Recipe recipe) {
        System.out.println("\tTitle:" + recipe.getTitle());
        System.out.println("\tAuthor:" + recipe.getAuthor());
        System.out.println("\tCooktime:" + recipe.getCookTime());
        System.out.println("\tRating:" + recipe.getRating());
    }

    public void recipesPreview(ArrayList<Recipe> recipes) {
        int index = 0;
        for (Recipe r: recipes) {
            recipePreview(r);
            System.out.println("(" + Integer.toString(index) + ")" + " " + "View This Recipe");
            createDivider();
            index++;
        }
    }

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

    public void addToCollection(Recipe recipe) {
        System.out.println("(N) Add to New Collection");
        System.out.println("(A) Add to Existing Collection");
        System.out.println("(Q) Back to Main Menu");
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

    public void addToNewCollection(Recipe recipe) {
        System.out.println("Let's create a new collection!");
        UserCollection newcollection = new UserCollection();
        System.out.println("Enter the title of your collection:");
        String title = this.userinput.nextLine();
        System.out.println("Enter the description of your collection:");
        String description = this.userinput.nextLine();
        newcollection.setTitle(title);
        newcollection.setDescription(description);
        
        optionAddCollection(newcollection, recipe);
    }

    public void optionAddCollection(UserCollection collection, Recipe recipe) {
        System.out.println("Would you like to add your recipe to this collection?");
        System.out.println("(Y) Yes");
        System.out.println("(N) No");
        String input = genericLowercaseInput();
        if (input.equals("y")) {
            confirmAddRecipeCollection(input, collection, recipe);
        } else {
            System.out.println("The recipe was not added to the collection.");
            returnToMenu();
        }
    }

    public void confirmAddRecipeCollection(String input, UserCollection collection, Recipe recipe) {
        database.addToExistingUserCollection(collection);
        boolean success = collection.addRecipe(recipe);
        if (success) {
            System.out.println("The recipe was successfully added to your collection.");
        } else {
            System.out.println("This recipe already exists in the collection.");
        }
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

    public void returnToMenu() {
        System.out.println("(Q) Back to Menu");
        String input = this.userinput.nextLine();
        input = input.toLowerCase();
        if (input.equals("q")) {
            menu();
        } else {
            wrongKey();
        }
    }

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

    public void createDivider() {
        System.out.println("------------------------");
    }

}
