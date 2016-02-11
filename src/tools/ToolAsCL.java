/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import dataModel.InputDataModel;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.cli.CommandLine;

/**
 *
 * @author elmarce
 */
public class ToolAsCL {

    //private String[] args;
    private final InputDataModel IDM;

    public ToolAsCL(InputDataModel idm) {
        this.IDM = idm;
    }

    public void runAsCLTool() {
        try {
            if (IDM.getCommandLineArgs() != null) {
                CommandLine commandLineArgs = IDM.getCommandLineArgs();
                int mode = Integer.parseInt(commandLineArgs.getOptionValue("mode"));
                switch (mode) {
                    case 1: {

                        break;
                    }
                    case 2: {

                        break;
                    }
                    case 3: {

                        break;
                    }
                    default: {

                        System.out.println("unknown mode");
                        System.exit(0);
                    }
                    break;
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(ToolAsCL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
