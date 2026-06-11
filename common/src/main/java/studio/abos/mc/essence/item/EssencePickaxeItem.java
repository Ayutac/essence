package studio.abos.mc.essence.item;

import net.minecraft.world.item.Item;

public class EssencePickaxeItem extends Item implements EssenceItem {

    public EssencePickaxeItem(final Properties properties) {
        super(properties.pickaxe(ModItems.ESSENCE_MATERIAL, 1f, -2.8f));
    }

}
