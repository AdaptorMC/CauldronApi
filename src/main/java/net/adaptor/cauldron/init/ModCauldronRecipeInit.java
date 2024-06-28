package net.adaptor.cauldron.init;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Items;
import net.adaptor.cauldron.api.CauldronRecipeProvider;
import net.adaptor.cauldron.api.CauldronRecipeRegistry;
import net.adaptor.cauldron.recipe.CauldronRecipe;

public class ModCauldronRecipeInit implements CauldronRecipeProvider {
    static CauldronRecipe gravel = new CauldronRecipe(CauldronRecipeRegistry.DeviceType.NORMAL, "gravel")
            .setRecipe(Items.GRAVEL.getDefaultStack())
            .setResult(Items.SAND.getDefaultStack(), Items.FLINT.getDefaultStack());
    @Override
    public void addCauldronRecipes() {
        if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
            CauldronRecipe sand = new CauldronRecipe(CauldronRecipeRegistry.DeviceType.NORMAL, "sand")
                    .setRecipe(Items.SAND.getDefaultStack())
                    .setResult(Items.CLAY.getDefaultStack());
            CauldronRecipe chicken = new CauldronRecipe(CauldronRecipeRegistry.DeviceType.BOILED, "cookChick")
                    .setRecipe(Items.CHICKEN.getDefaultStack())
                    .setResult(Items.COOKED_CHICKEN.getDefaultStack());

            CauldronRecipe recipe = new CauldronRecipe(CauldronRecipeRegistry.DeviceType.BOILED, "test0")
                    .setRecipe(Items.SLIME_BALL.getDefaultStack())
                    .setResult(EntityType.SLIME);

            CauldronRecipe reci2pe = new CauldronRecipe(CauldronRecipeRegistry.DeviceType.BOILED, "test1")
                    .setRecipe(EntityType.SLIME)
                    .setResult(Items.SLIME_BALL.getDefaultStack());

            CauldronRecipe test = new CauldronRecipe(CauldronRecipeRegistry.DeviceType.BOILED, "test2")
                    .setRecipe(EntityType.AXOLOTL)
                    .setResult(EntityType.AXOLOTL, EntityType.AXOLOTL);

            CauldronRecipeRegistry.registerRecipe(gravel);
            CauldronRecipeRegistry.registerRecipe(sand);
            CauldronRecipeRegistry.registerRecipe(chicken);
            CauldronRecipeRegistry.registerRecipe(recipe);
            CauldronRecipeRegistry.registerRecipe(reci2pe);
            CauldronRecipeRegistry.registerRecipe(test);
        }
    }
}
