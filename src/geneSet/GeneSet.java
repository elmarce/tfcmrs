/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geneSet;

import core.gene.Gene;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author benji
 */
public abstract class GeneSet {

    private SetClass setClass;
    private List<Gene> genes;
    private List<GeneSet> subSets;
    private String description;
    private String name;
    private String identifier;
    private int size;

    public GeneSet() {
        genes = new ArrayList<>();
        subSets = new ArrayList<>();
        this.size = 0;
    }

    public List<GeneSet> getSubSets() {
        return subSets;
    }

    public void setSubSets(List<GeneSet> subSets) {
        this.subSets = subSets;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setClass(SetClass setClass) {
        this.setClass = setClass;
    }

    public SetClass getSetClass() {
        return setClass;
    }

    public List<Gene> getGenes() {
        return genes;
    }

    public void setGenes(List<Gene> genes) {
        this.genes = genes;
    }

    public List<GeneSet> getSubSet() {
        return subSets;
    }

    public void setSubSet(List<GeneSet> subSet) {
        this.subSets = subSet;
    }

    public void addGene(Gene gene) {
       // try {
            if (!this.genes.contains(gene)) {
                this.genes.add(gene);
                size++;
            }
//        } catch (NullPointerException e) {
//            System.out.println(""+e.getMessage());
//        }

    }

    public int getSize() {
        return size;
    }
    

    public void removeGene(Gene gene) {
        if (this.genes.contains(gene)) {
            this.genes.remove(gene);
        }
    }

    public Gene getGeneAt(int position) {
        return this.genes.get(position);
    }

    public void addSubset(GeneSet subSet) {
        if (!subSets.contains(subSet)) {
            subSets.add(subSet);
        }

    }

    public void removeSubset(GeneSet subSet) {
        if (subSets.contains(subSet)) {
            subSets.remove(subSet);
        }
    }

    public GeneSet getSetAt(int position) {
        return subSets.get(position);
    }

    public void sortGeneBySignalDescending() {

    }

    public void sortGeneBySignal() {

    }

    public void printGenes() {
        genes.stream().forEach((gene) -> {
            System.out.println(gene.toString());
        });
    }

    @Override
    public String toString() {
        return "GeneSet{\n" + "setClass=" + setClass + "\n, genes=" + genes + "\n, subSets=" + subSets + "\n, description=" + description + "\n, name=" + name + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.setClass);
        hash = 53 * hash + Objects.hashCode(this.description);
        hash = 53 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GeneSet other = (GeneSet) obj;
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (this.setClass != other.setClass) {
            return false;
        }
        return true;
    }

}
