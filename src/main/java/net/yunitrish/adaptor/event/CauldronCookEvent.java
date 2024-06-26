package net.yunitrish.adaptor.event;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;
import net.yunitrish.adaptor.Adaptor;
import net.yunitrish.adaptor.api.CauldronRecipeRegistry;
import net.yunitrish.adaptor.recipe.CauldronRecipe;

public class CauldronCookEvent implements UseBlockCallback {
    public static int lastTick = -1;

    @Override
    public ActionResult interact(PlayerEntity player, World world, Hand hand, BlockHitResult hitResult) {
        if (!(lastTick == -1 || Math.abs(player.age - lastTick) > 5)) return ActionResult.PASS;
        else lastTick = player.age;

        if (hand != Hand.MAIN_HAND) return ActionResult.PASS;
        if (!player.getMainHandStack().isEmpty()) return ActionResult.PASS;
        for (CauldronRecipe recipe : CauldronRecipeRegistry.getRecipes()) {
            Adaptor.LOGGER.info("test recipe {}", recipe.getName());

            boolean deviceReady;
            if (recipe.getDeviceType().equals("normal")) {
                deviceReady = !world.isClient && world.getBlockState(hitResult.getBlockPos()).getBlock().equals(Blocks.WATER_CAULDRON);
            } else {
                deviceReady = false;
            }
            if (recipe.set(world, hitResult.getBlockPos()).checkDevice(deviceReady).run(player)) {
                player.sendMessage(Text.of("O"), true);
                return ActionResult.SUCCESS;
            }
            player.sendMessage(Text.of("X"), true);
        }
        return ActionResult.PASS;
    }
}
