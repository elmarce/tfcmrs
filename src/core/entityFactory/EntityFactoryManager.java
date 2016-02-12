/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.entityFactory;

import java.util.Map;

/**
 *
 * @author elmarce
 */
public interface EntityFactoryManager<T> {
  public Map<String, T>  getEntities();
}
