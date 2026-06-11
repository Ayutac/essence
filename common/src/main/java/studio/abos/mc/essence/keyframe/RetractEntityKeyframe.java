package studio.abos.mc.essence.keyframe;

import lombok.NonNull;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.EntityHitResult;
import studio.abos.mc.essence.entity.EssenceEntity;
import studio.abos.mc.essence.move.EssenceContext;

public record RetractEntityKeyframe(float speed) implements Keyframe {

    @Override
    public EssenceContext run(final @NonNull LivingEntity user, final EssenceContext context) {
        EssenceContext newContext = context;
        if (!(newContext instanceof EssenceEntity)) {
            final EntityHitResult lookedAtEssence = EssenceEntity.getOwnedEssenceInLineOfSight(user);
            if (lookedAtEssence != null) {
                newContext = (EssenceEntity)lookedAtEssence.getEntity();
            }
        }
        if (newContext instanceof EssenceEntity entity) {
            entity.retract(speed);
        }
        return newContext;
    }

}
