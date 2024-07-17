package model;

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
        bananabread = new Recipe();
        tomatopasta = new Recipe();
    }

    @Test
    void testUserCollection() {
        assertEquals(0, mycollection.getRecipes().size());
        assertEquals("My Collection", mycollection.getTitle());
        //assertEquals(..., mycollection.getThumbnail());
        assertEquals(0, newcollection.getRecipes().size());
        assertEquals("My Collection", newcollection.getTitle());
        assertEquals("No description provided.", mycollection.getDescription());
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
    void testsetThumbnail() {
        //STUB
        // TODO: test thumbnail
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
    void testaddRecipe() {
        mycollection.addRecipe(bananabread);
        mycollection.addRecipe(tomatopasta);
        assertEquals(bananabread, mycollection.getRecipes().get(0));
        assertEquals(tomatopasta, mycollection.getRecipes().get(1));
        assertEquals(2, mycollection.getRecipes().size());
        newcollection.addRecipe(tomatopasta);
        newcollection.addRecipe(bananabread);
        newcollection.addRecipe(tomatopasta);
        assertEquals(tomatopasta, newcollection.getRecipes().get(0));
        assertEquals(bananabread, newcollection.getRecipes().get(1));
        assertEquals(2, newcollection.getRecipes().size());
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
