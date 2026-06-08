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

public class EssenceSwordItem extends Item implements EssenceItem {

    public EssenceSwordItem(final Properties properties) {
        super(properties.sword(ModItems.ESSENCE_MATERIAL, 3f, -2.4f));
    }

    public static void summon(final LivingEntity user) {
        if (user == null || !user.getActiveItem().isEmpty() || user.isSpectator() || !EssenceAttachment.of(user).attemptMove(EssenceMoves.SWORD, user)) {
            return;
        }
        final ItemStack pickaxe = new ItemStack(ModItems.ESSENCE_SWORD.asItem());
        pickaxe.set(ModDataComponents.ESSENCE_OWNER.value(), user.getUUID());
        EssenceAttachment.of(user).getActiveMoves().add(Pair.of(EssenceMoves.SWORD, (DiscardMove)ModItems.ESSENCE_SWORD.asItem()));
        user.setItemInHand(InteractionHand.MAIN_HAND, pickaxe);
    }

}
