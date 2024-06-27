package net.adaptor.cauldron.api;

public interface CauldronRecipeProvider {
    /**
     * Adds cauldron recipes to the registry.
     * <p>
     * Implement this method in your class to register custom cauldron recipes.
     * </p>
     *
     * <pre>
     * {@code
     * public class MyModCauldronRecipes implements CauldronRecipeProvider {
     *     @Override
     *     public void addCauldronRecipes() {
     *         CauldronRecipe recipe = new CauldronRecipe(CauldronRecipeRegistry.DeviceType.NORMAL, "MyRecipe")
     *             .setRecipeItem(new ItemStack(Items.DIRT))
     *             .setResultItem(new ItemStack(Items.DIAMOND));
     *         CauldronRecipeRegistry.registerRecipe(recipe);
     *     }
     * }
     * }
     * </pre>
     */
    void addCauldronRecipes();
}
