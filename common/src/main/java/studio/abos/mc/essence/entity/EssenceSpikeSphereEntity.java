package studio.abos.mc.essence.entity;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import studio.abos.mc.essence.attachment.EssenceAttachment;
import studio.abos.mc.essence.damage.ModDamageTypes;
import studio.abos.mc.essence.move.EssenceMove;
import studio.abos.mc.essence.move.EssenceMoveTypes;

import java.util.Collection;

public class EssenceSpikeSphereEntity extends EssenceEntity {

    public static final int SPIKES_TICK = 5;

    public EssenceSpikeSphereEntity(final Level level) {
        super(ModEntities.ESSENCE_SPIKE_SPHERE.value(), level);
    }

    @Override
    public void retract(float speed) {
        final Vec3 position = position();
        final LivingEntity owner = getOwner();
        final EssenceBallEntity ball = new EssenceBallEntity(level());
        if (owner != null) {
            ball.setOwner(owner);
            final EssenceMove move = new EssenceMove(EssenceMoveTypes.BALL, owner, true);
            move.setContext(ball);
            move.incrementTickCount();
            EssenceAttachment.of(owner).getActiveMoves().add(move);
        }
        ball.setPos(position);
        level().addFreshEntity(ball);
        ball.retract(speed);
        discard();
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

}
