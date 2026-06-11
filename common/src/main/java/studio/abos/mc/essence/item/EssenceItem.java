package studio.abos.mc.essence.item;

import net.minecraft.world.level.ItemLike;

public interface EssenceItem extends ItemLike {

//    static void discard(final LivingEntity user, final Class<? extends EssenceItem> itemClass) {
//        boolean discarded = false;
//        for (final EquipmentSlot slot : EquipmentSlot.values()) {
//            if (itemClass.isAssignableFrom(user.getItemBySlot(slot).getItem().getClass())
//                    && user.getUUID().equals(user.getItemBySlot(slot).get(ModDataComponents.ESSENCE_OWNER.value()))
//            ) {
//                user.setItemSlot(slot, ItemStack.EMPTY);
//                final List<Pair<EssenceMoveType, EssenceMove>> activeMoves = EssenceAttachment.of(user).getActiveMoves();
//                var entry = activeMoves.stream()
//                        .filter(pair -> itemClass.isAssignableFrom(pair.getSecond().getClass()))
//                        .findFirst();
//                entry.ifPresent(activeMoves::remove);
//                discarded = true;
//                break;
//            }
//        }
//        if (!discarded && user instanceof ServerPlayer player) {
//            var items = player.getInventory().getNonEquipmentItems();
//            int i = -1;
//            for (final ItemStack stack : items) {
//                i++;
//                if (itemClass.isAssignableFrom(stack.getItem().getClass())
//                        && user.getUUID().equals(stack.get(ModDataComponents.ESSENCE_OWNER.value()))
//                ) {
//                    discarded = true; // not yet discarded, but we use the boolean as a marker
//                    break;
//                }
//            }
//            if (discarded) {
//                player.getInventory().setItem(i, ItemStack.EMPTY);
//                final List<Pair<EssenceMoveType, EssenceMove>> activeMoves = EssenceAttachment.of(user).getActiveMoves();
//                var entry = activeMoves.stream()
//                        .filter(pair -> itemClass.isAssignableFrom(pair.getSecond().getClass()))
//                        .findFirst();
//                entry.ifPresent(activeMoves::remove);
//            }
//        }
//    }

}
