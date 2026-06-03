package studio.abos.mc.essence.attachment;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import net.blay09.mods.balm.platform.attachment.BalmDataAttachmentTypeRegistrar;
import net.blay09.mods.balm.platform.attachment.DataAttachmentLookup;

@UtilityClass
public class ModDataAttachments {

    public DataAttachmentLookup<@NonNull EssenceAttachment> ESSENCE;

    public void initialize(BalmDataAttachmentTypeRegistrar registrar) {
        ESSENCE = registrar.register("essence", EssenceAttachment.CODEC, EssenceAttachment::new, true).asLookup();
    }

}
