/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.entityFactory;

import core.ec.Enzyme;
import core.gene.Gene;
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
public class EnzymeFactoryManager implements EntityFactoryManager<Enzyme> {

    private final String filename;

    public EnzymeFactoryManager(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

    @Override
    public Map<String, Enzyme> getEntities() {
        Map<String, Enzyme> ko_emzyme = new HashMap<>();
        Map<KOGroup, Enzyme> map_ko_emzyme = new HashMap<>();
        EnzymeFactory efactory = new EnzymeFactory();
        KOFactory kofactory = new KOFactory();
        Charset charset = Charset.forName("US-ASCII");
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filename), charset)) {
            String line = null;

            while ((line = reader.readLine()) != null) {
                String[] fields = line.split("\\s+", 2);
                Enzyme enzyme = efactory.createEntity(fields[1]);
                KOGroup ko = kofactory.createEntity(fields[0].split(":")[1]);
                //enzyme.setKoGroup(ko);
                if (!ko_emzyme.containsKey(fields[0].split(":")[1])) {
                    ko_emzyme.put(fields[0].split(":")[1], enzyme);
                }
                if (!map_ko_emzyme.containsKey(ko)) {
                    map_ko_emzyme.put(ko, enzyme);
                }
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }

        return ko_emzyme;
    }

}
