package studio.abos.mc.essence.attachment;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import studio.abos.mc.essence.move.EssenceMove;
import studio.abos.mc.essence.move.EssenceMoveType;
import studio.abos.mc.essence.move.EssenceMoveTypes;

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

    private List<EssenceMove> activeMoves = new LinkedList<>();

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

    public boolean attemptMove(final EssenceMoveType move) {
        float usedWillpower = (float)activeMoves.stream()
                .map(EssenceMove::getMoveType)
                .mapToDouble(EssenceMoveType::getNeededWillpower)
                .sum();
        while (willpower - usedWillpower < move.getNeededWillpower() && !activeMoves.isEmpty()) {
            final EssenceMove nextMoveToVanish = activeMoves.getFirst();
            nextMoveToVanish.endMove(); // also removes from the list of active moves
            usedWillpower -= nextMoveToVanish.getMoveType().getNeededWillpower();
        }
        return willpower - usedWillpower >= move.getNeededWillpower();
    }

    public void dissipateAll() {
        EssenceMove previous = null;
        while (!activeMoves.isEmpty()) { // dangerous
            if (activeMoves.getFirst() == previous) {
                // we make sure to remove moves where the discarding failed to remove it
                // especially useful for disappearing Essence items
                activeMoves.removeFirst();
            }
            else {
                previous = activeMoves.getFirst();
                activeMoves.getFirst().endMove();
            }
        }
    }

    public boolean moveActive(final @NonNull EssenceMoveType move) {
        return activeMoves.stream()
                .map(EssenceMove::getMoveType)
                .anyMatch(move::equals);
    }

    public void removeFirst(final @NonNull EssenceMoveType move) {
        final var firstMove = activeMoves.stream()
                .filter(m -> m.getMoveType().equals(move))
                .findFirst();
        firstMove.ifPresent(m -> activeMoves.remove(m));
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
            essence.dissipateAll();
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

    public static void tick(final MinecraftServer server) {
        // tick essence keyframes
        for (final ServerLevel level : server.getAllLevels()) {
            for (final Entity entity : level.getAllEntities()) {
                if (entity instanceof final LivingEntity living) {
                    final EssenceAttachment essence = ModDataAttachments.ESSENCE.get(living);
                    if (essence != null) {
                        for (final EssenceMove move : essence.activeMoves) {
                            move.tickMove();
                        }
                    }
                }
            }
        }
        // tick player-exclusive essence stuff
        for (final ServerPlayer player : server.getPlayerList().getPlayers()) {
            final EssenceAttachment essence = ModDataAttachments.ESSENCE.get(player);
            if (essence != null) {
                if (essence.moveActive(EssenceMoveTypes.LEGS)) {
                    player.getAbilities().flying = true;
                    player.onUpdateAbilities();
                }
            }
        }
    }
}
