package studio.abos.mc.essence.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import lombok.experimental.UtilityClass;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;
import studio.abos.mc.essence.move.EssenceMoveType;

@UtilityClass
public class EssenceMoveCommand {

    public void register(final CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("essence")
                .then(Commands.literal("move")
                        .then(Commands.argument("move", new EssenceMoveArgumentType())
                            .executes(EssenceMoveCommand::run))
                ));
    }

    public int run(final CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        final ServerPlayer player = context.getSource().getPlayer();
        if (player == null || player.isSpectator()) {
            return 0;
        }

        final EssenceMoveType move = context.getArgument("move", EssenceMoveType.class);
        move.perform(player);

        return 1;
    }

}
