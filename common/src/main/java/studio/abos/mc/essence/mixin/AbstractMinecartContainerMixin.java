package studio.abos.mc.essence.mixin;

import net.minecraft.world.entity.vehicle.minecart.AbstractMinecartContainer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import studio.abos.mc.essence.item.EssenceItem;

@Mixin(AbstractMinecartContainer.class)
public abstract class AbstractMinecartContainerMixin {

    @Inject(method = "setItem(ILnet/minecraft/world/item/ItemStack;)V", at = @At("HEAD"), cancellable = true)
    public void abosessence$dontStoreEssenceItems(final int slot, final ItemStack itemStack, final CallbackInfo ci) {
        if (itemStack.getItem() instanceof EssenceItem) {
            ci.cancel();
        }
    }

}
