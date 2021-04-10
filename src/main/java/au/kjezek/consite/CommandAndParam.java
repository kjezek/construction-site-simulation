package au.kjezek.consite;

/**
 * This is a command with possible parameter.
 */
public class CommandAndParam {

    public final CommandType commandType;
    public final Integer param;

    public CommandAndParam(CommandType commandType, Integer param) {
        this.commandType = commandType;
        this.param = param;
    }
}
