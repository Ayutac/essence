package studio.abos.mc.essence.component;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import net.blay09.mods.balm.core.component.BalmDataComponentTypeRegistrar;
import net.minecraft.core.Holder;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.component.DataComponentType;

import java.util.UUID;

@UtilityClass
public class ModDataComponents  {

    public Holder<@NonNull DataComponentType<@NonNull UUID>> ESSENCE_OWNER;

    public void initialize(BalmDataComponentTypeRegistrar dataComponentTypes) {
        ESSENCE_OWNER = dataComponentTypes.register("essence_owner", UUIDUtil.CODEC).asHolder();
    }

}
