package au.kjezek.consite;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This service executes a simulation. It has methods to generate commands
 * and methods to run the commands.
 *
 * The simulation updates map, move bulldozer, and computes the bill.
 *
 *
 */
public class SimulationService {

    /**
     * Add an action into the input list of actions. Update the bill to charge the command.
     * @param actions actions to extend
     * @param bill the bill to be extended with the price for using the action.
     * @param type action type
     */
    public void addAction(final List<SimulationAction> actions,
                          final SimulationBill bill,
                          final CommandType type,
                          final int param) {
        if (type.billable) {
            bill.add(BillItem.RADIO, 1);
        }
        actions.add(type.action.get().apply(param));
    }

    /**
     * Add an action into the input list of actions. Update the bill to charge the command.
     * @param actions actions to extend
     * @param bill the bill to be extended with the price for using the action.
     * @param type action type
     */
    public void addAction(final List<SimulationAction> actions,
                          final SimulationBill bill,
                          final CommandType type) {

        addAction(actions, bill, type, 0);
    }

    /**
     * Process all collected commands. Update maps, update bill, check end conditions.
     * When any ends conditions are reached, not more commands are processed.
     *
     * @return return false when the simulation was interrupted
     */
    public boolean process(final List<SimulationAction> actions,
                        final SiteMap map,
                        final SimulationBill bill,
                        final Bulldozer bulldozer) {

        // atomic used as a holder only
        AtomicBoolean active = new AtomicBoolean(true);

        for (SimulationAction action: actions) {
            action.action(bill, bulldozer, map, (val) -> active.set(!val));
            if (!active.get()) {
                break;
            }
        }

        return active.get();
    }

}
