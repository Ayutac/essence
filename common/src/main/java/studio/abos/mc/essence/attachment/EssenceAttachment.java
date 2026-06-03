package studio.abos.mc.essence.attachment;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

@Data
@NoArgsConstructor
@AllArgsConstructor
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

    public float getImagination() {
        return blue + red + yellow + purple + green + orange;
    }

    public static EssenceAttachment of(@NonNull LivingEntity living) {
        final EssenceAttachment essence = ModDataAttachments.ESSENCE.getOrCreate(living);
        if (living instanceof ServerPlayer player) {
            essence.setWillpower(calculateWillpower(player) + essence.getWillpowerBonus());
        }
        return essence;
    }

    public static float calculateWillpower(ServerPlayer player) {
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
