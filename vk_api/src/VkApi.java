import json.JSONArray;
import json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class VkApi {

    final String token = "e4038440e4038440e4038440efe468c28fee403e4038440b90d2a662f8942353e6902f9";
    static final String userId_ = "id_vikaanisimova";

    public static void main(String[] args) throws IOException {
        VkApi api = new VkApi();
        final String userId = api.getUserId(userId_);
        api.getFriendsList(userId);
    }

    public String getUserId(String userId) throws IOException {
        final String method = "users.get";
        final String stringUrl = "https://api.vk.com/method/" + method + "?user_ids=" + userId + "&v=5.95&" + "access_token=" + token;
        JSONObject jsonObject = getJsonObj(stringUrl);
        JSONArray arr = jsonObject.getJSONArray("response");
        JSONObject obj = arr.getJSONObject(0);
        return obj.get("id").toString();
    }

    public void getFriendsList(String userId) throws IOException {
        final String method = "friends.get";
        final String stringUrl = "https://api.vk.com/method/" + method + "?user_id=" + userId + "&v=5.95&" + "access_token=" + token + "&order=name&" + "fields=last_name,first_name";
        JSONObject jsonObject = getJsonObj(stringUrl);
        JSONObject obj = jsonObject.getJSONObject("response");
        JSONArray arr = obj.getJSONArray("items");
        System.out.println("Friends count " + arr.length());
        System.out.println();
        for (int i = 0; i < arr.length(); i++) {
            String firstName = arr.getJSONObject(i).getString("first_name");
            String lastName = arr.getJSONObject(i).getString("last_name");
            System.out.println(firstName + " " + lastName);
        }
    }

    public JSONObject getJsonObj(String stringUrl) throws IOException {
        URL url = new URL(stringUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine = "";
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return new JSONObject(response.toString());
    }
}
