package net.meano.EliteMonster;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
//import org.bukkit.craftbukkit.v1_9_R1.CraftWorld;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.MagmaCube;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
//import net.minecraft.server.v1_9_R1.MathHelper;
//import net.minecraft.server.v1_9_R1.World;

public class EliteMonsterListeners implements Listener {
	EliteMonsterMain EMM;
	int random;
	// 刷怪笼控制
	public Date ZombieNow;
	public int ZombieCount;
	public Date SkeletonNow;
	public int SkeletonCount;
	public Date BlazeNow;
	public int BlazeCount;
	public long IronManControl;

	public EliteMonsterListeners(EliteMonsterMain GetPlugin) {
		EMM = GetPlugin;
		IronManControl = System.currentTimeMillis();
	}

	// 加速效果
	public PotionEffect Speed(int Duration, int Level) {
		return new PotionEffect(PotionEffectType.SPEED, Duration, Level);
	}

	// 抗火效果
	public PotionEffect FireResistance(int Duration, int Level) {
		return new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Duration, Level);
	}

	// 隐身效果
	public PotionEffect Invisible(int Duration, int Level) {
		return new PotionEffect(PotionEffectType.INVISIBILITY, Duration, Level);
	}

	// 高跳效果
	public PotionEffect Jump(int Duration, int Level) {
		return new PotionEffect(PotionEffectType.JUMP, Duration, Level);
	}

	// 伤害增加
	public PotionEffect IncreaseDamage(int Duration, int Level) {
		return new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Duration, Level);
	}

	// 怪物受伤事件
	@EventHandler(priority = EventPriority.LOW)
	public void onEntityDamage(EntityDamageEvent event) {
		Entity DamageEntity = event.getEntity();
		if (DamageEntity instanceof Monster) {
			// Bukkit.broadcastMessage("Cause: "+ event.getCause() + "Type: " +
			// event.getEntityType().toString() + "Damage: " + event.getDamage());
			if (event.getCause().equals(DamageCause.FALL) && event.getDamage() > 9) {
				DamageEntity.setMetadata("isFall", new FixedMetadataValue(EMM, 1));
				// Creeper creeper = (Creeper) DamageEntity;
				// creeper.setPowered(true);
			}
		}
	}

	// 怪物死亡事件
	@EventHandler(priority = EventPriority.LOW)
	public void onEntityDeath(EntityDeathEvent event) {
		final LivingEntity DeadEntity = event.getEntity();
		boolean NotInCat = true;
		if (EMM.CatacombsPlugin != null) {
			if (EMM.CatacombsPlugin.getDungeons().getDungeon(DeadEntity.getLocation().getBlock()) != null) {
				NotInCat = false;
			}
		}
		boolean isUnNormalDead = (DeadEntity.getKiller() == null) && (DeadEntity instanceof Monster)
				&& (!(DeadEntity instanceof Zombie)) && (!(DeadEntity instanceof Skeleton));
		if ((DeadEntity.hasMetadata("isFall") || isUnNormalDead && NotInCat)) {
			/*EMM.getLogger().info("刷怪塔掉落物缩减。 X:" + DeadEntity.getLocation().getBlockX() + " Z:" + DeadEntity.getLocation().getBlockZ());
			int FallExpRate = (int) (Math.random() * 150);
			if (FallExpRate > 20) {
				List<ItemStack> drops = event.getDrops();
				for (ItemStack d : drops) {
					if (d.getType().equals(Material.ROTTEN_FLESH) || d.getType().equals(Material.BONE))
						d.setAmount(1);
					else
						d.setType(Material.AIR);
				}
				if (event.getDroppedExp() > 0)
					event.setDroppedExp(1);
			}
			if (!DeadEntity.hasMetadata("isSpawner"))
				return;*/
			if (DeadEntity.isCustomNameVisible()) {
				event.setDroppedExp(1);
				List<ItemStack> drops = event.getDrops();
				for (ItemStack d : drops) {
					d.setType(Material.AIR);
				}
			}
		}
		String KillerName = null;
		String KillerAmor = "";
		if (DeadEntity.getKiller() != null && DeadEntity.getKiller() instanceof Player) {
			if (DeadEntity.isCustomNameVisible()) {
				if (DeadEntity.getKiller().getDisplayName() != "")
					KillerName = DeadEntity.getKiller().getDisplayName();
				else
					KillerName = "[" + DeadEntity.getKiller().getName() + "]";
				if (DeadEntity.getKiller().getInventory().getItemInMainHand().getItemMeta().hasDisplayName()) {
					KillerAmor = new StringBuffer().append(ChatColor.RED).append(" 用传说中的").append(ChatColor.AQUA)
							.append(" [").append(DeadEntity.getKiller().getInventory().getItemInMainHand().getItemMeta()
									.getDisplayName())
							.append("]").toString();
				}
				int Exp = 15 + (int) (Math.random() * DeadEntity.getHealth()) / 2;
				Bukkit.broadcastMessage(new StringBuffer().append(DeadEntity.getCustomName()).append(ChatColor.RED)
						.append(" 被 ").append(ChatColor.YELLOW).append(KillerName).append(KillerAmor)
						.append(ChatColor.RED).append(" 杀死了！掉落经验").append(Exp).append("点！").toString());
				event.setDroppedExp(Exp);
			}
//			else if (DeadEntity instanceof EnderDragon) {
//				if (DeadEntity.getKiller().getDisplayName() != "")
//					KillerName = DeadEntity.getKiller().getDisplayName();
//				else
//					KillerName = "[" + DeadEntity.getKiller().getName() + "]";
//				if (DeadEntity.getKiller().getInventory().getItemInMainHand().getItemMeta().hasDisplayName()) {
//					KillerAmor = new StringBuffer()
//							.append(ChatColor.RED).append("用传说中的").append(ChatColor.AQUA).append(" [").append(DeadEntity
//									.getKiller().getInventory().getItemInMainHand().getItemMeta().getDisplayName())
//							.append("]").toString();
//				}
//				int Exp = event.getDroppedExp();
//				Bukkit.broadcastMessage(new StringBuffer().append(ChatColor.BOLD).append(ChatColor.LIGHT_PURPLE)
//						.append("末影龙").append(ChatColor.RED).append(" 被 ").append(ChatColor.YELLOW).append(KillerName)
//						.append(KillerAmor).append(ChatColor.RED).append(" 杀死了！掉落经验").append(Exp).append("点！")
//						.toString());
//				event.setDroppedExp(Exp);
//			} 
//			else if (DeadEntity instanceof Enderman) {
//				if (DeadEntity.getWorld().getName().equals("world_the_end")) {
//					if (Math.abs(DeadEntity.getLocation().getBlockX()) > 150
//							|| Math.abs(DeadEntity.getLocation().getBlockY()) > 150) {
//						int Exp = (int) (Math.random() * 2);
//						event.setDroppedExp(Exp);
//						List<ItemStack> drops = event.getDrops();
//						int Endermanrate = 100;
//						random = 1 + (int) (Math.random() * (double) ((Endermanrate - 1) + 1));
//						if (random >= 90) {
//						} else {
//							for (ItemStack d : drops) {
//								d.setType(Material.AIR);
//							}
//						}
//					}
//				}
//			}
		}	
		
//		if (DeadEntity.hasMetadata("isSpawner") && NotInCat) {
//			int SpawnerExpRate = (int) (Math.random() * 150);
//			// Bukkit.broadcastMessage("SpawnerExpRate"+SpawnerExpRate);
//			if (DeadEntity instanceof Blaze) {
//				if (SpawnerExpRate > 90) {
//					List<ItemStack> drops = event.getDrops();
//					for (ItemStack d : drops) {
//						d.setType(Material.AIR);
//					}
//					if (event.getDroppedExp() > 0)
//						event.setDroppedExp(1);
//				} else {
//					event.setDroppedExp(6);
//				}
//			} else if (DeadEntity instanceof Zombie) {
//				if (SpawnerExpRate > 20) {
//					List<ItemStack> drops = event.getDrops();
//					for (ItemStack d : drops) {
//						if (!d.getType().equals(Material.ROTTEN_FLESH))
//							d.setType(Material.AIR);
//					}
//					if (event.getDroppedExp() > 0)
//						event.setDroppedExp(1);
//				}
//				if (SpawnerExpRate < 2) {
//					if (DeadEntity.getKiller() != null) {
//						DeadEntity.getKiller().sendMessage("刷怪笼愤怒了！你有3秒的逃离时间！加油！");
//						EMM.getLogger().info(DeadEntity.getKiller().toString() + "愤怒了刷怪笼");
//					}
//					Bukkit.getScheduler().scheduleSyncDelayedTask(EMM, new Runnable() {
//						public void run() {
//							Location DeadLocation = DeadEntity.getLocation();
//							if (DeadLocation.getBlock().getType().equals(Material.WATER))
//								DeadLocation.getBlock().setType(Material.AIR);
//							DeadEntity.getWorld().createExplosion(DeadLocation, 2);
//							DeadEntity.getWorld().createExplosion(DeadLocation.getX(), DeadLocation.getY(),
//									DeadLocation.getZ(), 5, false, false);
//						}
//					}, 3 * 20L);
//				}
//			} else if (DeadEntity instanceof Skeleton) {
//				if (SpawnerExpRate > 20) {
//					List<ItemStack> drops = event.getDrops();
//					for (ItemStack d : drops) {
//						d.setType(Material.AIR);
//					}
//					if (event.getDroppedExp() > 0)
//						event.setDroppedExp(1);
//				}
//				if (SpawnerExpRate < 2) {
//					if (DeadEntity.getKiller() != null) {
//						DeadEntity.getKiller().sendMessage("刷怪笼愤怒了！你有3秒的逃离时间！加油！");
//						EMM.getLogger().info(DeadEntity.getKiller().toString() + "愤怒了刷怪笼");
//					}
//					Bukkit.getScheduler().scheduleSyncDelayedTask(EMM, new Runnable() {
//						public void run() {
//							Location DeadLocation = DeadEntity.getLocation();
//							if (DeadLocation.getBlock().getType().equals(Material.WATER))
//								DeadLocation.getBlock().setType(Material.AIR);
//							DeadEntity.getWorld().createExplosion(DeadLocation, 2);
//							DeadEntity.getWorld().createExplosion(DeadLocation.getX(), DeadLocation.getY(),
//									DeadLocation.getZ(), 5, false, false);
//						}
//					}, 3 * 20L);
//				}
//			} else if (DeadEntity instanceof CaveSpider) {
//				if (SpawnerExpRate > 20) {
//					List<ItemStack> drops = event.getDrops();
//					for (ItemStack d : drops) {
//						d.setType(Material.AIR);
//					}
//					if (event.getDroppedExp() > 0)
//						event.setDroppedExp(1);
//				}
//				if (SpawnerExpRate < 2) {
//					if (DeadEntity.getKiller() != null) {
//						DeadEntity.getKiller().sendMessage("刷怪笼愤怒了！你有3秒的逃离时间！加油！");
//						EMM.getLogger().info(DeadEntity.getKiller().toString() + "愤怒了刷怪笼");
//					}
//					Bukkit.getScheduler().scheduleSyncDelayedTask(EMM, new Runnable() {
//						public void run() {
//							Location DeadLocation = DeadEntity.getLocation();
//							if (DeadLocation.getBlock().getType().equals(Material.WATER))
//								DeadLocation.getBlock().setType(Material.AIR);
//							DeadEntity.getWorld().createExplosion(DeadLocation, 2);
//							DeadEntity.getWorld().createExplosion(DeadLocation.getX(), DeadLocation.getY(),
//									DeadLocation.getZ(), 5, false, false);
//						}
//					}, 3 * 20L);
//				}
//			}
//		}
	}

	// 巨鬼
	/*
	 * public void ZombieGiantGhost(LivingEntity GiantGhost){ Giant GiantZombie;
	 * Location GiantLocation = GiantGhost.getLocation();
	 * 
	 * //GiantZombie= (Giant) GiantGhost.getWorld().spawnEntity(GiantLocation,
	 * EntityType.GIANT); GiantGhost.remove(); //CreatureSpawnEvent e = new
	 * CreatureSpawnEvent(GiantZombie,SpawnReason.NATURAL);
	 * //Bukkit.getPluginManager().callEvent(e); double x = GiantLocation.getX();
	 * double y = GiantLocation.getY(); double z = GiantLocation.getZ(); float pitch
	 * = GiantLocation.getPitch(); float yaw = GiantLocation.getYaw();
	 * net.minecraft.server.v1_9_R1.Entity newGiant = null; //newGiant = new
	 * EntityGiant(((CraftWorld)GiantGhost.getWorld()).getHandle());
	 * newGiant.setLocation(x, y, z, pitch, yaw); //World world =
	 * ((CraftWorld)GiantGhost.getWorld()).getHandle(); int i =
	 * MathHelper.floor(newGiant.locX / 16.0D); int j =
	 * MathHelper.floor(newGiant.locZ / 16.0D); // world.getChunkAt(i,
	 * j).a(newGiant); // world.entityList.add(newGiant); // try { // Method b =
	 * World.class.getDeclaredMethod("b", new Class[] {
	 * net.minecraft.server.v1_9_R1.Entity.class }); // b.setAccessible(true); //
	 * b.invoke(world, new Object[] { (net.minecraft.server.v1_9_R1.Entity)newGiant
	 * }); // } catch (Exception e) { // e.printStackTrace(); // }
	 * //world.b(newGiant); //if(newGiant.) GiantZombie = new
	 * CraftGiant(newGiant.world.getServer(),(EntityGiant) newGiant);
	 * GiantZombie.addPotionEffect(FireResistance(35000,1), false); //抗火
	 * GiantZombie.addPotionEffect(Speed(35000,3),false); //三倍加速
	 * GiantZombie.addPotionEffect(IncreaseDamage(35000,3),false); //三倍攻击
	 * GiantZombie.addPotionEffect(Jump(35000,5),false); //5倍跳跃
	 * GiantZombie.setCustomName((new
	 * StringBuilder()).append(ChatColor.BOLD).append(ChatColor.GREEN).append("巨鬼").
	 * toString()); //巨鬼名字 GiantZombie.setCustomNameVisible(true); //名字显示
	 * GiantZombie.setCanPickupItems(true); //可拾取掉落物
	 * GiantZombie.getEquipment().setHelmet(new ItemStack(Material.IRON_HELMET, 1));
	 * GiantZombie.getEquipment().setHelmetDropChance(0.2F);
	 * GiantZombie.getEquipment().setChestplate(new
	 * ItemStack(Material.DIAMOND_CHESTPLATE, 1)); //钻石胸甲
	 * GiantZombie.getEquipment().setChestplateDropChance(0.15F); //胸甲掉落15%
	 * GiantZombie.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(60D); //血量50 GiantZombie.setHealth(60D); //血量50 }
	 */
	
	// 钻石矿工
	public void ZombieDiamondMiner(LivingEntity Miner) {
		ItemStack DiamondBlock = new ItemStack(Material.DIAMOND_BLOCK, 1);
		Miner.addPotionEffect(FireResistance(35000, 1), false);												// 抗火
		Miner.addPotionEffect(Speed(35000, 2), false);														// 两倍加速
		Miner.addPotionEffect(IncreaseDamage(35000, 2), false);												// 一倍攻击
		((Zombie) Miner).setBaby(false);																	// 不能产生小僵尸形态
		// ((Zombie)Miner).setVillagerProfession(null);														// 不能产生村民僵尸形态
		Miner.setCustomName((new StringBuilder()).append(ChatColor.BLUE).append("钻石矿工").toString()); 	// 钻石矿工名字
		Miner.setCustomNameVisible(true);																	// 名字显示
		Miner.getEquipment().setItemInMainHand(new ItemStack(Material.DIAMOND_PICKAXE));					// 手持钻石镐
		Miner.getEquipment().setHelmet(DiamondBlock);														// 头顶钻石块
		Miner.getEquipment().setChestplate(null);															// 没有胸甲
		Miner.getEquipment().setLeggings(null);																// 没有裤甲
		Miner.getEquipment().setBoots(null);																// 没有鞋子
		Miner.getEquipment().setItemInMainHandDropChance(0.5F);												// 手中物品掉落概率5%
		Miner.getEquipment().setHelmetDropChance(0.25F);													// 钻石块掉落概率20%
		Miner.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(28D);								// 血量28
		Miner.setHealth(28D); // 28点血
	}

	// 狂战骷髅
	public void SkeletonCrazySkeleton(LivingEntity CrazySkeleton) {
		CrazySkeleton.addPotionEffect(Speed(35000, 2), false);												// 两倍加速
		CrazySkeleton.addPotionEffect(IncreaseDamage(35000, 3), false);										// 三倍攻击
		CrazySkeleton.setCustomName((new StringBuilder()).append(ChatColor.DARK_RED).append("狂战骷髅").toString()); // 狂战名字
		CrazySkeleton.setCustomNameVisible(true);															// 名字显示
		CrazySkeleton.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE, 1));		// 钻石胸甲
		CrazySkeleton.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS, 1));			// 钻石裤甲
		CrazySkeleton.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS, 1));					// 钻石鞋子
		CrazySkeleton.getEquipment().setHelmet(new ItemStack(Material.DIAMOND_HELMET, 1));				// 钻石头盔
		CrazySkeleton.getEquipment().setItemInMainHand(new ItemStack(Material.DIAMOND_SWORD, 1));			// 钻石剑
		CrazySkeleton.getEquipment().setChestplateDropChance(0.1F);											// 胸甲掉落10%
		CrazySkeleton.getEquipment().setLeggingsDropChance(0.1F);											// 裤甲掉落10%
		CrazySkeleton.getEquipment().setBootsDropChance(0.1F);												// 鞋子掉落10%
		CrazySkeleton.getEquipment().setItemInMainHandDropChance(0.1F);										// 手中掉落10%
		CrazySkeleton.getEquipment().setHelmetDropChance(0.1F);												// 头盔掉落10%
		CrazySkeleton.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(35D);						// 血量35
		CrazySkeleton.setHealth(35D);																		// 血量35
	}

	// 暗影死神
	public void SkeletonDarkAzrael(LivingEntity DarkAzrael) {
		// 死霸装
		ItemStack DeathChestplate = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
		LeatherArmorMeta DeathChestplateMeta = (LeatherArmorMeta) DeathChestplate.getItemMeta();
		DeathChestplateMeta.setColor(Color.BLACK);
		DeathChestplateMeta.addEnchant(Enchantment.THORNS, 2, true);
		DeathChestplateMeta.setDisplayName("死霸装");
		DeathChestplate.setItemMeta(DeathChestplateMeta);
		// 死神镰刀
		ItemStack StoneHoe = new ItemStack(Material.STONE_HOE, 1);
		ItemMeta StoneHoeMate = StoneHoe.getItemMeta();
		StoneHoeMate.addEnchant(Enchantment.FIRE_ASPECT, 2, true);
		StoneHoeMate.setDisplayName("死神镰刀");

		DarkAzrael.addPotionEffect(FireResistance(35000, 1), false);										// 抗火
		DarkAzrael.addPotionEffect(IncreaseDamage(35000, 4), false);										// 四倍攻击
		DarkAzrael.addPotionEffect(Invisible(35000, 1), false);												// 隐身
		DarkAzrael.addPotionEffect(Speed(35000, 2), false);													// 两倍速度
		DarkAzrael.setFireTicks(800000);																	// 着火
		DarkAzrael.setCustomName((new StringBuilder()).append(ChatColor.DARK_RED).append("暗影死神").toString()); // 暗影死神名字
		DarkAzrael.setCustomNameVisible(true);																// 名字显示
		DarkAzrael.getEquipment().setHelmet(new ItemStack(Material.SKELETON_SKULL));						// 头戴骷髅头
		DarkAzrael.getEquipment().setLeggings(null);														// 无裤装
		DarkAzrael.getEquipment().setBoots(null);															// 无鞋子
		DarkAzrael.getEquipment().setChestplate(DeathChestplate);											// 胸甲死霸装
		DarkAzrael.getEquipment().setItemInMainHand(StoneHoe);												// 手持死神镰刀
		DarkAzrael.getEquipment().setItemInMainHandDropChance(0.1F);										// 死神镰刀掉落概率10%
		DarkAzrael.getEquipment().setHelmetDropChance(0.1F);												// 死神头颅掉落概率10%
		DarkAzrael.getEquipment().setChestplateDropChance(0.1F);											// 死霸装掉落概率10%
		DarkAzrael.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(50D);							// 血量50
		DarkAzrael.setHealth(50D);																			// 血量50
	}

	// 恶魔骷髅
	public void SkeletonDemonSkeleton(LivingEntity DemonSkeleton) {
		DemonSkeleton.addPotionEffect(FireResistance(35000, 1), false); // 抗火
		DemonSkeleton.addPotionEffect(Speed(35000, 2), false); // 倍速
		DemonSkeleton.addPotionEffect(IncreaseDamage(35000, 3), false); // 三倍伤害
		DemonSkeleton.setCustomName((new StringBuilder()).append(ChatColor.DARK_BLUE).append("恶魔骷髅").toString()); // 恶魔骷髅名字
		DemonSkeleton.setCustomNameVisible(true); // 名字显示
		DemonSkeleton.getEquipment().setHelmet(new ItemStack(Material.CREEPER_HEAD)); // 头戴刷怪笼
		DemonSkeleton.getEquipment().setItemInMainHand(new ItemStack(Material.IRON_PICKAXE)); // 手持铁镐
		DemonSkeleton.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE)); // 铁胸甲
		DemonSkeleton.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS)); // 铁腿甲
		DemonSkeleton.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS)); // 铁鞋子
		DemonSkeleton.getEquipment().setHelmetDropChance(0.00F); // 头盔掉落率0
		DemonSkeleton.getEquipment().setItemInMainHandDropChance(0.1F); // 铁镐掉落率10%
		DemonSkeleton.getEquipment().setChestplateDropChance(0.05F); // 铁甲掉落率5%
		DemonSkeleton.getEquipment().setBootsDropChance(0.1F); // 铁鞋掉落率10%
		DemonSkeleton.getEquipment().setLeggingsDropChance(0.1F); // 铁裤甲掉落率10%
		DemonSkeleton.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(40D); // 血量40
		DemonSkeleton.setHealth(40D); // 血量40
	}

	// 狂暴史莱姆
	public void SlimeCrazySlime(LivingEntity CrazySlime) {
		CrazySlime.addPotionEffect(FireResistance(35000, 1), false);											// 抗火
		CrazySlime.addPotionEffect(IncreaseDamage(35000, 6), false);											// 6倍攻击增加
		CrazySlime.addPotionEffect(Speed(35000, 3), false);														// 三倍速度
		CrazySlime.addPotionEffect(Jump(35000, 4), false);														// 三倍高跳
		CrazySlime.setCustomName((new StringBuilder()).append(ChatColor.AQUA).append("狂暴史莱姆").toString());// 狂暴史莱姆名字
		CrazySlime.setCustomNameVisible(true);																	// 名字显示
		((Slime) CrazySlime).setSize(6);																		// 尺寸5
		CrazySlime.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(70D);								// 血量60
		CrazySlime.setHealth(70D);																				// 血量60
	}

	// 恐怖岩浆怪
	public void MagmaCubeBigMagmaCube(LivingEntity BigMagmaCube) {
		BigMagmaCube.addPotionEffect(FireResistance(35000, 1));
		BigMagmaCube.addPotionEffect(IncreaseDamage(35000, 6));
		BigMagmaCube.addPotionEffect(Speed(35000, 3));
		BigMagmaCube.addPotionEffect(Jump(35000, 4));
		BigMagmaCube.setCustomName((new StringBuilder()).append(ChatColor.RED).append("恐怖岩浆怪").toString());	// 狂暴史莱姆名字
		BigMagmaCube.setCustomNameVisible(true);																// 名字显示
		((MagmaCube) BigMagmaCube).setSize(6);
		BigMagmaCube.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(70D);
		BigMagmaCube.setHealth(70D);
	}

	// 烈焰之狼
	public void WolfFireWolf(LivingEntity FireWolf) {
		FireWolf.addPotionEffect(FireResistance(35000, 1), false);
		FireWolf.addPotionEffect(Speed(35000, 2), false);
		((Wolf) FireWolf).setBreed(false);
		((Wolf) FireWolf).setAngry(true);
		((Wolf) FireWolf).setCustomName((new StringBuilder()).append(ChatColor.DARK_RED).append("烈焰之狼").toString());
		((Wolf) FireWolf).setCustomNameVisible(true);
		((Wolf) FireWolf).setFireTicks(800000);
		((Wolf) FireWolf).setTamed(false);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onEntitySpawn(CreatureSpawnEvent event) {
		LivingEntity Spawned = event.getEntity();
		EntityType entityType = Spawned.getType();
		Random RandSpawn = new Random();
		boolean NotInCat = true;
		// 地牢插件检查
		if (EMM.CatacombsPlugin != null) {
			if (EMM.CatacombsPlugin.getDungeons().getDungeon(Spawned.getLocation().getBlock()) != null) {
				NotInCat = false;
			}
		}
		
		// 铁傀儡控制
//		if (event.getEntityType().equals(EntityType.IRON_GOLEM)) {
//			if (event.getSpawnReason() == SpawnReason.VILLAGE_DEFENSE
//					|| event.getSpawnReason() == SpawnReason.VILLAGE_INVASION) {
//				if (System.currentTimeMillis() - IronManControl > 1000 * 60 * 6) {
//					IronManControl = System.currentTimeMillis();
//				} else {
//					event.setCancelled(true);
//				}
//			}
//			return;
//		}
		
		// 刷怪笼控制
		if (event.getSpawnReason() == SpawnReason.SPAWNER && NotInCat) {
			Spawned.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(30D);
			Spawned.setHealth(30D);
			Spawned.setMetadata("isSpawner", new FixedMetadataValue(EMM, 1));
			Spawned.getEquipment().setChestplate(null);
			Spawned.getEquipment().setLeggings(null);
			Spawned.getEquipment().setBoots(null);
			Spawned.getEquipment().setHelmet(null);
			Spawned.getEquipment().setItemInMainHandDropChance(0.001F);
			int NearCount = 0;
			switch (entityType) {
				case ZOMBIE:
					for (Entity Near : Spawned.getNearbyEntities(8, 20, 8)) {
						if (Near.getType().equals(EntityType.ZOMBIE)) {
							NearCount++;
						}
					}
					if (NearCount > 10) {
						event.setCancelled(true);
					} else
						Spawned.addPotionEffect(Jump(35000, 10), false);
					break;
				case SKELETON:
					for (Entity Near : Spawned.getNearbyEntities(8, 20, 8)) {
						if (Near.getType().equals(EntityType.SKELETON)) {
							NearCount++;
						}
					}
					if (NearCount > 10) {
						event.setCancelled(true);
					} else
						Spawned.addPotionEffect(Jump(35000, 10), false);
					break;
				case BLAZE:
					if (BlazeNow == null) {
						BlazeNow = new Date();
						BlazeCount += 1;
					} else {
						long min = (new Date().getTime() - BlazeNow.getTime()) / 60000;
						if (min < 5) {
							if (BlazeCount < 10) {
								BlazeCount++;
							} else {
								Spawned.remove();
								return;
							}
						} else {
							BlazeNow = null;
							BlazeCount = 0;
						}
	
					}
					break;
				default:
					break;
			}
		} else if (Spawned instanceof Monster || Spawned instanceof Slime || Spawned instanceof Wolf) {
//			double Heigh = Spawned.getLocation().getY();
//			if (Heigh > 150) {
//				Heigh = Math.abs(200 - Heigh);
//				if (Bukkit.getOnlinePlayers().size() < 3) {
//					if ((int) ((Math.random() * (60 - Heigh)) + 1) == 1) {
//					} else {
//						Spawned.remove();
//						return;
//					}
//				} else {
//					if ((int) ((Math.random() * 4) + 1) == 1) {
//					} else {
//						Spawned.remove();
//						return;
//					}
//				}
//			}
			
			int Rate;
			if (NotInCat) {
				Rate = RandSpawn.nextInt(100);
			} else {
				Rate = RandSpawn.nextInt(18);
			}
			switch (entityType) {
				default:
					break;
				case SKELETON:
					if (Rate == 1) {
						SkeletonCrazySkeleton(Spawned); // 狂战骷髅
					} else if (Rate == 2) {
						SkeletonDarkAzrael(Spawned); // 暗影死神
					} else if (Rate == 3) {
						SkeletonDemonSkeleton(Spawned); // 恶魔骷髅
					}
					break;
				case SLIME:
					if (!event.getSpawnReason().equals(SpawnReason.SLIME_SPLIT))
						if (Rate == 1) {
							SlimeCrazySlime(Spawned); // 狂暴史莱姆
						}
					break;
				case ZOMBIE:
					if (Rate == 1) {
						ZombieDiamondMiner(Spawned); // 钻石矿工
						if (!NotInCat) {
							Spawned.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(39D);
							Spawned.setHealth(39D);
						}
					} else if (Rate == 2 && NotInCat) {
						// ZombieGiantGhost(Spawned); //巨鬼
					}
					break;
				case MAGMA_CUBE:
					if (!event.getSpawnReason().equals(SpawnReason.SLIME_SPLIT))
						if (Rate == 1) {
							MagmaCubeBigMagmaCube(Spawned); //
						}
					break;
				case WOLF:
					if (!NotInCat) {
						((Wolf) Spawned).setAngry(true);
					}
					if (Rate == 1) {
						WolfFireWolf(Spawned); // 烈焰之狼
					}
					break;
				case PIG_ZOMBIE:
//					if (Spawned.getWorld().getName().equals("world_nether")) {
//						if (Spawned.getLocation().getBlockY() > 127) {
//							Spawned.remove();
//						}
//					} else if (!NotInCat) {
						Spawned.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(35D);
						Spawned.setHealth(35D);
//					}
					break;
			/*
			 * case ENDERMAN: if (Spawned.getWorld().getName().equals("world_the_end")) { if
			 * (Math.abs(Spawned.getLocation().getBlockX()) > 150 ||
			 * Math.abs(Spawned.getLocation().getBlockY()) > 150) { int Endermanrate = 100;
			 * random = 1 + (int) (Math.random() * (double) ((Endermanrate - 1) + 1)); if
			 * (random >= 8) { Spawned.remove(); } else { ((Enderman)
			 * Spawned).getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(70D);
			 * ((Enderman) Spawned).setHealth(70D); } } } break;
			 */
			}
		}
	}
}
