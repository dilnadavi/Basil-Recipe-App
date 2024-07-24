package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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
        assertEquals(0, database.getRecipeDatabase().size());
        assertEquals(0, database.viewAllUserCollection().size());
    }

    @Test
    void testaddandRemoveUserRecipeDatabase() {
        database.addUserRecipeDatabase(tomatobread);
        database.addUserRecipeDatabase(crepe);
        database.addUserRecipeDatabase(tomatobread);
        assertEquals(2, database.getUserRecipeDatabase().size());
        assertEquals(2, database.getRecipeDatabase().size());
        assertEquals(tomatobread, database.getUserRecipeDatabase().get(0));
        assertEquals(crepe, database.getUserRecipeDatabase().get(1));
        database.removeUserRecipeDatabase(tomatobread);
        assertEquals(1, database.getUserRecipeDatabase().size());
        assertEquals(crepe, database.getUserRecipeDatabase().get(0));
        database.removeUserRecipeDatabase(crepe);
        assertEquals(0, database.getUserRecipeDatabase().size());
    }

    @Test
    void testaddSameRecipe() {
        assertEquals(0, database.getRecipeDatabase().size());
        database.addDefaultRecipeDatabase(crepe);
        assertEquals(1, database.getRecipeDatabase().size());
        Recipe duplicateCrepe = new Recipe("Crepe", "Foo", 5);
        database.addUserRecipeDatabase(duplicateCrepe);
        assertEquals(1, database.getRecipeDatabase().size());
        Recipe differentTimeCrepe = new Recipe("Crepe", "Foo", 10);
        database.addUserRecipeDatabase(differentTimeCrepe);
        assertEquals(2, database.getRecipeDatabase().size());
    }

    @Test
    void testgetRecipeDatabase() {
        database.addUserRecipeDatabase(tomatobread);
        database.addUserRecipeDatabase(crepe);
        database.addUserRecipeDatabase(tomatobread);
        assertEquals(tomatobread, database.getRecipeDatabase().get(0));
        assertEquals(crepe, database.getRecipeDatabase().get(1));
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
        database.addUserRecipeDatabase(crepe);
        crepe.setRaters(1);
        crepe.setRecommends(1);
        database.addUserRecipeDatabase(tomatobread);
        Recipe toprecipe = database.findTopRecipe();
        assertEquals("Crepe", toprecipe.getTitle());
        database.getRecipeDatabase().get(1).addReccomend(true);
        toprecipe = database.findTopRecipe();
        assertEquals("Tomato Bread", toprecipe.getTitle());
        toprecipe = database.findTopRecipe();
        assertEquals("Tomato Bread", toprecipe.getTitle());
    }

    @Test
    void testaddDefaultRecipeDatabase() {
        database.addDefaultRecipeDatabase(crepe);
        assertEquals(1, database.getRecipeDatabase().size());
        assertEquals(0, database.getUserRecipeDatabase().size());
        database.addDefaultRecipeDatabase(tomatobread);
        assertEquals(2, database.getRecipeDatabase().size());
        assertEquals(0, database.getUserRecipeDatabase().size());
        database.addDefaultRecipeDatabase(crepe);
        assertEquals(2, database.getRecipeDatabase().size());
        assertEquals(0, database.getUserRecipeDatabase().size());
    }

    @Test
    void testLookUp() {
        database.addDefaultRecipeDatabase(crepe);
        database.addDefaultRecipeDatabase(tomatobread);

        assertEquals(crepe, database.getRecipeDatabase().get(0));

        assertEquals(crepe, database.lookUp("Crepe", "Foo", 5));
        assertEquals(tomatobread, database.lookUp("Tomato Bread", "Me", 30));
        assertNull(database.lookUp("Crepe", "Foo", 4));
        assertNull(database.lookUp("Crep", "Foo", 5));
        assertNull(database.lookUp("Crepe", "F", 5));
    }

}
