package io.github.mortuusars.exposure_catalog.data.client;

import io.github.mortuusars.exposure_catalog.data.ExposureInfo;
import io.github.mortuusars.exposure_catalog.data.ExposureThumbnail;
import io.github.mortuusars.exposure_catalog.gui.screen.CatalogScreen;
import io.github.mortuusars.exposure_catalog.gui.screen.OverlayScreen;
import io.github.mortuusars.exposure_catalog.network.Packets;
import io.github.mortuusars.exposure_catalog.network.packet.server.QueryThumbnailC2SP;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class ClientCatalog {
    private static final Map<String, ExposureInfo> exposures = new HashMap<>();
    private static final Map<String, ExposureThumbnail> thumbnails = new HashMap<>();

    public static Map<String, ExposureInfo> getExposures() {
        return exposures;
    }

    public static Map<String, ExposureThumbnail> getThumbnails() {
        return thumbnails;
    }

    public static Optional<ExposureThumbnail> getOrQueryThumbnail(String exposureId) {
        @Nullable ExposureThumbnail thumbnail = thumbnails.get(exposureId);

        if (thumbnail == null) {
            Packets.sendToServer(new QueryThumbnailC2SP(exposureId));
            return Optional.empty();
        }

        return Optional.of(thumbnail);
    }

    public static void setThumbnail(String exposureId, ExposureThumbnail thumbnail) {
        thumbnails.put(exposureId, thumbnail);
    }

    public static void setExposures(List<ExposureInfo> exposuresList) {
        exposures.clear();

        for (ExposureInfo data : exposuresList) {
            exposures.put(data.getExposureId(), data);
        }

        getCatalogScreen().ifPresent(catalogScreen -> catalogScreen.onExposuresReceived(exposures));
    }

    public static Optional<CatalogScreen> getCatalogScreen() {
        Screen openedScreen = Minecraft.getInstance().screen instanceof OverlayScreen overlayScreen ?
                overlayScreen.getParent() : Minecraft.getInstance().screen;

        return openedScreen instanceof CatalogScreen catalogScreen ? Optional.of(catalogScreen) : Optional.empty();
    }
}