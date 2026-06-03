package studio.abos.mc.essence.fabric.client;

import net.blay09.mods.balm.client.BalmClient;
import net.blay09.mods.balm.fabric.platform.runtime.FabricLoadContext;
import net.fabricmc.api.ClientModInitializer;
import studio.abos.mc.essence.YourMod;
import studio.abos.mc.essence.client.YourModClient;

public class FabricYourModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BalmClient.initializeMod(YourMod.MOD_ID, FabricLoadContext.INSTANCE, YourModClient::initialize);
    }
}
