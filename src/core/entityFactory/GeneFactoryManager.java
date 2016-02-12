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
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author elmarce
 */
public class GeneFactoryManager extends Thread implements EntityFactoryManager<Gene> {

    private String filename;

    public GeneFactoryManager() {
        this.filename = "/mnt/fungen/db02/Reference_DB/KEGG_DATA/kegg/genes/ko/ko_genes.list";
    }

    public static void main(String[] args) {
        //"/mnt/fungen/db02/Reference_DB/KEGG_DATA/kegg/genes/ko/ko_genes.list"
        Map<Gene, KOGroup> temp = new GeneFactoryManager().getGeneKOGroup("/mnt/fungen/db02/Reference_DB/KEGG_DATA/kegg/genes/ko/ko_genes.list");
        for (Map.Entry<Gene, KOGroup> entrySet : temp.entrySet()) {
            Gene gene = entrySet.getKey();
            KOGroup ko = entrySet.getValue();
            System.out.println(gene.toString());
            System.out.println(ko.toString());

        }

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
                gene.setKoGroup(ko);

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
                                    gene.setOrgCode(org);

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
                gene.setKoGroup(ko);

                if (!gene_hash.containsKey(gene)) {
                    gene_hash.put(gene, ko);
                }
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }

        return gene_hash;
    }
}
