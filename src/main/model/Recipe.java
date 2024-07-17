package model;

import java.util.ArrayList;

public class Recipe {
    private String author;
    private String title;
    private int cookTime;
    private double recommends;
    private ArrayList<String> ingredients;
    private ArrayList<String> directions;
    private ArrayList<String> comments;
    private int totalRaters;


    // EFFECTS: Constructs a recipe with the name of the respective author, title and cook-time,
    // with a starting rating of zero and no comments so far. Ingredients and directions are also
    // empty.
    public Recipe() {
        recommends = 0;
        totalRaters = 0;
        comments = new ArrayList<String>();
        ingredients = new ArrayList<String>();
        directions = new ArrayList<String>();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    // REQUIRES: > 0
    // MODIFIES: this
    // EFFECTS: sets the CookTime of the recipe in minutes
    public void setCookTime(int cookTime) {
        this.cookTime = cookTime;
    }

    public int getCookTime() {
        return cookTime;
    }

    // MODIFIES: this
    //EFFECTS: adds one if user recommends the recipe (true), adds one 
    // to totalRaters
    public void addReccomend(boolean rec) {
        if (rec) {
            this.recommends++;
        }
        this.totalRaters++;
    }

    public double getRecommends() {
        return recommends;
    }

    // EFFECTS: returns the rating of the recipe by representing
    // the portion of those who reccomend as a percentage
    public double getRating() {
        if (totalRaters != 0) {
            return Math.round((recommends / totalRaters) * 100);
        }
        return 0;
    }

    // EFFECTS: returns the total number of raters of the recipe
    public int getTotalRaters() {
        return totalRaters;
    }

    public void addComment(String comment) {
        this.comments.add(comment);
    }

    public ArrayList<String> getComment() {
        return comments;
    }

    // MODIFIES: this
    // EFFECTS: adds inputted ingredient onto recipe's list of ingredients
    public void addIngredient(String ingredient) {
        if (!ingredients.contains(ingredient)) {
            ingredients.add(ingredient);
        }
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public boolean getSpecificIngredient(String ingredient) {
        return ingredients.contains(ingredient);
    }
    
    // MODIFIES: this
    // adds direction to the recipe
    public void addDirection(String direction) {
        directions.add(direction);
    }

    public ArrayList<String> getDirections() {
        return directions;
    }
}
