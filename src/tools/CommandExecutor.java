/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author elmarce
 */
public class CommandExecutor {

    public static void executeCommand(String command) {
        String commandOutput = null;
        long start = System.currentTimeMillis();

        try {

            Process p = Runtime.getRuntime().exec(command);
            System.out.println("Executing: " + command);
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

            while ((commandOutput = stdInput.readLine()) != null) {
                System.out.println(commandOutput);
            }

            while ((commandOutput = stdError.readLine()) != null) {
                System.out.println(commandOutput);
            }
            long end = System.currentTimeMillis();
            System.out.println("the command took " + (end - start) + "ms");

        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Failed to execute " + command);
            System.out.println(e.getClass() + " caused by : " + e.getMessage() + " in \n" + CommandExecutor.class);
            System.exit(0);
        }
    }
}
