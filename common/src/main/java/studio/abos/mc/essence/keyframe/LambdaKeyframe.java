package studio.abos.mc.essence.keyframe;

import lombok.NonNull;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import studio.abos.mc.essence.Essence;
import studio.abos.mc.essence.move.EssenceContext;
import studio.abos.mc.essence.move.EssenceLambdaContext;

import java.util.function.Consumer;

public record LambdaKeyframe(Consumer<LivingEntity> start, Consumer<LivingEntity> end) implements Keyframe {

    public static AttributeModifier SPEED_MODIFIER_FEET = new AttributeModifier(
            Essence.id("essence_feet"), 1.5f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
    );
    public static AttributeModifier JUMP_MODIFIER_FEET = new AttributeModifier(
            Essence.id("essence_feet"), 0.2f, AttributeModifier.Operation.ADD_VALUE
    );
    public static AttributeModifier STEP_MODIFIER_FEET = new AttributeModifier(
            Essence.id("essence_feet"), 1f, AttributeModifier.Operation.ADD_VALUE
    );

    public static LambdaKeyframe FEET = new LambdaKeyframe(user -> {
        final AttributeInstance speed = user.getAttribute(Attributes.MOVEMENT_SPEED);
        speed.addTransientModifier(SPEED_MODIFIER_FEET);
        final AttributeInstance jump = user.getAttribute(Attributes.JUMP_STRENGTH);
        jump.addTransientModifier(JUMP_MODIFIER_FEET);
        final AttributeInstance step = user.getAttribute(Attributes.STEP_HEIGHT);
        step.addTransientModifier(STEP_MODIFIER_FEET);
    }, user -> {
        final AttributeInstance speed = user.getAttribute(Attributes.MOVEMENT_SPEED);
        speed.removeModifier(SPEED_MODIFIER_FEET);
        final AttributeInstance jump = user.getAttribute(Attributes.JUMP_STRENGTH);
        jump.removeModifier(JUMP_MODIFIER_FEET);
        final AttributeInstance step = user.getAttribute(Attributes.STEP_HEIGHT);
        step.removeModifier(STEP_MODIFIER_FEET);
    });

    public static LambdaKeyframe LEGS = new LambdaKeyframe(user -> {
        if (user instanceof ServerPlayer player) {
            player.getAbilities().flying = true;
            player.onUpdateAbilities();
        }
    }, user -> {
        if (user instanceof ServerPlayer player && !(player.isCreative() || player.isSpectator())) {
            player.getAbilities().flying = false;
            player.onUpdateAbilities();
        }
    });

    @Override
    public EssenceContext run(final @NonNull LivingEntity user, final EssenceContext context) {
        start.accept(user);
        return new EssenceLambdaContext(end);
    }

}
