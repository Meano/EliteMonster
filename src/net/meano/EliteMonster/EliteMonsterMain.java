package net.meano.EliteMonster;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import net.steeleyes.catacombs.Catacombs;

public class EliteMonsterMain extends JavaPlugin {
	public Catacombs CatacombsPlugin;

	public void onEnable() {
		if (getServer().getPluginManager().getPlugin("Catacombs") != null) {
			CatacombsPlugin = (Catacombs) getServer().getPluginManager().getPlugin("Catacombs");
			getLogger().info("已找到Catacombs插件，地牢刷怪控制可控！");
		} else {
			getLogger().info("未找到Catacombs插件！");
		}

		PluginManager PM = Bukkit.getServer().getPluginManager();
		PM.registerEvents(new EliteMonsterListeners(this), this);
	}

}
