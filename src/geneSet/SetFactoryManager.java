/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geneSet;

import core.entityFactory.GeneFactory;
import core.entityFactory.KOFactory;
import core.gene.Gene;
import core.ko.KOGroup;
import java.io.BufferedReader;
import java.io.FileReader;
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
import json.KOToJSON;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import tools.Params;

/**
 *
 * @author benji
 */
public class SetFactoryManager {

    private List<GeneSet> sets;
    private String filename;

    public SetFactoryManager(String filename) {
        this.filename = filename;
        sets = new ArrayList<>();
    }
    public static void main(String[] args) {
        List<GeneSet> listOfSet = new SetFactoryManager("").createSetByKO2();
        for (GeneSet set : listOfSet) {
            System.out.println(set.toString());
//            for (Gene gene : set.getGenes()) {
//                System.out.println(gene.toString());
//            }
        }
    }

    public List<GeneSet> createSetByKO() {
        Charset charset = Charset.forName("US-ASCII");
        Map<String, List<String>> ko_geneList = new HashMap<>();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filename), charset)) {
            String line = null;
            while ((line = reader.readLine()) != null) {

                String[] fields = line.split("\\s+", 2);

            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }

        return null;
    }

    public List<GeneSet> createSetByKO2() {
        List<GeneSet> setList = new ArrayList<>();
        try {
            JSONArray keggKOArray = KOToJSON.loadKO(Params.KO_GROUPS);
            JSONParser parser = new JSONParser();
            JSONArray foundGenes = (JSONArray) parser.parse(new FileReader(Params.GENE_JSON_FILE));
            GeneFactory geneFactory = new GeneFactory();
            KOFactory koFactory = new KOFactory();

            for (int j = 0; j < keggKOArray.size(); j++) { //list of annotated KO-Group
                JSONObject ko_obj = (JSONObject) keggKOArray.get(j);
                
                //System.out.println(ko_obj.toJSONString());
                
                JSONArray ko_genes = (JSONArray) ko_obj.get("genes");
//                System.out.println("j = "+j);
//                System.out.println(ko_genes.toJSONString());
                

                //GeneSet currentSet = koFactory.createEntity(ko_obj);
                GeneSet currentSet = new KOGroup();
                currentSet.setClass(SetClass.KOGROUP);
                
                //System.out.println(currentSet.toString());

                for (int i = 0; i < foundGenes.size(); i++) {//list of genes found
                    JSONObject gene_obj = (JSONObject) foundGenes.get(i);//current gene_obj from the found list
                    //System.out.println(gene_obj.toJSONString());

                    //KO_GENES_LOOP:
                    for (int k = 0; k < ko_genes.size(); k++) {//list of genes of the current KO-Group
                        String geneName = ko_genes.get(k).toString();
                        String gene_obj_name = gene_obj.get("name").toString();
                        
                        if (gene_obj_name.equals(geneName)) {
                            //System.out.println("Treffer");
                            Gene gene = geneFactory.createEntity(gene_obj);
                            currentSet.addGene(gene);
                            break;
                        }
                    }
                }
                if (currentSet.getGenes().size() >= 1) {
                    System.out.println("Setsize = "+currentSet.getGenes().size());
                    currentSet.setName(ko_obj.get("name").toString());
                    currentSet.setDescription(ko_obj.get("description").toString());
                    currentSet.setIdentifier(ko_obj.get("ko_number").toString());
                    setList.add(currentSet);
                }
            }

        } catch (IOException |NullPointerException| ParseException e) {
            System.out.println("" + e.getLocalizedMessage());
            Logger.getLogger(SetFactoryManager.class.getName()).log(Level.SEVERE, null, e);
        }
        this.sets.addAll(setList);

        return setList;
    }

}
