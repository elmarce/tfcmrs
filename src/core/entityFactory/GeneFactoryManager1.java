/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.entityFactory;

import core.gene.Gene;
import core.ko.KOGroup;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import tools.Params;

/**
 *
 * @author elmarce
 */
public class GeneFactoryManager1 extends Thread implements EntityFactoryManager<Gene> {

    private String filename;

    public GeneFactoryManager1() {
        this.filename = "/mnt/fungen/db02/Reference_DB/KEGG_DATA/kegg/genes/ko/ko_genes.list";
    }

    public static void main(String[] args) {
        GeneFactoryManager1 gfm = new GeneFactoryManager1();
        //"/mnt/fungen/db02/Reference_DB/KEGG_DATA/kegg/genes/ko/ko_genes.list"
        Map<Gene, KOGroup> temp = new GeneFactoryManager1().getGeneKOGroup(Params.KO_GENE_LIST);
        for (Map.Entry<Gene, KOGroup> entrySet : temp.entrySet()) {
            Gene gene = entrySet.getKey();
            KOGroup ko = entrySet.getValue();
            System.out.println(gene.toString());
            System.out.println(ko.toString());

        }

        //gfm.assignKOString(Params.KO_GENE_LIST, Params.GENE_JSON_FILE);

    }

    @Override
    public Map<String, Gene> getEntities() {
        Map<String, Gene> gene_hash = new HashMap<>();
        GeneFactory factory = new GeneFactory();
        KOFactory koFactory = new KOFactory();

        Charset charset = Charset.forName("US-ASCII");
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filename), charset)) {
            String line = null;

            while ((line = reader.readLine()) != null) {
                String[] fields = line.split("\\s+", 2);
                Gene gene = factory.createEntity(fields[1]);
                KOGroup ko = koFactory.createEntity(fields[0].split(":")[1]);
                //gene.setKoGroup(ko);
                gene.setKoNumber(ko.getKo_number());

                if (!gene_hash.containsKey(fields[0])) {
                    gene_hash.put(fields[1], gene);
                }
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }

        return gene_hash;
    }

    @Override
    public void run() {

    }

    public static Map<String, Map<String, Gene>> listDir(String path) {
        Charset charset = Charset.forName("US-ASCII");
        File[] dirList = new File(path).listFiles();

        Map<String, Map<String, Gene>> hashOfhash = new HashMap<>();
        GeneFactory factory = new GeneFactory();
        Gene gene = factory.createEntity();

        try {
            for (File file : dirList) {
                if (file.isDirectory()) {
                    String dirName = file.getName();
                    //System.out.println("dir: " + file.getName());
                    File[] files = new File(file.getAbsolutePath()).listFiles();

                    for (File file1 : files) {
                        if (file1.isFile() && file1.getName().endsWith(".kff")) {
                            //System.out.println(file1);
                            Map<String, Gene> gene_hash = new HashMap<>();

                            try (BufferedReader reader = Files.newBufferedReader(Paths.get(file1.getAbsolutePath()), charset)) {
                                String line = null;

                                while ((line = reader.readLine()) != null) {
                                    String[] fields = line.split("\\t+");
                                    //System.out.println(fields[0]);
                                    gene.setName(fields[0]);
                                    String org = fields[0].split(":")[0];
                                    //System.out.println(org);
                                    gene.setDescription(fields[fields.length - 1]);
                                    gene.setOrganismCode(org);

                                    if (!gene_hash.containsKey(fields[0])) {
                                        gene_hash.put(fields[0], gene);
                                        //System.out.println(gene.toString());

                                    }
                                    gene = new Gene();
                                }
                            } catch (IOException x) {
                                System.err.format("IOException: %s%n", x);
                            }
                            hashOfhash.put(dirName, gene_hash);

                            gene_hash.clear();
                        }
                    }

                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return hashOfhash;
    }

    public Map<Gene, KOGroup> getGeneKOGroup(String filename) {
        Map<Gene, KOGroup> gene_hash = new HashMap<>();
        GeneFactory factory = new GeneFactory();
        KOFactory koFactory = new KOFactory();
        Charset charset = Charset.forName("US-ASCII");
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filename), charset)) {
            String line = null;

            while ((line = reader.readLine()) != null) {
                String[] fields = line.split("\\s+", 2);
                Gene gene = factory.createEntity(fields[1]);
                KOGroup ko = koFactory.createEntity(fields[0]);
                //gene.setKoGroup(ko);
                gene.setKoNumber(fields[0]);

                if (!gene_hash.containsKey(gene)) {
                    gene_hash.put(gene, ko);
                }
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }

        return gene_hash;
    }

    public void assignKOString(String filename, String jsonFile) {
        Map<String, String> gene_hash = new HashMap<>();
        Map<String, String> gene_description = new HashMap<>();

        GeneFactory factory = new GeneFactory();
        KOFactory koFactory = new KOFactory();
        Charset charset = Charset.forName("US-ASCII");

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try (BufferedReader reader = Files.newBufferedReader(Paths.get(filename), charset)) {
                    String line = null;

                    while ((line = reader.readLine()) != null) {
                        String[] fields = line.split("\\s+", 2);
                        if (!gene_hash.containsKey(fields[1])) {
                            gene_hash.put(fields[1], fields[0]);
                        }
                    }
                } catch (IOException x) {
                    System.err.format("IOException: %s%n", x);
                }
            }
        });
        t1.start();
        try {
            t1.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(GeneFactoryManager1.class.getName()).log(Level.SEVERE, null, ex);
        }

        JSONParser jsonParser = new JSONParser();
        JSONArray newJsonArray = new JSONArray();
        try {
            FileReader fr = new FileReader(jsonFile);
            JSONArray jsonArray = (JSONArray) jsonParser.parse(fr);
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject gene_Obj = (JSONObject) jsonArray.get(i);
                String geneName = gene_Obj.get("name").toString();
                //gene_hash
                if (gene_hash.containsKey(geneName)) {
                    JSONObject newJsonObj = new JSONObject();
                    newJsonObj.put("name", geneName);

                    JSONObject ko_obj = new JSONObject();
                    ko_obj.put("ko_number", gene_hash.get(geneName));
                    newJsonObj.put("koGroup", ko_obj);

                    newJsonObj.put("description", gene_Obj.get("description"));
                    newJsonObj.put("organismCode", gene_Obj.get("organismCode"));
                    newJsonObj.put("readCount", gene_Obj.get("readCount"));
                    newJsonObj.put("signal", gene_Obj.get("signal"));

                    newJsonObj.put("ecNumber", null);
                    newJsonObj.put("ecPathway", null);
                    newJsonObj.put("koPathway", null);

                    newJsonArray.add(newJsonObj);

                    //System.out.println("" + geneName + "\t" + gene_hash.get(geneName));
                } else {
                    newJsonArray.add(gene_Obj);
                }

            }

            try (FileWriter file = new FileWriter(Params.GENE_KO_FILE)) {
                file.write(newJsonArray.toJSONString());
            } catch (Exception e) {
                System.out.println("" + e.getMessage());
            }

        } catch (FileNotFoundException | ParseException ex) {
            Logger.getLogger(GeneFactoryManager1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | NullPointerException ex) {
            System.out.println("" + ex.getLocalizedMessage());
        }

    }
    
    public void assignKO_Object(String ko_son, String genes_ko){
        
    }

    @Override
    public List<Gene> getEntitiesAsList() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
