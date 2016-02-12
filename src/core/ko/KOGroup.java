/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.ko;

import core.ec.EnzymeClass;
import core.gene.Gene;
import core.pathway.Pathway;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author elmarce
 */
public class KOGroup {
    private String ko_number;
    private String name;
    private String description;
    private Pathway pathway;
    private List<Gene>genes;
    private EnzymeClass enzymeClass;

    public KOGroup() {
        
    }
    
    public String getKo_number() {
        return ko_number;
    }

    public void setKo_number(String ko_number) {
        this.ko_number = ko_number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Pathway getPathway() {
        return pathway;
    }

    public void setPathway(Pathway pathway) {
        this.pathway = pathway;
    }

    public List<Gene> getGenes() {
        return genes;
    }

    public void setGenes(List<Gene> genes) {
        this.genes = genes;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.ko_number);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final KOGroup other = (KOGroup) obj;
        if (!Objects.equals(this.ko_number, other.ko_number)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "KOGroup{" + "ko_number=" + ko_number + ", name=" + name + ", description=" + description + '}';
    }
    
   public void setEnzymeClass(EnzymeClass ec){
       this.enzymeClass = ec;
   }
   
   public EnzymeClass getEnzymeClass(){
       return this.enzymeClass;
   }
    
}
