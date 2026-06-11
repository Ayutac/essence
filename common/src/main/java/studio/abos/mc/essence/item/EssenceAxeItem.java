package studio.abos.mc.essence.item;

import net.minecraft.world.item.AxeItem;

public class EssenceAxeItem extends AxeItem implements EssenceItem {

    public EssenceAxeItem(final Properties properties) {
        super(ModItems.ESSENCE_MATERIAL, 5f, -3f, properties);
    }

}
