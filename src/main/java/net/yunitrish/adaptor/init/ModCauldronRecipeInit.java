package net.yunitrish.adaptor.init;

import net.minecraft.item.Items;
import net.yunitrish.adaptor.api.CauldronRecipeProvider;
import net.yunitrish.adaptor.api.CauldronRecipeRegistry;
import net.yunitrish.adaptor.recipe.CauldronRecipe;


public class ModCauldronRecipeInit implements CauldronRecipeProvider {
    @Override
    public void addCauldronRecipes() {
        CauldronRecipe gravel = new CauldronRecipe(CauldronRecipe.DeviceType.NORMAL, "gravel")
                .setRecipeItem(Items.GRAVEL.getDefaultStack())
                .setResultItem(Items.SAND.getDefaultStack(), Items.FLINT.getDefaultStack());
        CauldronRecipe sand = new CauldronRecipe(CauldronRecipe.DeviceType.NORMAL,"sand")
                .setRecipeItem(Items.SAND.getDefaultStack())
                .setResultItem(Items.CLAY.getDefaultStack());

        CauldronRecipeRegistry.registerRecipe(gravel);
        CauldronRecipeRegistry.registerRecipe(sand);
    }
}
