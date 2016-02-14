/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.entityFactory;

import core.ko.KOGroup;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import tools.Params;

/**
 *
 * @author elmarce
 */
public class KOFactoryManager implements EntityFactoryManager<KOGroup> {

    private final String filename;

    public KOFactoryManager(String filename) {
        this.filename = filename;
    }

    public static void main(String[] args) {
        KOFactoryManager kofm = new KOFactoryManager(Params.KO_GROUPS);
        //Map<String, KOGroup> test = kofm.getEntities();
        List<KOGroup> koList = kofm.getEntitiesAsList();
//        for (Map.Entry<String, KOGroup> entrySet : test.entrySet()) {
//            String key = entrySet.getKey();
//            KOGroup ko = entrySet.getValue();
//            System.out.println(ko.toString());
//        }
    }

    @Override
    public Map<String, KOGroup> getEntities() {
        Map<String, KOGroup> ko_hash = new HashMap<>();
        KOFactory factory = new KOFactory();
        KOGroup ko = factory.createEntity();

        Charset charset = Charset.forName("US-ASCII");
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(this.filename), charset)) {
            String line = null;
            String ko_number = "";
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("ENTRY")) {
                    String[] fields = line.split("\\s+");
                    ko_number = fields[1];
                    ko.setKo_number(fields[1]);
                }
                if (line.startsWith("NAME")) {
                    String[] fields = line.split("\\s+", 2);
                    ko.setName(fields[1]);
                }
                if (line.startsWith("DEFINITION")) {
                    String[] fields = line.split("\\s+", 2);
                    ko.setDescription(fields[1]);
                }

                if (line.startsWith("///")) {
                    if (!ko_hash.containsKey(ko_number)) {
                        ko_hash.put(ko_number, ko);
                    }
                    ko = new KOGroup();
                }
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }

        return ko_hash;

    }

    @Override
    public List<KOGroup> getEntitiesAsList() {
        List<KOGroup> koList = new ArrayList<>();
        KOFactory factory = new KOFactory();
        KOGroup ko = factory.createEntity();
        
        JSONObject jsonObj = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        JSONParser jsonParser = new JSONParser();
        JSONArray newJsonArray = new JSONArray();

        Charset charset = Charset.forName("US-ASCII");
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(this.filename), charset)) {
            String line = null;
            String ko_number = "";
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("ENTRY")) {
                    String[] fields = line.split("\\s+");
                    ko_number = fields[1];
                    ko.setKo_number(fields[1]);
                    jsonObj.put("ko_number", ko_number);
                }
                if (line.startsWith("NAME")) {
                    String[] fields = line.split("\\s+", 2);
                    ko.setName(fields[1]);
                    jsonObj.put("name", fields[1]);
                }
                if (line.startsWith("DEFINITION")) {
                    String[] fields = line.split("\\s+", 2);
                    ko.setDescription(fields[1]);
                    if(fields[1].contains("/")){
                        String newDesc = fields[1].replaceAll("/", " or ");
                        jsonObj.put("description", newDesc);
                    }else
                    jsonObj.put("description", fields[1]);

                }

                if (line.startsWith("///")) {
                    if (!koList.contains(ko)) {
                        koList.add(ko);
                    }
                    ko = new KOGroup();
                    jsonArray.add(jsonObj);
                    jsonObj = new JSONObject();
                }
            }

            try (FileWriter file = new FileWriter("/home/benji/NetBeansProjects/results/KOGroup.json")) {
                file.write(jsonArray.toJSONString());
            }catch(Exception e){
                System.out.println(""+e.getMessage());
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
        return koList;
    }

}
