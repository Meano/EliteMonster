package net.meano.Giant;
/*
import net.minecraft.server.v1_8_R2.DamageSource;
import net.minecraft.server.v1_8_R2.EntityCreeper;
import net.minecraft.server.v1_8_R2.EntityHuman;
import net.minecraft.server.v1_8_R2.EntityIronGolem;
import net.minecraft.server.v1_8_R2.EntityMonster;
import net.minecraft.server.v1_8_R2.EntityPigZombie;
import net.minecraft.server.v1_8_R2.EntityVillager;
import net.minecraft.server.v1_8_R2.Navigation;
import net.minecraft.server.v1_8_R2.PathfinderGoalFloat;
import net.minecraft.server.v1_8_R2.PathfinderGoalHurtByTarget;
import net.minecraft.server.v1_8_R2.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_8_R2.PathfinderGoalMeleeAttack;
import net.minecraft.server.v1_8_R2.PathfinderGoalMoveThroughVillage;
import net.minecraft.server.v1_8_R2.PathfinderGoalMoveTowardsRestriction;
import net.minecraft.server.v1_8_R2.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.v1_8_R2.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_8_R2.PathfinderGoalRandomStroll;
import net.minecraft.server.v1_8_R2.World;

public class EntityGiant extends EntityMonster{
	public EntityGiant(World world) {
		super(world);
		// TODO Auto-generated constructor stub
		((Navigation)getNavigation()).b(true);
		this.goalSelector.a(0, new PathfinderGoalFloat(this));
		this.goalSelector.a(2, new PathfinderGoalMeleeAttack(this, EntityHuman.class, 1.0D, false));
		this.goalSelector.a(5, new PathfinderGoalMoveTowardsRestriction(this, 1.0D));
		this.goalSelector.a(7, new PathfinderGoalRandomStroll(this, 1.0D));
		this.goalSelector.a(8, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 25.0F));
		this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));
		setSize(this.width, this.length*6);
		
		this.goalSelector.a(4, new PathfinderGoalMeleeAttack(this, EntityVillager.class, 1.0D, true));
		this.goalSelector.a(4, new PathfinderGoalMeleeAttack(this, 1.0D, true));
		this.goalSelector.a(6, new PathfinderGoalMoveThroughVillage(this, 1.0D, false));
		this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, true, new Class[] { EntityPigZombie.class }));
		this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget<EntityHuman>(this, EntityHuman.class, true));
		this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget<EntityVillager>(this, EntityVillager.class, false));
		this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget<EntityIronGolem>(this, EntityIronGolem.class, true));
	}*/

import org.bukkit.event.entity.EntityCombustByEntityEvent;
//import net.minecraft.server.v1_8_R3.*;
import net.minecraft.server.v1_8_R3.*;

public class EntityGiant extends EntityMonster
{
	//protected static final IAttribute a = new AttributeRanged(null, "giant.spawnReinforcements", 0.0D, 0.0D, 1.0D).a("Spawn Reinforcements Chance");
	//private static final UUID b = UUID.fromString("A9766B59-9566-4402-BC1F-2EE2A276D839");
	private final PathfinderGoalBreakDoor bm = new PathfinderGoalBreakDoor(this);
	private boolean bo = false;

	public EntityGiant(World world) {
		super(world);
		((Navigation)getNavigation()).b(true);
		this.goalSelector.a(0, new PathfinderGoalFloat(this));
		this.goalSelector.a(2, new PathfinderGoalMeleeAttack(this, EntityHuman.class, 1.0D, false));
		this.goalSelector.a(5, new PathfinderGoalMoveTowardsRestriction(this, 1.0D));
		this.goalSelector.a(7, new PathfinderGoalRandomStroll(this, 1.0D));
		this.goalSelector.a(8, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
		this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));
		n();
		setSize(0.6F, (float) (1.95*3));
	}

	protected void n() {
		if (this.world.spigotConfig.zombieAggressiveTowardsVillager) this.goalSelector.a(4, new PathfinderGoalMeleeAttack(this, EntityVillager.class, 1.0D, true));
		this.goalSelector.a(4, new PathfinderGoalMeleeAttack(this, EntityIronGolem.class, 1.0D, true));
		this.goalSelector.a(6, new PathfinderGoalMoveThroughVillage(this, 1.0D, false));
		this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, true, new Class[] { EntityPigZombie.class }));
		this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget<>(this, EntityHuman.class, true));
		if (this.world.spigotConfig.zombieAggressiveTowardsVillager) this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget<>(this, EntityVillager.class, false));
		this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget<>(this, EntityIronGolem.class, true));
	}

 	/*protected void initAttributes() {
 		super.initAttributes();
 		getAttributeInstance(GenericAttributes.b).setValue(35.0D);
 		getAttributeInstance(GenericAttributes.d).setValue(0.2300000041723251D);
    		getAttributeInstance(GenericAttributes.e).setValue(3.0D);
    		//getAttributeMap().b(a).setValue(this.random.nextDouble() * 0.1000000014901168D);
 	}*/

  /*protected void h() {
    super.h();
    getDataWatcher().a(12, Byte.valueOf((byte)0));
    getDataWatcher().a(13, Byte.valueOf((byte)0));
    getDataWatcher().a(14, Byte.valueOf((byte)0));
  }*/

 	public int br() {
 		int i = super.br() + 2;

 		if (i > 20) {
 			i = 20;
 		}

 		return i;
 	}

 	public boolean cn() {
 		return this.bo;
 	}

 	public void a(boolean flag) {
 		if (this.bo != flag) {
 			this.bo = flag;
 			if (flag)
 				this.goalSelector.a(1, this.bm);
 			else
 				this.goalSelector.a(this.bm);
 		}
 	}

 	protected int getExpValue(EntityHuman entityhuman) {
 		if (isBaby()) {
 			this.b_ = ((int)(this.b_ * 2.5F));
 		}
 		return super.getExpValue(entityhuman);
 	}

 	/*public boolean damageEntity(DamageSource damagesource, float f) {
 		if (super.damageEntity(damagesource, f)) {
 			EntityLiving entityliving = getGoalTarget();

 			if ((entityliving == null) && ((damagesource.getEntity() instanceof EntityLiving))) {
 				entityliving = (EntityLiving)damagesource.getEntity();
 			}

 			if ((entityliving != null) && (this.world.getDifficulty() == EnumDifficulty.HARD) && (this.random.nextFloat() < getAttributeInstance(a).getValue())) {
 				int i = MathHelper.floor(this.locX);
 				int j = MathHelper.floor(this.locY);
 				int k = MathHelper.floor(this.locZ);
 				EntityGiant entityzombie = new EntityGiant(this.world);

 				for (int l = 0; l < 50; l++) {
 					int i1 = i + MathHelper.nextInt(this.random, 7, 40) * MathHelper.nextInt(this.random, -1, 1);
 					int j1 = j + MathHelper.nextInt(this.random, 7, 40) * MathHelper.nextInt(this.random, -1, 1);
 					int k1 = k + MathHelper.nextInt(this.random, 7, 40) * MathHelper.nextInt(this.random, -1, 1);

 					if ((World.a(this.world, new BlockPosition(i1, j1 - 1, k1))) && (this.world.getLightLevel(new BlockPosition(i1, j1, k1)) < 10)) {
 						entityzombie.setPosition(i1, j1, k1);
 						if ((!this.world.isPlayerNearby(i1, j1, k1, 7.0D)) && (this.world.a(entityzombie.getBoundingBox(), entityzombie)) && (this.world.getCubes(entityzombie, entityzombie.getBoundingBox()).isEmpty()) && (!this.world.containsLiquid(entityzombie.getBoundingBox()))) {
 							this.world.addEntity(entityzombie, SpawnReason.REINFORCEMENTS);
 							entityzombie.setGoalTarget(entityliving,  TargetReason.REINFORCEMENT_TARGET, true);
 							entityzombie.prepare(this.world.E(new BlockPosition(entityzombie)), null);
 							getAttributeInstance(a).b(new AttributeModifier("Zombie reinforcement caller charge", -0.0500000007450581D, 0));
 							entityzombie.getAttributeInstance(a).b(new AttributeModifier("Zombie reinforcement callee charge", -0.0500000007450581D, 0));
 							break;
 						}
 					}
 				}
 			}

 			return true;
 		}
 		return false;
 	}*/

 	public boolean r(Entity entity) {
 		boolean flag = super.r(entity);
 		if (flag) {
 			int i = this.world.getDifficulty().a();
 			if ((bA() == null) && (isBurning()) && (this.random.nextFloat() < i * 0.3F))
 			{
 				EntityCombustByEntityEvent event = new EntityCombustByEntityEvent(getBukkitEntity(), entity.getBukkitEntity(), 2 * i);
 				this.world.getServer().getPluginManager().callEvent(event);
 				if (!event.isCancelled()) {
 					entity.setOnFire(event.getDuration());
 				}
 			}
 		}
 		return flag;
 	}

 	/*protected String z() {
 		return "mob.giant.say";
 	}*/

 	/*protected String bo() {
 		return "mob.giant.hurt";
 	}*/

 	/*protected String bp() {
 		return "mob.giant.death";
 	}*/

 	protected void a(BlockPosition blockposition, Block block) {
 		makeSound("mob.zombie.step", 0.15F, 1.0F);
 	}

 	protected Item getLoot() {
 		return Items.PUMPKIN_PIE;
 	}

 	public EnumMonsterType getMonsterType() {
 		return EnumMonsterType.UNDEAD;
 	}

 	protected void getRareDrop() {
 		switch (this.random.nextInt(3)) {
 		case 0:
 			a(Items.GOLD_INGOT, 2);
 			break;
 		case 1:
 			a(Items.DIAMOND, 2);
 			break;
 		case 2:
 			a(Items.GOLDEN_CARROT, 2);
 		}
 	}

 	/*protected void a(DifficultyDamageScaler difficultydamagescaler)
 	{
 		super.a(difficultydamagescaler);
 		if (this.random.nextFloat() < (this.world.getDifficulty() == EnumDifficulty.HARD ? 0.05F : 0.01F)) {
 			int i = this.random.nextInt(3);

 			if (i == 0)
 				setEquipment(0, new ItemStack(Items.IRON_SWORD));
 			else
 				setEquipment(0, new ItemStack(Items.IRON_SHOVEL));
 		}
 	}*/

 	public float getHeadHeight()
 	{
 		float f = (float) (1.95*6F);
 		return f;
 	}


 	/*protected boolean a(ItemStack itemstack) {
 		return (itemstack.getItem() == Items.EGG) && (isBaby()) && (au()) ? false : super.a(itemstack);
 	}*/
}
