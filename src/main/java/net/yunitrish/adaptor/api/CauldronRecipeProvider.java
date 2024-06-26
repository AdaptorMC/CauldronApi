package net.yunitrish.adaptor.api;

/**
 * Interface for providing cauldron recipes.
 * <p>
 * Other mods can implement this interface to add their own cauldron recipes.
 * </p>
 *
 * <pre>
 * {@code
 * // Example usage in your mod's initialization class
 * public class YourModInit implements CauldronRecipeProvider {
 *     @Override
 *     public void addCauldronRecipes() {
 *         CauldronRecipe gravelRecipe = CauldronRecipe.NORMAL_RECIPE
 *             .setName("gravelRecipe")
 *             .setRecipeItem(Items.GRAVEL.getDefaultStack())
 *             .setResultItem(Items.SAND.getDefaultStack(), Items.FLINT.getDefaultStack());
 *         CauldronCookEvent.registerRecipe(gravelRecipe);
 *     }
 *
 *     public static void init() {
 *         CauldronCookEvent.registerRecipeProvider(new YourModInit());
 *     }
 * }
 * }
 * </pre>
 *
 * @see net.yunitrish.adaptor.init.ModCauldronRecipeInit
 */
public interface CauldronRecipeProvider {
    /**
     * Now Other mod Can call this method to implement into their own mod or other mod project
     */
    void addCauldronRecipes();
}
