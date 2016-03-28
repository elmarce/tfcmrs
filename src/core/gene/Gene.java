/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.gene;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author elmarce
 */
public class Gene implements Serializable{
   private String name;
   private String organismCode;
   private String description;
   //private KOGroup koGroup;
   private String dnaSequence;
   private double signal;
   private int readCount;
   private String koNumber;
   
   public Gene(){
       
   }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrganismCode() {
        return organismCode;
    }

    public void setOrganismCode(String organismCode) {
        this.organismCode = organismCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    public KOGroup getKoGroup() {
//        return koGroup;
//    }

//    public void setKoGroup(KOGroup koGroup) {
//        this.koGroup = koGroup;
//    }

    public String getKoNumber() {
        return koNumber;
    }

    public void setKoNumber(String koNumber) {
        this.koNumber = koNumber;
    }
    

    
    

    public String getDnaSequence() {
        return dnaSequence;
    }

    public void setDnaSequence(String dnaSequence) {
        this.dnaSequence = dnaSequence;
    }

    public double getSignal() {
        return signal;
    }

    public void setSignal(double signal) {
        this.signal = signal;
    }

    public int getReadCount() {
        return readCount;
    }

    public void setReadCount(int readCount) {
        this.readCount = readCount;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 19 * hash + Objects.hashCode(this.name);
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
        final Gene other = (Gene) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Gene{" + "name=" + name + ", orgCode=" + organismCode + ", description=" + description  + '}';
    }
    
    
   
   
}
