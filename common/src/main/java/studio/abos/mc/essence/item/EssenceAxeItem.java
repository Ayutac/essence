package studio.abos.mc.essence.item;

import com.mojang.datafixers.util.Pair;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import studio.abos.mc.essence.attachment.EssenceAttachment;
import studio.abos.mc.essence.component.ModDataComponents;
import studio.abos.mc.essence.move.DiscardMove;
import studio.abos.mc.essence.move.EssenceMoves;

public class EssenceAxeItem extends AxeItem implements EssenceItem {

    public EssenceAxeItem(final Properties properties) {
        super(ModItems.ESSENCE_MATERIAL, 5f, -3f, properties);
    }

    @Override
    public void discard(final LivingEntity user) {
        EssenceItem.discard(user, getClass());
    }

    public static void summon(final LivingEntity user) {
        if (user == null || !user.getActiveItem().isEmpty() || user.isSpectator() || !EssenceAttachment.of(user).attemptMove(EssenceMoves.AXE, user)) {
            return;
        }
        final ItemStack axe = new ItemStack(ModItems.ESSENCE_AXE.asItem());
        axe.set(ModDataComponents.ESSENCE_OWNER.value(), user.getUUID());
        EssenceAttachment.of(user).getActiveMoves().add(Pair.of(EssenceMoves.AXE, (DiscardMove)ModItems.ESSENCE_AXE.asItem()));
        user.setItemInHand(InteractionHand.MAIN_HAND, axe);
    }

}
