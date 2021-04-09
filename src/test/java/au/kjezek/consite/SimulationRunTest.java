package au.kjezek.consite;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Test simulation run.
 */
public class SimulationRunTest {

    private final SiteMap map = new SiteMap(new String[]{
            "ooro",
            "totT",
            "rror"
    });
    private final Bulldozer bulldozer = new Bulldozer();
    private final SimulationBill bill = new SimulationBill();

    private final SimulationRun run = new SimulationRun(map, bill, bulldozer);

    /** Test successful run of actions. */
    @Test
    public void testAddAndRunActions() {

        run.addAction(CommandType.ADVANCE, 1);
        run.addAction(CommandType.RIGHT);
        run.addAction(CommandType.ADVANCE, 10); // out of map

        run.process(); // run ALL commands

        // check map - cleared areas
        assertEquals(FieldType.PLAIN, map.getField(1, 0));
        assertEquals(FieldType.PLAIN, map.getField(2, 0));

        // check the bill
        assertEquals(5 * BillItem.FUEL.price, bill.getSumItem(BillItem.FUEL));    // 1 - plain + 2 - tree + 2 - rock
        assertEquals(4 * BillItem.UNCLEARED.price, bill.getSumItem(BillItem.UNCLEARED)); // 5 sites uncleared
        assertEquals(BillItem.PAINT.price, bill.getSumItem(BillItem.PAINT));
        assertEquals(3 * BillItem.RADIO.price, bill.getSumItem(BillItem.RADIO)); // 3 commands

        // end of simulation
        assertFalse(run.isActive());
    }

    @Test(expected = IllegalStateException.class)
    public void commandsAfterEnd() {

        run.addAction(CommandType.ADVANCE, 1);
        run.addAction(CommandType.ADVANCE, 10); // out of map
        run.addAction(CommandType.ADVANCE, 10); // out of map

        run.process(); // run ALL commands

        assertEquals(3 * BillItem.RADIO.price, bill.getSumItem(BillItem.RADIO)); // 3 commands

        run.addAction(CommandType.RIGHT); // must fail
    }

}
