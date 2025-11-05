package eu.byquanton.mods.mcefadblock.fabric;

import eu.byquanton.mods.mcefadblock.MCEFAdblock;
import net.fabricmc.api.ModInitializer;

public final class McefAdblockFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        MCEFAdblock.init();
    }
}
