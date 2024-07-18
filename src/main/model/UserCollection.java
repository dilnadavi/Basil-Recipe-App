package model;


import java.util.ArrayList;

// A class that represents a user collection, which has a title, description
// and recipes that users can add into the collection.
public class UserCollection {
    private String title;
    private String description;
    private ArrayList<Recipe> recipes;

    // EFFECTS: Creates a user collection with default description and title,
    // with no recipes so far
    public UserCollection() {
        this.title = "My Collection";
        this.description = "No description provided.";
        recipes = new ArrayList<Recipe>();
    }

    // MODIFIES: this
    // EFFECTS: sets the title of the collection
    public void setTitle(String title) {
        this.title = title;
    }

    // EFFECTS: returns the title of the collection
    public String getTitle() {
        return title;
    }

    // MODIFIES: this
    // EFFECTS: sets the description of the collection
    public void setDescription(String description) {
        this.description = description;
    }

    // EFFECTS: returns the description of the collection
    public String getDescription() {
        return description;
    }

    // MODIFIES: this
    // EFFECTS: adds the recipe to the collection if it is found
    public boolean addRecipe(Recipe recipe) {
        if (!recipes.contains(recipe)) {
            this.recipes.add(recipe);
            return true;
        } else {
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: removes the recipe from the collection if it is found
    public boolean removeRecipe(Recipe recipe) {
        if (recipes.contains(recipe)) {
            this.recipes.remove(recipe);
            return true;
        } else {
            return false;
        }
    }

    // EFFECTS: gets the recipes of the collection
    public ArrayList<Recipe> getRecipes() {
        return recipes;
    }

    // EFFECTS: removes all the recipes from the collection
    public void resetCollection() {
        recipes = new ArrayList<Recipe>();
    }

}
