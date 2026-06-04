package studio.abos.mc.essence.damage;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.level.Level;
import studio.abos.mc.essence.Essence;

@UtilityClass
public class ModDamageTypes {

    public ResourceKey<@NonNull DamageType> ESSENCE = ResourceKey.create(Registries.DAMAGE_TYPE, Essence.id("essence"));

    public void initialize() {
        // intentionally left empty
    }

    public Holder<@NonNull DamageType> essence(final @NonNull Level level) {
        return level.registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(ESSENCE);
    }

}
