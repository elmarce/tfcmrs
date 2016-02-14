/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.entityFactory;

import core.ko.KOGroup;
import org.json.simple.JSONObject;

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

    public KOGroup createEntity(JSONObject obj) {
        KOGroup kog = new KOGroup();
        if (obj == null) {
            return kog;
        } else {
            kog.setKo_number(obj.get("ko_number").toString());
            kog.setName(obj.get("name").toString());
            kog.setDescription(obj.get("description").toString());
        }

        return kog;
    }

}
