package studio.abos.mc.essence;

import net.blay09.mods.balm.Balm;
import net.blay09.mods.balm.core.BalmRegistrars;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import studio.abos.mc.essence.api.EssenceApi;
import studio.abos.mc.essence.block.ModBlocks;
import studio.abos.mc.essence.attachment.ModDataAttachments;
import studio.abos.mc.essence.command.EssenceInfoCommand;
import studio.abos.mc.essence.command.EssenceSetCommand;
import studio.abos.mc.essence.entity.ModEntities;
import studio.abos.mc.essence.item.ModItems;

public class Essence {

    public static final Logger logger = LoggerFactory.getLogger(Essence.class);

    public static final String MOD_ID = EssenceApi.MOD_ID;

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }

    public static EssenceConfig config() {
        return Balm.config().getActiveConfig(EssenceConfig.class);
    }

    public static void initialize(BalmRegistrars registrars) {
        Balm.config().registerConfig(EssenceConfig.class);

        registrars.blocks(ModBlocks::initialize);
        registrars.items(ModItems::initialize);
        registrars.entityTypes(ModEntities::initialize);
        registrars.creativeModeTabs(ModItems::initialize);
        registrars.dataAttachmentTypes(ModDataAttachments::initialize);
        Balm.commands().register(EssenceInfoCommand::register);
        Balm.commands().register(EssenceSetCommand::register);
    }

}
