package studio.abos.mc.essence.move;

import lombok.Getter;
import lombok.NonNull;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import studio.abos.mc.essence.Essence;
import studio.abos.mc.essence.entity.EssenceBallEntity;

import java.util.function.Consumer;

public enum EssenceMoves implements EssenceMove {

    BALL("ball", EssenceBallEntity::summonAndShoot);

    @Getter
    @NonNull
    private final Identifier id;

    @NonNull
    private final Consumer<LivingEntity> performance;

    EssenceMoves(final @NonNull String path, final @NonNull Consumer<LivingEntity> performance) {
        id = Essence.id(path);
        this.performance = performance;
    }

    @Override
    public void perform(final LivingEntity user) {
        performance.accept(user);
    }
}
