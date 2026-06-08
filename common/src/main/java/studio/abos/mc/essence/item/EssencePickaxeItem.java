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

public class EssencePickaxeItem extends Item implements EssenceItem {

    public EssencePickaxeItem(final Properties properties) {
        super(properties.pickaxe(ModItems.ESSENCE_MATERIAL, 1f, -2.8f));
    }

    public static void summon(final LivingEntity user) {
        if (user == null || !user.getActiveItem().isEmpty() || user.isSpectator() || !EssenceAttachment.of(user).attemptMove(EssenceMoves.PICKAXE, user)) {
            return;
        }
        final ItemStack pickaxe = new ItemStack(ModItems.ESSENCE_PICKAXE.asItem());
        pickaxe.set(ModDataComponents.ESSENCE_OWNER.value(), user.getUUID());
        EssenceAttachment.of(user).getActiveMoves().add(Pair.of(EssenceMoves.PICKAXE, (DiscardMove)ModItems.ESSENCE_PICKAXE.asItem()));
        user.setItemInHand(InteractionHand.MAIN_HAND, pickaxe);
    }

}
