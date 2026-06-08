package studio.abos.mc.essence.move;

import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import studio.abos.mc.essence.Essence;
import studio.abos.mc.essence.attachment.EssenceAttachment;
import studio.abos.mc.essence.entity.EssenceEntity;
import studio.abos.mc.essence.entity.SegmentedEssence;

import java.util.ArrayList;
import java.util.List;

public interface EssenceMove {

    AttributeModifier SPEED_MODIFIER_FEET = new AttributeModifier(
            Essence.id("essence_feet"), 1.5f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
    );
    AttributeModifier JUMP_MODIFIER_FEET = new AttributeModifier(
            Essence.id("essence_feet"), 0.2f, AttributeModifier.Operation.ADD_VALUE
    );
    AttributeModifier STEP_MODIFIER_FEET = new AttributeModifier(
            Essence.id("essence_feet"), 1f, AttributeModifier.Operation.ADD_VALUE
    );

    Identifier getId();

    float getNeededWillpower();

    void perform(LivingEntity user);

    static EntityHitResult getOwnedEssenceInLineOfSight(final LivingEntity user) {
        final Vec3 start = user.getEyePosition();
        return ProjectileUtil.getEntityHitResult(user, start,
                start.add(user.getLookAngle().scale(EssenceEntity.EFFECTIVE_RANGE)),
                user.getBoundingBox().inflate(EssenceEntity.EFFECTIVE_RANGE_SQR),
                entity -> entity instanceof EssenceEntity essence
                        && essence.getOwner() == user,
                EssenceEntity.EFFECTIVE_RANGE_SQR
        );
    }

    static void dissipate(final LivingEntity user) {
        final EntityHitResult lookedAtEssence = getOwnedEssenceInLineOfSight(user);
        if (lookedAtEssence != null) {
            if (lookedAtEssence.getEntity() instanceof SegmentedEssence part && part.getOrigin() != null) {
                part.getOrigin().discard();
            }
            else {
                lookedAtEssence.getEntity().discard();
            }
        }
    }

    static void retract(final LivingEntity user) {
        final EntityHitResult lookedAtEssence = getOwnedEssenceInLineOfSight(user);
        if (lookedAtEssence != null) {
            ((EssenceEntity)lookedAtEssence.getEntity()).retract();
        }
    }

    static void retractAll(final LivingEntity user) {
        final List<Pair<EssenceMove, DiscardMove>> moves = new ArrayList<>(EssenceAttachment.of(user).getActiveMoves());
        for (final var entry : moves) {
            if (entry.getSecond() instanceof EssenceEntity entity) {
                entity.retract();
            }
            else {
                entry.getSecond().discard(user);
            }
        }
    }

    static void feet(final LivingEntity user) {
        if (user == null) {
            return;
        }
        final EssenceAttachment essence = EssenceAttachment.of(user);
        if (essence.moveActive(EssenceMoves.FEET) || !essence.attemptMove(EssenceMoves.FEET, user)) {
            return;
        }
        essence.getActiveMoves().add(Pair.of(EssenceMoves.FEET, u -> {
            final EssenceAttachment e = EssenceAttachment.of(u);
            final var m = e.getActiveMoves().stream()
                    .filter(pair -> pair.getFirst() == EssenceMoves.FEET)
                    .findFirst();
            m.ifPresent(pair -> e.getActiveMoves().remove(pair));
            final AttributeInstance speed = user.getAttribute(Attributes.MOVEMENT_SPEED);
            speed.removeModifier(SPEED_MODIFIER_FEET);
            final AttributeInstance jump = user.getAttribute(Attributes.JUMP_STRENGTH);
            jump.removeModifier(JUMP_MODIFIER_FEET);
            final AttributeInstance step = user.getAttribute(Attributes.STEP_HEIGHT);
            step.removeModifier(STEP_MODIFIER_FEET);
        }));
        final AttributeInstance speed = user.getAttribute(Attributes.MOVEMENT_SPEED);
        speed.addTransientModifier(SPEED_MODIFIER_FEET);
        final AttributeInstance jump = user.getAttribute(Attributes.JUMP_STRENGTH);
        jump.addTransientModifier(JUMP_MODIFIER_FEET);
        final AttributeInstance step = user.getAttribute(Attributes.STEP_HEIGHT);
        step.addTransientModifier(STEP_MODIFIER_FEET);
    }

}
