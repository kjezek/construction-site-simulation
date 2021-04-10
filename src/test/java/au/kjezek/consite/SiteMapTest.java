package au.kjezek.consite;

import au.kjezek.consite.domain.FieldType;
import au.kjezek.consite.domain.SiteMap;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class SiteMapTest {

    private final String[] map = new String[] {
            "ooro",
            "totT",
    };

    private final SiteMap sm = new SiteMap(map);

    @Test
    public void testClear() {
        sm.clear(0, 0);
        assertEquals(FieldType.PLAIN, sm.getField(0, 0));

        sm.clear(1, 0);
        assertEquals(FieldType.PLAIN, sm.getField(1, 0));
    }

    /** Load map with valid values. */
    @Test
    public void testValidMap() {
        assertEquals(FieldType.PLAIN, sm.getField(0,0));
        assertEquals(FieldType.PLAIN, sm.getField(0,3));
        assertEquals(FieldType.TREES, sm.getField(1,0));
        assertEquals(FieldType.PROTECTED, sm.getField(1,3));
    }

    /** Coordinate validity test. */
    @Test
    public void testOutsideMap() {
        assertTrue(sm.isOutsideMap(-1, 0));
        assertTrue(sm.isOutsideMap(-1, -1));
        assertTrue(sm.isOutsideMap(0, -1));
        assertTrue(sm.isOutsideMap(2, 0));
        assertTrue(sm.isOutsideMap(0, 4));
        assertTrue(sm.isOutsideMap(2, 4));

        assertFalse(sm.isOutsideMap(1, 1));
    }

    /** Test the input map is empty. */
    @Test(expected = IllegalArgumentException.class)
    public void testEmptyMap() {
        new SiteMap(new String[0]);
    }

    /** Test the input map has unknown field types. */
    @Test(expected = NoSuchElementException.class)
    public void testUnknownFieldTypes() {
        new SiteMap(new String[] {"abc"});
    }

    /** Test the map does not have the same size of rows. */
    @Test(expected = IllegalArgumentException.class)
    public void testNoRectShape() {
        new SiteMap(new String[] {"ooo", "tt"});
    }
}
