/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cli;

import java.util.logging.Level;
import java.util.logging.Logger;
import jdk.nashorn.internal.runtime.ParserException;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 *
 * @author benji
 */
public class CommandLineOption {

    private Options options;
    private CommandLine cmd;
    private CommandLineParser parser;

    public CommandLineOption(String[] args) {
        parser = new BasicParser();

        this.options = new Options();
        defineOptions();
        boolean checkCLOptions = checkCLOptions(args);
        
    }

    private void defineOptions() {
        this.options.addOption("m", "mode", true, "1,2 or 3");
        this.options.addOption("t", "type", true, "type: single or paired-end");
        this.options.addOption("cA", "condA", true, "contidion A");
        this.options.addOption("cB", "condB", true, "contidion B");

    }

    private boolean checkCLOptions(String[] args) {
      
        try {
            this.cmd = this.parser.parse(options, args);
        } catch (ParseException ex) {
            Logger.getLogger(CommandLineOption.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

}
