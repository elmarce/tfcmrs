/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package json;

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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import tools.Params;

//jsonlint -v -s filename.json to check the validity of a filename.json
/**
 *
 * @author benji
 */
public class KOToJSON {

    public static void main(String[] args) {
        //Map gene_ko = geneKOMap(Params.KO_GENE_LIST);
        //generateKOJSONFile(Params.KO_GROUPS);
        JSONArray test = loadKO(Params.KO_GROUPS);
        for (int i = 0; i < test.size(); i++) {
            JSONObject obj = (JSONObject) test.get(i);
            System.out.println(obj.get("ko_number"));

        }
    }

    public static void generateKOJSONFile(String filename) {
        Charset charset = Charset.forName("US-ASCII");
        Map<String, List<String>> gene_ko = geneKOMap(Params.KO_GENE_LIST);
        JSONArray jsonArray = new JSONArray();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonObj = new JSONObject();

                try (BufferedReader reader = Files.newBufferedReader(Paths.get(filename), charset)) {
                    String line = null;
                    String ko_number = "";
                    while ((line = reader.readLine()) != null) {
                        if (line.startsWith("ENTRY")) {
                            String[] fields = line.split("\\s+");
                            ko_number = "ko:" + fields[1];
                            jsonObj.put("ko_number", ko_number);

                            if (gene_ko.containsKey(ko_number)) {
                                JSONArray arrayOfGenes = new JSONArray();
//                                if(gene_ko.get(ko_number) == null){
//                                    System.out.println("KO-Gruppe hat keine Gene");
//                                }
                                arrayOfGenes.addAll(gene_ko.get(ko_number));
                                jsonObj.put("genes", arrayOfGenes);
                            }

                        }
                        if (line.startsWith("NAME")) {
                            String[] fields = line.split("\\s+", 2);
                            jsonObj.put("name", fields[1]);
                        }
                        if (line.startsWith("DEFINITION")) {
                            String[] fields = line.split("\\s+", 2);
                            if (fields[1].contains("/")) {
                                String newDesc = fields[1].replaceAll("/", " or ");
                                jsonObj.put("description", newDesc);
                            } else {
                                jsonObj.put("description", fields[1]);
                            }

                        }

                        if (line.startsWith("///")) {

                            jsonArray.add(jsonObj);
                            jsonObj = new JSONObject();
                        }
                    }

                } catch (IOException x) {
                    System.err.format("IOException: %s%n", x);
                }

            }
        });

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(KOToJSON.class.getName()).log(Level.SEVERE, null, ex);
        }

        try (FileWriter file = new FileWriter(Params.KEGG_KO)) {
            file.write(jsonArray.toJSONString());
        } catch (Exception e) {
            System.out.println("" + e.getMessage());
        }
    }

    public static Map<String, List<String>> geneKOMap(String filename) {
        Charset charset = Charset.forName("US-ASCII");
        Map<String, List<String>> ko_geneList = new HashMap<>();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filename), charset)) {
            String line = null;
            while ((line = reader.readLine()) != null) {

                String[] fields = line.split("\\s+", 2);
                String geneName = fields[1];
                String koNumber = fields[0];
                List<String> genes = new ArrayList<>();
                genes.add(geneName);
                if (!ko_geneList.containsKey(koNumber)) {
                    ko_geneList.put(koNumber, genes);
                } else {
                    genes = ko_geneList.get(koNumber);
                    genes.add(geneName);
                    ko_geneList.replace(koNumber, genes);
                }

            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
        for (Map.Entry<String, List<String>> entry : ko_geneList.entrySet()) {
            String key = entry.getKey();
            List<String> value = entry.getValue();
            if (value == null || key == null) {
                System.out.println("Null object found");
            }

        }

        return ko_geneList;
    }

    public static JSONArray loadKO(String filename) {
        Charset charset = Charset.forName("US-ASCII");
        Map<String, List<String>> gene_ko = geneKOMap(Params.KO_GENE_LIST);
        JSONArray jsonArray = new JSONArray();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonObj = new JSONObject();

                try (BufferedReader reader = Files.newBufferedReader(Paths.get(filename), charset)) {
                    String line = null;
                    String ko_number = "";
                    while ((line = reader.readLine()) != null) {
                        if (line.startsWith("ENTRY")) {
                            String[] fields = line.split("\\s+");
                            ko_number = "ko:" + fields[1];
                            jsonObj.put("ko_number", ko_number);

//                            if (gene_ko.containsKey(ko_number)) {
//                                JSONArray arrayOfGenes = new JSONArray();
//                                
//                                arrayOfGenes.addAll(gene_ko.get(ko_number));
//                                jsonObj.put("genes", arrayOfGenes);
//                            }
                        }
                        if (line.startsWith("NAME")) {
                            String[] fields = line.split("\\s+", 2);
                            jsonObj.put("name", fields[1]);
                        }
                        if (line.startsWith("DEFINITION")) {
                            String[] fields = line.split("\\s+", 2);
                            if (fields[1].contains("/")) {
                                String newDesc = fields[1].replaceAll("/", " or ");
                                jsonObj.put("description", newDesc);
                            } else {
                                jsonObj.put("description", fields[1]);
                            }

                        }

                        if (line.startsWith("///")) {
                            if (gene_ko.containsKey(ko_number)) {
                                JSONArray arrayOfGenes = new JSONArray();

                                arrayOfGenes.addAll(gene_ko.get(ko_number));
                                jsonObj.put("genes", arrayOfGenes);
                                jsonArray.add(jsonObj);
                            }

                            jsonObj = new JSONObject();
                        }
                    }

                } catch (IOException x) {
                    System.err.format("IOException: %s%n", x);
                }

            }
        });

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(KOToJSON.class.getName()).log(Level.SEVERE, null, ex);
        }

//
//        try (FileWriter file = new FileWriter(Params.KEGG_KO)) {
//            file.write(jsonArray.toJSONString());
//        } catch (Exception e) {
//            System.out.println("" + e.getMessage());
//        }
        return jsonArray;
    }

}
