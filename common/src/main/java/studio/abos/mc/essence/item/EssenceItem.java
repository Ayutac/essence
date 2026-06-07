package studio.abos.mc.essence.item;

import com.mojang.datafixers.util.Pair;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import studio.abos.mc.essence.attachment.EssenceAttachment;
import studio.abos.mc.essence.component.ModDataComponents;
import studio.abos.mc.essence.move.DiscardMove;
import studio.abos.mc.essence.move.EssenceMove;

import java.util.List;

public interface EssenceItem extends ItemLike, DiscardMove {

    @Override
    default void discard(final LivingEntity user) {
        EssenceItem.discard(user, getClass());
    }

    static void discard(final LivingEntity user, final Class<? extends EssenceItem> itemClass) {
        boolean discarded = false;
        for (final EquipmentSlot slot : EquipmentSlot.values()) {
            if (itemClass.isAssignableFrom(user.getItemBySlot(slot).getItem().getClass())
                    && user.getUUID().equals(user.getItemBySlot(slot).get(ModDataComponents.ESSENCE_OWNER.value()))
            ) {
                user.setItemSlot(slot, ItemStack.EMPTY);
                final List<Pair<EssenceMove, DiscardMove>> activeMoves = EssenceAttachment.of(user).getActiveMoves();
                var entry = activeMoves.stream()
                        .filter(pair -> itemClass.isAssignableFrom(pair.getSecond().getClass()))
                        .findFirst();
                entry.ifPresent(activeMoves::remove);
                discarded = true;
                break;
            }
        }
        if (!discarded && user instanceof ServerPlayer player) {
            var items = player.getInventory().getNonEquipmentItems();
            int i = -1;
            for (final ItemStack stack : items) {
                i++;
                if (itemClass.isAssignableFrom(stack.getItem().getClass())
                        && user.getUUID().equals(stack.get(ModDataComponents.ESSENCE_OWNER.value()))
                ) {
                    discarded = true; // not yet discarded, but we use the boolean as a marker
                    break;
                }
            }
            if (discarded) {
                player.getInventory().setItem(i, ItemStack.EMPTY);
                final List<Pair<EssenceMove, DiscardMove>> activeMoves = EssenceAttachment.of(user).getActiveMoves();
                var entry = activeMoves.stream()
                        .filter(pair -> itemClass.isAssignableFrom(pair.getSecond().getClass()))
                        .findFirst();
                entry.ifPresent(activeMoves::remove);
            }
        }
    }

}
