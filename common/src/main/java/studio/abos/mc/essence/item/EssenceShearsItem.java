package studio.abos.mc.essence.item;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShearsItem;
import studio.abos.mc.essence.attachment.EssenceAttachment;
import studio.abos.mc.essence.component.ModDataComponents;
import studio.abos.mc.essence.move.DiscardMove;
import studio.abos.mc.essence.move.EssenceMoves;

public class EssenceShearsItem extends ShearsItem implements EssenceItem {

    public EssenceShearsItem(final Properties properties) {
        super(properties.component(DataComponents.TOOL, ShearsItem.createToolProperties()));
    }

    public static void summon(final LivingEntity user) {
        if (user == null || !user.getActiveItem().isEmpty() || user.isSpectator() || !EssenceAttachment.of(user).attemptMove(EssenceMoves.SHEARS, user)) {
            return;
        }
        final ItemStack shears = new ItemStack(ModItems.ESSENCE_SHEARS.asItem());
        shears.set(ModDataComponents.ESSENCE_OWNER.value(), user.getUUID());
        EssenceAttachment.of(user).getActiveMoves().add(Pair.of(EssenceMoves.SHEARS, (DiscardMove)ModItems.ESSENCE_SHEARS.asItem()));
        user.setItemInHand(InteractionHand.MAIN_HAND, shears);
    }

}
