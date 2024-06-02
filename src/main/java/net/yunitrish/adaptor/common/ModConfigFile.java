package net.yunitrish.adaptor.common;

import com.google.gson.Gson;
import net.fabricmc.loader.api.FabricLoader;
import net.yunitrish.adaptor.Adaptor;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class ModConfigFile {

    private static final Path FABRIC_CONFIG_DIR = FabricLoader.getInstance().getConfigDir();
    public final File file;
    public final String FILENAME;
    public ModConfig config = null;

    public ModConfigFile(String fileName) throws IOException {
        file = new File(FABRIC_CONFIG_DIR.toFile(), fileName);

        this.FILENAME = fileName;
        if (file.exists()) {
            config = read();
        } else {
            if (file.createNewFile() && file.setReadable(true) && file.setWritable(true)) {
                config = new ModConfig();
                write();
            } else {
                Adaptor.LOGGER.info("Error creating config file");
            }
        }
    }

    public ModConfig read() {
        try {
            FileReader reader = new FileReader(file);
            config = new Gson().fromJson(reader, ModConfig.class);
            reader.close();
            return config;
        } catch (IOException ignored) {
            return new ModConfig();
        }
    }

    public void write() {
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(new Gson().toJson(config));
            writer.close();
        } catch (IOException ignored) {
        }
    }

    public void addBindData(String discordId, String minecraftId) {
        config.bindData.put(discordId, minecraftId);
        write();
    }

    public boolean isBindPlayer(String discordId) {
        return config.bindData.containsKey(discordId);
    }
}