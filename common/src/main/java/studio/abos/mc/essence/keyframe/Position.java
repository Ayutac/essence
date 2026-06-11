package studio.abos.mc.essence.keyframe;

import lombok.NonNull;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

import java.util.function.Function;

public enum Position {
    FRONT(living -> {
        final double yRad = Math.toRadians(living.getYHeadRot());
        final double xRad = Math.toRadians(living.getXRot());
        final float xd = -Mth.sin(yRad) * Mth.cos(xRad);
        final float yd = -Mth.sin(xRad);
        final float zd = Mth.cos(yRad) * Mth.cos(xRad);
        return living.getEyePosition().add(xd, yd, zd);
    }),
    FRONT_LOW(living -> {
        final double yRad = Math.toRadians(living.getYHeadRot());
        final double xRad = Math.toRadians(living.getXRot());
        final float xd = -Mth.sin(yRad) * Mth.cos(xRad);
        final float yd = -Mth.sin(xRad);
        final float zd = Mth.cos(yRad) * Mth.cos(xRad);
        return living.position().add(xd, yd, zd);
    }),
    BELOW(LivingEntity::position);

    @NonNull
    final Function<LivingEntity, Vec3> function;

    Position(final @NonNull Function<LivingEntity, Vec3> function) {
        this.function = function;
    }

    public Vec3 calculate(final LivingEntity living) {
        return function.apply(living);
    }

}
