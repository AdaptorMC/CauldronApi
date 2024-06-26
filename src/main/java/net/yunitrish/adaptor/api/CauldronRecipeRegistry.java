package net.yunitrish.adaptor.api;

import net.yunitrish.adaptor.recipe.CauldronRecipe;

import java.util.ArrayList;
import java.util.List;

public class CauldronRecipeRegistry {
    /**
     * List of cauldron recipes.
     * <p>
     * This list should not be accessed or modified directly. Instead, use the
     * {@link #registerRecipe(CauldronRecipe)} method to add recipes.
     * </p>
     *
     * <pre>
     * {@code
     * // Correct way to add a recipe
     * CauldronRecipe recipe = new CauldronRecipe();
     * CauldronCookEvent.registerRecipe(recipe);
     * }
     * </pre>
     *
     * @see #registerRecipe(CauldronRecipe)
     */
    private static final List<CauldronRecipe> recipes = new ArrayList<>();
    private static final List<CauldronRecipeProvider> providers = new ArrayList<>();
    //TODO: this providers are not never queried

    /**
     * Registers a new cauldron recipe.
     * <p>
     * This is the preferred way to add new recipes to the cauldron. Directly
     * accessing the {@link #recipes} list is discouraged.
     * </p>
     *
     * <pre>
     * {@code
     * // Example usage
     * CauldronRecipe recipe = new CauldronRecipe();
     * CauldronCookEvent.registerRecipe(recipe);
     * }
     * </pre>
     *
     * @param recipe the {@link CauldronRecipe} to register.
     */
    public static void registerRecipe(CauldronRecipe recipe) {
        recipes.add(recipe);
    }

    /**
     * Registers a new cauldron recipe provider.
     * <p>
     * This method should be used to register a provider that will add cauldron recipes.
     * </p>
     *
     * <pre>
     * {@code
     * // Example usage in your mod's initialization class
     * public class MainMethod implements ModInitializer {
     *     public static void init() {
     *         CauldronCookEvent.registerRecipeProvider(new YourModInit());
     *         CauldronRecipeRegistry.registerRecipeProvider(new ModCauldronRecipeInit());
     *     }
     * }
     * }
     * </pre>
     *
     * @param provider the {@link CauldronRecipeProvider} to register.
     */

    public static void registerRecipeProvider(CauldronRecipeProvider provider) {
        providers.add(provider);
        provider.addCauldronRecipes();
    }

    public static List<CauldronRecipe> getRecipes() {
        return recipes;
    }
}
