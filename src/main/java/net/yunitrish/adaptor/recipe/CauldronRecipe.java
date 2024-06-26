package net.yunitrish.adaptor.recipe;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.HuskEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.yunitrish.adaptor.Adaptor;

import java.util.*;

public class CauldronRecipe {
    protected final SoundEvent sound = SoundEvents.AMBIENT_UNDERWATER_ENTER;
    private final List<ItemStack> recipeItem = new ArrayList<>();
    private final Map<String, Integer> recipeEntity = new HashMap<>();
    private final List<ItemStack> itemResults = new ArrayList<>();
    private final Map<String, Integer> entityResults = new HashMap<>();
    protected BlockPos core;
    protected World world;
    protected Box box;
    protected String id;
    protected String deviceType;
    protected boolean deviceReady;

    public CauldronRecipe setRecipeItem(ItemStack... items) {
        recipeItem.addAll(Arrays.asList(items));
        return this;
    }

    public CauldronRecipe setResultItem(ItemStack... items) {
        itemResults.addAll(Arrays.asList(items));
        return this;
    }

    public CauldronRecipe setRecipeEntity(LivingEntity... entities) {
        for (LivingEntity entity : entities) {
            if (recipeEntity.containsKey(entity.getType().getTranslationKey())) {
                recipeEntity.replace(entity.getType().getTranslationKey(), recipeEntity.get(entity.getType().getTranslationKey()) + 1);
            } else {
                recipeEntity.put(entity.getType().getTranslationKey(), 1);
            }
        }
        return this;
    }

    public CauldronRecipe setResultEntity(LivingEntity... entities) {
        for (LivingEntity entity : entities) {
            if (entityResults.containsKey(entity.getType().getTranslationKey())) {
                entityResults.replace(entity.getType().getTranslationKey(), entityResults.get(entity.getType().getTranslationKey()) + 1);
            } else {
                entityResults.put(entity.getType().getTranslationKey(), 1);
            }
        }
        return this;
    }

    public CauldronRecipe set(World world, BlockPos blockPos) {
        this.core = blockPos;
        this.world = world;
        this.box = Box.of(core.toCenterPos(), 0.5, 0.5, 0.5);
        return this;
    }

    public String getName() {
        return id;
    }

    public CauldronRecipe setName(String name) {
        this.id = name;
        return this;
    }

    public CauldronRecipe setType(String type) {
        if (type != null) this.deviceType = type;
        else this.deviceType = "normal";
        return this;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public CauldronRecipe checkDevice() {
        switch (deviceType) {
            case "normal": {
                deviceReady = !world.isClient && world.getBlockState(core).getBlock().equals(Blocks.WATER_CAULDRON);
                break;
            }
            case "boiled": {
                deviceReady = !world.isClient() && world.getBlockState(core).getBlock() == Blocks.WATER_CAULDRON && world.getBlockState(core.down()).getBlock() == Blocks.CAMPFIRE;
                break;
            }
            case "lava": {
                deviceReady = !world.isClient() && world.getBlockState(core).getBlock() == Blocks.LAVA_CAULDRON;
                break;
            }
            case "freeze": {
                deviceReady = !world.isClient() && world.getBlockState(core).getBlock() == Blocks.POWDER_SNOW_CAULDRON;
                break;
            }
            default: {
                deviceReady = false;
                break;
            }
        }
        return this;
    }

    public CauldronRecipe checkDevice(boolean valid) {
        this.deviceReady = valid;
        return this;
    }

    public boolean run(PlayerEntity player) {
        Adaptor.LOGGER.info("{}# entered", id);
        if (!deviceReady) return false;
        Adaptor.LOGGER.info("{}# device checked", id);
        //list item in cauldron
        Map<Item, ItemEntity> itemIngredient = new HashMap<>();
        for (ItemEntity itemEntity : world.getEntitiesByClass(ItemEntity.class, box, itemEntity -> {
            for (ItemStack recipe : recipeItem) {
                if (recipe.getItem() == itemEntity.getStack().getItem()) {
                    return true;
                }
            }
            return false;
        })) {
            Item type = itemEntity.getStack().getItem();
            if (itemIngredient.containsKey(type)) {
                itemIngredient.get(type).getStack().increment(itemEntity.getStack().getCount());
                itemEntity.setDespawnImmediately();
            } else {
                itemIngredient.put(itemEntity.getStack().getItem(), itemEntity);
            }
        }
        Adaptor.LOGGER.info("{}# item ingredient searched", id);
        //list entity in cauldron
        Map<String, List<LivingEntity>> entityIngredient = new HashMap<>();
        for (LivingEntity entity : world.getEntitiesByClass(LivingEntity.class, box, entity -> {
            for (Map.Entry<String, Integer> recipe : recipeEntity.entrySet()) {
                if (recipe.getKey().equals(entity.getType().getTranslationKey())) {
                    return true;
                }
            }
            return false;
        })) {
            EntityType<?> entityType = entity.getType();

            if (entityIngredient.containsKey(entityType.getTranslationKey())) {
                entityIngredient.get(entityType.getTranslationKey()).add(entity);
            } else {
                entityIngredient.put(entityType.getTranslationKey(), List.of(entity));
            }
        }
        Adaptor.LOGGER.info("{}# entity ingredient searched", id);
        //check recipe
        int maxCount = Integer.MAX_VALUE;
        for (ItemStack recipe : recipeItem) {
            Item type = recipe.getItem();
            if (itemIngredient.containsKey(type)) {
                int temp = itemIngredient.get(type).getStack().getCount() / recipe.getCount();
                if (temp < maxCount) {
                    maxCount = temp;
                }
            } else {
                return false;
            }
        }
        Adaptor.LOGGER.info("{}# item recipe checked", id);
        //check entity recipe
        for (Map.Entry<String, Integer> recipe : recipeEntity.entrySet()) {
            String type = recipe.getKey();
            if (entityIngredient.containsKey(type)) {
                int temp = entityIngredient.get(type).size() / recipe.getValue();
                if (temp < maxCount) {
                    maxCount = temp;
                }
            } else {
                return false;
            }
        }
        Adaptor.LOGGER.info("{}# entity recipe checked", id);
        // cook
        if (player.isSneaking()) {
            cook(maxCount, world, core, recipeItem, recipeEntity, itemIngredient, entityIngredient, itemResults, entityResults);
        } else {
            cook(1, world, core, recipeItem, recipeEntity, itemIngredient, entityIngredient, itemResults, entityResults);
        }
        Adaptor.LOGGER.info("{}# cooked", id);
        return true;
    }

    protected void cook(
            int maxCount, World world, BlockPos core,
            List<ItemStack> recipeItem, Map<String, Integer> recipeEntity,
            Map<Item, ItemEntity> itemIngredient, Map<String, List<LivingEntity>> entityIngredient,
            List<ItemStack> itemResults, Map<String, Integer> entityResults
    ) {
        for (ItemStack recipe : recipeItem) {
            Item type = recipe.getItem();
            itemIngredient.get(type).getStack().decrement(maxCount);
        }
        for (Map.Entry<String, Integer> recipe : recipeEntity.entrySet()) {
            String type = recipe.getKey();
            for (int i = 0; i < maxCount; i++) {
                entityIngredient.get(type).getLast().kill();
            }
        }
        for (ItemStack result : itemResults) {
            spawnItem(world, core, result, maxCount);
        }
        for (Map.Entry<String, Integer> result : entityResults.entrySet()) {
            for (int i = 0; i < maxCount; i++) {
                spawnEntity(world, core, result.getKey());
            }
        }
    }

    protected void spawnItem(World world, BlockPos pos, ItemStack itemStack, int count) {
        ItemStack temp = itemStack.copy();
        temp.setCount(count);
        world.spawnEntity(new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, temp));
        world.playSound(null, pos, sound, SoundCategory.PLAYERS, 0.8f, 0.5f);
    }

    protected void spawnEntity(World world, BlockPos pos, String entity) {
        LivingEntity result;

        switch (entity) {
            case "minecraft.entity.zombie": {
                result = new ZombieEntity(world);
                result.getType().getTranslationKey();
                break;
            }
            case "minecraft.entity.husk": {
                result = new HuskEntity(EntityType.HUSK, world);
                break;
            }
            default: {
                result = new PigEntity(EntityType.PIG, world);
                break;
            }
        }
        result.setPos(pos.getX(), pos.getY(), pos.getZ());
        world.spawnEntity(result);
    }
}