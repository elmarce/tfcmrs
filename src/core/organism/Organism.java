/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.organism;

import core.genome.Genome;

/**
 *creates Objects of type Organism by reading the containing all taxonomies
 * taxonomy.txt
 * @author elmarce
 */
public class Organism {
    private String taxNumber;
    private String orgCode; 
    private String name;
    private Genome genome;

    public Organism(String taxNumber, String orgCode, String name) {
        this.taxNumber = taxNumber;
        this.orgCode = orgCode;
        this.name = name;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Genome getGenome() {
        return genome;
    }

    public void setGenome(Genome genome) {
        this.genome = genome;
    }
    
}
