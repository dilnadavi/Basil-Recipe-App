package persistence;

import model.Recipe;
import model.UserCollection;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    
    protected void checkRecipe(String title, String author, int cookTime, Recipe recipe) {
        assertEquals(title, recipe.getTitle());
        assertEquals(author, recipe.getAuthor());
        assertEquals(cookTime, recipe.getCookTime());
    }

    protected void checkUserCollection(String title, String desc, UserCollection uc) {
        assertEquals(title, uc.getTitle());
        assertEquals(desc, uc.getDescription());
    }
}
