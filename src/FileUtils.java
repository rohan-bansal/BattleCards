import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;


public class FileUtils {

    static void createAccount(String username, int PIN) throws java.lang.Exception {
        JSONObject ob1 = new JSONObject();
        JSONArray ob2 = new JSONArray();
        JSONObject ob3 = new JSONObject();
        ob3.put("username", username);
        ob3.put("PIN", PIN);
        ob2.add(ob3);
        ob1.put(username, ob3);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(ob1.toJSONString());
        String prettyJsonString = gson.toJson(je);
        Main.jsondata = prettyJsonString;

        try(FileWriter writer = new FileWriter(Main.datafile, true)) {
            writer.write(prettyJsonString);
            writer.flush();
            System.out.println("Creating Account...\nSuccess.");
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

    static void prettyify() throws java.lang.Exception {
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
