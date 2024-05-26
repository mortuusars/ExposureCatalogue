package io.github.mortuusars.exposure_catalog.network.packet.client;

import io.github.mortuusars.exposure_catalog.ExposureCatalog;
import io.github.mortuusars.exposure_catalog.network.PacketDirection;
import io.github.mortuusars.exposure_catalog.network.handler.ClientPacketsHandler;
import io.github.mortuusars.exposure_catalog.network.packet.IPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public record NotifySendingStartS2CP(int page, List<String> exposureIds) implements IPacket {
    public static final ResourceLocation ID = ExposureCatalog.resource("notify_sending_start");

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public FriendlyByteBuf toBuffer(FriendlyByteBuf buffer) {
        buffer.writeInt(page);
        buffer.writeInt(exposureIds.size());
        for (String exposureId : exposureIds) {
            buffer.writeUtf(exposureId);
        }
        return buffer;
    }

    public static NotifySendingStartS2CP fromBuffer(FriendlyByteBuf buffer) {
        int page = buffer.readInt();
        int count = buffer.readInt();
        List<String> ids = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            ids.add(buffer.readUtf());
        }
        return new NotifySendingStartS2CP(page, ids);
    }

    @Override
    public boolean handle(PacketDirection direction, @Nullable Player player) {
        ClientPacketsHandler.notifySendingStart(this);
        return true;
    }
}
