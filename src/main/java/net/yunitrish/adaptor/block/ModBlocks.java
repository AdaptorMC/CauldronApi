package net.yunitrish.adaptor.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.ColorCode;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.yunitrish.adaptor.Adaptor;
import net.yunitrish.adaptor.block.custom.SoundBlock;

public class ModBlocks {

    public static final Block SALT_BLOCK = registerBlock("salt_block",new ColoredFallingBlock(new ColorCode(14406560),FabricBlockSettings.create().noCollision().breakInstantly().sounds(BlockSoundGroup.SAND).pistonBehavior(PistonBehavior.DESTROY)));
    public static final Block FLOUR_BLOCK = registerBlock("flour_block",new Block(FabricBlockSettings.copyOf(Blocks.WHEAT).sounds(BlockSoundGroup.SAND)));
    public static final Block SALT_ORE = registerBlock("salt_ore",new ExperienceDroppingBlock(UniformIntProvider.create(0,2),FabricBlockSettings.copyOf(Blocks.STONE).strength(2f)));
    public static final Block DEEPSLATE_SALT_ORE = registerBlock("deepslate_salt_ore",new ExperienceDroppingBlock(UniformIntProvider.create(0,2),FabricBlockSettings.copyOf(Blocks.DEEPSLATE).strength(4f)));
    public static final Block NETHER_SALT_ORE = registerBlock("nether_salt_ore",new ExperienceDroppingBlock(UniformIntProvider.create(0,2),FabricBlockSettings.copyOf(Blocks.NETHERRACK).strength(1.5f).sounds(BlockSoundGroup.SAND)));
    public static final Block END_STONE_SALT_ORE = registerBlock("end_stone_salt_ore",new ExperienceDroppingBlock(UniformIntProvider.create(0,2),FabricBlockSettings.copyOf(Blocks.END_STONE).sounds(BlockSoundGroup.SAND).strength(4f)));

    public static final Block SOUND_BLOCK = registerBlock("sound_block",new SoundBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK)));

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK,new Identifier(Adaptor.MOD_ID,name),block);
    }

    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registries.ITEM, new Identifier(Adaptor.MOD_ID,name),new BlockItem(block, new FabricItemSettings()));
    }

    public static void registerModBlocks (){
        Adaptor.LOGGER.info("Registering blocks for " + Adaptor.MOD_ID);
    }
}
