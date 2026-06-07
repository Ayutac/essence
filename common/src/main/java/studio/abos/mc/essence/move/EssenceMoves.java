package studio.abos.mc.essence.move;

import lombok.Getter;
import lombok.NonNull;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import studio.abos.mc.essence.Essence;
import studio.abos.mc.essence.entity.EssenceBallEntity;
import studio.abos.mc.essence.entity.EssenceLanceEntity;
import studio.abos.mc.essence.entity.EssencePillarEntity;
import studio.abos.mc.essence.entity.EssenceSpikeSphereEntity;

import java.util.function.Consumer;

public enum EssenceMoves implements EssenceMove {

    BALL("ball", 1f, EssenceBallEntity::summonAndShoot),
    DISSIPATE("dissipate", 0f, EssenceMove::dissipate),
    PILLAR("pillar", 5f, EssencePillarEntity::summon),
    LANCE("lance", 8f, EssenceLanceEntity::summon),
    SPIKE_SPHERE("spike_sphere", 20f, EssenceSpikeSphereEntity::summon);

    @Getter
    @NonNull
    private final Identifier id;

    @Getter
    private final float neededWillpower;

    @NonNull
    private final Consumer<LivingEntity> performance;

    EssenceMoves(final @NonNull String path, final float neededWillpower, final @NonNull Consumer<LivingEntity> performance) {
        id = Essence.id(path);
        if (neededWillpower < 0f) {
            throw new IllegalArgumentException("Willpower must be non-negative!");
        }
        this.neededWillpower = neededWillpower;
        this.performance = performance;
    }

    @Override
    public void perform(final LivingEntity user) {
        performance.accept(user);
    }
}
