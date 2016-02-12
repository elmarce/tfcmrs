/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.ec;

import core.ko.KOGroup;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author elmarce
 */
public class EnzymeClass {
    private String name;
    private KOGroup koGroup;
    private List<KOGroup> koGroupList;

    public EnzymeClass() {
        this.koGroupList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public KOGroup getKoGroup() {
        return koGroup;
    }

//    public void setKoGroup(KOGroup koGroup) {
//        this.koGroup = koGroup;
//    }
    
    public void addKOGroup(KOGroup ko){
       if(!koGroupList.contains(ko)) {
           koGroupList.add(ko);
       }
    }

    public List<KOGroup> getKoGroupList() {
        return koGroupList;
    }

    public void setKoGroupList(List<KOGroup> koGroupList) {
        this.koGroupList = koGroupList;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.name);
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
        final EnzymeClass other = (EnzymeClass) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "EnzymeClass{" + "name=" + name + '}';
    }
    
    
    
    
}
