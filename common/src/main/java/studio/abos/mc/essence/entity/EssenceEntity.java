package studio.abos.mc.essence.entity;

import com.mojang.serialization.Codec;
import lombok.NonNull;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityReference;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;
import studio.abos.mc.essence.move.EssenceContext;

public abstract class EssenceEntity extends Entity implements TraceableEntity, EssenceContext {

    public static final int EFFECTIVE_RANGE = 64;
    public static final int EFFECTIVE_RANGE_SQR = EFFECTIVE_RANGE * EFFECTIVE_RANGE;

    private EntityReference<@NonNull Entity> owner;
    protected int tickCount;

    protected EssenceEntity(final EntityType<?> type, final Level level) {
        super(type, level);
    }

    @Override
    public void tick() {
        super.tick();
        final LivingEntity owner = getOwner();
        if (owner != null && (owner.isDeadOrDying() || owner.distanceToSqr(this) >= EFFECTIVE_RANGE_SQR)) {
            discard();
            return;
        }
        tickPhysics();
        tickCount++;
    }

    protected abstract void tickPhysics();

    public void setOwner(final LivingEntity owner) {
        this.owner = EntityReference.of(owner);
    }

    public LivingEntity getOwner() {
        return (LivingEntity)EntityReference.getEntity(owner, level());
    }

    public abstract void retract(float speed);

    @Override
    public void removeContext(final @NonNull LivingEntity user) {
        discard();
    }

    @Override
    public boolean isContextRemoved() {
        return isRemoved();
    }

    public boolean canHitEntity(final Entity entity) {
        if (entity == null) {
            return false;
        } else {
            Entity owner = this.getOwner();
            return owner == null || owner != entity;
        }
    }

    @Override
    public boolean canBeHitByProjectile() {
        return true;
    }

    @Override
    public boolean canCollideWith(final @NonNull Entity entity) {
        return true;
    }

    @Override
    public boolean canBeCollidedWith(final @Nullable Entity other) {
        return true;
    }

    @Override
    public boolean hurtServer(final @NonNull ServerLevel serverLevel, final @NonNull DamageSource damageSource, final float v) {
        return false;
    }

    @Override
    protected void defineSynchedData(final @NonNull SynchedEntityData.Builder builder) {
        // left empty on purpose
    }

    @Override
    protected void readAdditionalSaveData(final @NonNull ValueInput input) {
        owner = EntityReference.read(input, "Owner");
        tickCount = input.read("TickCount", Codec.INT).orElse(0);
    }

    @Override
    protected void addAdditionalSaveData(final @NonNull ValueOutput output) {
        EntityReference.store(owner, output, "Owner");
        output.store("TickCount", Codec.INT, tickCount);
    }

    public static EntityHitResult getOwnedEssenceInLineOfSight(final LivingEntity user) {
        final Vec3 start = user.getEyePosition();
        return ProjectileUtil.getEntityHitResult(user, start,
                start.add(user.getLookAngle().scale(EssenceEntity.EFFECTIVE_RANGE)),
                user.getBoundingBox().inflate(EssenceEntity.EFFECTIVE_RANGE_SQR),
                entity -> entity instanceof EssenceEntity essence
                        && essence.getOwner() == user,
                EssenceEntity.EFFECTIVE_RANGE_SQR
        );
    }

}
