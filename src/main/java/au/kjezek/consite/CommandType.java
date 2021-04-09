package au.kjezek.consite;

import java.util.Arrays;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Enum representing operation command types.
 */
public enum CommandType {

    ADVANCE('a', ActionsFactory::advance),
    LEFT('l', (x) -> ActionsFactory.rotateLeft()),
    RIGHT('r', (x) -> ActionsFactory.rotateRight()),
    QUIT('q', (x) -> ActionsFactory.quit());

    /** Letter representing the command. */
    public final char letter;
    public final Function<Integer, SimulationAction> action;

    /** Map for faster access to items. */
    private final static Map<Character, CommandType> cache;
    static {
        cache =  Arrays.stream(CommandType.values()).collect(
                Collectors.toMap(k -> k.letter, k -> k)
        );
    }

    CommandType(char letter, Function<Integer, SimulationAction> action) {
        this.letter = letter;
        this.action = action;
    }

    /**
     * Find field by its letter representation.
     * @param c letter
     * @return the field type or exception for wrong input.
     */
    public static CommandType find(char c) {
        return Optional.ofNullable(cache.get(c)).orElseThrow(() -> new NoSuchElementException("No matching field for: " + c));
    }
}