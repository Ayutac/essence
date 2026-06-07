package studio.abos.mc.essence.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import studio.abos.mc.essence.component.ModDataComponents;

public class EssenceAxeItem extends AxeItem implements EssenceItem {

    public EssenceAxeItem(final Properties properties) {
        super(ModItems.ESSENCE_MATERIAL, 5f, -3f, properties);
    }

    public static void summon(final LivingEntity user) {
        if (user == null || !user.getActiveItem().isEmpty() || user.isSpectator()) {
            return;
        }
        final ItemStack axe = new ItemStack(ModItems.ESSENCE_AXE.asItem());
        axe.set(ModDataComponents.ESSENCE_OWNER.value(), user.getUUID());
        user.setItemInHand(InteractionHand.MAIN_HAND, axe);
    }

}
