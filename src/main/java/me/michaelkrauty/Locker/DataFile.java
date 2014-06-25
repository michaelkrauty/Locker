package me.michaelkrauty.Locker;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created on 6/24/2014.
 *
 * @author michaelkrauty
 */
public class DataFile {

	private File dataFile = new File(Main.main.getDataFolder() + "/data.yml");
	private YamlConfiguration data = new YamlConfiguration();

	public DataFile() {
		checkFile();
		reload();
	}

	private void checkFile() {
		if (!dataFile.exists()) {
			try {
				dataFile.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void reload() {
		try {
			data.load(dataFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void save() {
		try {
			data.save(dataFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void set(String path, String str) {
		data.set(path, str);
		save();
		reload();
	}

	public String getString(String path) {
		return data.getString(path);
	}

	public void delete(String path) {
		data.set(path, null);
		save();
		reload();
	}
}
