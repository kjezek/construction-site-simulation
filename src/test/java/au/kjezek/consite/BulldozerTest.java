package au.kjezek.consite;

import au.kjezek.consite.domain.Bulldozer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BulldozerTest {

    private final Bulldozer bulldozer = new Bulldozer();

    /** Test rotation. */
    @Test
    public void testRotateLeft() {
        bulldozer.step();  // enter map - go east

        assertEquals(0, bulldozer.getCol());
        assertEquals(0, bulldozer.getRow());

        bulldozer.rotateLeft(); // go north
        bulldozer.step();

        assertEquals(0, bulldozer.getCol());
        assertEquals(-1, bulldozer.getRow());

        bulldozer.rotateLeft(); // go west
        bulldozer.step();

        assertEquals(-1, bulldozer.getCol());
        assertEquals(-1, bulldozer.getRow());

        bulldozer.rotateLeft(); // go south
        bulldozer.step();

        assertEquals(-1, bulldozer.getCol());
        assertEquals(0, bulldozer.getRow());

        bulldozer.rotateLeft(); // go east again
        bulldozer.step();

        assertEquals(0, bulldozer.getCol());
        assertEquals(0, bulldozer.getRow());
    }

    /** Test rotation. */
    @Test
    public void testRotateRight() {
        bulldozer.step();  // enter map - go east

        assertEquals(0, bulldozer.getCol());
        assertEquals(0, bulldozer.getRow());

        bulldozer.rotateRight(); // go south
        bulldozer.step();

        assertEquals(0, bulldozer.getCol());
        assertEquals(1, bulldozer.getRow());

        bulldozer.rotateRight(); // go west
        bulldozer.step();

        assertEquals(-1, bulldozer.getCol());
        assertEquals(1, bulldozer.getRow());

        bulldozer.rotateRight(); // go north
        bulldozer.step();

        assertEquals(-1, bulldozer.getCol());
        assertEquals(0, bulldozer.getRow());

        bulldozer.rotateRight(); // go east again
        bulldozer.step();

        assertEquals(0, bulldozer.getCol());
        assertEquals(0, bulldozer.getRow());
    }
}
