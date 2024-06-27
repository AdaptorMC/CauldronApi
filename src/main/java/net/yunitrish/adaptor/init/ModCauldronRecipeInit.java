package net.yunitrish.adaptor.init;

import net.minecraft.item.Items;
import net.yunitrish.adaptor.api.CauldronRecipeProvider;
import net.yunitrish.adaptor.api.CauldronRecipeRegistry;
import net.yunitrish.adaptor.recipe.CauldronRecipe;


public class ModCauldronRecipeInit implements CauldronRecipeProvider {
    @Override
    public void addCauldronRecipes() {
        CauldronRecipe gravel = new CauldronRecipe(CauldronRecipeRegistry.DeviceType.NORMAL, "gravel")
                .setRecipeItem(Items.GRAVEL.getDefaultStack())
                .setResultItem(Items.SAND.getDefaultStack(), Items.FLINT.getDefaultStack());
        CauldronRecipe sand = new CauldronRecipe(CauldronRecipeRegistry.DeviceType.NORMAL,"sand")
                .setRecipeItem(Items.SAND.getDefaultStack())
                .setResultItem(Items.CLAY.getDefaultStack());
        CauldronRecipe chicken = new CauldronRecipe(CauldronRecipeRegistry.DeviceType.BOILED, "cookChick")
                .setRecipeItem(Items.CHICKEN.getDefaultStack())
                        .setResultItem(Items.COOKED_CHICKEN.getDefaultStack());
        CauldronRecipeRegistry.registerRecipe(gravel);
        CauldronRecipeRegistry.registerRecipe(sand);
        CauldronRecipeRegistry.registerRecipe(chicken);
    }
}
