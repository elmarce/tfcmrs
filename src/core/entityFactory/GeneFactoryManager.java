/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.entityFactory;

import core.gene.Gene;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import tools.Params;

/**
 *
 * @author elmarce
 */
public class GeneFactoryManager extends Thread implements EntityFactoryManager<Gene> {

    private final String filename;
    static File DBFile = new File("AllGeneDB.binary");
    //static FileOutputStream foutStream = new FileOutputStream(DBFile); 

    public GeneFactoryManager() {
        this.filename = Params.KO_GENE_LIST;
    }

    public static void main(String[] args) {
        String fileName = "/media/benji/Volume1/tests/inSilicoTest2/condA/s1/condA_Sample1.sam";
        retrieveObject();

    }

    public static boolean notStartWithAT(String line) {
        return !line.startsWith("@");
    }

    @Override
    public List<Gene> getEntitiesAsList() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<String, Gene> getEntities() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static void assignGeneDescription(String fileName) {
        Path path = Paths.get(Params.GENE_LIST);
        try (Stream<String> genes = Files.lines(path, StandardCharsets.UTF_8)) {
            Stream<String> descriptions = Files.lines(path, StandardCharsets.UTF_8);
            //lines.forEachOrdered(System.out::println);

        } catch (IOException ex) {
            Logger.getLogger(GeneFactoryManager.class.getName()).log(Level.SEVERE, null, ex);
        }

//        try (Stream<String> descriptions = Files.lines(path, StandardCharsets.UTF_8)) {
//            //lines.forEachOrdered(System.out::println);
//            
//        } catch (IOException ex) {
//            Logger.getLogger(GeneFactoryManager.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    public static void createGeneDB() {
        Path path = Paths.get(Params.GENE_LIST);
        try {
            RandomAccessFile DATABASE = new RandomAccessFile("GENEDB.binary", "rw");

            try (Stream<String> genes = Files.lines(path, StandardCharsets.UTF_8)) {
//                File DBFile = new File("AllGeneDB.binary");
//                FileOutputStream foutStream = new FileOutputStream(DBFile);
//                ObjectOutputStream objOutStream = new ObjectOutputStream(foutStream);

                genes
                        .map(GeneFactoryManager::createGeneObject)
                        .forEach(GeneFactoryManager::saveGene);

            } catch (IOException ex) {
                Logger.getLogger(GeneFactoryManager.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(GeneFactoryManager.class.getName()).log(Level.SEVERE, null, ex);
        }

//        try (Stream<String> descriptions = Files.lines(path, StandardCharsets.UTF_8)) {
//            //lines.forEachOrdered(System.out::println);
//            
//        } catch (IOException ex) {
//            Logger.getLogger(GeneFactoryManager.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    public static Gene createGeneObject(String line) {
        Gene gene = new Gene();
        line = line.substring(1);
        String[] fields = line.split("\\s+", 2);
        gene.setName(fields[0]);
        gene.setDescription(fields[1]);
        String[] temp = fields[0].split(":");
        gene.setOrganismCode(temp[0]);
        return gene;
    }

    public static void saveGene(Gene gene) {
        try {
            //RandomAccessFile DATABASE = new RandomAccessFile("AllGeneDB.binary", "rw");
            //File DBFile = new File("AllGeneDB.binary");
            FileOutputStream foutStream = new FileOutputStream(DBFile, true);
            try (ObjectOutputStream objOutStream = new ObjectOutputStream(foutStream)) {
                objOutStream.writeObject(gene);
            }

        } catch (IOException ex) {
            Logger.getLogger(GeneFactoryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void retrieveObject() {

        try {
            File file = new File("AllGeneDB.binary");
            long fileSize = file.length();
            long totalBytesRead = 0;
            long mappedOffset = 0;
            long maxToMapAtATime = 1024 * 1024 * 1024;
            long mappedSize = Math.min(fileSize, maxToMapAtATime);

            RandomAccessFile DATABASE = new RandomAccessFile(file, "r");
            FileChannel fileChannel = DATABASE.getChannel();

            while (totalBytesRead < fileSize) {
//                System.err.println("mappedOffset: " + mappedOffset + "; mappedSize: " + mappedSize);
//                System.err.println("fileSize: " + fileSize + "; totalBytesRead: " + totalBytesRead);
                MappedByteBuffer mappedBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, mappedOffset, mappedSize);
                for (int i = 0; i < mappedBuffer.limit(); i++) {
                   // System.out.print((char) mappedBuffer.get()); //Print the content of file
                }
                totalBytesRead = mappedBuffer.limit();
                
                mappedOffset += mappedSize;
                mappedSize = Math.min(fileSize - totalBytesRead, maxToMapAtATime);

            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GeneFactoryManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GeneFactoryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
