package au.kjezek.consite;

import au.kjezek.consite.actions.SimulationAction;
import au.kjezek.consite.domain.*;
import au.kjezek.consite.services.SimulationService;
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

        run.process(CommandAndParam.of(CommandType.ADVANCE, 1), map, bill, bulldozer); // run commands
        run.process(CommandAndParam.of(CommandType.RIGHT), map, bill, bulldozer); // run commands
        run.process(CommandAndParam.of(CommandType.ADVANCE, 10), map, bill, bulldozer); // run commands

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

        boolean active = run.process(CommandAndParam.of(CommandType.ADVANCE, 1), map, bill, bulldozer); // run ALL commands
        active &= run.process(CommandAndParam.of(CommandType.ADVANCE, 1), map, bill, bulldozer); // run ALL commands
        active &= run.process(CommandAndParam.of(CommandType.QUIT), map, bill, bulldozer); // run ALL commands

        assertFalse(active);
        assertEquals(2 * BillItem.RADIO.price, bill.getSumItem(BillItem.RADIO)); // 3 commands
    }


}
