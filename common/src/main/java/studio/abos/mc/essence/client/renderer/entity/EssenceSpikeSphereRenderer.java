package studio.abos.mc.essence.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import org.jetbrains.annotations.NotNull;
import studio.abos.mc.essence.client.renderer.entity.state.EssenceRenderState;
import studio.abos.mc.essence.entity.EssenceSpikeSphereEntity;

public class EssenceSpikeSphereRenderer extends EssenceRenderer<EssenceSpikeSphereEntity, EssenceRenderState> {

    public EssenceSpikeSphereRenderer(final EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public @NotNull EssenceRenderState createRenderState() {
        return new EssenceRenderState();
    }
}
