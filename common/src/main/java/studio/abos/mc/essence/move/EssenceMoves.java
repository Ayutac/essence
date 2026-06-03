package studio.abos.mc.essence.move;

import lombok.Getter;
import lombok.NonNull;
import net.minecraft.resources.Identifier;
import studio.abos.mc.essence.Essence;

public enum EssenceMoves implements EssenceMove {

    BALL("ball");

    @Getter
    private final Identifier id;

    EssenceMoves(final @NonNull String path) {
        id = Essence.id(path);
    }

}
