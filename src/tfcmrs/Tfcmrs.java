/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tfcmrs;

import dataModel.InputDataModel;
import tools.ToolAsCL;

/**
 *
 * @author elmarce
 */
public class Tfcmrs {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            InputDataModel idm = new InputDataModel(args);
            ToolAsCL clTool = new ToolAsCL(idm);
            clTool.runAsCLTool();
        } catch (Exception e) {
            System.out.println("EXCEPTION : "+e.getMessage());
        }

    }

}
