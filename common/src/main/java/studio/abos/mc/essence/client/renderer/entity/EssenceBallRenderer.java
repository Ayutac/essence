package studio.abos.mc.essence.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import studio.abos.mc.essence.client.renderer.entity.state.EssenceRenderState;
import studio.abos.mc.essence.entity.EssenceBall;

public class EssenceBallRenderer extends EssenceEntityRenderer<EssenceBall, EssenceRenderState> {

    public EssenceBallRenderer(final EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public EssenceRenderState createRenderState() {
        return new EssenceRenderState();
    }
}
