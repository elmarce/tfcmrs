/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.entityFactory;

import core.ec.Enzyme;

/**
 *
 * @author elmarce
 */
public class EnzymeFactory implements EntityFactory<Enzyme>{

    @Override
    public Enzyme createEntity() {
        return new Enzyme();
    }

    @Override
    public Enzyme createEntity(String name) {
        Enzyme enzyme = new Enzyme();
        enzyme.setName(name);
        
        return enzyme;
    }
    
}
