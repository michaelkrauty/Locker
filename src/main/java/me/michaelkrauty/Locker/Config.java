package me.michaelkrauty.Locker;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.Set;

/**
 * Created on 6/25/2014.
 *
 * @author michaelkrauty
 */
public class Config {

	private File configFile = new File(Main.main.getDataFolder() + "/config.yml");
	private YamlConfiguration config = new YamlConfiguration();

	public Config() {
		checkFile();
		reload();
	}

	private void checkFile() {
		if (!configFile.exists()) {
			try {
				configFile.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void reload() {
		try {
			config.load(configFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void save() {
		try {
			config.save(configFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void set(String path, String str) {
		config.set(path, str);
		save();
		reload();
	}

	public String getString(String path) {
		return config.getString(path);
	}

	public Set<String> getKeys(String path) {
		return config.getConfigurationSection(path).getKeys(true);
	}

	public void delete(String path) {
		config.set(path, null);
		save();
		reload();
	}
}
