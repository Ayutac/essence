package studio.abos.mc.essence.entity;

import net.minecraft.world.level.Level;

public class EssenceBall extends EssenceEntity {

    public EssenceBall(final Level level) {
        super(ModEntities.ESSENCE_BALL.value(), level);
    }

    @Override
    protected void tickPhysics() {

    }
}
