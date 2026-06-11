package studio.abos.mc.essence.keyframe;

import lombok.NonNull;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import studio.abos.mc.essence.entity.EssenceEntity;
import studio.abos.mc.essence.move.EssenceContext;

public record ShootEntityKeyframe(float speed) implements Keyframe {

    @Override
    public EssenceContext run(final @NonNull LivingEntity user, final EssenceContext context) {
        if (context instanceof final EssenceEntity entity) {
            final double yRad = Math.toRadians(entity.getYRot());
            final double xRad = Math.toRadians(entity.getXRot());
            final float xd = -Mth.sin(yRad) * Mth.cos(xRad);
            final float yd = -Mth.sin(xRad);
            final float zd = Mth.cos(yRad) * Mth.cos(xRad);
            entity.setDeltaMovement(new Vec3(xd, yd, zd).scale(speed));
        }
        return context;
    }

}
