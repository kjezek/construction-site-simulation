package au.kjezek.consite;

import java.util.LinkedList;
import java.util.List;

/**
 * This class collects actions and executes simulation. It is created with an input map
 * a bill and a bulldozer.
 *
 * It accepts commands unless the simulation ends.
 *
 * The map and the bill are modified based as the simulation progresses.
 *
 *
 */
public class SimulationRun {

    /** The simulation is done on a map. */
    private SiteMap map;
    /** The simulation computes the bill. */
    private SimulationBill bill;

    /** The actor. */
    private Bulldozer bulldozer;

    /** List of actions. */
    private List<SimulationAction> actions = new LinkedList<>();

    /** Once false, the simulation is over. */
    private boolean active = true;

    public SimulationRun(SiteMap map, SimulationBill bill, Bulldozer bulldozer) {
        this.map = map;
        this.bill = bill;
        this.bulldozer = bulldozer;
    }

    /**
     * Add action
     * @param type action type
     * @param param possible parameters for some of the commands.
     */
    public void addAction(CommandType type, int param) {
        if (!active) {
            throw new IllegalStateException("Simulation has already ended.");
        }

        actions.add(type.action.apply(param));
    }

    /**
     * Add action
     * @param type action type
     */
    public void addAction(CommandType type) {
        addAction(type, 0);
    }

    /**
     * Process all collected commands. Update maps, update bill, check end conditions.
     * When any ends conditions are reached, not more commands are processed.
     */
    public void process() {

        if (!active) {
            throw new IllegalStateException("Simulation has already ended.");
        }

        // charge radio communication for all commands even if it ends earlier.
        bill.add(BillItem.RADIO, actions.size());

        for (SimulationAction action: actions) {
            active &= action.action(bill, bulldozer, map);
            if (!active) {
                break;
            }
        }

        actions.clear();
    }

    /**
     * A siulatioon is active if it can still accept new commands and can be executed.
     * It happens when the bulldozer can operate, i.e. is not out of the map, no protected area is destroyed, etc.
     * @return true if this simulation is still active
     */
    public boolean isActive() {
        return active;
    }
}
