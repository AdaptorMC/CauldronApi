package net.adaptor.cauldron;

import net.adaptor.cauldron.init.ModCauldronRecipeInit;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.util.Identifier;
import net.adaptor.cauldron.api.CauldronRecipeRegistry;
import net.adaptor.cauldron.event.CauldronCookEvent;
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
		LOGGER.info("Initializing Testing API");
		CauldronRecipeRegistry.registerRecipeProvider(new ModCauldronRecipeInit());
		UseBlockCallback.EVENT.register(new CauldronCookEvent());
	}
}