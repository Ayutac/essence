package studio.abos.mc.essence.mixin;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import studio.abos.mc.essence.item.EssenceItem;

@Mixin(HopperBlockEntity.class)
public abstract class HopperBlockEntityMixin {

    @Inject(method = "setItem(ILnet/minecraft/world/item/ItemStack;)V", at = @At("HEAD"), cancellable = true)
    public void abosessence$dontStoreEssenceItems(final int slot, final ItemStack itemStack, final CallbackInfo ci) {
        if (itemStack.getItem() instanceof EssenceItem) {
            ci.cancel();
        }
    }

}
