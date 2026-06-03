package studio.abos.mc.essence.move;

import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;

public interface EssenceMove {

    Identifier getId();

    void perform(LivingEntity user);

}
