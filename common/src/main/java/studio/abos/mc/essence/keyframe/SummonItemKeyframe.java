package studio.abos.mc.essence.keyframe;

import lombok.NonNull;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import studio.abos.mc.essence.move.EssenceContext;
import studio.abos.mc.essence.move.EssenceItemStackContext;

public record SummonItemKeyframe(ItemLike item, EquipmentSlot slot) implements Keyframe {

    @Override
    public EssenceContext run(final @NonNull LivingEntity user, final EssenceContext context) {
        EssenceContext newContext = context;
        if ((newContext == null || newContext.asItemStack() == null || newContext.asItemStack().isEmpty())) {
            newContext = new EssenceItemStackContext(new ItemStack(item));
        }
        if (user.getItemBySlot(slot).isEmpty()) {
            user.setItemSlot(slot, newContext.asItemStack());
        }
        return newContext;
    }

}
