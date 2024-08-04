package model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;


// A class that represents a the database, which contains all the program's recipes.
// A database contains default hard-coded recipes, user recipes, and multiple collections
// that a user can create and add recipes into.
public class Database implements Writable {

    private ArrayList<Recipe> allrecipes;
    private ArrayList<Recipe> userrecipes;
    private ArrayList<UserCollection> collections;

    // EFFECTS: Constructs a database with recipes loaded in and empty user recipes
    //          and empty collections
    public Database() {

        allrecipes = new ArrayList<Recipe>();

        userrecipes = new ArrayList<Recipe>();
        collections = new ArrayList<UserCollection>();
    }

    // EFFECTS: gets all the recipes in the database
    public ArrayList<Recipe> getRecipeDatabase() {
        return allrecipes;
    }

    // MODIFIES: this
    // EFFECTS: adds a recipe to the database's all recipes directly
    public void addDefaultRecipeDatabase(Recipe recipe) {
        if (!allrecipes.contains(recipe)) {
            this.allrecipes.add(recipe);
        }
    }

    // MODIFIES: this
    // EFFECTS: If there exists the same recipe in all recipes, simply adds
    //          recipe to userRecipes. If the recipe does not already exist
    //          in all recipes, adds the recipe to both user recipes and
    //          all recipes
    public void addUserRecipeDatabase(Recipe recipe) {
        boolean sameRecipe = false;
        for (Recipe r: allrecipes) {
            boolean confirmedSame = recipe.equals(r);
            if (confirmedSame) {
                sameRecipe = true;
            }
        }
        if (!userrecipes.contains(recipe) && !sameRecipe) {
            this.userrecipes.add(recipe);
            this.allrecipes.add(recipe);
        } else if (!userrecipes.contains(recipe)) {
            Recipe equalRecipe = lookUp(recipe.getTitle(), recipe.getAuthor(), recipe.getCookTime());
            this.userrecipes.add(equalRecipe);
        }
    }

    // EFFECTS: returns the recipes in the database
    public ArrayList<Recipe> getUserRecipeDatabase() {
        return userrecipes;
    }

    // MODIFIES: this
    // EFFECTS: removes a user-created recipe from the database
    public void removeUserRecipeDatabase(Recipe recipe) {
        this.userrecipes.remove(recipe);
    }

    // MODIFIES: this
    // EFFECTS: removes all user-created recipes and collection from the database
    public void resetDatabase() {
        this.userrecipes = new ArrayList<Recipe>();
    }

    // MODIFIES: this
    // EFFECTS: returns list of recipes that contain inputted ingredient
    public ArrayList<Recipe> searchByIngredient(String ingredient) {
        ArrayList<Recipe> matches = new ArrayList<Recipe>();
        for (Recipe r: allrecipes) {
            for (String i: r.getIngredients()) {
                if (i.equalsIgnoreCase(ingredient)) {
                    matches.add(r);
                }
            }
        }
        return matches;
    }

    // EFFECTS: finds the recipe with the highest rating in the database
    public Recipe findTopRecipe() {
        double rating = 0;
        Recipe recipe = allrecipes.get(0);
        for (Recipe r: allrecipes) {
            if (r.getRating() >= rating) {
                rating = r.getRating();
                recipe = r;
            }
        }
        return recipe;
    }

    // EFFECTS: returns all the user collections in the database
    public ArrayList<UserCollection> viewAllUserCollection() {
        return collections;
    }

    // MODIFIES: this
    // EFFECTS: adds a new collection
    public void addToExistingUserCollection(UserCollection collection) {
        if (!collections.contains(collection)) {
            collections.add(collection);
        }
    }

    // MODIFIES: this
    // EFFECTS: removes collection from the existing user collections
    public void removeFromExistingUserCollection(UserCollection collection) {
        collections.remove(collection);
    }

    // EFFECTS: accepts parameters for title, author and cookTime and returns
    //          a recipe if it has the same title, author and cooktime, otherwise
    //          returns null.
    public Recipe lookUp(String title, String author, int cookTime) {
        for (Recipe r: allrecipes) {
            boolean sameAuthor = author.equals(r.getAuthor());
            boolean sameTitle = title.equals(r.getTitle());
            boolean samecookTime = (cookTime == r.getCookTime());
            if (sameTitle && sameAuthor && samecookTime) {
                return r;
            }
        }
        return null;
    }

    // EFFECTS: returns all components of the database as a JSONObject
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("user collections", userCollectionToJson());
        json.put("personal recipes", personalRecipesToJson());
        json.put("all recipes", allRecipesToJson());
        return json;
    }

    // EFFECTS: returns user collections in this database as a JSON array
    private JSONArray userCollectionToJson() {
        JSONArray jsonArray = new JSONArray();

        for (UserCollection uc : collections) {
            jsonArray.put(uc.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: returns personal recipes in this database as a JSON array
    private JSONArray personalRecipesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Recipe r : userrecipes) {
            jsonArray.put(r.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: returns all recipes in this database as a JSON array
    private JSONArray allRecipesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Recipe r : allrecipes) {
            jsonArray.put(r.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: lists all the cars in list and their respective details
    public String printRecipes(ArrayList<Recipe> recipes) {
        String print = "";
        for (int i = 0; i < recipes.size(); i++) {
            print += "Car #" + i + "\n" + recipes.get(i).getTitle()
                    + "\n" + recipes.get(i).getAuthor();
        }
        return print;
    }
}
