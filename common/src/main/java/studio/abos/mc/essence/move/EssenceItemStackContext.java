package studio.abos.mc.essence.move;

import lombok.NonNull;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public record EssenceItemStackContext(ItemStack itemStack) implements EssenceContext {

    @Override
    public void removeContext(final @NonNull LivingEntity user) {
        itemStack.setCount(0);
    }

    @Override
    public boolean isContextRemoved() {
        return itemStack.getCount() <= 0;
    }

    @Override
    public ItemStack asItemStack() {
        return itemStack;
    }

}
