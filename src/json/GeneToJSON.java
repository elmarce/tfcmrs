/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package json;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import tools.Params;

/**
 *
 * @author benji
 */
public class GeneToJSON {

    public static void main(String[] args) {
        generateJSONFile(Params.GENE_LIST);
    }

    public static void generateJSONFile(String filename) {
        Charset charset = Charset.forName("US-ASCII");
        JSONArray jsonArray = new JSONArray();
        try (Scanner input = new Scanner(new File(filename))) {
            FileWriter file = new FileWriter(Params.KEGG_GENES);
            file.write("[\n");

            String line = null;
            while (input.hasNextLine()) {
                line = input.nextLine();
                line = line.substring(1);
                String[] fields = line.split("\\s+", 2);
                String geneName = fields[0];
                String description = fields[1];
                JSONObject gene = new JSONObject();
                gene.put("name", geneName);
                gene.put("description", description);
                gene.put("organismCode", geneName.split(":")[0]);

                file.write(gene.toJSONString());
                
                if (input.hasNextLine()) {
                    file.write(",\n");
                } else {
                    file.write("\n");
                }
                //jsonArray.add(gene);

            }
            file.write("\n");
            file.write("]\n");
            file.write("\n");
        } catch (IOException | NoSuchElementException x) {
            System.err.format("IOException: %s%n", x);
        }
//        try (FileWriter file = new FileWriter(Params.KEGG_GENES)) {
//            file.write(jsonArray.toJSONString());
//        } catch (Exception e) {
//            System.out.println("" + e.getMessage());
//        }
    }
}
