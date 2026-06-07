package studio.abos.mc.essence.tag;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import static studio.abos.mc.essence.Essence.id;

public class ModBlockTags {

    public static final TagKey<Block> INCORRECT_FOR_ESSENCE_TOOL = TagKey.create(Registries.BLOCK, id("incorrect_for_essence_tool"));

}
