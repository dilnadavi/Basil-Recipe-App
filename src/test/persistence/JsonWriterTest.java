package persistence;

import model.Recipe;
import model.Database;
import model.UserCollection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

// REFERENCE: Core tests are taken from UBC CPSC210's Workroom App's JsonWriterTest
// class with personal customizations to fit user project.
class JsonWriterTest extends JsonTest {

    private Database db;
    
    @BeforeEach
    void runBefore() {
        db = new Database();
    }

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyDatabase() {
        try {
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyDatabase.json");
            writer.open();
            writer.write(db);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyDatabase.json");
            db = reader.read();
            assertEquals(0, db.getUserRecipeDatabase().size());
            assertEquals(0, db.viewAllUserCollection().size());
            assertEquals(0, db.getRecipeDatabase().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    void setRecipeOne() {
        Recipe r1 = new Recipe("TestTitle", "TestAuthor", 1);
        r1.addIngredient("banana");
        r1.addIngredient("apple");
        r1.addDirection("Turn the oven on.");
        r1.addDirection("Put it in.");
        r1.addComment("Good job!");
        r1.addComment("Well done!");
        r1.setRaters(4);
        r1.setRecommends(3);
        db.addUserRecipeDatabase(r1);
    }

    @Test
    void testWriterGeneralDatabase() {
        try {
            setRecipeOne();
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralDatabase.json");
            writer.open();
            writer.write(db);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralDatabase.json");
            db = reader.read();
            assertEquals(1, db.getRecipeDatabase().size());
            assertEquals(1, db.getUserRecipeDatabase().size());
            Recipe retrived = db.getUserRecipeDatabase().get(0);
            checkRecipe("TestTitle", "TestAuthor",1, retrived);
            assertEquals(75, retrived.getRating());
            assertEquals("banana", retrived.getIngredients().get(0));
            assertEquals("apple", retrived.getIngredients().get(1));
            assertEquals("Turn the oven on.", retrived.getDirections().get(0));
            assertEquals("Good job!", retrived.getComments().get(0));
            assertEquals("Well done!", retrived.getComments().get(1));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testUserCollectionsDatabase() {
        try {
            UserCollection uc = new UserCollection();
            uc.setTitle("Collection Title");
            uc.setDescription("Sample Description");
            Recipe r1 = new Recipe("TestTitle", "TestAuthor", 1);
            Recipe r2 = new Recipe("SecondTitle", "SecondAuthor", 7);
            uc.addRecipe(r1);
            uc.addRecipe(r2);
            db.addToExistingUserCollection(uc);
            JsonWriter writer = new JsonWriter("./data/testWriterUserCollectionsDatabase.json");

            writer.open();
            writer.write(db);
            writer.close();
            JsonReader reader = new JsonReader("./data/testWriterUserCollectionsDatabase.json");
            db = reader.read();
            ArrayList<UserCollection> collections = db.viewAllUserCollection();
            assertEquals(0, db.getRecipeDatabase().size());
            assertEquals(1, collections.size());
            checkUserCollection("Collection Title", "Sample Description", collections.get(0));
            assertEquals(2, collections.get(0).getRecipes().size());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}