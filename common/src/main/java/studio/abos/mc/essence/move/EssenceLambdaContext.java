package studio.abos.mc.essence.move;

import lombok.NonNull;
import net.minecraft.world.entity.LivingEntity;

import java.util.function.Consumer;

public class EssenceLambdaContext implements EssenceContext {

    boolean removed;
    final Consumer<LivingEntity> removalLambda;

    public EssenceLambdaContext(final Consumer<LivingEntity> removalLambda) {
        this.removalLambda = removalLambda;
    }

    @Override
    public void removeContext(final @NonNull LivingEntity user) {
        removalLambda.accept(user);
        removed = true;
    }

    @Override
    public boolean isContextRemoved() {
        return removed;
    }
}
