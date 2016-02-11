/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tfcmrs;

import cli.CommandLineOption;
import dataModel.InputDataModel;

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
        } catch (Exception e) {
            System.out.println("EXCEPTION : "+e.getMessage());
        }

    }

}
