package net.adaptor.cauldron.api;

import net.adaptor.cauldron.common.CauldronEnhance;
import net.adaptor.cauldron.common.recipe.CauldronRecipe;

import java.util.ArrayList;
import java.util.List;

public class CauldronRecipeRegistry {
    private static final List<CauldronRecipe> recipes = new ArrayList<>();
    private static final List<CauldronRecipeProvider> providers = new ArrayList<>();

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
     * CauldronRecipeRegistry.registerRecipe(recipe);
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
     *     @Override
     *     public void onInitialize() {
     *         CauldronRecipeRegistry.registerRecipeProvider(new YourCauldronRecipe());
     *     }
     * }
     * }
     * </pre>
     *
     * @param provider the {@link CauldronRecipeProvider} to register.
     */
    public static void registerRecipeProvider(CauldronRecipeProvider provider) {
        providers.add(provider);
        provider.init();
    }
    /**
     * Ensures that all providers have added their recipes.
     * This method should be called once all recipes and providers have been registered.
     */
    public static void initializeRecipes() {
        CauldronEnhance.LOGGER.info("Loaded {} recipes", recipes.size());
    }

    /**
     * Returns the complete list of cauldron recipes, including those from registered providers.
     * <p>
     * This method ensures that recipes from all registered providers are included.
     * </p>
     *
     * @return a list of {@link CauldronRecipe}.
     */
    public static List<CauldronRecipe> getRecipes() {
        for (CauldronRecipeProvider provider : providers) {
            provider.init();
        }
        return recipes;
    }

    public enum DeviceType {
        NORMAL,
        BOILED,
        LAVA,
        FREEZE
    }

    public enum WaterConsume {
        NONE(0),
        ONE(1),
        TWO(2),
        THREE(3);

        private final int amount;

        WaterConsume(int amount) {
            this.amount = amount;
        }

        public int getAmount() {
            return this.amount;
        }
    }
}



