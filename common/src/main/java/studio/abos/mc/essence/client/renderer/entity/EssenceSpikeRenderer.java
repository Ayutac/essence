package studio.abos.mc.essence.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import studio.abos.mc.essence.client.renderer.entity.state.EssenceRenderState;
import studio.abos.mc.essence.entity.EssenceSpikeEntity;

public class EssenceSpikeRenderer extends EssenceRenderer<EssenceSpikeEntity, EssenceRenderState> {

    public EssenceSpikeRenderer(final EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public EssenceRenderState createRenderState() {
        return new EssenceRenderState();
    }
}
