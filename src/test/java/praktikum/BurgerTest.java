package praktikum;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BurgerTest {

    private Burger burger;

    @Mock
    private Bun mockBun;

    @Mock
    private Ingredient mockIngredient;

    @Before
    public void setUp() {
        burger = new Burger();
    }

    @Test
    public void setBunsShouldSetBunCorrectly() {
        burger.setBuns(mockBun);
        assertEquals(mockBun, burger.bun);
    }

    @Test
    public void addIngredientShouldAddIngredientCorrectly() {
        burger.addIngredient(mockIngredient);
        assertEquals(1, burger.ingredients.size());
        assertEquals(mockIngredient, burger.ingredients.get(0));
    }

    @Test
    public void removeIngredientShouldRemoveIngredientCorrectly() {
        burger.addIngredient(mockIngredient);
        assertEquals(1, burger.ingredients.size());
        burger.removeIngredient(0);
        assertEquals(0, burger.ingredients.size());
    }

    @Test
    public void moveIngredientShouldMoveIngredientCorrectly() {
        Ingredient ingredient1 = mock(Ingredient.class);
        Ingredient ingredient2 = mock(Ingredient.class);
        burger.addIngredient(ingredient1);
        burger.addIngredient(ingredient2);
        burger.moveIngredient(1, 0);
        assertEquals(ingredient2, burger.ingredients.get(0));
        assertEquals(ingredient1, burger.ingredients.get(1));
    }

    @Test
    public void getPriceShouldReturnCorrectSum() {
        when(mockBun.getPrice()).thenReturn(1.5f);
        when(mockIngredient.getPrice()).thenReturn(2.0f);
        burger.setBuns(mockBun);
        burger.addIngredient(mockIngredient);
        float price = burger.getPrice();
        float expectedPrice = 1.5f * 2 + 2.0f;
        assertEquals(expectedPrice, price, 0.01f);
    }

    @Test
    public void getReceiptShouldReturnCorrectFormat() {
        when(mockBun.getName()).thenReturn("Краторная булка");
        when(mockBun.getPrice()).thenReturn(1.5f);
        when(mockIngredient.getName()).thenReturn("Соус");
        when(mockIngredient.getType()).thenReturn(IngredientType.SAUCE);
        when(mockIngredient.getPrice()).thenReturn(2.0f);
        burger.setBuns(mockBun);
        burger.addIngredient(mockIngredient);
        String receipt = burger.getReceipt();
        assertTrue(receipt.contains("(==== Краторная булка ====)"));
        assertTrue(receipt.contains("= sauce Соус ="));
        assertTrue(receipt.contains("(==== Краторная булка ====)"));
        assertTrue(receipt.contains("Price:"));
    }
}