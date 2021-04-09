package au.kjezek.consite;

/**
 * A factory class to build various simulation actions.
 * This covers key simulation operations: bulldozer movement, end of simulation, etc.
 */
public class ActionsFactory {


    /**
     *
     * @return an action that rotates the bulldozer
     */
    public static ActionArgument rotateLeft() {
        return (x) -> (bill, bulldozer, map, end) -> bulldozer.rotateLeft();
    }

    /**
     *
     * @return an action that rotates the bulldozer
     */
    public static ActionArgument rotateRight() {
        return (x) -> (bill, bulldozer, map, end) -> bulldozer.rotateRight();
    }

    /**
     *
     * @return an action that quits the simulation and computes the bill of uncleared land.
     */
    public static ActionArgument quit() {
        return (x) -> (bill, bulldozer, map, end) -> {
            for (FieldType field : map.fields()) {
                if (field != FieldType.PLAIN && field != FieldType.PROTECTED) {
                    bill.add(BillItem.UNCLEARED, 1);
                }
            }

            end.accept(true);   // signal end
        };
    }

    /**
     *
     * @return an action that moves the bulldozer and adjust the land and the bill accordingly.
     */
    public static ActionArgument advance() {
        return (steps) -> (bill, bulldozer, map, end) -> {

            for (int step = 0; step < steps; step++) {

                // move and charge fuel all the time
                bulldozer.step();
                int row = bulldozer.getRow();
                int col = bulldozer.getCol();

                // if this is out of the map, the simulation ends
                if (map.isOutsideMap(row, col)) {
                    // TODO - no fuel charged when moving out of the map?
                    quit().noArgs().action(bill, bulldozer, map, end);
                    break;
                }

                FieldType origField = map.getField(row, col);

                // update bill to charge fuel
                bill.add(BillItem.FUEL, origField.fuel);

                // clear the land
                map.clear(row, col);

                // a corner cases - protected land, it ends simulation with penalty
                if (FieldType.PROTECTED == origField) {
                    bill.add(BillItem.PROTECTED, 1);
                    quit().noArgs().action(bill, bulldozer, map, end);
                    break;      // no more move
                }

                // a corner case - fast drive through a tree area - fee for painting incurs
                if (FieldType.TREES == origField
                        && step < steps - 1) {

                    bill.add(BillItem.PAINT, 1);
                }
            }
        };
    }
}
