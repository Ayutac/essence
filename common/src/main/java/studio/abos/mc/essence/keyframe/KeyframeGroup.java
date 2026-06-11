package studio.abos.mc.essence.keyframe;

import lombok.NonNull;
import net.minecraft.world.entity.LivingEntity;
import studio.abos.mc.essence.move.EssenceContext;

/**
 * For an array of keyframes to be executed in the same tick.
 * @param keyframes
 */
public record KeyframeGroup(Keyframe... keyframes) implements Keyframe {
    @Override
    public EssenceContext run(final @NonNull LivingEntity user, final EssenceContext context) {
        if (keyframes == null) {
            return context;
        }
        EssenceContext currentContext = context;
        for (final Keyframe keyframe : keyframes) {
            currentContext = keyframe.run(user, currentContext);
        }
        return currentContext;
    }
}
