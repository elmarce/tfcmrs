/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.entityFactory;

import core.gene.Gene;

/**
 *
 * @author elmarce
 */
public class GeneFactory implements EntityFactory<Gene> {

    @Override
    public Gene createEntity() {
        return new Gene();
    }

    @Override
    public Gene createEntity(String name) {
        Gene gene = new Gene();
        gene.setName(name);
        gene.setOrgCode(name.split(":")[0]);
        
        return gene;
    }

}
