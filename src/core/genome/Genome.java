/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.genome;

import core.gene.Gene;
import java.util.List;

/**
 *
 * @author elmarce
 */
public class Genome {
    private String orgCode;
    private String genbank_id;
    private String description;
    private String dnaSequence;
    private List<Gene> genes;

    public Genome() {
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getGenbank_id() {
        return genbank_id;
    }

    public void setGenbank_id(String genbank_id) {
        this.genbank_id = genbank_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDnaSequence() {
        return dnaSequence;
    }

    public void setDnaSequence(String dnaSequence) {
        this.dnaSequence = dnaSequence;
    }

    public List<Gene> getGenes() {
        return genes;
    }

    public void setGenes(List<Gene> genes) {
        this.genes = genes;    }
    
    
}
