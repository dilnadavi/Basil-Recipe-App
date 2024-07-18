package model;

import java.util.ArrayList;

public class Database {

    private ArrayList<Recipe> allrecipes;
    private ArrayList<Recipe> userrecipes;
    private ArrayList<UserCollection> collections;
    private Recipe bananabread;
    private Recipe sugarcookie;

    // EFFECTS: Constructs a database with recipes loaded in and empty user recipes
    //          and empty collections
    public Database() {
        addBananaBread();
        addSugarCookie();

        allrecipes = new ArrayList<Recipe>();
        allrecipes.add(bananabread);
        allrecipes.add(sugarcookie);

        userrecipes = new ArrayList<Recipe>();
        collections = new ArrayList<UserCollection>();
    }

    // MODIFIES: this
    // EFFECTS: adds the hard-coded recipe of Chrissy Teigen's Banana Bread
    private void addBananaBread() {
        bananabread = new Recipe("Banana Bread", "Chrissy Teigen", 60);
        bananabread.addReccomend(true);
        bananabread.addIngredient("Banana");
        bananabread.addIngredient("Egg");
        bananabread.addIngredient("Sugar");
        bananabread.addIngredient("Baking soda");
        bananabread.addIngredient("Canola oil");
        bananabread.addIngredient("Flour");
        bananabread.addIngredient("Vanilla jello instant pudding");
        bananabread.addIngredient("Kosher salt");
        bananabread.addIngredient("Coconut");
        bananabread.addIngredient("Dark chocolate");
        bananabread.addIngredient("Butter");
        bananabread.addDirection("Preheat the over to 325F.");
        bananabread.addDirection("In a large bowl take your ripe bananas, eggs and oil and mash.");
        bananabread.addDirection("In another bowl, combine the flour, sugar, pudding, baking soda and salt.");
        bananabread.addDirection("Mix dry mix and add to wet mix.");
        bananabread.addDirection("Fold in the chocolate and shredded coconut.");
        bananabread.addDirection("Flour pan and bake for 50-65 minutes");
        bananabread.addDirection("Let it cool and enjoy!");
    }

    // MODIFIES: this
    // EFFECTS: adds the hard-coded recipe of Bellyful's Sugar Cookies
    private void addSugarCookie() {
        sugarcookie = new Recipe("Easy Sugar Cookies", "Bellyfull", 15);
        sugarcookie.addIngredient("Butter");
        sugarcookie.addIngredient("Sugar");
        sugarcookie.addIngredient("Flour");
        sugarcookie.addIngredient("Vanilla");
        sugarcookie.addIngredient("Sprinkles");
        sugarcookie.addDirection("Preheat oven to 325 degrees F. Line 2 large baking sheets with parchment paper.");
        sugarcookie.addDirection("In a medium bowl, beat together butter and 2/3 cup sugar until combined.");
        sugarcookie.addDirection("Add in flour and blend well, then blend in the vanilla.");
        sugarcookie.addDirection("Using a cookie scoop, roll the dough into 1-inch balls.");
        sugarcookie.addDirection("Transfer to the baking sheets 2 inches apart and flatten.");
        sugarcookie.addDirection("Bake for 14-16 minutes.");
        sugarcookie.addDirection("Let rest on the baking sheets for at least 10-15 minutes");
        sugarcookie.addDirection("Enjoy!");
    }

    // EFFECTS: gets all the recipes in the database
    public ArrayList<Recipe> getRecipeDatabase() {
        return allrecipes;
    }

    // MODIFIES: this
    // EFFECTS: adds a user recipe to the database
    public void addUserRecipeDatabase(Recipe recipe) {
        if (!userrecipes.contains(recipe) && !allrecipes.contains(recipe)) {
            this.userrecipes.add(recipe);
            this.allrecipes.add(recipe);
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

}
