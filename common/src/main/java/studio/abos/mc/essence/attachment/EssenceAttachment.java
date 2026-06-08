package studio.abos.mc.essence.attachment;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import studio.abos.mc.essence.move.DiscardMove;
import studio.abos.mc.essence.move.EssenceMove;

import java.util.LinkedList;
import java.util.List;

@Data
@NoArgsConstructor
public class EssenceAttachment {

    public static final Codec<EssenceAttachment> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.FLOAT.fieldOf("blue").forGetter(EssenceAttachment::getBlue),
            Codec.FLOAT.fieldOf("red").forGetter(EssenceAttachment::getRed),
            Codec.FLOAT.fieldOf("yellow").forGetter(EssenceAttachment::getYellow),
            Codec.FLOAT.fieldOf("purple").forGetter(EssenceAttachment::getPurple),
            Codec.FLOAT.fieldOf("green").forGetter(EssenceAttachment::getGreen),
            Codec.FLOAT.fieldOf("orange").forGetter(EssenceAttachment::getOrange),
            Codec.FLOAT.fieldOf("willpower").forGetter(EssenceAttachment::getWillpower),
            Codec.FLOAT.fieldOf("willpowerBonus").forGetter(EssenceAttachment::getWillpowerBonus)
    ).apply(instance, EssenceAttachment::new));

    private float blue;
    private float red;
    private float yellow;
    private float purple;
    private float green;
    private float orange;

    private float willpower;
    private float willpowerBonus;

    private List<Pair<EssenceMove, DiscardMove>> activeMoves = new LinkedList<>();

    public EssenceAttachment(final float blue, final float red, final float yellow, final float purple, final float green, final float orange, final float willpower, final float willpowerBonus) {
        this.blue = blue;
        this.red = red;
        this.yellow = yellow;
        this.purple = purple;
        this.green = green;
        this.orange = orange;
        this.willpower = willpower;
        this.willpowerBonus = willpowerBonus;
    }

    public float getImagination() {
        return blue + red + yellow + purple + green + orange;
    }

    public boolean attemptMove(final EssenceMove move, final LivingEntity user) {
        float usedWillpower = (float)activeMoves.stream()
                .map(Pair::getFirst)
                .mapToDouble(EssenceMove::getNeededWillpower)
                .sum();
        while (willpower - usedWillpower < move.getNeededWillpower() && !activeMoves.isEmpty()) {
            final Pair<EssenceMove, DiscardMove> nextMoveToVanish = activeMoves.getFirst();
            nextMoveToVanish.getSecond().discard(user); // also removes from the list of active moves
            usedWillpower -= nextMoveToVanish.getFirst().getNeededWillpower();
        }
        return willpower - usedWillpower >= move.getNeededWillpower();
    }

    public void dissipateAll(final LivingEntity user) {
        while (!activeMoves.isEmpty()) { // dangerous
            activeMoves.getFirst().getSecond().discard(user);
        }
    }

    public boolean moveActive(final @NonNull EssenceMove move) {
        return activeMoves.stream()
                .map(Pair::getFirst)
                .anyMatch(move::equals);
    }

    public static EssenceAttachment of(@NonNull LivingEntity living) {
        final EssenceAttachment essence = ModDataAttachments.ESSENCE.getOrCreate(living);
        if (living instanceof ServerPlayer player) {
            essence.setWillpower(calculateWillpower(player) + essence.getWillpowerBonus());
        }
        return essence;
    }

    public static void dissipate(@NonNull LivingEntity living) {
        final EssenceAttachment essence = ModDataAttachments.ESSENCE.get(living);
        if (essence != null) {
            essence.dissipateAll(living);
        }
    }

    public static float calculateWillpower(final @NonNull ServerPlayer player) {
        int blocksMined = 0;
        for (Stat<@NonNull Block> blockStat : Stats.BLOCK_MINED) {
            blocksMined += player.getStats().getValue(blockStat);
        }
        float metersTravelled = player.getStats().getValue(Stats.CUSTOM.get(Stats.WALK_ONE_CM)) / 100f;
        int itemsCrafted = 0;
        for (Stat<@NonNull Item> itemStat : Stats.ITEM_CRAFTED) {
            itemsCrafted += player.getStats().getValue(itemStat);
        }
        return blocksMined / 1_000f + metersTravelled / 1_000f + itemsCrafted / 6_400f;
    }

}
