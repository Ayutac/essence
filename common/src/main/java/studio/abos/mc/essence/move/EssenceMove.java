package studio.abos.mc.essence.move;

import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import studio.abos.mc.essence.entity.EssenceEntity;
import studio.abos.mc.essence.entity.SegmentedEssence;

public interface EssenceMove {

    Identifier getId();

    float getNeededWillpower();

    void perform(LivingEntity user);

    static void dissipate(LivingEntity user) {
        final Vec3 start = user.getEyePosition();
        final EntityHitResult lookedAtEssence = ProjectileUtil.getEntityHitResult(user, start,
                start.add(user.getLookAngle().scale(EssenceEntity.EFFECTIVE_RANGE)),
                user.getBoundingBox().inflate(EssenceEntity.EFFECTIVE_RANGE_SQR),
                entity -> entity instanceof EssenceEntity essence
                        && essence.getOwner() == user,
                EssenceEntity.EFFECTIVE_RANGE_SQR
        );
        if (lookedAtEssence != null) {
            if (lookedAtEssence.getEntity() instanceof SegmentedEssence part && part.getOrigin() != null) {
                part.getOrigin().discard();
            }
            else {
                lookedAtEssence.getEntity().discard();
            }
        }
    }

}
