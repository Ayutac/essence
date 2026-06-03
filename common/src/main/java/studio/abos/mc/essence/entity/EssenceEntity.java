package studio.abos.mc.essence.entity;

import lombok.NonNull;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityReference;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public abstract class EssenceEntity extends Entity {

    private EntityReference<@NonNull Entity> owner;

    protected EssenceEntity(final EntityType<?> type, final Level level) {
        super(type, level);
    }

    @Override
    public void tick() {
        super.tick();
        final LivingEntity owner = getOwner();
        if (owner != null && owner.isDeadOrDying()) {
            discard();
            return;
        }
        tickPhysics();
    }

    protected abstract void tickPhysics();

    public void setOwner(final LivingEntity owner) {
        this.owner = EntityReference.of(owner);
    }

    public LivingEntity getOwner() {
        return (LivingEntity)EntityReference.getEntity(owner, level());
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
    }

    @Override
    protected void addAdditionalSaveData(final @NonNull ValueOutput output) {
        EntityReference.store(owner, output, "Owner");
    }
}
