package persistence;

import model.Database;
import model.UserCollection;
import model.Recipe;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// REFERENCE: Core methods are taken from UBC CPSC210's Workroom App's JsonReader
// class with personal customizations to fit user project.
// Represents a reader that reads database from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads database from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Database read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseDatabase(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses database from JSON object and returns it
    private Database parseDatabase(JSONObject jsonObject) {
        Database db = new Database();
        addUserCollections(db, jsonObject);
        addPersonalRecipes(db, jsonObject);
        addAllRecipes(db, jsonObject);
        return db;
    }

    // MODIFIES: db
    // EFFECTS: parses personal recipes from JSON object and adds them to database
    private void addPersonalRecipes(Database db, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("personal recipes");
        for (Object json : jsonArray) {
            JSONObject nextRecipe = (JSONObject) json;
            addUserRecipe(db, nextRecipe);
        }
    }

    // MODIFIES: db
    // EFFECTS: parses user collections from JSON object and adds them to database
    private void addUserCollections(Database db, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("user collections");
        for (Object json : jsonArray) {
            JSONObject nextUserCollection = (JSONObject) json;
            addUserCollection(db, nextUserCollection);
        }
    }

    // MODIFIES: db
    // EFFECTS: parses all recipes from JSON object and adds them to database
    private void addAllRecipes(Database db, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("all recipes");
        for (Object json : jsonArray) {
            JSONObject nextRecipe = (JSONObject) json;
            addAllRecipe(db, nextRecipe);
        }
    }

    // MODIFIES: db
    // EFFECTS: parses a user collection from JSON object and adds it to
    //          the the database's user collections
    private void addUserCollection(Database db, JSONObject jsonObject) {
        UserCollection usercollection = new UserCollection();
        String title = jsonObject.getString("title");
        String description = jsonObject.getString("description");
        
        usercollection.setTitle(title);
        usercollection.setDescription(description);
        
        JSONArray jsonArray = jsonObject.getJSONArray("recipes");
        for (Object json : jsonArray) {
            JSONObject nextRecipe = (JSONObject) json;
            addRecipeToCollection(db, nextRecipe, usercollection);
        }

        db.addToExistingUserCollection(usercollection);
    }

    // MODIFIES: db
    // EFFECTS: parses a recipe from JSON object and adds it to
    //          the database's user recipes
    private void addUserRecipe(Database db, JSONObject jsonObject) {
        String title = jsonObject.getString("name");
        String author = jsonObject.getString("author");
        int cookTime = jsonObject.getInt("cook time");
        int recommends = jsonObject.getInt("recommends");
        int raters = jsonObject.getInt("raters");

        Recipe recipe = new Recipe(title, author, cookTime);
        recipe.setRaters(raters);
        recipe.setRecommends(recommends);

        addIngredients(recipe, jsonObject);
        addComments(recipe, jsonObject);
        addDirections(recipe, jsonObject);

        db.addUserRecipeDatabase(recipe);
    }

    // MODIFIES: db
    // EFFECTS: parses a recipe from JSON object and adds it to
    //          the database's all recipes
    private void addAllRecipe(Database db, JSONObject jsonObject) {
        String title = jsonObject.getString("name");
        String author = jsonObject.getString("author");
        int cookTime = jsonObject.getInt("cook time");
        int recommends = jsonObject.getInt("recommends");
        int raters = jsonObject.getInt("raters");

        Recipe recipe = new Recipe(title, author, cookTime);
        recipe.setRaters(raters);
        recipe.setRecommends(recommends);

        addIngredients(recipe, jsonObject);
        addComments(recipe, jsonObject);
        addDirections(recipe, jsonObject);

        db.addDefaultRecipeDatabase(recipe);
    }

    // MODIFIES: db
    // EFFECTS: parses a recipe from JSON object and adds it to
    //          a specific collection in the database
    private void addRecipeToCollection(Database db, JSONObject jsonObject, UserCollection uc) {
        String title = jsonObject.getString("name");
        String author = jsonObject.getString("author");
        int cookTime = jsonObject.getInt("cook time");
        int recommends = jsonObject.getInt("recommends");
        int raters = jsonObject.getInt("raters");

        Recipe recipe = new Recipe(title, author, cookTime);
        recipe.setRaters(raters);
        recipe.setRecommends(recommends);

        addIngredients(recipe, jsonObject);
        addComments(recipe, jsonObject);
        addDirections(recipe, jsonObject);

        uc.addRecipe(recipe);
    }

    // MODIFIES: db
    // EFFECTS: parses the ingredients from JSON object and adds it to
    //          the the database's recipe
    public void addIngredients(Recipe recipe, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("ingredients");
        for (Object json : jsonArray) {
            String ingredient = json.toString();
            recipe.addIngredient(ingredient);
        }
    }

    // MODIFIES: db
    // EFFECTS: parses the directions from JSON object and adds it to
    //          the the database's recipe
    public void addDirections(Recipe recipe, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("directions");
        for (Object json : jsonArray) {
            String direction = json.toString();
            recipe.addDirection(direction);
        }
    }

    // MODIFIES: db
    // EFFECTS: parses the comments from JSON object and adds it to
    //          the the database's recipe
    public void addComments(Recipe recipe, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("comments");
        for (Object json : jsonArray) {
            String comment = json.toString();
            recipe.addComment(comment);
        }
    }

}