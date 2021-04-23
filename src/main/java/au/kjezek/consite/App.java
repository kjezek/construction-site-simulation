package au.kjezek.consite;

import au.kjezek.consite.domain.Bulldozer;
import au.kjezek.consite.domain.CommandAndParam;
import au.kjezek.consite.domain.SimulationBill;
import au.kjezek.consite.domain.SiteMap;
import au.kjezek.consite.services.ConsoleUIService;
import au.kjezek.consite.services.SimulationService;
import au.kjezek.consite.services.UIService;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Application - the main class.
 */
public class App {

    /**
     * Main class.
     * @param args
     */
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Error: A filepath pointing to a file with the map must be provided. ");
            System.out.println();
            System.out.println("Usage:");
            System.out.println("java -jar program.jar <path>  ");
            System.out.println("    path:  a filepath must target to a TXT file with the map");
            System.exit(-1);
        }

        // build services here (no Spring or other IoC frameworks at the moment)
        UIService ui = new ConsoleUIService();
        SimulationService simulation = new SimulationService();
        SimulationBill bill = new SimulationBill();
        Bulldozer bulldozer = new Bulldozer();
        SiteMap map = ui.readMap(new FileInputStream(args[0]));

        System.out.println("Welcome to the site clearing simulator. This is a map of the site:");

        ui.printMap(map);

        System.out.println("The bulldozer is currently located at the Northern edge of the site, " +
                "immediately to the West of the site, and facing East.\n");

        String[] directions = new String[] {"North", "East", "South", "West"};
        boolean run = true;
        while (run) {
            System.out.print("Type your command: (l)eft, (r)ight, (a)dvance <n>, (q)uit: ");
            CommandAndParam command = ui.readActions(System.in);
            run = simulation.process(command, map, bill, bulldozer);
            System.out.println("  Got it. Command: '" + command
                    + "' Bulldozer is at: row: "
                    + bulldozer.getRow() + " col: " + bulldozer.getCol()
                    + " oriented: " + directions[bulldozer.getDirection()]);
        }

        System.out.println("\nSimulation done. The bill is: ");
        System.out.println("----------------------------------");

        ui.printBill(bill);
    }
}
