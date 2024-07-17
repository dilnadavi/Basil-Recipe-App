package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// TODO: added like 90 functions

public class TestDatabase {

    private Database database;
    private Recipe strudel;
    private Recipe pie;

    @BeforeEach
    void runBefore() {
        database = new Database();
        strudel = new Recipe();
        pie = new Recipe();
    }

    @Test
    void testDatabase() {
        assertEquals(database.bananabread, database.getRecipeDatabase().get(0));
        assertEquals(database.sugarcookie, database.getRecipeDatabase().get(1));

        // TODO: ARRAY LISTS
    }

    @Test
    void testaddBananaBread() {
        database.addBananaBread();
        // TODO: CHECK EACH INGREDIENT AND DIRECTION


    }

    @Test
    void testaddSugarCookie() {
        database.addSugarCookie();
        // TODO: CHECK EACH INGREDIENT AND DIRECTION
    }

    @Test
    void testaddandRemoveUserRecipeDatabase() {
        database.addUserRecipeDatabase(strudel);
        database.addUserRecipeDatabase(pie);
        database.addUserRecipeDatabase(strudel);
        assertEquals(2, database.getUserRecipeDatabase().size());
        assertEquals(strudel, database.getUserRecipeDatabase().get(0));
        assertEquals(pie, database.getUserRecipeDatabase().get(1));
        database.removeUserRecipeDatabase(strudel);
        assertEquals(1, database.getUserRecipeDatabase().size());
        assertEquals(pie, database.getUserRecipeDatabase().get(0));
    }

    @Test
    void testgetRecipeDatabase() {
        database.addUserRecipeDatabase(strudel);
        database.addUserRecipeDatabase(pie);
        database.addUserRecipeDatabase(strudel);
        assertEquals("Banana Bread", database.getRecipeDatabase().get(0).getTitle());
        assertEquals("Easy Sugar Cookies", database.getRecipeDatabase().get(1).getTitle());
        assertEquals(strudel, database.getRecipeDatabase().get(2));
        assertEquals(pie, database.getRecipeDatabase().get(3));
    }

    @Test
    void testresetDatabase() {
        database.addUserRecipeDatabase(strudel);
        database.addUserRecipeDatabase(pie);
        assertEquals(2, database.getUserRecipeDatabase().size());
        database.resetDatabase();
        assertEquals(0, database.getUserRecipeDatabase().size());
    }

}
