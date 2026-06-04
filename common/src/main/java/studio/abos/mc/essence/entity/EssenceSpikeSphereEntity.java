package studio.abos.mc.essence.entity;

import lombok.NonNull;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;
import studio.abos.mc.essence.damage.ModDamageTypes;

import java.util.Collection;

public class EssenceSpikeSphereEntity extends EssenceEntity {

    public static final int SPIKES_TICK = 5;

    protected EssenceSpikeSphereEntity(final Level level) {
        super(ModEntities.ESSENCE_SPIKE_SPHERE.value(), level);
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
    public void tick() {
        LivingEntity owner = getOwner();
        if (owner != null && owner.distanceToSqr(this) >= 3) {
            discard();
        }
        super.tick();
    }

    @Override
    protected void tickPhysics() {
        if (spikesOut() && tickCount % 3 == 0) {
            // maybe hit entities
            final Collection<Entity> entitiesHit = level().getEntities(this, getBoundingBox().inflate(1d),
                    ent -> ent.isAlive() && !ent.isRemoved() && ent != getOwner() && !(ent instanceof Player player && (player.isSpectator() || player.isCreative())));
            for (final Entity hitEntity : entitiesHit) {
                onHit(hitEntity);
            }
        }
    }

    public boolean spikesOut() {
        return tickCount >= SPIKES_TICK;
    }

    protected void onHit(final Entity entity) {
        if (entity == null) {
            return;
        }
        if (!level().isClientSide()) {
            entity.hurtServer((ServerLevel)level(),
                    new DamageSource(ModDamageTypes.essence(level()), getOwner()), 8f);
        }
    }

    public static void summon(final LivingEntity user) {
        if (user == null) {
            return;
        }
        final Level level = user.level();
        final EssenceSpikeSphereEntity spikeSphere = new EssenceSpikeSphereEntity(level);
        spikeSphere.setOwner(user);
        spikeSphere.setPos(user.position());
        spikeSphere.setYRot(user.getYRot());
        level.addFreshEntity(spikeSphere);
    }

}
