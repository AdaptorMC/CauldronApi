package net.yunitrish.adaptor.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.yunitrish.adaptor.Adaptor;
import net.yunitrish.adaptor.block.ModBlocks;

public class ModItemGroups {

    public static final ItemGroup AdaptorGroup = Registry.register(Registries.ITEM_GROUP,new Identifier(Adaptor.MOD_ID, "adaptor_group"), FabricItemGroup.builder().displayName(Text.translatable("itemgroup.adaptor_group")).icon(()-> new ItemStack(ModItems.Salt)).entries(((displayContext, entries) -> {
        entries.add(ModItems.Flour);
        entries.add(ModItems.Salt);
        entries.add(ModBlocks.SALT_BLOCK);
        entries.add(ModBlocks.SALT_ORE);
        entries.add(ModBlocks.DEEPSLATE_SALT_ORE);
        entries.add(ModBlocks.NETHER_SALT_ORE);
        entries.add(ModBlocks.END_STONE_SALT_ORE);
        entries.add(ModBlocks.FLOUR_BLOCK);
        entries.add(ModItems.METAL_DETECTOR);
        entries.add(ModBlocks.SOUND_BLOCK);
    })).build());

    public static void registerItemGroups() {
        Adaptor.LOGGER.info("Registering Item Groups for " + Adaptor.MOD_ID);
    }
}
