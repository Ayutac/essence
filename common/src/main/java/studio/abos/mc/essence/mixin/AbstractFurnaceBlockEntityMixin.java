package studio.abos.mc.essence.mixin;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import studio.abos.mc.essence.item.EssenceItem;

@Mixin(AbstractFurnaceBlockEntity.class)
public abstract class AbstractFurnaceBlockEntityMixin {

    @Inject(method = "setItem(ILnet/minecraft/world/item/ItemStack;)V", at = @At("HEAD"), cancellable = true)
    public void abosessence$dontStoreEssenceItems(final int slot, final ItemStack itemStack, final CallbackInfo ci) {
        if (itemStack.getItem() instanceof EssenceItem) {
            ci.cancel();
        }
    }

}
