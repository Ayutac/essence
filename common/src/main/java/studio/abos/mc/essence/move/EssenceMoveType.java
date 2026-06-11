package studio.abos.mc.essence.move;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import lombok.NonNull;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import studio.abos.mc.essence.keyframe.Keyframe;

public interface EssenceMoveType {

    Identifier getId();

    float getNeededWillpower();

    Int2ObjectMap<Keyframe> getKeyframeMap();

    void perform(final @NonNull LivingEntity user);

}
