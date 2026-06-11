package studio.abos.mc.essence.item;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ShearsItem;

public class EssenceShearsItem extends ShearsItem implements EssenceItem {

    public EssenceShearsItem(final Properties properties) {
        super(properties.component(DataComponents.TOOL, ShearsItem.createToolProperties()));
    }

}
