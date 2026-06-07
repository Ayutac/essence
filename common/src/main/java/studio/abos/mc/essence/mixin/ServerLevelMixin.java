package studio.abos.mc.essence.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import studio.abos.mc.essence.item.EssenceItem;

@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin {

    @Inject(method = "addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z", at = @At("HEAD"), cancellable = true)
    public void abosessence$dontSpawnEssenceItems(final Entity entity, final CallbackInfoReturnable<Boolean> cir) {
        if (entity instanceof ItemEntity itemEntity && itemEntity.getItem().getItem() instanceof EssenceItem) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }

}
