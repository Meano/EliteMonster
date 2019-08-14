package net.meano.Giant;

import net.minecraft.server.v1_14_R1.*;

public class EntityGiant extends EntityMonster {
	protected EntityGiant(EntityTypes<? extends EntityMonster> var0, World var1) {
		super(var0, var1);
		// TODO Auto-generated constructor stub
	}
	protected static final IAttribute a = new AttributeRanged(null, "giant.spawnReinforcements", 0.0D, 0.0D, 1.0D).a("Spawn Reinforcements Chance");
	//private float bC = -1.0F;
	//private float bD;

	/*public EntityGiant(World world) {
		super(world);
		setSize(1.0F, 4.0F);
	}

	protected void r() {
		goalSelector.a(0, new PathfinderGoalFloat(this));
		goalSelector.a(2, new PathfinderGoalMeleeAttack(this, 1.0D, false));
		goalSelector.a(5, new PathfinderGoalMoveTowardsRestriction(this, 1.0D));
		goalSelector.a(7, new PathfinderGoalRandomStroll(this, 1.0D));
		goalSelector.a(8, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
		goalSelector.a(9, new PathfinderGoalLookAtPlayer(this, EntityCow.class, 8.0F));
		goalSelector.a(10, new PathfinderGoalLookAtPlayer(this, EntityVillager.class, 8.0F));
		goalSelector.a(8, new PathfinderGoalRandomLookaround(this));
		goalSelector.a(6, new PathfinderGoalMoveThroughVillage(this, 1.0D, false));
		targetSelector.a(1, new PathfinderGoalHurtByTarget(this, true, new Class[] { EntityPigZombie.class }));
		targetSelector.a(2, new PathfinderGoalNearestAttackableTarget<EntityHuman>(this, EntityHuman.class, true));
		targetSelector.a(5, new PathfinderGoalNearestAttackableTarget<EntityCow>(this, EntityCow.class, true));
		if (world.spigotConfig.zombieAggressiveTowardsVillager) {
			targetSelector.a(3,
					new PathfinderGoalNearestAttackableTarget<EntityVillager>(this, EntityVillager.class, false));
		}
		targetSelector.a(3,
				new PathfinderGoalNearestAttackableTarget<EntityIronGolem>(this, EntityIronGolem.class, true));
	}

	protected void initAttributes() {
		super.initAttributes();
		getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(35.0D);
		getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.23000000417232513D);
		getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(5.0D);
		getAttributeInstance(GenericAttributes.g).setValue(2.0D);
		getAttributeMap().b(a).setValue(random.nextDouble() * 0.1D);
	}

	protected int getExpValue(EntityHuman entityhuman) {
		return super.getExpValue(entityhuman);
	}

	protected SoundEffect G() {
		return SoundEffects.hh;
	}

	protected SoundEffect bR() {
		return SoundEffects.hp;
	}

	protected SoundEffect bS() {
		return SoundEffects.hl;
	}

	protected void a(BlockPosition blockposition, Block block) {
		a(SoundEffects.hv, 0.15F, 1.0F);
	}

	public EnumMonsterType getMonsterType() {
		return EnumMonsterType.UNDEAD;
	}

	protected MinecraftKey J() {
		return LootTables.t;
	}

	protected void a(DifficultyDamageScaler difficultydamagescaler) {
		super.a(difficultydamagescaler);
		if (random.nextFloat() < (world.getDifficulty() == EnumDifficulty.HARD ? 0.05F : 0.01F)) {
			int i = random.nextInt(3);
			if (i == 0) {
				setSlot(EnumItemSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
			} else {
				setSlot(EnumItemSlot.MAINHAND, new ItemStack(Items.IRON_SHOVEL));
			}
		}
	}

	public float getHeadHeight() {
		return 4.0F;
	}

	protected void b(int i) {
		removeEffect(MobEffects.WEAKNESS);
		addEffect(new MobEffect(MobEffects.INCREASE_DAMAGE, i, Math.min(world.getDifficulty().a() - 1, 0)));
		world.broadcastEntityEffect(this, (byte) 16);
	}

	public final void setSize(float f, float f1) {
		boolean flag = (bC > 0.0F) && (bD > 0.0F);
		bC = f;
		bD = f1;
		if (!flag) {
			a(1.0F);
		}
	}

	protected final void a(float f) {
		super.setSize(bC * f, bD * f);
	}

	public void die(DamageSource damagesource) {
		a(new ItemStack(Items.SKULL, 1, 2), 0.0F);

		super.die(damagesource);
	}*/
}