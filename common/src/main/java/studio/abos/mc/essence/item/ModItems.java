package studio.abos.mc.essence.item;

import net.blay09.mods.balm.world.item.BalmCreativeModeTabRegistrar;
import net.blay09.mods.balm.world.item.BalmItemRegistrar;
import net.blay09.mods.balm.world.item.DeferredItem;
import net.minecraft.world.item.ToolMaterial;
import studio.abos.mc.essence.tag.ModBlockTags;
import studio.abos.mc.essence.tag.ModItemTags;

public class ModItems {

    public static ToolMaterial ESSENCE_MATERIAL = new ToolMaterial(ModBlockTags.INCORRECT_FOR_ESSENCE_TOOL, Integer.MAX_VALUE, 8f, 3f, 1, ModItemTags.ESSENCE_TOOL_MATERIALS);

    public static DeferredItem ESSENCE_AXE;
    public static DeferredItem ESSENCE_PICKAXE;
    public static DeferredItem ESSENCE_SHOVEL;

    public static void initialize(final BalmItemRegistrar items) {
        ESSENCE_AXE = items.register("essence_axe", EssenceAxeItem::new).asDeferredItem();
        ESSENCE_PICKAXE = items.register("essence_pickaxe", EssencePickaxeItem::new).asDeferredItem();
        ESSENCE_SHOVEL = items.register("essence_shovel", EssenceShovelItem::new).asDeferredItem();
    }

    public static void initialize(final BalmCreativeModeTabRegistrar creativeModeTabs) {
//        creativeModeTabs.register(Essence.MOD_ID, builder ->
//                builder.title(Component.translatable(id(Essence.MOD_ID).toLanguageKey("itemGroup")))
//                        .icon(() -> ModItems.yourItem.createStack())
//                        .displayItems((displayParameters, output) -> {
//                            output.accept(ModItems.yourItem);
//                        })
//        );
    }

}
