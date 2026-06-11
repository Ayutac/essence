package studio.abos.mc.essence.move;

import lombok.Getter;
import lombok.NonNull;
import net.minecraft.world.entity.LivingEntity;
import studio.abos.mc.essence.attachment.EssenceAttachment;
import studio.abos.mc.essence.entity.EssenceEntity;
import studio.abos.mc.essence.keyframe.Keyframe;

public class EssenceMove {

    @Getter
    @NonNull
    protected final EssenceMoveType moveType;
    @Getter
    @NonNull
    protected final LivingEntity user;
    @Getter
    protected EssenceContext context;
    @Getter
    protected final boolean endMoveOnContextRemoval;
    @Getter
    protected int tickCount;

    public EssenceMove(final @NonNull EssenceMoveType moveType, final @NonNull LivingEntity user, final boolean endMoveOnContextRemoval) {
        this.moveType = moveType;
        this.user = user;
        this.endMoveOnContextRemoval = endMoveOnContextRemoval;
    }

    public void setContext(final EssenceContext context) {
        if (context instanceof EssenceEntity entity) {
            this.context = entity;
        }
    }

    public void incrementTickCount() {
        tickCount++;
    }

    public boolean initMove() {
        final EssenceAttachment essence = EssenceAttachment.of(getUser());
        if (!essence.attemptMove(getMoveType())) {
            return false;
        }
        return essence.getActiveMoves().add(this);
    }

    public void tickMove() {
        final Keyframe keyframe = getMoveType().getKeyframeMap().get(getTickCount());
        if (keyframe != null) {
            setContext(keyframe.run(getUser(), getContext()));
        }
        incrementTickCount();
        if (endMoveOnContextRemoval && context != null && context.isContextRemoved()) {
            endMove();
        }
    }

    public boolean endMove() {
        if (getContext() != null && !getContext().isContextRemoved()) {
            getContext().removeContext(getUser());
        }
        return EssenceAttachment.of(getUser()).getActiveMoves().remove(this);
    }

}
