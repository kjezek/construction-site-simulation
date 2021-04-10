package au.kjezek.consite.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * A bill of the simulation.
 */
public class SimulationBill {

    private final Map<BillItem, Integer> bill = new HashMap<>();

    /**
     * Add number of units in the bill.
     * @param item the bill item.
     * @param units units.
     */
    public void add(final BillItem item, final int units) {
        if (item == null) {
            throw new IllegalArgumentException("Item must be filled. ");
        }
        if (units < 0) {
            throw new IllegalArgumentException("Units must be non-negative. ");
        }
        bill.compute(item, (k, v) -> v == null ? units : v + units);
    }

    /**
     * Get sum price for the input item.
     * @param item the item
     * @return the total price of the item, or null.
     */
    public int getSumItem(final BillItem item) {
        return Optional.ofNullable(bill.get(item)).orElse(0) * item.price;
    }


    /**
     * Get the number of units of the input item.
     * @param item the item
     * @return the total price of the item, or null.
     */
    public int getUnitsItem(final BillItem item) {
        return Optional.ofNullable(bill.get(item)).orElse(0);
    }

    /**
     * Total value of the bill.
     * @return sum of all items in the bill
     */
    public int total() {

        return bill.entrySet().stream().mapToInt(e -> e.getKey().price * e.getValue()).sum();
    }
}
