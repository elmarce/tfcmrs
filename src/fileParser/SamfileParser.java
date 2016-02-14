/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fileParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import tools.CommandExecutor;
import tools.Params;

/**
 *
 * @author benji
 */
public class SamfileParser {

    public static void main(String[] args) {

        File file = new File(Params.TEST_SAMFILE);
        parseSamFileWithPerl(file.getAbsolutePath());
    }

    public static void getReadCountFromFile(String fileName) {
        Map<String, Integer> hash = new HashMap();
        Path file = Paths.get(fileName);
        Charset charset = Charset.forName("US-ASCII");
        try (BufferedReader reader = Files.newBufferedReader(file, charset)) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                if (line.equals("GENE	READ-COUNT") || line.startsWith("GENE")) {
                    continue;
                }
                String[] fields = line.split("\\s+");
                String gene = fields[0];
                hash.put(gene, Integer.parseInt(fields[1]));
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
            System.exit(0);
        }

    }

    /**
     * parses a samfile using a perl-Script
     * @param samfile 
     */
    public static void parseSamFileWithPerl(String samfile) {
        File file = new File(samfile);
        String path_to_perlScript = Params.ROOT_DIR + "/src/fileParser";
        String command = "perl " + path_to_perlScript + "/basicSamFileParser.pl -i " + samfile;
        CommandExecutor.executeCommand(command);
        //getReadCountFromFile(file.getParent() + "/results/ReadCountPerGen.txt");
    }

    /**
     * 
     * @param fileName 
     */
    public static void parseSamFileSimple(String fileName) {
        //refactoring needed

        Map<String, Integer> hash = new HashMap();
        Path file = Paths.get(fileName);
        Charset charset = Charset.forName("US-ASCII");
        try (BufferedReader reader = Files.newBufferedReader(file, charset)) {
            String line = null;
            int count = 0;
            while ((line = reader.readLine()) != null) {
                if (line.equals("@")) {
                    continue;
                }
                String[] fields = line.split("\\s+");
                if (fields.length < 11 || fields[2].equals("*")) {
                    continue;
                }
                String gene = fields[2];
                hash.put(gene, hash.getOrDefault(gene, 0) + 1);
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }

        for (String key : hash.keySet()) {
            System.out.println(key + "\t" + hash.get(key));
        }
        System.out.println("hash-size = " + hash.size());
    }
}
