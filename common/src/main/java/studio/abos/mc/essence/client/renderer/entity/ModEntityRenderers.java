package studio.abos.mc.essence.client.renderer.entity;

import net.blay09.mods.balm.client.renderer.entity.BalmEntityRendererRegistrar;
import studio.abos.mc.essence.entity.ModEntities;

public class ModEntityRenderers {

    public static void initialize(final BalmEntityRendererRegistrar renderers) {
        renderers.register(ModEntities.ESSENCE_BALL, EssenceBallRenderer::new);
        renderers.register(ModEntities.ESSENCE_PILLAR, EssencePillarRenderer::new);
        renderers.register(ModEntities.ESSENCE_LANCE, EssenceLanceRenderer::new);
        renderers.register(ModEntities.ESSENCE_SPIKE_SPHERE, EssenceSpikeSphereRenderer::new);
    }

}
