package persistence;

import model.Recipe;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkRecipe(String title, String author, int cookTime, Recipe recipe) {
        assertEquals(title, recipe.getTitle());
        assertEquals(author, recipe.getAuthor());
    }
}
