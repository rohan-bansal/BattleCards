import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.*;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;


public class FileUtils {

    static void createAccount(String username, int PIN) throws java.lang.Exception {
        JSONObject ob1 = new JSONObject();
        JSONObject ob2 = new JSONObject();

        JSONArray overall = new JSONArray();
        JSONArray inventory = new JSONArray();
        JSONArray battleD = new JSONArray();

        ob2.put("username", username);
        ob2.put("PIN", PIN);
        ob2.put("OverallCards", overall);
        ob2.put("Inventory", inventory);
        ob2.put("BattleDeck", battleD);
        ob2.put("PlayerLevel", 1);
        ob2.put("PlayerDust", 25);
        ob2.put("PlayerEXP", 0);
        ob2.put("GameLevel", 1);
        ob1.put(username, ob2);


        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(ob1.toJSONString());
        String prettyJsonString = gson.toJson(je);
        Main.jsondata = prettyJsonString;

        try(FileWriter writer = new FileWriter(Main.datafile, true)) {
            writer.write(prettyJsonString);
            writer.flush();
            System.out.println("Creating Account...\nLinking to JSON file...\nSuccess.");
        }
        prettyify();
    }

    static boolean checkinDataBase(String username, int... pincheck) {
        JSONParser jsonParser = new JSONParser();
        try {

            Object object = jsonParser.parse(new FileReader(Main.datafile));
            JSONObject jsonObject = (JSONObject) object;

            JSONObject getparse = (JSONObject) jsonObject.get(username);
            String getuser = (String) getparse.get("username");
            long getPIN = (Long) getparse.get("PIN");
            if(pincheck.length > 0) {
                if(getuser.equals(username) && getPIN == pincheck[0]) {
                    return true;
                } else {
                    return false;
                }
            }
            if(getuser.equals(username)) {
                return true;
            }

        } catch (Exception e) {

        }
        return false;
    }

    static void saveGame(GameUtils gm) throws java.lang.Exception {

        ArrayList<String> saveOverall = new ArrayList<>();
        ArrayList<String> saveInventory = new ArrayList<>();
        ArrayList<String> saveBattleDeck = new ArrayList<>();

        for(Card item : gm.overall) {
            saveOverall.add(item.getName() + "~" + item.getLevel() + "~" + item.getDamage() + "~" + item.getBlock() + "~" + item.getCardType() + "~" + item.getLore());
        }
        for(Card item : gm.currentdeck) {
            saveBattleDeck.add(item.getName() + "~" + item.getLevel() + "~" + item.getDamage() + "~" + item.getBlock() + "~" + item.getCardType() + "~" + item.getLore());
        }
        for(Card item : gm.inventory) {
            saveInventory.add(item.getName() + "~" + item.getLevel() + "~" + item.getDamage() + "~" + item.getBlock() + "~" + item.getCardType() + "~" + item.getLore());
        }

        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(Main.datafile));
        JSONObject Jobj = (JSONObject) obj;

        JSONObject userData = (JSONObject) Jobj.get(Main.username);
        userData.put("PlayerEXP", Main.playerEXP);
        userData.put("PlayerLevel", Main.playerlevel);
        userData.put("PlayerDust", Main.dust);
        userData.put("GameLevel", Main.playGamelevel);

        JSONArray setoverall = (JSONArray) userData.get("OverallCards");
        setoverall.clear();
        setoverall.addAll(saveOverall);
        JSONArray setinventory = (JSONArray) userData.get("Inventory");
        setinventory.clear();
        setinventory.addAll(saveInventory);
        JSONArray setbattledeck = (JSONArray) userData.get("BattleDeck");
        setbattledeck.clear();
        setbattledeck.addAll(saveBattleDeck);

        userData.put("OverallCards", setoverall);
        userData.put("Inventory", setinventory);
        userData.put("BattleDeck", setbattledeck);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(Jobj.toJSONString());
        String prettyJsonString = gson.toJson(je);
        Main.jsondata = prettyJsonString;

        try(FileWriter writer = new FileWriter(Main.datafile)) {
            writer.write(prettyJsonString);
            writer.flush();
            System.out.println("Game Saved.");
        }
    }

    static void loadData(GameUtils gm) throws Exception {

        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(Main.datafile));
        JSONObject Jobj = (JSONObject) obj;

        JSONObject userData = (JSONObject) Jobj.get(Main.username);

        Main.playerlevel = ((Long) userData.get("PlayerLevel")).intValue();
        Main.dust = ((Long) userData.get("PlayerDust")).intValue();
        Main.playGamelevel = ((Long) userData.get("GameLevel")).intValue();
        Main.playerEXP = ((Long) userData.get("PlayerEXP")).intValue();
        JSONArray Overall = (JSONArray) userData.get("OverallCards");
        JSONArray Inventory = (JSONArray) userData.get("Inventory");
        JSONArray BattleDeck = (JSONArray) userData.get("BattleDeck");
        String[] getOverall = new String[Overall.size()];
        String[] getInventory = new String[Overall.size()];
        String[] getBattleDeck = new String[Overall.size()];

        gm.overall.clear();
        gm.inventory.clear();
        gm.currentdeck.clear();

        for (int i = 0; i < Overall.size(); i++) {
            getOverall[i] = (String) Overall.get(i);
        }
        for (int i = 0; i < Inventory.size(); i++) {
            getInventory[i] = (String) Inventory.get(i);
        }
        for (int i = 0; i < BattleDeck.size(); i++) {
            getBattleDeck[i] = (String) BattleDeck.get(i);
        }

        for(String item : getOverall) {
            String[] split = item.split("~");
            gm.overall.add(new Card(split[0], Integer.parseInt(split[2]), Integer.parseInt(split[3]), split[4], split[5], Integer.parseInt(split[1])));
        }
        for(String item : getInventory) {
            String[] split = item.split("~");
            gm.inventory.add(new Card(split[0], Integer.parseInt(split[2]), Integer.parseInt(split[3]), split[4], split[5], Integer.parseInt(split[1])));
        }
        for(String item : getBattleDeck) {
            String[] split = item.split("~");
            gm.currentdeck.add(new Card(split[0], Integer.parseInt(split[2]), Integer.parseInt(split[3]), split[4], split[5], Integer.parseInt(split[1])));
        }

    }

    private static void prettyify() throws java.lang.Exception {
        BufferedReader in = new BufferedReader(new FileReader(Main.datafile));
        String str;

        List<String> list = new ArrayList<String>();
        while((str = in.readLine()) != null) {
            list.add(str);
        }
        for(int x = 1; x < list.size() - 1; x++) {
            if(list.get(x).equals("}{")) {
                list.remove(x);
                list.set(x-1, "  },");
            }
            if(list.get(x).equals("}")) {
                list.remove(x);
                list.set(x-1, "  },");
            }
            if(list.get(x).equals("{")) {
                list.remove(x);
            }
        }
        BufferedWriter outputWriter = new BufferedWriter(new FileWriter(Main.datafile));
        for(String line : list) {
            outputWriter.write(line + "\n");
        }
        outputWriter.close();

    }
}
