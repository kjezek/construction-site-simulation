package au.kjezek.consite.actions;


import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * A simple wrapper representing input argument of an action.
 * It passes the argument into the returned action.
 */
@FunctionalInterface
public interface ActionArgument extends BiFunction<Integer, Integer, SimulationAction> {

    /**
     * A shortcut when an action has no argument.
     * @return an action.
     */
    default SimulationAction noArgs() {
        return apply(0, 0);
    }

    default SimulationAction oneArg(Integer x) {
        return apply(x, 0);
    }
}
