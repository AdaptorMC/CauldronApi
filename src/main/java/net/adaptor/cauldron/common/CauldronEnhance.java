package net.adaptor.cauldron.common;

import net.adaptor.cauldron.common.init.ModCauldronRecipe;
import net.adaptor.cauldron.common.init.ModParticles;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import net.adaptor.cauldron.api.CauldronRecipeRegistry;
import net.adaptor.cauldron.common.event.CauldronCookEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CauldronEnhance implements ModInitializer {
	public static final String MOD_ID = "cauldron_api";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static Identifier id(String name) {
		return Identifier.of(CauldronEnhance.MOD_ID, name);
	}

	@Override
	public void onInitialize() {
		ModParticles.init();
		LOGGER.info("Initializing Cauldron API");
		if (FabricLoader.getInstance().isDevelopmentEnvironment()) //Register only dev
			CauldronRecipeRegistry.registerRecipeProvider(new ModCauldronRecipe());
		UseBlockCallback.EVENT.register(new CauldronCookEvent());
	}
}