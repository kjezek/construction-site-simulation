package au.kjezek.consite;


import java.util.function.Function;

/**
 * A simple wrapper representing input argument of an action.
 */
@FunctionalInterface
public interface ActionArgument extends Function<Integer, SimulationAction> {

    default SimulationAction noArgs() {
        return apply(0);
    }
}
