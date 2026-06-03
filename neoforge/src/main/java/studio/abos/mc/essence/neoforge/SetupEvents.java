package studio.abos.mc.essence.neoforge;

import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.RegisterEvent;
import studio.abos.mc.essence.Essence;
import studio.abos.mc.essence.command.EssenceMoveArgumentType;

@EventBusSubscriber
public class SetupEvents {

    @SubscribeEvent
    public static void register(RegisterEvent event) {
        if (event.getRegistryKey() == Registries.COMMAND_ARGUMENT_TYPE) {
            SingletonArgumentInfo<EssenceMoveArgumentType> standArgInfo = SingletonArgumentInfo.contextFree(EssenceMoveArgumentType::new);
            ArgumentTypeInfos.registerByClass(EssenceMoveArgumentType.class, standArgInfo);
            event.register(Registries.COMMAND_ARGUMENT_TYPE, Essence.id("essence_move"), () -> standArgInfo);
        }
    }

}
