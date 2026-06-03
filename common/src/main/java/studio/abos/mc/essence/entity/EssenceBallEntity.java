package studio.abos.mc.essence.entity;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class EssenceBallEntity extends EssenceEntity {

    public EssenceBallEntity(final Level level) {
        super(ModEntities.ESSENCE_BALL.value(), level);
    }

    @Override
    protected void tickPhysics() {
    }

    public static void summonAndShoot(final LivingEntity user) {
        if (user == null) {
            return;
        }
        final Level level = user.level();
        final EssenceBallEntity ball = new EssenceBallEntity(level);
        ball.setOwner(user);
        ball.setPos(user.getEyePosition());
        level.addFreshEntity(ball);
    }
}
