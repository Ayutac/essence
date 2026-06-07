package studio.abos.mc.essence.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import org.jetbrains.annotations.NotNull;
import studio.abos.mc.essence.client.renderer.entity.state.EssenceRenderState;
import studio.abos.mc.essence.entity.EssenceBridgeEntity;

public class EssenceBridgeRenderer extends EssenceRenderer<EssenceBridgeEntity, EssenceRenderState> {

    public EssenceBridgeRenderer(final EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public @NotNull EssenceRenderState createRenderState() {
        return new EssenceRenderState();
    }
}
