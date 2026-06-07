package studio.abos.mc.essence.mixin;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.world.level.GameType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import studio.abos.mc.essence.attachment.EssenceAttachment;

@Mixin(ServerPlayerGameMode.class)
public abstract class ServerPlayerGameModeMixin {

    @Shadow
    @Final
    protected ServerPlayer player;

    @Inject(method = "changeGameModeForPlayer(Lnet/minecraft/world/level/GameType;)Z", at = @At("RETURN"))
    public void abosessence$dissipateForSpectator(final GameType gameModeForPlayer, final CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValueZ() && gameModeForPlayer == GameType.SPECTATOR) {
            EssenceAttachment.dissipate(player);
        }
    }

}
