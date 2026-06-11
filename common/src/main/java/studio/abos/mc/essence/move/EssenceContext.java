package studio.abos.mc.essence.move;

import lombok.NonNull;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface EssenceContext {

    /**
     * Should remove this context, but not the move of this context itself.
     */
    void removeContext(final @NonNull LivingEntity user);

    boolean isContextRemoved();

    default ItemStack asItemStack() {
        return null;
    }

}
