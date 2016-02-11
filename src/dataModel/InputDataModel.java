/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataModel;

import cli.CommandLineOption;
import gui.MainGui;
import org.apache.commons.cli.CommandLine;

/**
 *
 * @author elmarce
 */
public class InputDataModel {

    private String[] args;
    private CommandLine commandLineArgs;
    private MainGui gui;

    public InputDataModel(String[] args) {
        this.args = args;
        if (CommandLineOption.checkCLArgument(this.args)) {
            this.commandLineArgs = CommandLineOption.commandLineArgs;
            if (this.commandLineArgs.getOptions().length <= 0) {
                this.commandLineArgs = null;
                this.gui = new MainGui();
                this.gui.setVisible(true);

            } else {
                this.gui = null;
            }
        } else {
            System.out.println("command line parameters or options are wrong");
            System.exit(0);
        }
    }

    public String[] getArgs() {
        return args;
    }

    public CommandLine getCommandLineArgs() {
        return commandLineArgs;
    }

    public MainGui getGui() {
        return gui;
    }

}
