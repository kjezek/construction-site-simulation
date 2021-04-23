package au.kjezek.consite.domain;

import au.kjezek.consite.actions.SimulationAction;

/**
 * This is a command with one possible parameter.
 */
public class CommandAndParam {

    public final CommandType commandType;
    public final Integer param;

    public CommandAndParam(CommandType commandType, Integer param) {
        this.commandType = commandType;
        this.param = param;
    }

    /**
     * Convert this command and param to simulation action.
     * @return the simulation action.
     */
    public SimulationAction toAction() {
        return commandType.action.get().oneArg(param);
    }

    public static CommandAndParam of(CommandType commandType, Integer param) {
        return new CommandAndParam(commandType, param);
    }

    public static CommandAndParam of(CommandType commandType) {
        return of(commandType, null);
    }

    public String toString() {
        String result = commandType.letter + "";
        if (param != null) {
            result += " " + param;
        }

        return result;
    }
}
