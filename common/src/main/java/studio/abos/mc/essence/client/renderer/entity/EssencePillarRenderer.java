package studio.abos.mc.essence.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import org.jetbrains.annotations.NotNull;
import studio.abos.mc.essence.client.renderer.entity.state.EssenceRenderState;
import studio.abos.mc.essence.entity.EssencePillarEntity;

public class EssencePillarRenderer extends EssenceRenderer<EssencePillarEntity, EssenceRenderState> {

    public EssencePillarRenderer(final EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public @NotNull EssenceRenderState createRenderState() {
        return new EssenceRenderState();
    }
}
