package studio.abos.mc.essence.item;

import net.minecraft.world.item.Item;

public class EssenceSwordItem extends Item implements EssenceItem {

    public EssenceSwordItem(final Properties properties) {
        super(properties.sword(ModItems.ESSENCE_MATERIAL, 3f, -2.4f));
    }

}
