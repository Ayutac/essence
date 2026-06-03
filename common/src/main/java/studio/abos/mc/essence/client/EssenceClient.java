package studio.abos.mc.essence.client;

import net.blay09.mods.balm.client.BalmClientRegistrars;
import studio.abos.mc.essence.client.renderer.entity.ModEntityRenderers;

public class EssenceClient {

    public static void initialize(BalmClientRegistrars registrars) {
        ModKeyMappings.initialize();
        registrars.entityRenderers(ModEntityRenderers::initialize);
    }

}
