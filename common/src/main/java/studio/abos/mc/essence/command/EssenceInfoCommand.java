package studio.abos.mc.essence.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import lombok.experimental.UtilityClass;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import studio.abos.mc.essence.attachment.EssenceAttachment;

@UtilityClass
public class EssenceInfoCommand {

    public void register(final CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("essence")
                .then(Commands.literal("info")
                        .executes(EssenceInfoCommand::run)));
    }

    public int run(final CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        final ServerPlayer player = context.getSource().getPlayer();
        if (player == null) {
            return 0;
        }

        final MutableComponent text = Component.empty();
        text.append(EssenceAttachment.of(player).toString());
        player.sendSystemMessage(text);

        return 1;
    }

}
