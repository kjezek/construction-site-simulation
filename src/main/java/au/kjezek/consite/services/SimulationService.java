package au.kjezek.consite.services;

import au.kjezek.consite.actions.SimulationAction;
import au.kjezek.consite.domain.*;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This service executes a simulation. It has methods to run the simulation via commands.
 * <p>
 * The simulation updates map, move bulldozer, and computes the bill.
 */
public class SimulationService {

    /**
     * Process all collected commands. Update maps, update bill, check end conditions.
     * When any ends conditions are reached, not more commands are processed.
     *
     * @param command command to execute.
     * @param map the site map, it will be updated by the simulation action
     * @param bill the bill, more expenses will be charged by the simulation action
     * @param bulldozer the bulldozer which may be changed by the simulation action
     * @return return false when the simulation was interrupted
     */
    public boolean process(
            final CommandAndParam command,
            final SiteMap map,
            final SimulationBill bill,
            final Bulldozer bulldozer) {

        if (command.commandType.billable) {
            bill.add(BillItem.RADIO, 1);
        }

        // atomic used as a holder only
        AtomicBoolean active = new AtomicBoolean(true);
        command.toAction().action(bill, bulldozer, map, (val) -> active.set(!val));

        return active.get();
    }

}
