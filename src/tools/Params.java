/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import java.nio.file.Paths;

/**
 *
 * @author elmarce
 */
public class Params {
    public static String ROOT_DIR = Paths.get("").toAbsolutePath().toString();
    public static String TEST_SAMFILE = "/home/benji/NetBeansProjects/fw_93_R1_A_fw_93_R2_A.sam";
    public static String GENE_LIST = "/home/benji/NetBeansProjects/genelist.txt";
    public static String KO_GENE_LIST = "/media/benji/MyBook/KEGG_DATA/kegg/genes/ko/ko_genes.list";
    public static String KO_GROUPS = "/media/benji/MyBook/KEGG_DATA/kegg/genes/ko/ko";
    public static String GENE_JSON_FILE = "/home/benji/NetBeansProjects/results/Gene.json";
    public static String GENE_KO_FILE = "/home/benji/NetBeansProjects/results/GENE.KO.json";
    public static String KEGG_KO = "/home/benji/NetBeansProjects/results/KEGG.KO.json";
    public static String KEGG_GENES = "/home/benji/NetBeansProjects/results/KEGG.GENES.json";
    public static String KO_GROUPS_FOUND = "/home/benji/NetBeansProjects/results/KO_GROUPS_FOUND.json";
}
