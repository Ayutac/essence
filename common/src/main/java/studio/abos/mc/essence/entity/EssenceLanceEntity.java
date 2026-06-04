package studio.abos.mc.essence.entity;

import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import studio.abos.mc.essence.damage.ModDamageTypes;

import java.util.Collection;

public class EssenceLanceEntity extends EssenceEntity {

    public static final double STEP_SIZE = 0.5;

    public EssenceLanceEntity(final Level level) {
        super(ModEntities.ESSENCE_LANCE.value(), level);
    }

    @Override
    protected AABB makeBoundingBox(final Vec3 position) {
        final double yRad = Math.toRadians(getYRot());
        final double xRad = Math.toRadians(getXRot());
        final float xd = -Mth.sin(yRad) * Mth.cos(xRad);
        final float yd = -Mth.sin(xRad);
        final float zd = Mth.cos(yRad) * Mth.cos(xRad);
        return super.makeBoundingBox(position).expandTowards(new Vec3(xd, yd, zd).scale(STEP_SIZE * Mth.clamp(1 + tickCount, 1, 20)));
    }

    protected int tickCount;

    @Override
    protected void tickPhysics() {
        setBoundingBox(makeBoundingBox());
        tickCount++;
//        // get one tick further in theory
//        final BlockHitResult blockHitResult = level().clipIncludingBorder(new ClipContext(
//                position(), position().add(getDeltaMovement()), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
//        // what have we hit?
//        final ArrayList<EntityHitResult> entitiesHit = new ArrayList<>(this.findHitEntities(position(), blockHitResult.getLocation()));
//        // what have we hit first?
//        entitiesHit.sort(Comparator.comparingDouble(c -> position().distanceToSqr(c.getEntity().position())));
//        final EntityHitResult firstEntityHit = entitiesHit.isEmpty() ? null : entitiesHit.getFirst();
//        // get one tick further in practice
//        final HitResult hitResult = Objects.requireNonNullElse(firstEntityHit, blockHitResult);
//        final Vec3 nextLocation = hitResult.getLocation();
//        setPos(nextLocation);
//        // hit something
//        if (isAlive() && hitResult.getType() != HitResult.Type.MISS) {
//            onHit(hitResult);
//        }
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
                        new DamageSource(level().registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(ModDamageTypes.ESSENCE),
                                getOwner()),
                        1f)
                ) {
                    discard();
                }
            }
        }
    }

    public static void summon(final LivingEntity user) {
        if (user == null) {
            return;
        }
        final Level level = user.level();
        final EssenceLanceEntity lance = new EssenceLanceEntity(level);
        lance.setOwner(user);
        lance.setRot(user.getYRot(), user.getXRot());
        final double yRad = Math.toRadians(lance.getYRot());
        final double xRad = Math.toRadians(lance.getXRot());
        final float xd = -Mth.sin(yRad) * Mth.cos(xRad);
        final float yd = -Mth.sin(xRad);
        final float zd = Mth.cos(yRad) * Mth.cos(xRad);
        lance.setPos(user.getEyePosition().add(xd, yd, zd));
        level.addFreshEntity(lance);
    }
}
