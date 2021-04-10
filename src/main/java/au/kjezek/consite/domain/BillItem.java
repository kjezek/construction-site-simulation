package au.kjezek.consite.domain;

/**
 * Bill items.
 */
public enum BillItem {

    RADIO(1),
    FUEL(1),
    UNCLEARED(3),
    PROTECTED(10),
    PAINT(2);

    /** Price per item. */
    public final int price;

    BillItem(final int price) {
        this.price = price;
    }
}
