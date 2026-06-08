package studio.abos.mc.essence.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.animal.sheep.Sheep;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.gameevent.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import studio.abos.mc.essence.item.ModItems;

@Mixin(Sheep.class)
public class SheepMixin {

    @Inject(method = "mobInteract(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/InteractionResult;", at = @At("HEAD"), cancellable = true)
    public void abosessence$shearSheep(final Player player, final InteractionHand hand, final CallbackInfoReturnable<InteractionResult> cir) {
        final Sheep sheep = (Sheep)(Object)this;
        final ItemStack itemStack = player.getItemInHand(hand);
        boolean sheared = false;
        if (itemStack.is(ModItems.ESSENCE_SHEARS)) {
            if (sheep.level() instanceof ServerLevel level) {
                if (sheep.readyForShearing()) {
                    sheep.shear(level, SoundSource.PLAYERS, itemStack);
                    sheep.gameEvent(GameEvent.SHEAR, player);
                    cir.setReturnValue(InteractionResult.SUCCESS_SERVER);
                    sheared = true;
                }
            }
            if (!sheared) {
                cir.setReturnValue(InteractionResult.CONSUME);
            }
            cir.cancel();
        }
    }

}
