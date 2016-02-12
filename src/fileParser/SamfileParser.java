/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fileParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author benji
 */
public class SamfileParser {

    public static void main(String[] args) {
        Date start = new Date();

        Map<String, Integer> hash = new HashMap();
        Path file = Paths.get("/home/benji/Dokumente/Master/s1/fw_91_R1_A_BEDB.sam");
        //Path file = Paths.get("/home/benji/Dokumente/Master/s1/results/outputDataModel.txt");
        Charset charset = Charset.forName("US-ASCII");
        try (BufferedReader reader = Files.newBufferedReader(file, charset)) {
            String line = null;
            int count = 0;
            while ((line = reader.readLine()) != null) {
                if (line.equals("GENE	KO-GROUP	EC-NUMBER	KO-PATHWAY	EC-PATHWAY	EDTD	SCC	ReadLength")) {
                    continue;
                }
                if (line.equals("@")) {
                    continue;
                }
                String[] fields = line.split("\\s+");
                if (fields.length < 11 || fields[2].equals("*")) {
                    continue;
                }
                String gene = fields[2];
//				if (!hash.containsKey(gene)) {
//					hash.put(gene, 1);
//				}
                hash.put(gene, hash.getOrDefault(gene, 0) + 1);//super !!!
//				if (hash.containsKey(gene)) {
//					int currentValue = hash.get(gene)+1;
//					hash.replace(gene, currentValue);
//					//hash.put(gene, currentValue);
//				}
                //System.out.println(gene);
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }

        for (String key : hash.keySet()) {
            System.out.println(key + "\t" + hash.get(key));
        }
        System.out.println("hash-size = " + hash.size());
        Date end = new Date();
        System.out.println("time = " + ((end.getTime() - start.getTime())) + "ms");

    }

    public static void readSamFile() {

    }

}
