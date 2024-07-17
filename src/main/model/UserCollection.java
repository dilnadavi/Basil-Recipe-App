package model;


import java.util.ArrayList;

public class UserCollection {
    private String title;
    private String description;
    private ArrayList<Recipe> recipes;

    // EFFECTS: Creates a user collection with default thumbnail image, description and title,
    // with no recipes so far
    public UserCollection() {
        this.title = "My Collection";
        this.description = "No description provided.";
        recipes = new ArrayList<Recipe>();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public boolean addRecipe(Recipe recipe) {
        if (!recipes.contains(recipe)) {
            this.recipes.add(recipe);
            return true;
        } else {
            return false;
        }

        // TODO: test the boolean
    }

    public boolean removeRecipe(Recipe recipe) {
        if (recipes.contains(recipe)) {
            this.recipes.remove(recipe);
            return true;
        } else {
            return false;
        }
        // TODO: test this
    }

    public ArrayList<Recipe> getRecipes() {
        return recipes;
    }

    public void resetCollection() {
        recipes = new ArrayList<Recipe>();
    }

}
