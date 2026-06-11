package studio.abos.mc.essence.item;

import net.minecraft.world.item.Item;

public class EssenceSpearItem extends Item implements EssenceItem {

    public EssenceSpearItem(final Properties properties) {
        super(properties.spear(ModItems.ESSENCE_MATERIAL, 1.05f, 1.075f, 0.5f, 3f, 10f, 6.5f, 5.1f, 10f, 4.6f));
    }

}
