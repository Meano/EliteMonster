package net.meano.Giant;

import org.bukkit.craftbukkit.v1_14_R1.CraftServer;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftMonster;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Giant;

import net.minecraft.server.v1_14_R1.EntityMonster;

public class CraftGiant extends CraftMonster implements Giant {
	public CraftGiant(CraftServer server, EntityMonster entity) {
		super(server, entity);
	}

	public EntityMonster getHandle() {
		return (EntityMonster) this.entity;
	}

	public String toString() {
		return "CraftGiant";
	}

	public EntityType getType() {
		return EntityType.GIANT;
	}
}
