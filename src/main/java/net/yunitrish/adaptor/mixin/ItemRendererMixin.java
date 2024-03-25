package net.yunitrish.adaptor.mixin;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.yunitrish.adaptor.AdaptorMain;
import net.yunitrish.adaptor.item.ModItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {
    @ModifyVariable(method = "renderItem", at = @At(value = "HEAD"), argsOnly = true)
    public BakedModel useIronHammerModel(BakedModel value, ItemStack stack, ModelTransformationMode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if (renderMode != ModelTransformationMode.GUI) {
            if (stack.isOf(ModItems.IRON_HAMMER)) {
                return ((ItemRendererAccessor) this).adaptor$getModels().getModelManager().getModel(new ModelIdentifier(AdaptorMain.MOD_ID, "iron_hammer_3d", "inventory"));
            }
            else if (stack.isOf(Items.STONE_AXE)) {
                return ((ItemRendererAccessor) this).adaptor$getModels().getModelManager().getModel(new ModelIdentifier(AdaptorMain.MOD_ID, "stone_axe_3d", "inventory"));
            }
        }
        return value;
    }
}
