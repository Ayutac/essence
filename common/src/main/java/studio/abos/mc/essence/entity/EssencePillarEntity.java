package studio.abos.mc.essence.entity;

import lombok.NonNull;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

public class EssencePillarEntity extends EssenceEntity {

    public static final double STEP_SIZE = 0.5;

    public EssencePillarEntity(final Level level) {
        super(ModEntities.ESSENCE_PILLAR.value(), level);
    }

    @NonNull
    @Override
    protected AABB makeBoundingBox(final @NonNull Vec3 position) {
        final AABB centered = new AABB(-0.5, 0d, -0.5, 0.5, Mth.clamp(STEP_SIZE * (1 + tickCount), 0.5, 10), 0.5);
        return centered.move(position);
    }

    @Override
    protected void tickPhysics() {
        setBoundingBox(makeBoundingBox());
//        if (tickCount <= 20) {
        level().getEntities(this, getBoundingBox(), EntitySelector.NO_SPECTATORS.and(ent -> !(ent instanceof EssencePillarEntity)))
                .forEach(ent -> ent.setDeltaMovement(getDeltaMovement().add(0d, STEP_SIZE, 0d)));
//        }
    }

    @Override
    public boolean canCollideWith(final @NonNull Entity entity) {
        return true;
    }

    @Override
    public boolean canBeCollidedWith(final @Nullable Entity other) {
        return true;
    }

    public static void summon(final LivingEntity user) {
        if (user == null) {
            return;
        }
        final Level level = user.level();
        final EssencePillarEntity pillar = new EssencePillarEntity(level);
        pillar.setOwner(user);
        pillar.setPos(user.position());
        pillar.setYRot(user.getYRot());
        level.addFreshEntity(pillar);
    }

}
