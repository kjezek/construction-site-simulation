package au.kjezek.consite;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test variations of land clearing
 */
public class ActionMoveTest {

    private final SiteMap map = new SiteMap(new String[]{
            "ooro",
            "totT",
            "rror"
    });
    private final Bulldozer bulldozer = new Bulldozer();
    private final SimulationBill bill = new SimulationBill();

    /** Clear part - the first row. */
    @Test
    public void testClearPartly() {
        ActionsFactory.advance(10).action(bill, bulldozer, map);

        // first line clear
        assertEquals(FieldType.PLAIN, map.getField(0, 0));
        assertEquals(FieldType.PLAIN, map.getField(0, 1));
        assertEquals(FieldType.PLAIN, map.getField(0, 2));
        assertEquals(FieldType.PLAIN, map.getField(0, 3));

        // second line no change
        assertEquals(FieldType.TREES, map.getField(1, 0));
        assertEquals(FieldType.PLAIN, map.getField(1, 1));
        assertEquals(FieldType.TREES, map.getField(1, 2));
        assertEquals(FieldType.PROTECTED, map.getField(1, 3));

        // check the bill
        assertEquals((3+2) * BillItem.FUEL.price, bill.getSumItem(BillItem.FUEL));
        assertEquals((2+3) * BillItem.UNCLEARED.price, bill.getSumItem(BillItem.UNCLEARED));
        assertEquals(0, bill.getSumItem(BillItem.PROTECTED));
        assertEquals(0, bill.getSumItem(BillItem.PAINT));
        assertEquals(0, bill.getSumItem(BillItem.RADIO));       // computed at different level

        // check bulldozer position
        assertEquals(0, bulldozer.getRow());
        assertEquals(4, bulldozer.getCol());

    }

    /** Test protected area is cleared. */
    @Test
    public void testClearProtected() {

        // move to cleared area
        ActionsFactory.advance(4).action(bill, bulldozer, map);
        ActionsFactory.rotateRight().action(bill, bulldozer, map);
        ActionsFactory.advance(100).action(bill, bulldozer, map);

        // check that the fine applied
        assertEquals(BillItem.PROTECTED.price, bill.getSumItem(BillItem.PROTECTED));

        // check the bulldoer stays at the "T"
        assertEquals(1, bulldozer.getRow());
        assertEquals(3, bulldozer.getCol());

        // protected cleared
        assertEquals(FieldType.PLAIN, map.getField(1, 3));
    }


    /** Test new paint is charged. */
    @Test
    public void testPaintCharged() {

        // move to cleared area
        ActionsFactory.advance(1).action(bill, bulldozer, map);
        ActionsFactory.rotateRight().action(bill, bulldozer, map);
        ActionsFactory.advance(2).action(bill, bulldozer, map);

        // check that the fine applied
        assertEquals(BillItem.PAINT.price, bill.getSumItem(BillItem.PAINT));

        // check the bulldozer stays at the "T"
        assertEquals(2, bulldozer.getRow());
        assertEquals(0, bulldozer.getCol());

        // tree cleared
        assertEquals(FieldType.PLAIN, map.getField(2, 0));
    }
}
