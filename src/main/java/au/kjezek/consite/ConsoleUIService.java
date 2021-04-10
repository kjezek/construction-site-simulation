package au.kjezek.consite;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * UI service implementation for simple console.
 */
public class ConsoleUIService implements UIService {

    @Override
    public void printMap(SiteMap map) {

        FieldType[][] site = map.getSite();
        for (int row = 0; row < site.length; row++) {
            for (int col = 0; col < site[0].length; col++) {
                System.out.print(site[row][col].letter + " ");
            }
            System.out.println();
        }

    }

    @Override
    public void printBill(SimulationBill bill) {

        Map<BillItem, String> messages = new HashMap<>();
        messages.put(BillItem.RADIO, "communication overhead");
        messages.put(BillItem.FUEL, "fuel usage");
        messages.put(BillItem.UNCLEARED, "uncleared squares");
        messages.put(BillItem.PROTECTED, "destruction of protected tree");
        messages.put(BillItem.PAINT, "paint damage to bulldozer");

        System.out.printf ("%-35s %10s %10s %n", "Item", "Quantity", "Cost");

        for (BillItem item : new BillItem[] {BillItem.RADIO, BillItem.FUEL, BillItem.UNCLEARED, BillItem.PROTECTED, BillItem.PAINT}) {
            System.out.printf ("%-35s %10d %10d %n", messages.get(item), bill.getUnitsItem(item), bill.getSumItem(item));
        }

        System.out.println("---");
        System.out.printf ("%-35s %20d %n", "Total", bill.total());
    }

    @Override
    public List<CommandAndParam> readActions(InputStream input) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));

        List<CommandAndParam> actions = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            String[] params = line.trim().split(" ");
            CommandType commandType = null;
            Integer param = null;

            // command with no argument
            if (params.length >= 1) {
                // find just by the first letter (basically ignore when a user inputs the whole command
                char c = params[0].charAt(0);
                commandType = CommandType.find(c);
            }
            if (params.length >= 2) {
                param = Integer.parseInt(params[1]);
            }

            if (commandType != null) {
                actions.add(new CommandAndParam(commandType, param));
            }
        }

        return actions;
    }

    @Override
    public SiteMap readMap(InputStream input) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));

        List<String> lines = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }

        return new SiteMap(lines.toArray(new String[0]));
    }
}
