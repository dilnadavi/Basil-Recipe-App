package model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestUserCollection {

    private UserCollection mycollection;
    private UserCollection newcollection;
    private Recipe bananabread;
    private Recipe tomatopasta;

    @BeforeEach
    void runBefore() {
        mycollection = new UserCollection();
        newcollection = new UserCollection();
        bananabread = new Recipe("Banana Bread", "Chrissy Teigen", 60);
        tomatopasta = new Recipe("Tomato Pasta", "Author", 20);
    }

    @Test
    void testUserCollection() {
        assertEquals(0, mycollection.getRecipes().size());
        //assertEquals(..., mycollection.getThumbnail());
        assertEquals(0, newcollection.getRecipes().size());
    }

    @Test
    void testsetTitle() {
        mycollection.setTitle("Favourite Savory Recipes");
        assertEquals("Favourite Savory Recipes", mycollection.getTitle());
        mycollection.setTitle("Savory Wonderland");
        assertEquals("Savory Wonderland", mycollection.getTitle());
        newcollection.setTitle("Desserts");
        assertEquals("Desserts", newcollection.getTitle());
    }

    @Test
    void testsetDescription() {
        mycollection.setDescription("Here are some of the best desserts.");
        assertEquals("Here are some of the best desserts.", mycollection.getDescription());
        mycollection.setDescription("Enjoy!");
        assertEquals("Enjoy!", mycollection.getDescription());
        newcollection.setDescription("My favourites!");
        assertEquals("My favourites!", newcollection.getDescription());
    }

    @Test
    void testAddandRemoveRecipe() {
        boolean success;
        success = mycollection.addRecipe(bananabread);
        assertTrue(success);
        success = mycollection.addRecipe(tomatopasta);
        assertTrue(success);
        assertEquals(bananabread, mycollection.getRecipes().get(0));
        assertEquals(tomatopasta, mycollection.getRecipes().get(1));
        assertEquals(2, mycollection.getRecipes().size());



        success = newcollection.addRecipe(tomatopasta);
        assertTrue(success);
        success = newcollection.addRecipe(bananabread);
        assertTrue(success);
        success = newcollection.addRecipe(tomatopasta);
        assertFalse(success);
        assertEquals(tomatopasta, newcollection.getRecipes().get(0));
        assertEquals(bananabread, newcollection.getRecipes().get(1));
        assertEquals(2, newcollection.getRecipes().size());
    }

    @Test
    void testremoveRecipe() {
        boolean success;
        mycollection.addRecipe(bananabread);
        mycollection.addRecipe(tomatopasta);

        success = mycollection.removeRecipe(bananabread);
        assertTrue(success);
        assertEquals(tomatopasta, mycollection.getRecipes().get(0));
        success = mycollection.removeRecipe(bananabread);
        assertFalse(success);
        assertEquals(tomatopasta, mycollection.getRecipes().get(0));
        assertEquals(1, mycollection.getRecipes().size());

        newcollection.addRecipe(tomatopasta);
        newcollection.addRecipe(bananabread);
        newcollection.addRecipe(tomatopasta);

        success = newcollection. removeRecipe(tomatopasta);
        assertTrue(success);
        success = newcollection. removeRecipe(bananabread);
        assertTrue(success);
        assertEquals(0, newcollection.getRecipes().size());

    }

    @Test
    void testresetCollection() {
        mycollection.addRecipe(bananabread);
        mycollection.addRecipe(tomatopasta);
        assertEquals(2, mycollection.getRecipes().size());
        mycollection.resetCollection();
        assertEquals(0, mycollection.getRecipes().size());
        newcollection.addRecipe(tomatopasta);
        newcollection.addRecipe(bananabread);
        newcollection.addRecipe(tomatopasta);
        assertEquals(2, newcollection.getRecipes().size());
        newcollection.resetCollection();
        assertEquals(0, newcollection.getRecipes().size());
    }
}
