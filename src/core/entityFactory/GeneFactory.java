/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.entityFactory;

import core.gene.Gene;
import org.json.simple.JSONObject;

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
        gene.setOrganismCode(name.split(":")[0]);
        
        return gene;
    }
    public Gene createEntity(JSONObject obj) {
        Gene gene = new Gene();
        gene.setName(obj.get("name").toString());
        gene.setOrganismCode(obj.get("organismCode").toString());
        gene.setSignal(Double.parseDouble(obj.get("signal").toString()));
        gene.setReadCount(Integer.parseInt(obj.get("readCount").toString()));

        return gene;
    }

}
