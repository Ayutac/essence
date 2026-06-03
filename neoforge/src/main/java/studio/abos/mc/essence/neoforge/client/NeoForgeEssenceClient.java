package studio.abos.mc.essence.neoforge.client;

import net.blay09.mods.balm.client.BalmClient;
import net.blay09.mods.balm.neoforge.platform.runtime.NeoForgeLoadContext;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import studio.abos.mc.essence.Essence;
import studio.abos.mc.essence.client.EssenceClient;

@Mod(value = Essence.MOD_ID, dist = Dist.CLIENT)
public class NeoForgeEssenceClient {

    public NeoForgeEssenceClient(ModContainer modContainer, IEventBus modEventBus) {
        final var context = new NeoForgeLoadContext(modContainer, modEventBus);
        BalmClient.initializeMod(Essence.MOD_ID, context, EssenceClient::initialize);
    }
}
