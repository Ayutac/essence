package studio.abos.mc.essence.entity;

import net.blay09.mods.balm.world.entity.BalmEntityTypeRegistrar;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class ModEntities {

    public static Holder<@NotNull EntityType<@NotNull EssenceBallEntity>> ESSENCE_BALL;
    public static Holder<@NotNull EntityType<@NotNull EssencePillarEntity>> ESSENCE_PILLAR;

    public static void initialize(BalmEntityTypeRegistrar entities) {
        ESSENCE_BALL = entities.register("essence_ball", () ->
                EntityType.Builder.of(new WorldOnlyEntityFactory<>(EssenceBallEntity::new), MobCategory.MISC)
                        .sized(0.5f, 0.5f)
                        .eyeHeight(0.25f)).asHolder();
        ESSENCE_PILLAR = entities.register("essence_pillar", () ->
                EntityType.Builder.of(new WorldOnlyEntityFactory<>(EssencePillarEntity::new), MobCategory.MISC)
                        .sized(1f, 0.5f)
                        .eyeHeight(0.25f)).asHolder();
    }

    private record WorldOnlyEntityFactory<T extends Entity>(Function<Level, T> ctor) implements EntityType.EntityFactory<@NotNull T> {
        @Override
        public T create(@NotNull EntityType<@NotNull T> type, @NotNull Level world) {
                return ctor.apply(world);
            }
    }
}
