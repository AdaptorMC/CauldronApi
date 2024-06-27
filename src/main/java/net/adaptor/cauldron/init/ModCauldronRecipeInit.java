package net.adaptor.cauldron.init;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Items;
import net.adaptor.cauldron.api.CauldronRecipeProvider;
import net.adaptor.cauldron.api.CauldronRecipeRegistry;
import net.adaptor.cauldron.recipe.CauldronRecipe;

public class ModCauldronRecipeInit implements CauldronRecipeProvider {
    @Override
    public void addCauldronRecipes() {
        if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
            CauldronRecipe gravel = new CauldronRecipe(CauldronRecipeRegistry.DeviceType.NORMAL, "gravel")
                    .setRecipeItem(Items.GRAVEL.getDefaultStack())
                    .setResultItem(Items.SAND.getDefaultStack(), Items.FLINT.getDefaultStack());
            CauldronRecipe sand = new CauldronRecipe(CauldronRecipeRegistry.DeviceType.NORMAL, "sand")
                    .setRecipeItem(Items.SAND.getDefaultStack())
                    .setResultItem(Items.CLAY.getDefaultStack());
            CauldronRecipe chicken = new CauldronRecipe(CauldronRecipeRegistry.DeviceType.BOILED, "cookChick")
                    .setRecipeItem(Items.CHICKEN.getDefaultStack())
                    .setResultItem(Items.COOKED_CHICKEN.getDefaultStack());

            CauldronRecipe recipe = new CauldronRecipe(CauldronRecipeRegistry.DeviceType.BOILED, "test0")
                    .setRecipeItem(Items.SLIME_BALL.getDefaultStack())
                    .setResultEntity(EntityType.SLIME);

            CauldronRecipe reci2pe = new CauldronRecipe(CauldronRecipeRegistry.DeviceType.BOILED, "test1")
                    .setRecipeEntity(EntityType.SLIME)
                    .setResultItem(Items.SLIME_BALL.getDefaultStack());

            CauldronRecipe test = new CauldronRecipe(CauldronRecipeRegistry.DeviceType.BOILED, "test2")
                    .setRecipeEntity(EntityType.AXOLOTL)
                    .setResultEntity(EntityType.AXOLOTL, EntityType.AXOLOTL, EntityType.AXOLOTL, EntityType.AXOLOTL, EntityType.AXOLOTL, EntityType.AXOLOTL, EntityType.AXOLOTL, EntityType.AXOLOTL, EntityType.AXOLOTL, EntityType.AXOLOTL, EntityType.AXOLOTL, EntityType.AXOLOTL, EntityType.AXOLOTL, EntityType.AXOLOTL, EntityType.AXOLOTL, EntityType.AXOLOTL, EntityType.AXOLOTL, EntityType.AXOLOTL, EntityType.AXOLOTL, EntityType.AXOLOTL, EntityType.AXOLOTL, EntityType.AXOLOTL, EntityType.AXOLOTL);

            CauldronRecipeRegistry.registerRecipe(gravel);
            CauldronRecipeRegistry.registerRecipe(sand);
            CauldronRecipeRegistry.registerRecipe(chicken);
            CauldronRecipeRegistry.registerRecipe(recipe);
            CauldronRecipeRegistry.registerRecipe(reci2pe);
            CauldronRecipeRegistry.registerRecipe(test);
        }
    }
}
