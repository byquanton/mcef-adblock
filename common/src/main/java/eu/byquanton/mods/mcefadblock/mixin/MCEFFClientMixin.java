package eu.byquanton.mods.mcefadblock.mixin;

import com.cinemamod.mcef.MCEFClient;
import eu.byquanton.mods.mcefadblock.cef.AdBlockResourceHandlerAdapter;
import org.cef.CefClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MCEFClient.class)
public abstract class MCEFFClientMixin {

    @Inject(method = "<init>", at = @At("RETURN"))
    private static void init(CefClient cefClient, CallbackInfo ci) {
        cefClient.addRequestHandler(new AdBlockResourceHandlerAdapter());
    }


}
