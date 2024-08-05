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
        r1 = new Recipe("Banana Bread", "Chrissy Teigen", 60);
        r2 = new Recipe("Easy Sugar Cookies", "Bellyfull", 15);
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
    void testaddReccomend() {
        r1.addReccomend(true);
        r2.addReccomend(false);
        // assertEquals(100, r1.getRating());
        assertEquals(0, r2.getRating());
        r1.addReccomend(true);
        r2.addReccomend(true);
        // assertEquals(100, r1.getRating());
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
    void testgetTotalRaters() {
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
        r1.setRaters(10);
        r1.setRecommends(7);
        assertEquals(10, r1.getTotalRaters());
        assertEquals(7, r1.getRecommends());
    }

    @Test
    void testaddComment() {
        r1.addComment("Nice recipe!");
        assertEquals("Nice recipe!", r1.getComments().get(0));
        r1.addComment("It's alright.");
        assertEquals("It's alright.", r1.getComments().get(1));
        r2.addComment("I don't like it.");
        assertEquals("I don't like it.", r2.getComments().get(0));
    }

    @Test
    void testaddIngredients() {
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
    void testgetSpecificIngredient() {
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
    void testaddDirection() {
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

    @Test
    void testEquals() {
        Recipe recipeOne = new Recipe("Title", "Author", 1);
        Recipe sameObj = recipeOne;
        assertTrue(recipeOne.equals(sameObj));
        assertFalse(recipeOne.equals(null));
        Recipe differentTitle = new Recipe("Title1", "Author", 1);
        assertFalse(recipeOne.equals(differentTitle));
        Recipe differentAuthor = new Recipe("Title", "Author1", 1);
        assertFalse(recipeOne.equals(differentAuthor));
        Recipe differentTime = new Recipe("Title", "Author", 2);
        assertFalse(recipeOne.equals(differentTime));
        Recipe sameRecipe = new Recipe("Title", "Author", 1);
        assertTrue(recipeOne.equals(sameRecipe));

        recipeOne.setRaters(2);
        recipeOne.setRecommends(1);
        assertEquals(2, recipeOne.getTotalRaters());
        assertEquals(1, recipeOne.getRecommends());
        assertTrue(recipeOne.equals(sameRecipe));

        DifferentRecipeClass recipeDiffClass = new DifferentRecipeClass("Title", "Author", 1);
        assertFalse(recipeOne.equals(recipeDiffClass));
    }

    @Test
    void testPrintRecipe() {
        Recipe recipe = new Recipe("Baked Egg", "Random", 1);
        recipe.addIngredient("Butter");
        recipe.addIngredient("Egg");
        recipe.addDirection("Turn oven on.");
        recipe.addDirection("Put into oven.");

        String output = "<html><pre>Title: " + "Baked Egg" + "\nAuthor: " + "Random" + "\nCooktime: " + "1"
                + "\n\nIngredients:" + "\nButter\nEgg" + "\n\nDirections:"
                + "\nTurn oven on.\nPut into oven." + "\n<html><pre>";
        
        assertEquals("\nButter\nEgg", recipe.printList(recipe.getIngredients()));
        assertEquals("\nTurn oven on.\nPut into oven.", recipe.printList(recipe.getDirections()));
        assertEquals(output, recipe.printRecipe());
        assertEquals("Baked Egg", recipe.toString());
    }

    @Test
    void testEqualsNull() {
        Recipe recipeOne = new Recipe(null, "Author", 1);
        Recipe sameRecipe = new Recipe(null, "Author", 1);
        Recipe differentRecipe = new Recipe("Title", "Author", 1);

        assertTrue(recipeOne.equals(sameRecipe));
        assertFalse(recipeOne.equals(differentRecipe));
        recipeOne = new Recipe("Title", null, 1);
        sameRecipe = new Recipe("Title", null, 1);
        assertTrue(recipeOne.equals(sameRecipe));
        assertFalse(recipeOne.equals(differentRecipe));

    }

    private class DifferentRecipeClass extends Recipe {

        public DifferentRecipeClass(String title, String author, int cookTime) {
            super(title, author, cookTime);
        }

    }

}
