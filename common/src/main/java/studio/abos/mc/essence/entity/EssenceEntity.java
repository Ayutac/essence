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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public abstract class EssenceEntity extends Entity implements TraceableEntity {

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
}
