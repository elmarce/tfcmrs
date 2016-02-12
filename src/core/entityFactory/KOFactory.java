/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.entityFactory;

import core.ko.KOGroup;

/**
 *
 * @author elmarce
 */
public class KOFactory implements EntityFactory<KOGroup> {

    @Override
    public KOGroup createEntity() {
        return new KOGroup();
    }

    @Override
    public KOGroup createEntity(String ko_number) {
        KOGroup kog = new KOGroup();
        kog.setKo_number(ko_number);
        return kog;
    }

}
