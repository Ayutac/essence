package studio.abos.mc.essence.api;

import java.lang.reflect.InvocationTargetException;

public class EssenceApi {

    public static final String MOD_ID = "abosessence";

    private static final InternalMethods __internalMethods;

    static {
        try {
            __internalMethods = (InternalMethods) Class.forName("studio.abos.mc.essence.InternalMethodsImpl").getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
