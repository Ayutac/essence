package studio.abos.mc.essence.mixin;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import studio.abos.mc.essence.attachment.EssenceAttachment;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Inject(method = "die(Lnet/minecraft/world/damagesource/DamageSource;)V", at = @At("HEAD"))
    public void abosessence$dissipate(final DamageSource source, final CallbackInfo ci) {
        EssenceAttachment.dissipate((LivingEntity)(Object)this);
    }

}
