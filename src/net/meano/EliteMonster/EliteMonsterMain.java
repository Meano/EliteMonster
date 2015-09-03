package net.meano.EliteMonster;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import net.meano.Giant.EntityGiant;
import net.minecraft.server.v1_8_R3.BiomeBase;
import net.minecraft.server.v1_8_R3.BiomeBase.BiomeMeta;
import net.minecraft.server.v1_8_R3.EntityGiantZombie;
import net.minecraft.server.v1_8_R3.EntityTypes;
import net.steeleyes.catacombs.Catacombs;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class EliteMonsterMain extends JavaPlugin {
	public Catacombs CatacombsPlugin;
	public void onEnable(){
		//Log开始记录
		getLogger().info("EliteMonster 0.1,by Meano. 正在载入.");
		if(getServer().getPluginManager().getPlugin("Catacombs") != null ){
			CatacombsPlugin = (Catacombs) getServer().getPluginManager().getPlugin("Catacombs");
			getLogger().info("已找到Catacombs插件，地牢刷怪控制可控！");
		}else{
			getLogger().info("未找到Catacombs插件，地牢刷怪不可控！");
		}
		//RegisterOnTheWorld();
		RegisterGiant();
		PluginManager PM = Bukkit.getServer().getPluginManager();
		PM.registerEvents(new EliteMonsterListeners(this), this);
	}
	@SuppressWarnings("unchecked")
	public void RegisterOnTheWorld(){
		try {
			for (Field field : net.minecraft.server.v1_8_R3.BiomeBase.class.getDeclaredFields()) {
				if ((field.getType().getSimpleName().equals(net.minecraft.server.v1_8_R3.BiomeBase.class.getSimpleName())) && (field.get(null) != null)) {
					for (Field list : net.minecraft.server.v1_8_R3.BiomeBase.class.getDeclaredFields()) {
						if ((list.getType().getSimpleName().equals(List.class.getSimpleName())) && (
								(field.get(null) == net.minecraft.server.v1_8_R3.BiomeBase.PLAINS) || 
								(field.get(null) == net.minecraft.server.v1_8_R3.BiomeBase.ICE_PLAINS) || 
								(field.get(null) == net.minecraft.server.v1_8_R3.BiomeBase.STONE_BEACH) || 
								(field.get(null) == net.minecraft.server.v1_8_R3.BiomeBase.EXTREME_HILLS) || 
								(field.get(null) == net.minecraft.server.v1_8_R3.BiomeBase.EXTREME_HILLS_PLUS) || (
								(field.get(null) == net.minecraft.server.v1_8_R3.BiomeBase.DESERT) && (list.getName().equals("au"))))) {
							list.setAccessible(true);
							List<BiomeMeta> metaList = (List<BiomeMeta>)list.get(field.get(null));
							metaList.add(new BiomeBase.BiomeMeta(EntityGiantZombie.class, 10, 1, 4));
						}
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	public void RegisterGiant(){
		Bukkit.getLogger().info(getServer().getVersion());
		 if (getServer().getVersion().contains("(MC: 1.8.8)")) {
			 try {
				 Field fieldc = EntityTypes.class.getDeclaredField("c");
				 Field fielde = EntityTypes.class.getDeclaredField("e");
				 fieldc.setAccessible(true);
				 fielde.setAccessible(true);
				 Map<?, ?> c = (Map<?, ?>)fieldc.get(null);
				 Map<?, ?> e = (Map<?, ?>)fielde.get(null);
				 c.remove("Giant");
				 e.remove(53);
				 Method a = EntityTypes.class.getDeclaredMethod("a", new Class[] { Class.class, String.class, Integer.TYPE });
				 a.setAccessible(true);
				 a.invoke(null, new Object[] { EntityGiant.class, "Giant", 53 });
			 } catch (Exception e) {
				 e.printStackTrace();
			 }
		 }
	}
}

