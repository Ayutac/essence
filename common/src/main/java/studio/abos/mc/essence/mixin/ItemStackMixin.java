package studio.abos.mc.essence.mixin;

import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import studio.abos.mc.essence.item.EssenceItem;

@Mixin(ItemStack.class)
public class ItemStackMixin {

    @Inject(method = "isStackable", at = @At("RETURN"), cancellable = true)
    public void abosessence$dontStackEssenceTools(final CallbackInfoReturnable<Boolean> cir) {
        if (((ItemStack)(Object)this).getItem() instanceof EssenceItem) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "isDamageableItem", at = @At("RETURN"), cancellable = true)
    public void abosessence$dontDamageEssenceTools(final CallbackInfoReturnable<Boolean> cir) {
        if (((ItemStack)(Object)this).getItem() instanceof EssenceItem) {
            cir.setReturnValue(false);
        }
    }

}
