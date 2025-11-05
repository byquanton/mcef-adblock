package eu.byquanton.mods.mcefadblock;

import eu.byquanton.adblock.AdvtBlocker;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class MCEFAdblock {
    public static final String MOD_ID = "mcef_adblock";

    private static final List<String> ruleLists = new ArrayList<>(List.of(
            "https://easylist.to/easylist/easylist.txt",
            "https://easylist.to/easylist/easyprivacy.txt"
    ));

    private static final List<String> rules = new ArrayList<>();
    private static AdvtBlocker blocker;

    public static AdvtBlocker getBlocker() {
        return getBlocker(false);
    }

    public static AdvtBlocker getBlocker(boolean recreate) {
        if (blocker == null || recreate) {
            if (recreate && blocker != null) {
                blocker.destroyInstance();
                blocker = null;
                rules.clear();
            }
            fetchRulesFromRuleLists();
            blocker = AdvtBlocker.createInstance(rules);
        }
        return blocker;
    }


    private static void fetchRulesFromRuleLists() {
        HttpClient client = HttpClient.newHttpClient();

        for (String urlString : ruleLists) {
            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(urlString))
                        .GET()
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                String[] lines = response.body().split("\n");
                rules.addAll(Arrays.asList(lines));

            } catch (Exception e) {
                System.err.println("Failed to fetch rules from " + urlString + ": " + e.getMessage());
            }
        }
    }

    public static void init() {

    }
}
