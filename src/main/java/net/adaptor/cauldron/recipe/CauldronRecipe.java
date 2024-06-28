package net.adaptor.cauldron.recipe;

import net.adaptor.cauldron.CauldronEnhance;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.adaptor.cauldron.api.CauldronRecipeRegistry;

import java.util.*;

public class CauldronRecipe {
    private static final Map<String, SoundEvent> DEVICE_SOUNDS = new HashMap<>();

    static {
        DEVICE_SOUNDS.put(CauldronRecipeRegistry.DeviceType.NORMAL.name().toLowerCase(), SoundEvents.AMBIENT_UNDERWATER_ENTER);
        DEVICE_SOUNDS.put(CauldronRecipeRegistry.DeviceType.BOILED.name().toLowerCase(), SoundEvents.BLOCK_FIRE_EXTINGUISH);
        DEVICE_SOUNDS.put(CauldronRecipeRegistry.DeviceType.LAVA.name().toLowerCase(), SoundEvents.BLOCK_LAVA_POP);
        DEVICE_SOUNDS.put(CauldronRecipeRegistry.DeviceType.FREEZE.name().toLowerCase(), SoundEvents.BLOCK_POWDER_SNOW_STEP);
    }

    private final List<ItemStack> recipeItem = new ArrayList<>();
    private final Map<EntityType<?>, Integer> recipeEntity = new HashMap<>(); // I change from string into EntityType
    private final List<ItemStack> itemResults = new ArrayList<>();
    private final Map<EntityType<?>, Integer> entityResults = new HashMap<>();// I change from string into EntityType
    protected BlockPos core;
    protected World world;
    protected Box box;
    protected String id;
    protected String deviceType;
    protected boolean deviceReady;

    /**
     * Constructs a new CauldronRecipe with the specified device type and recipe name.
     *
     * @param type       The type of the cauldron device (NORMAL, BOILED, LAVA, FREEZE).
     * @param recipeName The name of the recipe.
     */
    public CauldronRecipe(CauldronRecipeRegistry.DeviceType type, String recipeName) {
        this.deviceType = type.name().toLowerCase();
        this.id = recipeName;
    }

    // Default Set
    public CauldronRecipe setRecipeItem(ItemStack... items) {
        recipeItem.addAll(Arrays.asList(items));
        return this;
    }

    public CauldronRecipe setResultItem(ItemStack... items) {
        itemResults.addAll(Arrays.asList(items));
        return this;
    }

    public CauldronRecipe setRecipeEntity(EntityType<?>... entityTypes) {
        for (EntityType<?> entityType : entityTypes) {
            recipeEntity.merge(entityType, 1, Integer::sum);
        }
        return this;
    }

    public CauldronRecipe setRecipeEntity(String... entityIds) {
        for (String id : entityIds) {
            if (EntityType.get(id).isPresent()) {
                recipeEntity.merge(EntityType.get(id).get(), 1, Integer::sum);
            }
        }
        return this;
    }

    public CauldronRecipe setResultEntity(EntityType<?>... entityTypes) {
        for (EntityType<?> entityType : entityTypes) {
            entityResults.merge(entityType, 1, Integer::sum);
        }
        return this;
    }

    public CauldronRecipe setResultEntity(String... entityIds) {
        for (String id : entityIds) {
            if (EntityType.get(id).isPresent()) {
                entityResults.merge(EntityType.get(id).get(), 1, Integer::sum);
            }
        }
        return this;
    }
    // Dynamic Set

    /**
     * Dynamically sets recipe items or entities for this Cauldron recipe.
     *
     * @param itemsOrIds Varargs representing ItemStacks, EntityTypes, or entity IDs (as Strings).
     * @return This CauldronRecipe instance for method chaining.
     * <pre>
     *{@code
     *       public class MyModCauldronRecipes implements CauldronRecipeProvider {
     *           @Override
     *           public void addCauldronRecipes() {
     *               CauldronRecipe chicken = new CauldronRecipe(CauldronRecipeRegistry.DeviceType.BOILED, "cookChick")
     *                     .setRecipe(Items.CHICKEN.getDefaultStack())
     *                     .setResult(Items.COOKED_CHICKEN.getDefaultStack());
     *               CauldronRecipeRegistry.registerRecipe(recipe);
     *           }
     *       }
     *}
     *</pre>
     * <p>
     *     this one is dynamic setResult/Recipe
     * </p>
     */

    // Method to set recipe - (ItemStacks or EntityTypes)
    public CauldronRecipe setRecipe(Object... itemsOrIds) {
        for (Object itemOrId : itemsOrIds) {
            if (itemOrId instanceof ItemStack) {
                recipeItem.add((ItemStack) itemOrId);
            } else if (itemOrId instanceof EntityType<?>) {
                recipeEntity.merge((EntityType<?>) itemOrId, 1, Integer::sum);
            } else if (itemOrId instanceof String) {
                if (EntityType.get((String) itemOrId).isPresent()) {
                    recipeEntity.merge(EntityType.get((String) itemOrId).get(), 1, Integer::sum);
                }
            }
        }
        return this;
    }

    /**
     * Dynamically sets result items or entities for this Cauldron recipe.
     *
     * @param itemsOrIds Varargs representing ItemStacks, EntityTypes, or entity IDs (as Strings).
     * @return This CauldronRecipe instance for method chaining.
     */
    // Method to set result - (ItemStacks or EntityTypes)
    public CauldronRecipe setResult(Object... itemsOrIds) {
        for (Object itemOrId : itemsOrIds) {
            if (itemOrId instanceof ItemStack) {
                itemResults.add((ItemStack) itemOrId);
            } else if (itemOrId instanceof EntityType<?>) {
                entityResults.merge((EntityType<?>) itemOrId, 1, Integer::sum);
            } else if (itemOrId instanceof String) {
                if (EntityType.get((String) itemOrId).isPresent()) {
                    entityResults.merge(EntityType.get((String) itemOrId).get(), 1, Integer::sum);
                }
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

    public String getId() {
        return id;
    }

    public CauldronRecipe checkDevice() {
        if (world.isClient) { //Warp check
            deviceReady = false;
            return this;
        }// end warp

        BlockState coreBlockState = world.getBlockState(core); //pre define
        Block blockAbove = world.getBlockState(core.down()).getBlock(); //pre define

        switch (deviceType) {
            case "normal":
                deviceReady = coreBlockState.getBlock() == Blocks.WATER_CAULDRON;
                break;
            case "boiled":
                deviceReady = coreBlockState.getBlock() == Blocks.WATER_CAULDRON && blockAbove == Blocks.CAMPFIRE;
                break;
            case "lava":
                deviceReady = coreBlockState.getBlock() == Blocks.LAVA_CAULDRON;
                break;
            case "freeze":
                deviceReady = coreBlockState.getBlock() == Blocks.POWDER_SNOW_CAULDRON;
                break;
            default:
                deviceReady = false;
                break;
        }
        return this;
    }

    public boolean run(PlayerEntity player) {
        CauldronEnhance.LOGGER.info("{}# entered", id);
        if (!deviceReady) return false;
        CauldronEnhance.LOGGER.info("{}# device checked", id);
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
            itemIngredient.merge(type, itemEntity, (existing, added) -> { // if this one bug or not work change back to old one.
                existing.getStack().increment(added.getStack().getCount());
                added.setDespawnImmediately();
                return existing;
            });
        }
        CauldronEnhance.LOGGER.info("{}# item ingredient searched", id);

        Map<EntityType<?>, List<LivingEntity>> entityIngredient = new HashMap<>();
        for (LivingEntity entity : world.getEntitiesByClass(LivingEntity.class, box, entity -> {
            for (EntityType<?> entityType : recipeEntity.keySet()) {
                if (entity.getType() == entityType) {
                    return true;
                }
            }
            return false;
        })) {
            EntityType<?> entityType = entity.getType();
            entityIngredient.computeIfAbsent(entityType, k -> new ArrayList<>()).add(entity);
        }
        CauldronEnhance.LOGGER.info("{}# entity ingredient searched", id);

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
        CauldronEnhance.LOGGER.info("{}# item recipe checked", id);
        //check entity recipe
        for (Map.Entry<EntityType<?>, Integer> recipe : recipeEntity.entrySet()) {
            EntityType<?> type = recipe.getKey(); //Change from String to EntityType
            if (entityIngredient.containsKey(type)) {
                int temp = entityIngredient.get(type).size() / recipe.getValue();
                if (temp < maxCount) {
                    maxCount = temp;
                }
            } else {
                return false;
            }
        }
        CauldronEnhance.LOGGER.info("{}# entity recipe checked", id);
        // cook task
        if (player.isSneaking()) {
            cook(maxCount, world, core, recipeItem, recipeEntity, itemIngredient, entityIngredient, itemResults, entityResults);
        } else {
            cook(1, world, core, recipeItem, recipeEntity, itemIngredient, entityIngredient, itemResults, entityResults);
        }
        CauldronEnhance.LOGGER.info("{}# cooked", id);
        return true;
    }

    protected void cook(
            int maxCount, World world, BlockPos core,
            List<ItemStack> recipeItem, Map<EntityType<?>, Integer> recipeEntity,
            Map<Item, ItemEntity> itemIngredient, Map<EntityType<?>, List<LivingEntity>> entityIngredient,
            List<ItemStack> itemResults, Map<EntityType<?>, Integer> entityResults
    ) {
        for (ItemStack recipe : recipeItem) {
            Item type = recipe.getItem();
            itemIngredient.get(type).getStack().decrement(maxCount);
        }
        for (Map.Entry<EntityType<?>, Integer> recipe : recipeEntity.entrySet()) {
            EntityType<?> type = recipe.getKey();
            for (int i = 0; i < maxCount; i++) {
                entityIngredient.get(type).getLast().kill();
            }
        }
        for (ItemStack result : itemResults) {
            spawnItem(world, core, result, maxCount);
        }
        for (Map.Entry<EntityType<?>, Integer> result : entityResults.entrySet()) {
            for (int i = 0; i < maxCount * result.getValue(); i++) {
                spawnEntity(world, core, result.getKey());
            }
        }
    }

    protected void spawnItem(World world, BlockPos pos, ItemStack itemStack, int count) {
        ItemStack temp = itemStack.copy();
        temp.setCount(count);
        world.spawnEntity(new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, temp));
        SoundEvent sound = DEVICE_SOUNDS.get(deviceType);
        if (sound != null)
            world.playSound(null, pos, sound, SoundCategory.PLAYERS, 0.8f, (float) (0.75f + world.getRandom().nextGaussian() / 20f));
    }

    protected void spawnEntity(World world, BlockPos pos, EntityType<?> entityType) {
        LivingEntity entity = (LivingEntity) entityType.create(world);
        if (entity != null) {
            entity.setPos(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
            world.spawnEntity(entity);
        }
    }
}