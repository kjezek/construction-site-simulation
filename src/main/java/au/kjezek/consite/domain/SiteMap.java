package au.kjezek.consite.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Site Map represents a 2 dimensional array, the map of the construction site.
 * It is like a chess board with various field types: tree, stone, protected land, ...
 * Operations changing the map are provided.
 */
public class SiteMap {

    public final FieldType[][] site;

    /**
     * Create a map from input string lines.
     * The lines contains rows of the map.
     * Letters of one line are rows of the map and may be 'o' 'r' 't' or 'T'
     *
     * @param lines lines
     */
    public SiteMap(final String[] lines) {
        if (lines.length == 0) {
            throw new IllegalArgumentException("Input map is empty");
        }

        final int rows = lines.length;
        final int cols = lines[0].length();

        site = new FieldType[rows][cols];

        for (int currentRow = 0; currentRow < rows; currentRow++) {
            if (lines[currentRow].length() != cols) {
                throw new IllegalArgumentException("Rows do not have the same length. Row: "
                        + currentRow + " length: " + lines[currentRow].length() + " is not: " + cols);
            }
            for (int currentCol = 0; currentCol < cols; currentCol++) {
                FieldType ft = FieldType.find(lines[currentRow].charAt(currentCol));
                site[currentRow][currentCol] = ft;
            }
        }
    }

    /**
     * Return field under certain column and row.
     * @param col column
     * @param row row
     * @return field type
     */
    public FieldType getField(final int row, final int col) {
        if (isOutsideMap(row, col)) {
            throw new IllegalArgumentException("Coordinates outside of the map: Row: " + row + " Col: " + col);
        }

        return site[row][col];
    }

    /**
     * Return all fields.
     * @return all fields in the array.
     */
    public List<FieldType> fieldsList() {
        List<FieldType> fields = new ArrayList<>();
        for (int row = 0; row < site.length; row++) {
            for (int col = 0; col < site[0].length; col++) {
                fields.add(getField(row, col));
            }
        }

        return fields;
    }

    /**
     * Return true if the input coordinates are outside of the map.
     * @param row row to check
     * @param col column to check
     * @return true if the coordinates are outside of the map.
     */
    public boolean isOutsideMap(final int row, final int col) {
        return row < 0 || col < 0
                || row >= site.length || col >= site[0].length;
    }

    /**
     * Clear the land
     * @param row row
     * @param col column
     */
    public void clear(final int row, final int col) {
        if (isOutsideMap(row, col)) {
            throw new IllegalArgumentException("Coordinates outside of the map: Row: " + row + " Col: " + col);
        }
        site[row][col] = FieldType.PLAIN;
    }
}
