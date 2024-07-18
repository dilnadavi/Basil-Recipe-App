package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestDatabase {

    private Database database;
    private Recipe tomatobread;
    private Recipe crepe;

    @BeforeEach
    void runBefore() {
        database = new Database();
        tomatobread = new Recipe("Tomato Bread", "Me", 30);
        crepe = new Recipe("Crepe", "Foo", 5);
    }

    @Test
    void testDatabase() {
        assertEquals(0, database.getUserRecipeDatabase().size());
        assertEquals(2, database.getRecipeDatabase().size());
        assertEquals(0, database.viewAllUserCollection().size());
    }

    @Test
    void testaddBananaBread() {
        assertEquals("Banana Bread", database.getRecipeDatabase().get(0).getTitle());
        assertEquals(100, database.getRecipeDatabase().get(0).getRating());
        assertEquals("Chrissy Teigen", database.getRecipeDatabase().get(0).getAuthor());
        assertEquals(60, database.getRecipeDatabase().get(0).getCookTime());
        assertEquals(11, database.getRecipeDatabase().get(0).getIngredients().size());
        assertEquals("Banana", database.getRecipeDatabase().get(0).getIngredients().get(0));
        assertEquals("Preheat the over to 325F.", database.getRecipeDatabase().get(0).getDirections().get(0));
        assertEquals(7, database.getRecipeDatabase().get(0).getDirections().size());
    }

    @Test
    void testaddSugarCookie() {
        assertEquals("Easy Sugar Cookies", database.getRecipeDatabase().get(1).getTitle());
        assertEquals(0, database.getRecipeDatabase().get(1).getRating());
        assertEquals("Bellyfull", database.getRecipeDatabase().get(1).getAuthor());
        assertEquals(15, database.getRecipeDatabase().get(1).getCookTime());
        assertEquals(5, database.getRecipeDatabase().get(1).getIngredients().size());
        assertEquals("Butter", database.getRecipeDatabase().get(1).getIngredients().get(0));
        assertEquals(8, database.getRecipeDatabase().get(1).getDirections().size());
    }

    @Test
    void testaddandRemoveUserRecipeDatabase() {
        database.addUserRecipeDatabase(tomatobread);
        database.addUserRecipeDatabase(crepe);
        database.addUserRecipeDatabase(tomatobread);
        assertEquals(2, database.getUserRecipeDatabase().size());
        assertEquals(4, database.getRecipeDatabase().size());
        assertEquals(tomatobread, database.getUserRecipeDatabase().get(0));
        assertEquals(crepe, database.getUserRecipeDatabase().get(1));
        database.removeUserRecipeDatabase(tomatobread);
        assertEquals(1, database.getUserRecipeDatabase().size());
        assertEquals(crepe, database.getUserRecipeDatabase().get(0));
        database.removeUserRecipeDatabase(crepe);
        database.addUserRecipeDatabase(database.getRecipeDatabase().get(0));
        assertEquals(0, database.getUserRecipeDatabase().size());
    }

    @Test
    void testgetRecipeDatabase() {
        database.addUserRecipeDatabase(tomatobread);
        database.addUserRecipeDatabase(crepe);
        database.addUserRecipeDatabase(tomatobread);
        assertEquals("Banana Bread", database.getRecipeDatabase().get(0).getTitle());
        assertEquals("Easy Sugar Cookies", database.getRecipeDatabase().get(1).getTitle());
        assertEquals(tomatobread, database.getRecipeDatabase().get(2));
        assertEquals(crepe, database.getRecipeDatabase().get(3));
    }

    @Test
    void testresetDatabase() {
        database.addUserRecipeDatabase(tomatobread);
        database.addUserRecipeDatabase(crepe);
        assertEquals(2, database.getUserRecipeDatabase().size());
        database.resetDatabase();
        assertEquals(0, database.getUserRecipeDatabase().size());
    }

    @Test
    void testsearchByIngredient() {
        ArrayList<Recipe> matches = database.searchByIngredient("butter");
        assertEquals("Banana Bread", matches.get(0).getTitle());
        assertEquals("Easy Sugar Cookies", matches.get(1).getTitle());
        database.addUserRecipeDatabase(tomatobread);
        database.addUserRecipeDatabase(crepe);
        tomatobread.addIngredient("apples");
        crepe.addIngredient("apples");
        matches = database.searchByIngredient("apples");
        assertEquals(tomatobread, matches.get(0));
        assertEquals(crepe, matches.get(1));
        crepe.addIngredient("Raisins");
        matches = database.searchByIngredient("raisins");
        assertEquals(crepe, matches.get(0));

    }

    @Test
    void testaddToandremoveFromExistingUserCollection() {
        UserCollection collection1 = new UserCollection();
        database.addToExistingUserCollection(collection1);;
        assertEquals(collection1, database.viewAllUserCollection().get(0));
        UserCollection collection2 = new UserCollection();
        database.addToExistingUserCollection(collection1);
        database.addToExistingUserCollection(collection2);
        assertEquals(collection1, database.viewAllUserCollection().get(0));
        assertEquals(collection2, database.viewAllUserCollection().get(1));
        database.removeFromExistingUserCollection(collection1);
        assertEquals(collection2, database.viewAllUserCollection().get(0));
        database.removeFromExistingUserCollection(collection1);
        assertEquals(collection2, database.viewAllUserCollection().get(0));
        database.removeFromExistingUserCollection(collection2);
        assertEquals(0, database.viewAllUserCollection().size());
    }

    @Test
    void testfindTopRecipe() {
        Recipe toprecipe = database.findTopRecipe();
        assertEquals("Banana Bread", toprecipe.getTitle());
        database.getRecipeDatabase().get(1).addReccomend(true);
        toprecipe = database.findTopRecipe();
        assertEquals("Easy Sugar Cookies", toprecipe.getTitle());
        toprecipe = database.findTopRecipe();
        assertEquals("Easy Sugar Cookies", toprecipe.getTitle());
    }

}
