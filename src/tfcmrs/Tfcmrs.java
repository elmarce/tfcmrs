/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tfcmrs;

import cli.CommandLineOption;

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
            if (CommandLineOption.checkCLArgument(args)) {
                System.out.println("-db = " + CommandLineOption.commandLineArgs.getOptionValue("db"));
                System.out.println("successful");
            }
        } catch (Exception e) {
            System.out.println("EXCEPTION : "+e.getMessage());
        }

    }

}
