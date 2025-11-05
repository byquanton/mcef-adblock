package eu.byquanton.mods.mcefadblock.cef;

import org.cef.callback.CefCallback;
import org.cef.handler.CefResourceHandler;
import org.cef.misc.IntRef;
import org.cef.misc.StringRef;
import org.cef.network.CefRequest;
import org.cef.network.CefResponse;

public class AdBlockResourceHandler implements CefResourceHandler {

    @Override
    public boolean processRequest(CefRequest cefRequest, CefCallback cefCallback) {
        return false;
    }

    @Override
    public void getResponseHeaders(CefResponse cefResponse, IntRef intRef, StringRef stringRef) {

    }

    @Override
    public boolean readResponse(byte[] bytes, int i, IntRef intRef, CefCallback cefCallback) {
        return false;
    }

    @Override
    public void cancel() {

    }
}
