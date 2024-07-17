package model;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestRecipe {

    private Recipe r1;
    private Recipe r2;

    @BeforeEach
    void runBefore() {
        r1 = new Recipe();
        r2 = new Recipe();
    }

    @Test
    void testRecipe() {
        assertEquals(0, r1.getRating());
        assertEquals(0, r1.getTotalRaters());
        assertEquals(0, r2.getRating());
        assertEquals(0, r2.getTotalRaters());
    }

    @Test
    void testsetTitle() {
        r1.setTitle("Banana Bread");
        assertEquals("Banana Bread", r1.getTitle());
        r1.setTitle("Wonderful Bread");
        assertEquals("Wonderful Bread", r1.getTitle());
        r2.setTitle("Homemade Salsa");
        assertEquals("Homemade Salsa", r2.getTitle());
    }

    @Test
    void testsetAuthor() {
        r1.setAuthor("Gordon Ramsay");
        assertEquals("Gordon Ramsay", r1.getAuthor());
        r1.setAuthor("Jamie Oliver");
        assertEquals("Jamie Oliver", r1.getAuthor());
        r2.setAuthor("Christine Ha");
        assertEquals("Christine Ha", r2.getAuthor());
    }

    @Test
    void testsetCookTime() {
        r1.setCookTime(20);
        r2.setCookTime(100);
        assertEquals(20, r1.getCookTime());
        assertEquals(100, r2.getCookTime());
        r1.setCookTime(20);
        r2.setCookTime(25);
        assertEquals(20, r1.getCookTime());
        assertEquals(25, r2.getCookTime());
    }

    @Test
    void addReccomendTest() {
        r1.addReccomend(true);
        r2.addReccomend(false);
        //assertEquals(100, r1.getRating());
        assertEquals(0, r2.getRating());
        r1.addReccomend(true);
        r2.addReccomend(true);
        //assertEquals(100, r1.getRating());
        assertEquals(50, r2.getRating());
        r1.addReccomend(false);
        r1.addReccomend(true);
        r2.addReccomend(true);
        assertEquals(75, r1.getRating());
        assertEquals(67, r2.getRating());
    }

    @Test
    void testgetRecommends() {
        r1.addReccomend(true);
        assertEquals(1, r1.getRecommends());
        r1.addReccomend(false);
        assertEquals(1, r1.getRecommends());
        r1.addReccomend(false);
        assertEquals(1, r1.getRecommends());
        r1.addReccomend(true);
        r1.addReccomend(true);
        r1.addReccomend(false);
        r1.addReccomend(true);
        assertEquals(4, r1.getRecommends());
    }

    @Test
    void getTotalRatersTest() {
        r1.addReccomend(true);
        assertEquals(1, r1.getTotalRaters());
        r1.addReccomend(false);
        assertEquals(2, r1.getTotalRaters());
        r1.addReccomend(false);
        assertEquals(3, r1.getTotalRaters());
        r1.addReccomend(true);
        r1.addReccomend(true);
        r1.addReccomend(false);
        r1.addReccomend(true);
        assertEquals(7, r1.getTotalRaters());
    }

    @Test
    void addCommentTest() {
        r1.addComment("Nice recipe!");
        assertEquals("Nice recipe!", r1.getComment().get(0));
        r1.addComment("It's alright.");
        assertEquals("It's alright.", r1.getComment().get(1));
        r2.addComment("I don't like it.");
        assertEquals("I don't like it.", r2.getComment().get(0));
    }

    @Test
    void addIngredientsTest() {
        r1.addIngredient("Banana");
        assertEquals("Banana", r1.getIngredients().get(0));
        r1.addIngredient("Banana");
        assertEquals("Banana", r1.getIngredients().get(0));
        assertEquals(1, r1.getIngredients().size());
        r1.addIngredient("Chocolate Chips");
        assertEquals("Chocolate Chips", r1.getIngredients().get(1));
        r1.addIngredient("Flour");
        assertEquals("Flour", r1.getIngredients().get(2));

    }

    @Test
    void getSpecificIngredientTest() {
        r1.addIngredient("Banana");
        r1.addIngredient("Brown Sugar");
        r1.addIngredient("Baking powder");
        assertTrue(r1.getSpecificIngredient("Banana"));
        assertTrue(r1.getSpecificIngredient("Brown Sugar"));
        assertTrue(r1.getSpecificIngredient("Baking powder"));
        assertFalse(r1.getSpecificIngredient("Salt"));
        assertFalse(r1.getSpecificIngredient("Cucumbers"));

    }

    @Test
    void addDirectionTest() {
        r1.addDirection("Preheat oven to 360F.");
        r1.addDirection("Mix wet ingredients.");
        r1.addDirection("Mix dry ingredients.");
        r1.addDirection("Place in oven and bake for 15 minutes.");
        r1.addDirection("Enjoy!");
        assertEquals("Preheat oven to 360F.", r1.getDirections().get(0));
        assertEquals("Mix wet ingredients.", r1.getDirections().get(1));
        assertEquals("Mix dry ingredients.", r1.getDirections().get(2));
        assertEquals("Place in oven and bake for 15 minutes.", r1.getDirections().get(3));
        assertEquals("Enjoy!", r1.getDirections().get(4));

    }
    
}
