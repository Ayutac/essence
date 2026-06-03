package studio.abos.mc.essence.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import lombok.experimental.UtilityClass;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.server.permissions.Permissions;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import studio.abos.mc.essence.attachment.EssenceAttachment;

import java.util.Collection;
import java.util.function.BiConsumer;

@UtilityClass
public class EssenceSetCommand {

    public void register(final CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("essence")
                .then(Commands.literal("set")
                        .requires(source -> source.permissions().hasPermission(Permissions.COMMANDS_GAMEMASTER))
                        .then(Commands.argument("targets", EntityArgument.entities())
                                .then(Commands.literal("blue")
                                        .then(Commands.argument("amount", FloatArgumentType.floatArg(0f))
                                                .executes(ctx -> run(ctx, EssenceAttachment::setBlue))))
                                .then(Commands.literal("red")
                                        .then(Commands.argument("amount", FloatArgumentType.floatArg(0f))
                                                .executes(ctx -> run(ctx, EssenceAttachment::setRed))))
                                .then(Commands.literal("yellow")
                                        .then(Commands.argument("amount", FloatArgumentType.floatArg(0f))
                                                .executes(ctx -> run(ctx, EssenceAttachment::setYellow))))
                                .then(Commands.literal("purple")
                                        .then(Commands.argument("amount", FloatArgumentType.floatArg(0f))
                                                .executes(ctx -> run(ctx, EssenceAttachment::setPurple))))
                                .then(Commands.literal("green")
                                        .then(Commands.argument("amount", FloatArgumentType.floatArg(0f))
                                                .executes(ctx -> run(ctx, EssenceAttachment::setGreen))))
                                .then(Commands.literal("orange")
                                        .then(Commands.argument("amount", FloatArgumentType.floatArg(0f))
                                                .executes(ctx -> run(ctx, EssenceAttachment::setOrange))))
                                .then(Commands.literal("willpower_bonus")
                                        .then(Commands.argument("amount", FloatArgumentType.floatArg(0f))
                                                .executes(ctx -> run(ctx, EssenceAttachment::setWillpowerBonus))))
                        )));
    }

    public int run(final CommandContext<CommandSourceStack> context, BiConsumer<EssenceAttachment, Float> consumer) throws CommandSyntaxException {
        final Collection<? extends Entity> targets = EntityArgument.getEntities(context, "targets");
        if (targets.isEmpty()) {
            return 0;
        }

        targets.stream()
                .filter(LivingEntity.class::isInstance)
                .map(LivingEntity.class::cast)
                .map(EssenceAttachment::of)
                .forEach(essence -> consumer.accept(essence, FloatArgumentType.getFloat(context, "amount")));

        return 1;
    }

}
