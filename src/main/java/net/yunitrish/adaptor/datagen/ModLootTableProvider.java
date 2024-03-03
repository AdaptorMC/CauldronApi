package net.yunitrish.adaptor.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.data.server.loottable.BlockLootTableGenerator;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.predicate.StatePredicate;
import net.yunitrish.adaptor.block.ModBlocks;
import net.yunitrish.adaptor.block.custom.SoyBeanCropBlock;
import net.yunitrish.adaptor.item.ModItems;

public class ModLootTableProvider extends FabricBlockLootTableProvider {
    public ModLootTableProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        addDrop(ModBlocks.SALT_BLOCK, customOreDrops(ModBlocks.SALT_BLOCK,ModItems.SALT,0f,9f));
        addDrop(ModBlocks.SOUND_BLOCK);

        addDrop(ModBlocks.SALT_ORE,customOreDrops(ModBlocks.SALT_ORE, ModItems.SALT,2f,5f));
        addDrop(ModBlocks.DEEPSLATE_SALT_ORE,customOreDrops(ModBlocks.DEEPSLATE_SALT_ORE, ModItems.SALT,2f,5f));
        addDrop(ModBlocks.END_STONE_SALT_ORE,customOreDrops(ModBlocks.END_STONE_SALT_ORE, ModItems.SALT,2f,5f));
        addDrop(ModBlocks.NETHER_SALT_ORE,customOreDrops(ModBlocks.NETHER_SALT_ORE, ModItems.SALT,2f,5f));

        addDrop(ModBlocks.DIRT_STAIRS);
        addDrop(ModBlocks.DIRT_TRAPDOOR);
        addDrop(ModBlocks.DIRT_WALL);
        addDrop(ModBlocks.DIRT_FENCE);
        addDrop(ModBlocks.DIRT_FENCE_GATE);
        addDrop(ModBlocks.DIRT_BUTTON);
        addDrop(ModBlocks.DIRT_PRESSURE_PLATE);
        addDrop(ModBlocks.DIRT_DOOR,doorDrops(ModBlocks.DIRT_DOOR));
        addDrop(ModBlocks.DIRT_SLAB,slabDrops(ModBlocks.DIRT_SLAB));


        addDrop(ModBlocks.SOYBEAN_CROP,
                cropDrops(
                        ModBlocks.SOYBEAN_CROP,
                        ModItems.SOYBEAN,
                        ModItems.SOYBEAN,
                        BlockStatePropertyLootCondition
                                .builder(ModBlocks.SOYBEAN_CROP)
                                .properties(StatePredicate.Builder.create().exactMatch(SoyBeanCropBlock.AGE,5))
                ));
        addDrop(ModBlocks.CORN_CROP,
                cropDrops(
                        ModBlocks.CORN_CROP,
                        ModItems.CORN,
                        ModItems.CORN_SEEDS,
                        BlockStatePropertyLootCondition
                                .builder(ModBlocks.CORN_CROP)
                                .properties(
                                        StatePredicate.Builder.create().exactMatch(SoyBeanCropBlock.AGE,7))
                                .or(
                                        BlockStatePropertyLootCondition
                                        .builder(ModBlocks.CORN_CROP)
                                        .properties(StatePredicate.Builder.create().exactMatch(SoyBeanCropBlock.AGE,8)))
                )
        );

        addDrop(ModBlocks.DAHLIA);
        addPottedPlantDrops(ModBlocks.POTTED_DAHLIA);
    }

    public LootTable.Builder customOreDrops(Block drop, Item item, float least, float most) {
        return BlockLootTableGenerator
                .dropsWithSilkTouch(
                        drop,
                        (LootPoolEntry.Builder)this.applyExplosionDecay(
                                drop,
                                (
                                        (LeafEntry.Builder) ItemEntry.builder(item)
                                                .apply(SetCountLootFunction
                                                .builder(
                                                        UniformLootNumberProvider
                                                                .create(least, most))))
                                        .apply(ApplyBonusLootFunction.oreDrops(Enchantments.FORTUNE))));
    }
}
