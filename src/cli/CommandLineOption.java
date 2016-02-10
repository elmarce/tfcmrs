/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cli;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 *
 * @author benji
 */
public class CommandLineOption {

    public static final Options options = new Options();
    public static CommandLine commandLineArgs;
    public static final CommandLineParser parser = new DefaultParser();

    public static Options getCLOptions() {

        options.addOption("m", "mode", true, "1,2 or 3");
        options.addOption("t", "type", true, "type: single or paired-end");
        options.addOption("cA", "condA", true, "contidion A");
        options.addOption("cB", "condB", true, "contidion B");

        options.addOption("db", "database", true, "the database to map with");
        options.addOption("o", "outdir", true, "the output directory");
        options.addOption("t", "threads", true, "maximal number of threads to be used");
        options.addOption("i", "input", true, "input directory containing the inputfile");

        //OptionGroup opttionGroup = new OptionGroup();
        return options;

    }

    public static boolean checkCLArgument(String[] args) {
        getCLOptions();
        try {

            commandLineArgs = parser.parse(options, args);

            if (!commandLineArgs.hasOption("db")) {
                System.out.println("please specify the database");
                System.exit(0);
            }
            if (!commandLineArgs.hasOption("mode")) {
                System.out.println("please specify mode");
                System.exit(0);
            }
            if (!commandLineArgs.hasOption("type")) {
                System.out.println("please specify type");
                System.out.println("--type = [single|paired]");
                System.exit(0);
            }
            int mode = Integer.parseInt(commandLineArgs.getOptionValue("mode"));
            switch (mode) {
                case 1: {
                    if (!commandLineArgs.hasOption("input")) {
                        System.out.println("please specify the inputfile");
                        System.exit(0);
                    }
                    break;
                }
                case 2: {
                    if (!commandLineArgs.hasOption("condA")) {
                        System.out.println("please specify the condition A");
                        System.exit(0);
                    }
                    break;

                }
                case 3: {
                    if (!commandLineArgs.hasOption("condB")) {
                        System.out.println("please specify the condition B");
                        System.exit(0);
                    }
                    break;
                }
                default: {
                    System.out.println("unknown mode");
                    System.exit(0);
                }
                break;
            }

        } catch (ParseException ex) {
            Logger.getLogger(CommandLineOption.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

}
