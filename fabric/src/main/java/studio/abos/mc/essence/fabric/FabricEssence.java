package studio.abos.mc.essence.fabric;

import net.blay09.mods.balm.Balm;
import net.blay09.mods.balm.fabric.platform.runtime.FabricLoadContext;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.ArgumentTypeRegistry;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;
import studio.abos.mc.essence.Essence;
import studio.abos.mc.essence.command.EssenceMoveArgumentType;

public class FabricEssence implements ModInitializer {
    @Override
    public void onInitialize() {
        Balm.initializeMod(Essence.MOD_ID, FabricLoadContext.INSTANCE, Essence::initialize);
        ArgumentTypeRegistry.registerArgumentType(Essence.id("essence_move"), EssenceMoveArgumentType.class, SingletonArgumentInfo.contextFree(EssenceMoveArgumentType::new));
    }
}
