package eu.byquanton.mods.mcefadblock.mixin;

import com.cinemamod.mcef.MCEFClient;
import eu.byquanton.adblock.CosmeticResources;
import eu.byquanton.mods.mcefadblock.cef.AdBlockResourceHandlerAdapter;
import org.cef.CefClient;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefFrame;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static eu.byquanton.mods.mcefadblock.MCEFAdblock.getBlocker;

@Mixin(MCEFClient.class)
public abstract class MCEFFClientMixin {

    @Inject(method = "<init>", at = @At("RETURN"))
    private static void init(CefClient cefClient, CallbackInfo ci) {
        cefClient.addRequestHandler(new AdBlockResourceHandlerAdapter());
    }


    @Inject(method = "onLoadEnd", at = @At("RETURN"))
    private static void onLoadEnd(CefBrowser cefBrowser, CefFrame cefFrame, int httpStatusCode, CallbackInfo ci) {
        CosmeticResources resources = getBlocker().getUrlCosmeticResources(cefBrowser.getURL());

        String css = resources.selectorsToStylesheet();
        if (!css.isEmpty()) {
            String jsToInjectCSS =
                    "var style = document.createElement('style');" +
                            "style.innerHTML = `" + css + "`;" +
                            "document.documentElement.appendChild(style);";

            cefFrame.executeJavaScript(jsToInjectCSS, cefFrame.getURL(), 0);
        }

        if (resources.injectedScript() != null && !resources.injectedScript().isBlank()) {
            cefFrame.executeJavaScript(resources.injectedScript(), cefFrame.getURL(), 0);
        }
    }


}
