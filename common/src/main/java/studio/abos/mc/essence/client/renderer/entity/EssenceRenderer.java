package studio.abos.mc.essence.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import org.jetbrains.annotations.NotNull;
import studio.abos.mc.essence.client.renderer.entity.state.EssenceRenderState;
import studio.abos.mc.essence.entity.EssenceEntity;

public abstract class EssenceRenderer<T extends EssenceEntity, S extends EssenceRenderState> extends EntityRenderer<@NotNull T, @NotNull S> {
    protected EssenceRenderer(final EntityRendererProvider.Context context) {
        super(context);
    }
}
