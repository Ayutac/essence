package studio.abos.mc.essence.entity;

import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.NotNull;

public abstract class EssenceEntity extends Entity {

    protected EssenceEntity(final EntityType<?> type, final Level level) {
        super(type, level);
    }

    @Override
    public boolean hurtServer(final @NotNull ServerLevel serverLevel, final @NotNull DamageSource damageSource, final float v) {
        return false;
    }

    @Override
    protected void defineSynchedData(final SynchedEntityData.@NotNull Builder builder) {

    }

    @Override
    protected void readAdditionalSaveData(final @NotNull ValueInput valueInput) {

    }

    @Override
    protected void addAdditionalSaveData(final @NotNull ValueOutput valueOutput) {

    }
}
