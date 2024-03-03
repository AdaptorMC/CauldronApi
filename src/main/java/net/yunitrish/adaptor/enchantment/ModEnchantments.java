package net.yunitrish.adaptor.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.yunitrish.adaptor.Adaptor;

public class ModEnchantments {

    public static Enchantment LEACH = register("leach", new LeachEnchantment(Enchantment.Rarity.RARE, EnchantmentTarget.WEAPON, EquipmentSlot.MAINHAND));

    private static Enchantment register(String name, Enchantment enchantment) {
        return Registry.register(Registries.ENCHANTMENT,new Identifier(Adaptor.MOD_ID,name),enchantment);
    }

    public static void registerModEnchantments() {
        System.out.println("Registering enchantments for " + Adaptor.MOD_ID);
    }
}
