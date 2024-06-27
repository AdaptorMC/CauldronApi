package net.adaptor.cauldron.event;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;
import net.adaptor.cauldron.Main;
import net.adaptor.cauldron.api.CauldronRecipeRegistry;
import net.adaptor.cauldron.recipe.CauldronRecipe;

public class CauldronCookEvent implements UseBlockCallback {
    public static int lastTick = -1;

    @Override
    public ActionResult interact(PlayerEntity player, World world, Hand hand, BlockHitResult hitResult) {
        if (!(lastTick == -1 || Math.abs(player.age - lastTick) > 5)) return ActionResult.PASS;
        else lastTick = player.age;

        if (hand != Hand.MAIN_HAND) return ActionResult.PASS;
        if (!player.getMainHandStack().isEmpty()) return ActionResult.PASS;
        for (CauldronRecipe recipe : CauldronRecipeRegistry.getRecipes()) {
            Main.LOGGER.info("test recipe {}", recipe.getName());

            if (recipe.set(world,hitResult.getBlockPos()).checkDevice().run(player)) {
                player.sendMessage(Text.of("O"), true);
                return ActionResult.SUCCESS;
            }
            player.sendMessage(Text.of("X"), true);
        }
        return ActionResult.PASS;
    }
}
