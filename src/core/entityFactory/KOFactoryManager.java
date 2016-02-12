/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.entityFactory;

import core.ko.KOGroup;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

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
        Map<String, KOGroup> test = new KOFactoryManager("/mnt/fungen/db02/Reference_DB/KEGG_DATA/kegg/genes/ko/ko").getEntities();
        for (Map.Entry<String, KOGroup> entrySet : test.entrySet()) {
            String key = entrySet.getKey();
            KOGroup ko = entrySet.getValue();
            System.out.println(ko.toString());
        }
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

}
