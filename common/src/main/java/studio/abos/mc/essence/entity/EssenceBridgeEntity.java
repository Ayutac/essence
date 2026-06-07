package studio.abos.mc.essence.entity;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import lombok.NonNull;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityReference;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import studio.abos.mc.essence.attachment.EssenceAttachment;
import studio.abos.mc.essence.move.EssenceMove;
import studio.abos.mc.essence.move.EssenceMoves;

public class EssenceBridgeEntity extends EssenceEntity implements SegmentedEssence {

    public static final double STEP_SIZE = 0.5;

    protected int segment;
    private EntityReference<@NonNull Entity> origin;

    public EssenceBridgeEntity(final Level level) {
        super(ModEntities.ESSENCE_BRIDGE.value(), level);
    }

    @Override
    public EssenceMove getMoveType() {
        return EssenceMoves.BRIDGE;
    }

    public void setOrigin(final EssenceBridgeEntity origin) {
        this.origin = EntityReference.of(origin);
    }

    public EssenceBridgeEntity getOrigin() {
        return (EssenceBridgeEntity)EntityReference.getEntity(origin, level());
    }

    @Override
    public void tick() {
        final EssenceBridgeEntity origin = getOrigin();
        if (!level().isClientSide() && (origin == null || !origin.isAlive() || origin.isRemoved())) {
            discard();
        }
        super.tick();
    }

    @Override
    protected void tickPhysics() {
        if (tickCount == 1 && segment < 19) {
            // maybe add new segment
            final EssenceBridgeEntity bridge = new EssenceBridgeEntity(level());
            bridge.setOwner(getOwner());
            bridge.setOrigin(getOrigin());
            bridge.segment = segment + 1;
            bridge.setRot(getYRot(), getXRot());
            final double yRad = Math.toRadians(bridge.getYRot());
            final double xRad = Math.toRadians(bridge.getXRot());
            final float xd = -Mth.sin(yRad) * Mth.cos(xRad);
            final float yd = -Mth.sin(xRad);
            final float zd = Mth.cos(yRad) * Mth.cos(xRad);
            bridge.setPos(position().add(new Vec3(xd, yd, zd).scale(STEP_SIZE)));
            level().addFreshEntity(bridge);
        }
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
        final EssenceBridgeEntity origin = getOrigin();
        if (origin != null && this != origin) {
            origin.discard();
        }
        else {
            discard();
        }
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
        if (user == null || !EssenceAttachment.of(user).attemptMove(EssenceMoves.BRIDGE)) {
            return;
        }
        final Level level = user.level();
        final EssenceBridgeEntity bridge = new EssenceBridgeEntity(level);
        bridge.setOwner(user);
        EssenceAttachment.of(user).getActiveMoves().add(Pair.of(EssenceMoves.BRIDGE, bridge));
        bridge.setOrigin(bridge);
        bridge.setRot(user.getYRot(), user.getXRot());
        final double yRad = Math.toRadians(bridge.getYRot());
        final double xRad = Math.toRadians(bridge.getXRot());
        final float xd = -Mth.sin(yRad) * Mth.cos(xRad);
        final float yd = -Mth.sin(xRad);
        final float zd = Mth.cos(yRad) * Mth.cos(xRad);
        bridge.setPos(user.position().add(xd, yd, zd));
        level.addFreshEntity(bridge);
    }
}
