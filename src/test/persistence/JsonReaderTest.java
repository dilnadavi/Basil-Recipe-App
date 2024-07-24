package persistence;

import model.Database;
import model.Recipe;
import model.UserCollection;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

// REFERENCE: Core tests are taken from UBC CPSC210's Workroom App's JsonReaderTest
// class with personal customizations to fit user project.
class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyDatabase() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyDatabase.json");
        try {
            Database db = reader.read();
            assertEquals(0, db.getRecipeDatabase().size());
            assertEquals(0, db.getUserRecipeDatabase().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralDatabase() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralDatabase.json");
        try {
            Database db = reader.read();
            assertEquals(4, db.getRecipeDatabase().size());
            assertEquals(2, db.getUserRecipeDatabase().size());
            ArrayList<UserCollection> collections = db.viewAllUserCollection();
            assertEquals(1, collections.size());
            checkUserCollection("CollectionSample", "Description", collections.get(0));
            ArrayList<Recipe> recipes = collections.get(0).getRecipes();
            assertEquals(2, recipes.size());
            checkRecipe("RecipeTitle", "RecipeAuthor", 1, recipes.get(0));
            ArrayList<String> ingredients = recipes.get(0).getIngredients();
            assertEquals("ingredient1", ingredients.get(0));
            assertEquals("ingredient2", ingredients.get(1));
            assertEquals("ingredient3", ingredients.get(2));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}