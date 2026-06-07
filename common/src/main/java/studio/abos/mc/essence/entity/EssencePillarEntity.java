package studio.abos.mc.essence.entity;

import com.mojang.datafixers.util.Pair;
import lombok.NonNull;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import studio.abos.mc.essence.attachment.EssenceAttachment;
import studio.abos.mc.essence.move.EssenceMove;
import studio.abos.mc.essence.move.EssenceMoves;

public class EssencePillarEntity extends EssenceEntity {

    public static final double STEP_SIZE = 0.5;

    public EssencePillarEntity(final Level level) {
        super(ModEntities.ESSENCE_PILLAR.value(), level);
    }

    @Override
    public EssenceMove getMoveType() {
        return EssenceMoves.PILLAR;
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
    public void retract() {
        final Vec3 position = position();
        final LivingEntity owner = getOwner();
        final EssenceBallEntity ball = new EssenceBallEntity(level());
        if (owner != null) {
            ball.setOwner(owner);
            EssenceAttachment.of(owner).getActiveMoves().add(Pair.of(EssenceMoves.BALL, ball));
        }
        ball.setPos(position);
        level().addFreshEntity(ball);
        ball.retract();
        discard();
    }

    public static void summon(final LivingEntity user) {
        if (user == null || !EssenceAttachment.of(user).attemptMove(EssenceMoves.PILLAR)) {
            return;
        }
        final Level level = user.level();
        final EssencePillarEntity pillar = new EssencePillarEntity(level);
        pillar.setOwner(user);
        EssenceAttachment.of(user).getActiveMoves().add(Pair.of(EssenceMoves.PILLAR, pillar));
        pillar.setPos(user.position());
        pillar.setYRot(user.getYRot());
        level.addFreshEntity(pillar);
    }

}
