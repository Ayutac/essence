package studio.abos.mc.essence.keyframe;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import lombok.NonNull;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import studio.abos.mc.essence.entity.EssenceEntity;
import studio.abos.mc.essence.move.EssenceContext;

import java.util.Map;
import java.util.function.Function;

public interface Keyframe {

    Keyframe FORGET_CONTEXT = (_, _) -> null;

    EssenceContext run(final @NonNull LivingEntity user, final EssenceContext context);

    @NonNull
    static Int2ObjectMap<Keyframe> singleton(Keyframe keyframe) {
        return new Int2ObjectArrayMap<>(Map.of(0, keyframe));
    }

    @NonNull
    static Int2ObjectMap<Keyframe> summon(final @NonNull ItemLike item, final @NonNull EquipmentSlot slot) {
        return new Int2ObjectArrayMap<>(Map.of(0, new SummonItemKeyframe(item, slot)));
    }

    @NonNull
    static <T extends EssenceEntity> Int2ObjectMap<Keyframe> summon(final @NonNull Function<Level, T> constructor, final @NonNull Position position) {
        return new Int2ObjectArrayMap<>(Map.of(0, new SummonEntityKeyframe<>(constructor, position)));
    }

    @NonNull
    static <T extends EssenceEntity> Int2ObjectMap<Keyframe> summonAndShoot(final @NonNull Function<Level, T> constructor, final @NonNull Position position, int delay, float speed) {
        if (delay <= 0) {
            throw new IllegalArgumentException("Delay must be positive!");
        }
        return new Int2ObjectArrayMap<>(Map.of(
                0, new SummonEntityKeyframe<>(constructor, position),
                delay, new ShootEntityKeyframe(speed)
        ));
    }

    @NonNull
    static Int2ObjectMap<Keyframe> retract(final float speed) {
        return new Int2ObjectArrayMap<>(Map.of(0, new KeyframeGroup(new RetractEntityKeyframe(speed), Keyframe.FORGET_CONTEXT)));
    }

}
