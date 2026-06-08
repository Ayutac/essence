package studio.abos.mc.essence.item;

import com.mojang.datafixers.util.Pair;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import studio.abos.mc.essence.attachment.EssenceAttachment;
import studio.abos.mc.essence.component.ModDataComponents;
import studio.abos.mc.essence.move.DiscardMove;
import studio.abos.mc.essence.move.EssenceMoves;

public class EssenceSpearItem extends Item implements EssenceItem {

    public EssenceSpearItem(final Properties properties) {
        super(properties.spear(ModItems.ESSENCE_MATERIAL, 1.05f, 1.075f, 0.5f, 3f, 10f, 6.5f, 5.1f, 10f, 4.6f));
    }

    public static void summon(final LivingEntity user) {
        if (user == null || !user.getActiveItem().isEmpty() || user.isSpectator() || !EssenceAttachment.of(user).attemptMove(EssenceMoves.SPEAR, user)) {
            return;
        }
        final ItemStack pickaxe = new ItemStack(ModItems.ESSENCE_SPEAR.asItem());
        pickaxe.set(ModDataComponents.ESSENCE_OWNER.value(), user.getUUID());
        EssenceAttachment.of(user).getActiveMoves().add(Pair.of(EssenceMoves.SPEAR, (DiscardMove)ModItems.ESSENCE_SPEAR.asItem()));
        user.setItemInHand(InteractionHand.MAIN_HAND, pickaxe);
    }

}
