package io.github.mortuusars.exposure_catalogue.forge;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.network.NetworkHooks;

import java.util.function.Consumer;

public class PlatformHelperImpl {
    public static void openMenu(ServerPlayer serverPlayer, MenuProvider menuProvider, Consumer<FriendlyByteBuf> extraDataWriter) {
        NetworkHooks.openScreen(serverPlayer, menuProvider, extraDataWriter);
    }

    public static boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }
}
