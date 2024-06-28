package net.adaptor.cauldron.init;

import net.minecraft.entity.EntityType;
import net.minecraft.item.Items;
import net.adaptor.cauldron.api.CauldronRecipeProvider;
import net.adaptor.cauldron.api.CauldronRecipeRegistry;
import net.adaptor.cauldron.recipe.CauldronRecipe;

public class ModCauldronRecipeInit implements CauldronRecipeProvider {
    static final CauldronRecipe[] recipes = {
            new CauldronRecipe(CauldronRecipeRegistry.DeviceType.NORMAL, "gravel", CauldronRecipeRegistry.WaterConsume.NONE)
                    .setRecipe(Items.GRAVEL.getDefaultStack())
                    .setResult(Items.SAND.getDefaultStack(), Items.FLINT.getDefaultStack()),

            new CauldronRecipe(CauldronRecipeRegistry.DeviceType.NORMAL, "sand", CauldronRecipeRegistry.WaterConsume.ONE)
                    .setRecipe(Items.SAND.getDefaultStack())
                    .setResult(Items.CLAY.getDefaultStack()),

            new CauldronRecipe(CauldronRecipeRegistry.DeviceType.BOILED, "cookChick", CauldronRecipeRegistry.WaterConsume.NONE)
                    .setRecipe(Items.CHICKEN.getDefaultStack())
                    .setResult(Items.COOKED_CHICKEN.getDefaultStack()),

            new CauldronRecipe(CauldronRecipeRegistry.DeviceType.BOILED, "test0", CauldronRecipeRegistry.WaterConsume.NONE)
                    .setRecipe(Items.SLIME_BALL.getDefaultStack())
                    .setResult(EntityType.SLIME),

            new CauldronRecipe(CauldronRecipeRegistry.DeviceType.BOILED, "test1", CauldronRecipeRegistry.WaterConsume.NONE)
                    .setRecipe(EntityType.SLIME)
                    .setResult(Items.SLIME_BALL.getDefaultStack()),

            new CauldronRecipe(CauldronRecipeRegistry.DeviceType.BOILED, "test2", CauldronRecipeRegistry.WaterConsume.NONE)
                    .setRecipe(EntityType.AXOLOTL)
                    .setResult(EntityType.AXOLOTL, EntityType.AXOLOTL),

            new CauldronRecipe(CauldronRecipeRegistry.DeviceType.BOILED, "food", CauldronRecipeRegistry.WaterConsume.NONE)
                    .setRecipe(Items.BOWL, Items.BROWN_MUSHROOM, Items.RED_MUSHROOM)
                    .setResult(Items.MUSHROOM_STEW)
    };


    @Override
    public void addCauldronRecipes() {
        // Register each CauldronRecipe in the recipes array
        for (CauldronRecipe recipe : recipes) {
            CauldronRecipeRegistry.registerRecipe(recipe);
        }
    }
}
