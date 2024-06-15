//package net.yunitrish.adaptor.enchantment;
//
//import net.minecraft.enchantment.Enchantment;
//import net.minecraft.registry.Registries;
//import net.minecraft.registry.Registry;
//import net.yunitrish.adaptor.Adaptor;
//import net.yunitrish.adaptor.enchantment.enchantments.*;
//
//public class ModEnchantments {
//    public static Enchantment LEACH = register("leach", new LeachEnchantment());
//    public static Enchantment MANIC = register("manic", new ManicEnchantment());
//    public static Enchantment DEXTERITY = register("dexterity", new DexterityEnchantment());
//    public static Enchantment WISDOM = register("wisdom", new WisdomEnchantment());
//    public static Enchantment TITAN = register("titan", new TitanEnchantment());
//
//    private static Enchantment register(String name, Enchantment enchantment) {
//        return Registry.register(Registries.ENCHANTMENT, Adaptor.modIdentifier(name),enchantment);
//    }
//
//    public static void registerModEnchantments() {
//        Adaptor.LOGGER.info("Registering enchantments...");
//    }
//}
