package model;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

import java.util.ArrayList;

// A class that represents a recipe with a title, author, cook-time,
// ingredients, directions, ratings and comments
public class Recipe implements Writable {
    private String author;
    private String title;
    private int cookTime;
    private double recommends;
    private ArrayList<String> ingredients;
    private ArrayList<String> directions;
    private ArrayList<String> comments;
    private int totalRaters;


    // EFFECTS: Constructs a recipe with the inputted title, author and cookTime,
    //          along with no comments, ingredients, directions or rating
    public Recipe(String title, String author, int cookTime) {
        this.title = title;
        this.author = author;
        this.cookTime = cookTime;
        recommends = 0;
        totalRaters = 0;
        comments = new ArrayList<String>();
        ingredients = new ArrayList<String>();
        directions = new ArrayList<String>();
    }

    // MODIFIES: this
    // EFFECTS: sets the title of the recipe to inputted value
    public void setTitle(String title) {
        this.title = title;
    }

    // EFFECTS: returns the title of the recipe
    public String getTitle() {
        return title;
    }

    // MODIFIES: this
    // EFFECTS: sets the author of the recipe to inputted value
    public void setAuthor(String author) {
        this.author = author;
    }

    // EFFECTS: returns the author of the recipe
    public String getAuthor() {
        return author;
    }

    // REQUIRES: > 0
    // MODIFIES: this
    // EFFECTS: sets the CookTime of the recipe in minutes
    public void setCookTime(int cookTime) {
        this.cookTime = cookTime;
    }

    // EFFECTS: returns the cook-time of the recipe in minutes
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

    // EFFECTS: returns the number of raters that recommend the dish
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

    // MODIFIES: this
    // EFFECTS: adds a comment to the recipe
    public void addComment(String comment) {
        this.comments.add(comment);
    }

    // EFFECTS: returns the comments of the recipe
    public ArrayList<String> getComments() {
        return comments;
    }

    // MODIFIES: this
    // EFFECTS: adds inputted ingredient onto recipe's list of ingredients if
    //          recipe does not already contain it
    public void addIngredient(String ingredient) {
        if (!ingredients.contains(ingredient)) {
            ingredients.add(ingredient);
        }
    }

    // EFFECTS: returns the ingredients of the recipe
    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    // EFFECTS: returns true if the recipe contains the specific ingredient
    public boolean getSpecificIngredient(String ingredient) {
        return ingredients.contains(ingredient);
    }
    
    // MODIFIES: this
    // EFFECTS: adds direction to the recipe
    public void addDirection(String direction) {
        directions.add(direction);
    }

    // EFFECTS: get directions of the recipe
    public ArrayList<String> getDirections() {
        return directions;
    }

    // MODIFIES: this
    // EFFECTS: sets the totalRaters of the recipe to input
    public void setRaters(int totalRaters) {
        this.totalRaters = totalRaters;
    }

    // MODIFIES: this
    // EFFECTS: sets the recommends of the recipe to input
    public void setRecommends(int recommends) {
        this.recommends = recommends;
    }

    // EFFECTS: returns all details of Recipe as a JSONObject
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", title);
        json.put("author", author);
        json.put("cook time", cookTime);
        json.put("recommends", recommends);
        json.put("raters", totalRaters);
        json.put("ingredients", ingredientsToJson());
        json.put("directions", directionsToJson());
        json.put("comments", commentsToJson());
        
        return json;
    }

    public String toString() {
        return title;
    }

    // EFFECTS: returns ingredients in this recipe as a JSON array
    private JSONArray ingredientsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (String ingredient : ingredients) {
            jsonArray.put(ingredient);
        }

        return jsonArray;
    }

    // EFFECTS: returns directions in this recipe as a JSON array
    private JSONArray directionsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (String direction : directions) {
            jsonArray.put(direction);
        }

        return jsonArray;
    }

    // EFFECTS: returns comments in this recipe as a JSON array
    private JSONArray commentsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (String comment : comments) {
            jsonArray.put(comment);
        }

        return jsonArray;
    }

    // EFFECTS: maintains the contract between the hashCode() and equals() method
    //          when overriding the equals() method from Object class
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((author == null) ? 0 : author.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        result = prime * result + cookTime;
        return result;
    }

    // EFFECTS: Checks whether the two recipes are equal. Returns true if
    // both recipes have the same title, author AND cookTime, false otherwise.
    @SuppressWarnings("methodlength")
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Recipe other = (Recipe) obj;
        if (author == null) {
            if (other.author != null) {
                return false;
            }
        } else if (!author.equals(other.author)) {
            return false;
        }
        if (title == null) {
            if (other.title != null) {
                return false;
            }
        } else if (!title.equals(other.title)) {
            return false;
        }
        if (cookTime != other.cookTime) {
            return false;
        }
        return true;
    }
}
