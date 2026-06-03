package studio.abos.mc.essence.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.blay09.mods.kuma.api.InputBinding;
import net.blay09.mods.kuma.api.Kuma;
import net.blay09.mods.kuma.api.ManagedKeyMapping;
import studio.abos.mc.essence.Essence;

import static studio.abos.mc.essence.Essence.id;

public class ModKeyMappings {

    public static ManagedKeyMapping yourKey;

    public static void initialize() {
        yourKey = Kuma.createKeyMapping(id("your_key"))
                .withDefault(InputBinding.key(InputConstants.KEY_B))
                .handleScreenInput(event -> {
                    Essence.logger.info("B was pressed - " + Essence.MOD_ID);
                    return true;
                })
                .build();
    }
}
