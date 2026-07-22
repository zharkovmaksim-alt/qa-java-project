package praktikum;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(Parameterized.class)
public class BurgerTest {

    private Burger burger;

    @Mock
    private Bun mockBun;

    @Mock
    private Ingredient mockIngredient;

    private IngredientType ingredientType;
    private String ingredientName;

    public BurgerTest(IngredientType ingredientType, String ingredientName) {
        this.ingredientType = ingredientType;
        this.ingredientName = ingredientName;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][]{
                {IngredientType.SAUCE, "Соус"},
                {IngredientType.FILLING, "Начинка"}
        };
    }

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        burger = new Burger();
    }

    @Test
    public void setBunsShouldSetBunCorrectly() {
        burger.setBuns(mockBun);
        assertEquals(mockBun, burger.bun);
    }

    @Test
    public void addIngredientShouldIncreaseSize() {
        burger.addIngredient(mockIngredient);
        assertEquals(1, burger.ingredients.size());
    }

    @Test
    public void addIngredientShouldAddCorrectIngredient() {
        burger.addIngredient(mockIngredient);
        assertEquals(mockIngredient, burger.ingredients.get(0));
    }

    @Test
    public void addIngredientWithDifferentTypesShouldWork() {
        Ingredient ingredient = mock(Ingredient.class);
        when(ingredient.getType()).thenReturn(ingredientType);
        when(ingredient.getName()).thenReturn(ingredientName);
        burger.addIngredient(ingredient);
        assertEquals(1, burger.ingredients.size());
        assertEquals(ingredient, burger.ingredients.get(0));
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
        Ingredient firstIngredient = mock(Ingredient.class);
        Ingredient secondIngredient = mock(Ingredient.class);
        burger.addIngredient(firstIngredient);
        burger.addIngredient(secondIngredient);
        burger.moveIngredient(1, 0);
        assertEquals(secondIngredient, burger.ingredients.get(0));
        assertEquals(firstIngredient, burger.ingredients.get(1));
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