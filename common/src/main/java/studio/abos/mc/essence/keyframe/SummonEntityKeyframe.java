package studio.abos.mc.essence.keyframe;

import lombok.NonNull;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import studio.abos.mc.essence.entity.EssenceEntity;
import studio.abos.mc.essence.move.EssenceContext;

import java.util.function.Function;

public record SummonEntityKeyframe<T extends EssenceEntity>(Function<Level, T> constructor, Position position) implements Keyframe {

    @Override
    public EssenceEntity run(final @NonNull LivingEntity user, final EssenceContext context) {
        EssenceEntity entity = constructor.apply(user.level());
        entity.setOwner(user);
        entity.setYRot(user.getYHeadRot());
        entity.setXRot(user.getXRot());
        entity.setPos(position.calculate(user));
        user.level().addFreshEntity(entity);
        return entity;
    }

}
