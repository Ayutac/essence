package studio.abos.mc.essence.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import org.jetbrains.annotations.NotNull;
import studio.abos.mc.essence.client.renderer.entity.state.EssenceRenderState;
import studio.abos.mc.essence.entity.EssenceLanceEntity;

public class EssenceLanceRenderer extends EssenceRenderer<EssenceLanceEntity, EssenceRenderState> {

    public EssenceLanceRenderer(final EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public @NotNull EssenceRenderState createRenderState() {
        return new EssenceRenderState();
    }
}
