package au.kjezek.consite;

import au.kjezek.consite.domain.BillItem;
import au.kjezek.consite.domain.SimulationBill;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SimulationBillTest {

    private SimulationBill bill = new SimulationBill();

    /**
     * Test adding and sum of items in the bill.
     */
    @Test
    public void testSumItems() {
        bill.add(BillItem.FUEL, 2);
        bill.add(BillItem.FUEL, 5);
        bill.add(BillItem.FUEL, 10);

        bill.add(BillItem.PAINT, 1);
        bill.add(BillItem.PROTECTED, 2);

        assertEquals(17, bill.getSumItem(BillItem.FUEL));
        assertEquals(2, bill.getSumItem(BillItem.PAINT));
        assertEquals(20, bill.getSumItem(BillItem.PROTECTED));
        assertEquals(0, bill.getSumItem(BillItem.RADIO));

        assertEquals(39, bill.total());
    }

    /**
     * Test null item.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNullItem() {
        bill.add(null, 0);
    }

    /**
     * Test negative units.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNegativeUnits() {
        bill.add(BillItem.PAINT, -1);
    }

}
