package studio.abos.mc.essence.command;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.network.chat.Component;
import studio.abos.mc.essence.move.EssenceMoveType;
import studio.abos.mc.essence.move.EssenceMoveTypes;

import java.util.Arrays;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

public class EssenceMoveArgumentType implements ArgumentType<EssenceMoveType> {

    public static final SimpleCommandExceptionType NOT_FOUND = new SimpleCommandExceptionType(Component.literal("That essence move was not found"));

    @Override
    public EssenceMoveType parse(final StringReader stringReader) throws CommandSyntaxException {
        try {
            final EssenceMoveTypes move = EssenceMoveTypes.valueOf(stringReader.getRemaining().toUpperCase(Locale.ROOT));
            stringReader.setCursor(stringReader.getTotalLength());
            return move;
        }
        catch (final IllegalArgumentException ex) {
            throw NOT_FOUND.createWithContext(stringReader);
        }
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(final CommandContext<S> context, final SuggestionsBuilder builder) {
        final String input = builder.getRemainingLowerCase();
        Arrays.stream(EssenceMoveTypes.values())
                .filter(move -> move.name().toLowerCase(Locale.ROOT).startsWith(input))
                .map(move -> move.name().toLowerCase(Locale.ROOT))
                .forEach(builder::suggest);
        return builder.buildFuture();
    }
}
