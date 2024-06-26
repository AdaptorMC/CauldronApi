package net.yunitrish.adaptor;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Adaptor implements ModInitializer {
	public static final String MOD_ID = "adaptor";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static Identifier id(String name) {
		return Identifier.of(Adaptor.MOD_ID, name);
	}

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing");
		UseBlockCallback.EVENT.register(CauldronCookListener::run);
	}
}