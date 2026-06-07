package studio.abos.mc.essence.move;

import lombok.Getter;
import lombok.NonNull;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import studio.abos.mc.essence.Essence;
import studio.abos.mc.essence.entity.EssenceBallEntity;
import studio.abos.mc.essence.entity.EssenceBridgeEntity;
import studio.abos.mc.essence.entity.EssenceLanceEntity;
import studio.abos.mc.essence.entity.EssencePillarEntity;
import studio.abos.mc.essence.entity.EssenceSpikeSphereEntity;
import studio.abos.mc.essence.item.EssenceAxeItem;
import studio.abos.mc.essence.item.EssencePickaxeItem;
import studio.abos.mc.essence.item.EssenceShovelItem;
import studio.abos.mc.essence.item.EssenceSwordItem;

import java.util.function.Consumer;

public enum EssenceMoves implements EssenceMove {

    AXE("axe", 8f, EssenceAxeItem::summon),
    BALL("ball", 1f, EssenceBallEntity::summonAndShoot),
    BRIDGE("bridge", 10f, EssenceBridgeEntity::summon),
    DISSIPATE("dissipate", 0f, EssenceMove::dissipate),
    PICKAXE("pickaxe", 6f, EssencePickaxeItem::summon),
    PILLAR("pillar", 5f, EssencePillarEntity::summon),
    LANCE("lance", 8f, EssenceLanceEntity::summon),
    RETRACT("retract", 0f, EssenceMove::retract),
    RETRACT_ALL("retract_all", 0f, EssenceMove::retractAll),
    SHOVEL("shovel", 3f, EssenceShovelItem::summon),
    SPIKE_SPHERE("spike_sphere", 20f, EssenceSpikeSphereEntity::summon),
    SWORD("sword", 7f, EssenceSwordItem::summon);

    @Getter
    @NonNull
    private final Identifier id;

    @Getter
    private final float neededWillpower;

    @NonNull
    private final Consumer<LivingEntity> performance;

    EssenceMoves(final @NonNull String path, final float neededWillpower, final @NonNull Consumer<LivingEntity> performance) {
        id = Essence.id(path);
        if (neededWillpower < 0f) {
            throw new IllegalArgumentException("Willpower must be non-negative!");
        }
        this.neededWillpower = neededWillpower;
        this.performance = performance;
    }

    @Override
    public void perform(final LivingEntity user) {
        performance.accept(user);
    }
}
