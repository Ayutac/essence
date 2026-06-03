package studio.abos.mc.essence.forge;

import net.blay09.mods.balm.Balm;
import net.blay09.mods.balm.client.BalmClient;
import net.blay09.mods.balm.forge.platform.runtime.ForgeLoadContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import studio.abos.mc.essence.Essence;
import studio.abos.mc.essence.client.EssenceClient;

@Mod(Essence.MOD_ID)
public class ForgeEssence {

    public ForgeEssence(FMLJavaModLoadingContext context) {
        final var loadContext = new ForgeLoadContext(context.getModBusGroup());
        Balm.initializeMod(Essence.MOD_ID, loadContext, Essence::initialize);
        if (FMLEnvironment.dist.isClient()) {
            BalmClient.initializeMod(Essence.MOD_ID, loadContext, EssenceClient::initialize);
        }
    }

}
