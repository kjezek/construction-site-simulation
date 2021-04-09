package au.kjezek.consite;

/**
 * A factory class to build various simulation actions.
 */
public class ActionsFactory {


    public static SimulationAction rotateLeft() {
        return (bill, bulldozer, map) -> {
            bulldozer.rotateLeft();
            return true;
        };
    }

    public static SimulationAction rotateRight() {
        return (bill, bulldozer, map) -> {
            bulldozer.rotateRight();
            return true;
        };
    }

    public static SimulationAction quit() {
        return (bill, bulldozer, map) -> {
            // TODO - mark somehow end aof simulation
            for (FieldType field : map.fields()) {
                if (field != FieldType.PLAIN && field != FieldType.PROTECTED) {
                    bill.add(BillItem.UNCLEARED, 1);
                }
            }

            return false;
        };
    }

    public static SimulationAction advance(final int steps) {
        return (bill, bulldozer, map) -> {
            boolean result = true;

            for (int step = 0; step < steps; step++) {

                // move and charge fuel all the time
                bulldozer.step();
                int row = bulldozer.getRow();
                int col = bulldozer.getCol();

                // if this is out of the map, the simulation ends
                if (map.isOutsideMap(row, col)) {
                    // TODO - no fuel charged when moving out of the map?
                    result = quit().action(bill, bulldozer, map);
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
                    result = quit().action(bill, bulldozer, map);
                    break;      // no more move
                }

                // a corner case - fast drive through a tree area - fee for painting incurs
                if (FieldType.TREES == origField
                        && step < steps - 1) {

                    bill.add(BillItem.PAINT, 1);
                }
            }

            return result;
        };
    }
}
