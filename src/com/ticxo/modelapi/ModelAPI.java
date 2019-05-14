package com.ticxo.modelapi;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.ticxo.modelapi.api.ModelManager;
import com.ticxo.modelapi.api.modeling.ModelBase;
import com.ticxo.modelapi.api.modeling.ModelEntity;

public class ModelAPI extends JavaPlugin {

	public static JavaPlugin plugin;
	
	private static PluginManager pm;
	private static ConsoleCommandSender cs; 

	public void onEnable() {

		plugin = this;
		pm = Bukkit.getServer().getPluginManager();
		cs = Bukkit.getServer().getConsoleSender();
		
		pm.registerEvents(new Event(), this);
		
		cs.sendMessage("[ModelAPI] Enabled!");
		
		ModelManager.renderModel();
		
	}

	public void onDisable() {

		cs.sendMessage("[ModelAPI] Disabled!");

	}
	
	public static void registerPlugin(JavaPlugin plugin, String texture) {
		ModelManager.registerPlugin(plugin, texture);
	}
	
	public static void registerModel(ModelBase model) {
		ModelManager.registerModel(model);
	}
	
	public static void registerModels(List<ModelBase> models) {
		for(ModelBase model : models) {
			registerModel(model);
		}
	}
	
	public static ModelEntity spawnEntity(Entity ent, String pluginId, String name, boolean addition) {
		return new ModelEntity(ent, pluginId + ":" + name, addition);
	}
	
	public static ModelEntity spawnEntity(Location loc, EntityType type, String pluginId, String name, boolean addition) {
		return spawnEntity(loc.getWorld().spawnEntity(loc, type), pluginId, name, addition);
	}
	
	public static ModelEntity spawnEntity(Entity ent, JavaPlugin plugin, String name, boolean addition) {
		return spawnEntity(ent, plugin.getDescription().getName(), name, addition);
	}
	
	public static ModelEntity spawnEntity(Location loc, EntityType type, JavaPlugin plugin, String name, boolean addition) {
		return spawnEntity(loc, type, plugin.getDescription().getName(), name, addition);
	}

}