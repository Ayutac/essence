package studio.abos.mc.essence.entity;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import studio.abos.mc.essence.damage.ModDamageTypes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;

public class EssenceBallEntity extends EssenceEntity {

    public EssenceBallEntity(final Level level) {
        super(ModEntities.ESSENCE_BALL.value(), level);
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
        setPos(nextLocation);
        // hit something
        if (isAlive() && !isRemoved() && hitResult.getType() != HitResult.Type.MISS) {
            onHit(hitResult);
        }
    }

    protected Collection<EntityHitResult> findHitEntities(final Vec3 from, final Vec3 to) {
        return ProjectileUtil.getManyEntityHitResult(level(), this, from, to,
                getBoundingBox().expandTowards(getDeltaMovement()).inflate(1.0), this::canHitEntity, false
        );
    }

    protected void onHit(final HitResult hitResult) {
        if (hitResult.getType() == HitResult.Type.MISS) {
            return;
        }
        setDeltaMovement(Vec3.ZERO);
        if (hitResult.getType() == HitResult.Type.ENTITY) {
            if (!level().isClientSide()) {
                if (((EntityHitResult) hitResult).getEntity().hurtServer((ServerLevel)level(),
                        new DamageSource(ModDamageTypes.essence(level()), getOwner()), 1f)
                ) {
                    discard();
                }
            }
        }
    }

    public static void summonAndShoot(final LivingEntity user) {
        if (user == null) {
            return;
        }
        final Level level = user.level();
        final EssenceBallEntity ball = new EssenceBallEntity(level);
        ball.setOwner(user);
        ball.setRot(user.getYRot(), user.getXRot());
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
