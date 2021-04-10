package au.kjezek.consite.domain;

import au.kjezek.consite.actions.ActionArgument;
import au.kjezek.consite.actions.ActionsFactory;

import java.util.Arrays;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Enum representing operation command types.
 */
public enum CommandType {

    ADVANCE('a', true, ActionsFactory::advance),
    LEFT('l', true, ActionsFactory::rotateLeft),
    RIGHT('r', true, ActionsFactory::rotateRight),
    QUIT('q', false, ActionsFactory::quit);

    /** Letter representing the command. */
    public final char letter;

    /** An executive code for this action. */
    public final Supplier<ActionArgument> action;

    /** Check if this item is incurs any cost. */
    public final boolean billable;

    /** Map for faster access to items. */
    private final static Map<Character, CommandType> cache;
    static {
        cache =  Arrays.stream(CommandType.values()).collect(
                Collectors.toMap(k -> k.letter, k -> k)
        );
    }

    CommandType(final char letter, final boolean billable, final Supplier<ActionArgument> action) {
        this.letter = letter;
        this.action = action;
        this.billable = billable;
    }

    /**
     * Find this type by its letter representation.
     * @param c letter
     * @return the field type or exception for wrong input.
     */
    public static CommandType find(final char c) {
        return cache.get(c);
    }
}
