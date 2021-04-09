package au.kjezek.consite;

import java.util.Arrays;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * An emum for all possible field types.
 */
public enum FieldType {

    PLAIN('o', 1),
    ROCKY('r', 2),
    TREES('t', 2),
    PROTECTED('T', 2);

    /** Text representation of the field thype */
    public final char letter;
    /** Fuel needed to clean this type of field. */
    public final int fuel;

    /** Map for faster access to items. */
    private final static Map<Character, FieldType> cache;
    static {
        cache =  Arrays.stream(FieldType.values()).collect(
                Collectors.toMap(k -> k.letter, k -> k)
        );
    }

    FieldType(final char letter, final int fuel) {
        this.letter = letter;
        this.fuel = fuel;

    }

    /**
     * A field is done if it is either cleared or cannot be cleared (is protectedú
     * @return true for finalised fields
     */
    public boolean isDone() {
        return this == PLAIN || this == PROTECTED;
    }

    /**
     * Find field by its letter representation.
     * @param c letter
     * @return the field type or exception for wrong input.
     */
    public static FieldType find(char c) {
        return Optional.ofNullable(cache.get(c)).orElseThrow(() -> new NoSuchElementException("No matching field for: " + c));
    }


}
