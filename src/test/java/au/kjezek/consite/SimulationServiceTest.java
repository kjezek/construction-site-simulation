package au.kjezek.consite;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Test simulation run.
 */
public class SimulationServiceTest {

    private final SiteMap map = new SiteMap(new String[]{
            "ooro",
            "totT",
            "rror"
    });
    private final Bulldozer bulldozer = new Bulldozer();
    private final SimulationBill bill = new SimulationBill();
    private final SimulationService run = new SimulationService();
    private final List<SimulationAction> actions = new ArrayList<>();

    /** Test successful run of actions. */
    @Test
    public void testAddAndRunActions() {

        run.addAction(actions, bill, CommandType.ADVANCE, 1);
        run.addAction(actions, bill, CommandType.RIGHT);
        run.addAction(actions, bill, CommandType.ADVANCE, 10); // out of map

        run.process(actions, map, bill, bulldozer); // run ALL commands

        // check map - cleared areas
        assertEquals(FieldType.PLAIN, map.getField(1, 0));
        assertEquals(FieldType.PLAIN, map.getField(2, 0));

        // check the bill
        assertEquals((1 + 2 + 2 + 1) * BillItem.FUEL.price, bill.getSumItem(BillItem.FUEL));    // 1 - plain + 2 - tree + 2 - rock + 1 outside the map
        assertEquals(4 * BillItem.UNCLEARED.price, bill.getSumItem(BillItem.UNCLEARED)); // 5 sites uncleared
        assertEquals(BillItem.PAINT.price, bill.getSumItem(BillItem.PAINT));
        assertEquals(3 * BillItem.RADIO.price, bill.getSumItem(BillItem.RADIO)); // 3 commands

    }

    @Test
    public void testQuitCommand() {

        run.addAction(actions, bill, CommandType.ADVANCE, 1);
        run.addAction(actions, bill, CommandType.ADVANCE, 1);
        run.addAction(actions, bill, CommandType.QUIT);

        boolean active = run.process(actions, map, bill, bulldozer); // run ALL commands

        assertFalse(active);
        assertEquals(2 * BillItem.RADIO.price, bill.getSumItem(BillItem.RADIO)); // 3 commands
    }


}
