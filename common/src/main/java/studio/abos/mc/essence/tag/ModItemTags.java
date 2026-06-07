package studio.abos.mc.essence.tag;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import static studio.abos.mc.essence.Essence.id;

public class ModItemTags {

    public static final TagKey<Item> ESSENCE_TOOL_MATERIALS = TagKey.create(Registries.ITEM, id("essence_tool_materials"));

}
