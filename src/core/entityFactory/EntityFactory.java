/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.entityFactory;

/**
 *
 * @author elmarce
 */
public interface EntityFactory<T> {
   public T createEntity();
   public T createEntity(String name);
}
