package eu.byquanton.mods.mcefadblock.cef;

import org.cef.browser.CefBrowser;
import org.cef.browser.CefFrame;
import org.cef.handler.CefRequestHandlerAdapter;
import org.cef.handler.CefResourceRequestHandler;
import org.cef.misc.BoolRef;
import org.cef.network.CefRequest;

import static eu.byquanton.mods.mcefadblock.MCEFAdblock.getBlocker;

public class AdBlockResourceHandlerAdapter extends CefRequestHandlerAdapter {
    @Override
    public CefResourceRequestHandler getResourceRequestHandler(CefBrowser browser, CefFrame frame, CefRequest request, boolean isNavigation, boolean isDownload, String requestInitiator, BoolRef disableDefaultHandling) {
        String url = request.getURL();
        boolean isBlocked = getBlocker().checkUrls(url, url, getRequestType(request.getResourceType()));
        System.out.println((isBlocked ? "Blocked - " : "") + url);
        if (isBlocked) {
            disableDefaultHandling.set(true);
            return new AdBlockResourceRequestHandler();
        }

        return null;
    }

    public String getRequestType(CefRequest.ResourceType resourceType) {
        return switch (resourceType) {
            case RT_MAIN_FRAME, RT_NAVIGATION_PRELOAD_MAIN_FRAME -> "document";
            case RT_SUB_FRAME, RT_NAVIGATION_PRELOAD_SUB_FRAME -> "subdocument";
            case RT_STYLESHEET -> "stylesheet";
            case RT_SCRIPT -> "script";
            case RT_IMAGE -> "image";
            case RT_FONT_RESOURCE -> "font";
            case RT_SUB_RESOURCE -> "other";
            case RT_OBJECT -> "object";
            case RT_MEDIA -> "media";
            case RT_WORKER, RT_SHARED_WORKER, RT_PREFETCH, RT_FAVICON, RT_PLUGIN_RESOURCE -> "other";
            case RT_XHR -> "xmlhttprequest";
            case RT_PING -> "ping";
            case RT_SERVICE_WORKER -> "other";
            case RT_CSP_REPORT -> "csp";
            default -> "other";
        };
    }
}