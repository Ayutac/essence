package studio.abos.mc.essence.entity;

import net.minecraft.world.level.Level;

public class EssenceBallEntity extends EssenceEntity {

    public EssenceBallEntity(final Level level) {
        super(ModEntities.ESSENCE_BALL.value(), level);
    }

    @Override
    protected void tickPhysics() {

    }
}
