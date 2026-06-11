package studio.abos.mc.essence.item;

import net.minecraft.world.item.HoeItem;

public class EssenceHoeItem extends HoeItem implements EssenceItem {

    public EssenceHoeItem(final Properties properties) {
        super(ModItems.ESSENCE_MATERIAL, -3f, 0f, properties);
    }

}
