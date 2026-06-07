package studio.abos.mc.essence.entity;

import com.mojang.datafixers.util.Pair;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import studio.abos.mc.essence.attachment.EssenceAttachment;
import studio.abos.mc.essence.damage.ModDamageTypes;
import studio.abos.mc.essence.move.EssenceMove;
import studio.abos.mc.essence.move.EssenceMoves;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;

public class EssenceBallEntity extends EssenceEntity {

    @Getter
    @Setter
    protected boolean retracting;

    public EssenceBallEntity(final Level level) {
        super(ModEntities.ESSENCE_BALL.value(), level);
    }

    @Override
    public EssenceMove getMoveType() {
        return EssenceMoves.BALL;
    }

    @Override
    protected void tickPhysics() {
        // get one tick further in theory
        final BlockHitResult blockHitResult = level().clipIncludingBorder(new ClipContext(
                position(), position().add(getDeltaMovement()), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
        // what have we hit?
        final ArrayList<EntityHitResult> entitiesHit = new ArrayList<>(this.findHitEntities(position(), blockHitResult.getLocation()));
        // what have we hit first?
        entitiesHit.sort(Comparator.comparingDouble(c -> position().distanceToSqr(c.getEntity().position())));
        final EntityHitResult firstEntityHit = entitiesHit.isEmpty() ? null : entitiesHit.getFirst();
        // get one tick further in practice
        final HitResult hitResult = Objects.requireNonNullElse(firstEntityHit, blockHitResult);
        final Vec3 nextLocation = hitResult.getLocation();
        if (firstEntityHit == null || firstEntityHit.getEntity() != getOwner() || !retracting) {
            setPos(nextLocation);
            // hit something
            if (isAlive() && !isRemoved() && hitResult.getType() != HitResult.Type.MISS) {
                onHit(hitResult);
            }
        }
        else {
            // we don't go hitting the user, even in theory
            retracting = false;
            setDeltaMovement(Vec3.ZERO);
        }
    }

    protected Collection<EntityHitResult> findHitEntities(final Vec3 from, final Vec3 to) {
        return ProjectileUtil.getManyEntityHitResult(level(), this, from, to,
                getBoundingBox().expandTowards(getDeltaMovement()).inflate(1.0), this::canHitEntity, false
        );
    }

    @Override
    public boolean canHitEntity(final Entity entity) {
        if (entity != null) {
            if (!entity.canBeHitByProjectile()) {
                return false;
            }
            if (retracting && entity == getOwner()) {
                return true;
            }
        }
        return super.canHitEntity(entity);
    }

    protected void onHit(final HitResult hitResult) {
        if (hitResult.getType() == HitResult.Type.MISS) {
            return;
        }
        setDeltaMovement(Vec3.ZERO);
        if (hitResult.getType() == HitResult.Type.ENTITY) {
            final Entity hitEntity = ((EntityHitResult) hitResult).getEntity();
            if (!level().isClientSide() && hitEntity != getOwner()) {
                if (hitEntity.hurtServer((ServerLevel)level(),
                        new DamageSource(ModDamageTypes.essence(level()), getOwner()), 1f)
                ) {
                    discard();
                }
            }
        }
    }

    @Override
    public void retract() {
        final LivingEntity owner = getOwner();
        if (owner == null) {
            return;
        }
        retracting = true;
        setRot(owner.getYHeadRot() - 180, -owner.getXRot());
        final double yRad = Math.toRadians(getYRot());
        final double xRad = Math.toRadians(getXRot());
        final float xd = -Mth.sin(yRad) * Mth.cos(xRad);
        final float yd = -Mth.sin(xRad);
        final float zd = Mth.cos(yRad) * Mth.cos(xRad);
        setDeltaMovement(xd, yd, zd);
    }

    public static void summonAndShoot(final LivingEntity user) {
        if (user == null || !EssenceAttachment.of(user).attemptMove(EssenceMoves.BALL)) {
            return;
        }
        final Level level = user.level();
        final EssenceBallEntity ball = new EssenceBallEntity(level);
        ball.setOwner(user);
        EssenceAttachment.of(user).getActiveMoves().add(Pair.of(EssenceMoves.BALL, ball));
        ball.setRot(user.getYHeadRot(), user.getXRot());
        final double yRad = Math.toRadians(ball.getYRot());
        final double xRad = Math.toRadians(ball.getXRot());
        final float xd = -Mth.sin(yRad) * Mth.cos(xRad);
        final float yd = -Mth.sin(xRad);
        final float zd = Mth.cos(yRad) * Mth.cos(xRad);
        ball.setPos(user.getEyePosition().add(xd, yd, zd));
        ball.setDeltaMovement(xd, yd, zd);
        level.addFreshEntity(ball);
    }
}
