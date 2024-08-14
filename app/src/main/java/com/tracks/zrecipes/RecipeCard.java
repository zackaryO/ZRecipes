// java/com/tracks/zrecipes/RecipeCard.java
package com.tracks.zrecipes;

import org.json.JSONException;
import org.json.JSONObject;

public class RecipeCard {
    private byte[] imageData;

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }
}




//
//public class RecipeCard {
//    private String card;
//    private String id = "0";
//
//    public RecipeCard() {
//
//    }
//
//    public RecipeCard(String card) {
//        this.card = card;
//    }
//
//    public RecipeCard(String card, String id) {
//        this.card = card;
//        this.id = id;
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public String extractUrlFromJsonString(String jsonString) {
//        try {
//            // Create a JSON object from the input string
//            JSONObject jsonObject = new JSONObject(jsonString);
//
//            // Extract the URL value from the "url" key
//            String url = jsonObject.getString("url");
//
//            // Return the extracted URL
//            return url;
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        // Return an empty string if the URL cannot be extracted
//        return "";
//    }
//
//    public String getCard() {
//        String url = extractUrlFromJsonString(card);
//        return url;
//    }
//
//    public String getCard2() {
//        String url = extractUrl(card);
//        return url;
//    }
//
//    public void setCard(String card) {
//        this.card = card;
//    }
//
//    public String extractUrl(String inputString) {
//        /*
//         * Extracts URL from a given string.
//         *
//         * @param inputString Input string.
//         *
//         * @return Extracted URL or an empty string if not found.
//         */
//        String url = "";
//        if (inputString != null && inputString.contains("https://")) {
//            // Find the starting position of the URL
//            int start = inputString.indexOf("https://");
//            // Find the ending position of the URL
//            int end = inputString.indexOf("}", start);
//            // Extract the URL substring
//            if (end != -1) {
//                url = inputString.substring(start, end);
//            }
//        }
//        return url;
//    }
//
//
//}
