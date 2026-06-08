package studio.abos.mc.essence.move;

import com.mojang.datafixers.util.Pair;
import lombok.NonNull;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import studio.abos.mc.essence.attachment.EssenceAttachment;
import studio.abos.mc.essence.entity.EssenceEntity;
import studio.abos.mc.essence.entity.SegmentedEssence;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public interface EssenceMove {

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

    private static @NonNull DiscardMove discard(final @NonNull EssenceMove move) {
        return u -> {
            final EssenceAttachment essence = EssenceAttachment.of(u);
            final var m = essence.getActiveMoves().stream()
                    .filter(pair -> pair.getFirst() == move)
                    .findFirst();
            m.ifPresent(pair -> essence.getActiveMoves().remove(pair));
        };
    }

    private static Consumer<LivingEntity> activateEffect(final @NonNull EssenceMove move) {
        return user -> {
            if (user == null) {
                return;
            }
            final EssenceAttachment essence = EssenceAttachment.of(user);
            if (essence.moveActive(move) || !essence.attemptMove(move, user)) {
                return;
            }
            essence.getActiveMoves().add(Pair.of(move, discard(move)));
        };
    }

    static void feet(final LivingEntity user) {
        activateEffect(EssenceMoves.FEET).accept(user);
    }

}
