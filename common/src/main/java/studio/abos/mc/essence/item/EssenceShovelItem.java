package studio.abos.mc.essence.item;

import net.minecraft.world.item.ShovelItem;

public class EssenceShovelItem extends ShovelItem implements EssenceItem {

    public EssenceShovelItem(final Properties properties) {
        super(ModItems.ESSENCE_MATERIAL, 1.5f, -3f, properties);
    }

}
