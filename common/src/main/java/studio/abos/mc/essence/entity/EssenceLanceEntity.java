package studio.abos.mc.essence.entity;

import com.mojang.serialization.Codec;
import lombok.NonNull;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityReference;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;
import studio.abos.mc.essence.damage.ModDamageTypes;

import java.util.Collection;

public class EssenceLanceEntity extends EssenceEntity implements SegmentedEssence {

    public static final double STEP_SIZE = 0.5;

    public EssenceLanceEntity(final Level level) {
        super(ModEntities.ESSENCE_LANCE.value(), level);
    }

    protected int segment;
    private EntityReference<@NonNull Entity> origin;

    public void setOrigin(final EssenceLanceEntity origin) {
        this.origin = EntityReference.of(origin);
    }

    public EssenceLanceEntity getOrigin() {
        return (EssenceLanceEntity)EntityReference.getEntity(origin, level());
    }

    @Override
    public void tick() {
        final EssenceLanceEntity origin = getOrigin();
        if (origin != null && (!origin.isAlive() || origin.isRemoved())) {
            discard();
        }
        super.tick();
    }

    @Override
    protected void tickPhysics() {
        if (tickCount == 0) {
            // maybe hit entities
            final Collection<EntityHitResult> entitiesHit = this.findHitEntities(getBoundingBox().getMinPosition(), getBoundingBox().getMaxPosition());
            for (final EntityHitResult hitResult : entitiesHit) {
                if (isAlive() && !isRemoved() && !(hitResult.getEntity() instanceof Player player && (player.isCreative() || player.isSpectator()))) {
                    onHit(hitResult);
                }
            }
        }
        if (tickCount == 1 && segment < 19) {
            // maybe add new segment
            final EssenceLanceEntity lance = new EssenceLanceEntity(level());
            lance.setOwner(getOwner());
            lance.setOrigin(getOrigin());
            lance.segment = segment + 1;
            lance.setRot(getYRot(), getXRot());
            final double yRad = Math.toRadians(lance.getYRot());
            final double xRad = Math.toRadians(lance.getXRot());
            final float xd = -Mth.sin(yRad) * Mth.cos(xRad);
            final float yd = -Mth.sin(xRad);
            final float zd = Mth.cos(yRad) * Mth.cos(xRad);
            lance.setPos(position().add(new Vec3(xd, yd, zd).scale(STEP_SIZE)));
            level().addFreshEntity(lance);
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
        if (hitResult.getType() == HitResult.Type.ENTITY) {
            if (!level().isClientSide()) {
                ((EntityHitResult) hitResult).getEntity().hurtServer((ServerLevel)level(),
                        new DamageSource(ModDamageTypes.essence(level()), getOwner()), 5f);
            }
        }
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
    protected void readAdditionalSaveData(final @NonNull ValueInput input) {
        super.readAdditionalSaveData(input);
        segment = input.read("Segment", Codec.INT).orElse(0);
        origin = EntityReference.read(input, "Origin");
    }

    @Override
    protected void addAdditionalSaveData(final @NonNull ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.store("Segment", Codec.INT, segment);
        EntityReference.store(origin, output, "Origin");
    }

    public static void summon(final LivingEntity user) {
        if (user == null) {
            return;
        }
        final Level level = user.level();
        final EssenceLanceEntity lance = new EssenceLanceEntity(level);
        lance.setOwner(user);
        lance.setOrigin(lance);
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
