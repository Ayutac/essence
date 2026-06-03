package studio.abos.mc.essence.neoforge;

import net.blay09.mods.balm.Balm;
import net.blay09.mods.balm.neoforge.platform.runtime.NeoForgeLoadContext;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import studio.abos.mc.essence.Essence;

@Mod(Essence.MOD_ID)
public class NeoForgeEssence {

    public NeoForgeEssence(ModContainer modContainer, IEventBus modEventBus) {
        final var context = new NeoForgeLoadContext(modContainer, modEventBus);
        Balm.initializeMod(Essence.MOD_ID, context, Essence::initialize);
    }
}
