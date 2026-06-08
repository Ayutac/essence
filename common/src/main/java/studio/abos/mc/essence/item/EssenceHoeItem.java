package studio.abos.mc.essence.item;

import com.mojang.datafixers.util.Pair;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import studio.abos.mc.essence.attachment.EssenceAttachment;
import studio.abos.mc.essence.component.ModDataComponents;
import studio.abos.mc.essence.move.DiscardMove;
import studio.abos.mc.essence.move.EssenceMoves;

public class EssenceHoeItem extends HoeItem implements EssenceItem {

    public EssenceHoeItem(final Properties properties) {
        super(ModItems.ESSENCE_MATERIAL, -3f, 0f, properties);
    }

    public static void summon(final LivingEntity user) {
        if (user == null || !user.getActiveItem().isEmpty() || user.isSpectator() || !EssenceAttachment.of(user).attemptMove(EssenceMoves.AXE, user)) {
            return;
        }
        final ItemStack hoe = new ItemStack(ModItems.ESSENCE_HOE.asItem());
        hoe.set(ModDataComponents.ESSENCE_OWNER.value(), user.getUUID());
        EssenceAttachment.of(user).getActiveMoves().add(Pair.of(EssenceMoves.HOE, (DiscardMove)ModItems.ESSENCE_HOE.asItem()));
        user.setItemInHand(InteractionHand.MAIN_HAND, hoe);
    }

}
