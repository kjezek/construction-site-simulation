package au.kjezek.consite.actions;


import java.util.function.Function;

/**
 * A simple wrapper representing input argument of an action.
 * It passes the argument into the returned action.
 */
@FunctionalInterface
public interface ActionArgument extends Function<Integer, SimulationAction> {

    /**
     * A shortcut when an action has no argument.
     * @return an action.
     */
    default SimulationAction noArgs() {
        return apply(0);
    }
}
