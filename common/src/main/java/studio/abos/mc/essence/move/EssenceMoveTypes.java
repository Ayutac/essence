package studio.abos.mc.essence.move;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import lombok.Getter;
import lombok.NonNull;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import studio.abos.mc.essence.Essence;
import studio.abos.mc.essence.entity.EssenceBallEntity;
import studio.abos.mc.essence.entity.EssenceBridgeEntity;
import studio.abos.mc.essence.entity.EssenceLanceEntity;
import studio.abos.mc.essence.entity.EssencePillarEntity;
import studio.abos.mc.essence.entity.EssenceSpikeSphereEntity;
import studio.abos.mc.essence.item.ModItems;
import studio.abos.mc.essence.keyframe.DissipateEntityKeyframe;
import studio.abos.mc.essence.keyframe.Keyframe;
import studio.abos.mc.essence.keyframe.LambdaKeyframe;
import studio.abos.mc.essence.keyframe.Position;

public enum EssenceMoveTypes implements EssenceMoveType {

    AXE("axe", 8f, Keyframe.summon(ModItems.ESSENCE_AXE, EquipmentSlot.MAINHAND)),
    BALL("ball", 1f, Keyframe.summonAndShoot(EssenceBallEntity::new, Position.FRONT, 1, 1f)),
    BRIDGE("bridge", 10f, Keyframe.summon(EssenceBridgeEntity::new, Position.FRONT_LOW)),
    DISSIPATE("dissipate", 0f, Keyframe.singleton(new DissipateEntityKeyframe())),
    FEET("feet", 8f, Keyframe.singleton(LambdaKeyframe.FEET)),
    HOE("hoe", 4f, Keyframe.summon(ModItems.ESSENCE_HOE, EquipmentSlot.MAINHAND)),
    PICKAXE("pickaxe", 6f, Keyframe.summon(ModItems.ESSENCE_PICKAXE, EquipmentSlot.MAINHAND)),
    PILLAR("pillar", 5f, Keyframe.summon(EssencePillarEntity::new, Position.BELOW)),
    LANCE("lance", 8f, Keyframe.summon(EssenceLanceEntity::new, Position.FRONT)),
    LEGS("legs", 50f, Keyframe.singleton(LambdaKeyframe.LEGS)),
    RETRACT("retract", 0f, Keyframe.retract(1f)),
    SHEARS("shears", 5f, Keyframe.summon(ModItems.ESSENCE_SHEARS, EquipmentSlot.MAINHAND)),
    SHIELD("shield", 3f, Keyframe.summon(ModItems.ESSENCE_SHIELD, EquipmentSlot.OFFHAND)),
    SHOVEL("shovel", 3f, Keyframe.summon(ModItems.ESSENCE_SHOVEL, EquipmentSlot.MAINHAND)),
    SPEAR("spear", 6f, Keyframe.summon(ModItems.ESSENCE_SPEAR, EquipmentSlot.MAINHAND)),
    SPIKE("spike", 3f, Keyframe.summonAndShoot(EssenceBallEntity::new, Position.FRONT, 10, 2f)),
    SPIKE_SPHERE("spike_sphere", 20f, Keyframe.summon(EssenceSpikeSphereEntity::new, Position.BELOW)),
    SWORD("sword", 7f, Keyframe.summon(ModItems.ESSENCE_SWORD, EquipmentSlot.MAINHAND));

    @Getter
    @NonNull
    private final Identifier id;

    @Getter
    private final float neededWillpower;

    @Getter
    @NonNull
    private final Int2ObjectMap<Keyframe> keyframeMap;

    EssenceMoveTypes(final @NonNull String path, final float neededWillpower, final @NonNull Int2ObjectMap<Keyframe> keyframeMap) {
        id = Essence.id(path);
        if (neededWillpower < 0f) {
            throw new IllegalArgumentException("Willpower must be non-negative!");
        }
        this.neededWillpower = neededWillpower;
        this.keyframeMap = keyframeMap;
    }

    @Override
    public void perform(final @NonNull LivingEntity user) {
        new EssenceMove(this, user, true).initMove();
    }

}
