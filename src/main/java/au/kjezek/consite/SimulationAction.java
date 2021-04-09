package au.kjezek.consite;

import java.util.function.Supplier;

/**
 * Interface performing a simulation step.
 * A step usually changes a map, a bulldozer and a bill
 *
 */
@FunctionalInterface
public interface SimulationAction {

    /**
     * Perform an action, potentially modifying one or more of the input elements.
     * @param bill the bill
     * @param bulldozer the bulldozer
     * @param map the map
     * @return false if the action has caused end of simulation
     */
    boolean action(SimulationBill bill, Bulldozer bulldozer, SiteMap map);
}
