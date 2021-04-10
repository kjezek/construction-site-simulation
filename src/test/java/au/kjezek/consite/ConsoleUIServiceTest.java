package au.kjezek.consite;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * UI is tested mostly only visually.
 */
public class ConsoleUIServiceTest {

    private final SiteMap map = new SiteMap(new String[]{
            "ooro",
            "totT",
            "rror"
    });

    private final SimulationBill bill = new SimulationBill();
    private final UIService ui = new ConsoleUIService();

    @Before
    public void init() {
        bill.add(BillItem.PROTECTED, 2);
        bill.add(BillItem.FUEL, 10);
        bill.add(BillItem.RADIO, 12);
    }

    @Test
    public void testPrintBill() {
        ui.printBill(bill);
    }

    @Test
    public void testPrintMap() {
        ui.printMap(map);
    }

    @Test
    public void testReadActions() throws IOException {
        String txt = "advance 4 \n a 3\n left \n l \n right \n r \n q ";
        ByteArrayInputStream stream = new ByteArrayInputStream(txt.getBytes());

        List<CommandAndParam> commands = ui.readActions(stream);

        assertEquals(CommandType.ADVANCE, commands.get(0).commandType);
        assertEquals(new Integer(4), commands.get(0).param);

        assertEquals(CommandType.ADVANCE, commands.get(1).commandType);
        assertEquals(new Integer(3), commands.get(1).param);

        assertEquals(CommandType.LEFT, commands.get(2).commandType);
        assertEquals(CommandType.LEFT, commands.get(3).commandType);
        assertEquals(CommandType.RIGHT, commands.get(4).commandType);
        assertEquals(CommandType.RIGHT, commands.get(5).commandType);
        assertEquals(CommandType.QUIT, commands.get(6).commandType);
    }

    @Test(expected = NoSuchElementException.class)
    public void testReadWrongCommands() throws IOException {
        String txt = "xxx";
        ByteArrayInputStream stream = new ByteArrayInputStream(txt.getBytes());

        ui.readActions(stream);
    }

    @Test
    public void testReadAdvanceWithoutArgs() throws IOException {
        String txt = "a";
        ByteArrayInputStream stream = new ByteArrayInputStream(txt.getBytes());

        List<CommandAndParam> commands = ui.readActions(stream);

        assertEquals(CommandType.ADVANCE, commands.get(0).commandType);
        assertNull(commands.get(0).param);
    }

    @Test
    public void testReadSiteMap() throws IOException {
        String txt =
                "ooro\n" +
                "totT\n" +
                "rror";

        ByteArrayInputStream stream = new ByteArrayInputStream(txt.getBytes());
        SiteMap map = ui.readMap(stream);

        assertEquals(FieldType.PLAIN, map.getField(0,0));
        assertEquals(FieldType.PLAIN, map.getField(0,1));
        assertEquals(FieldType.ROCKY, map.getField(0,2));
        assertEquals(FieldType.PLAIN, map.getField(0,3));

        assertEquals(FieldType.TREES, map.getField(1,0));
        assertEquals(FieldType.PLAIN, map.getField(1,1));
        assertEquals(FieldType.TREES, map.getField(1,2));
        assertEquals(FieldType.PROTECTED, map.getField(1,3));

        assertEquals(FieldType.ROCKY, map.getField(2,0));
        assertEquals(FieldType.ROCKY, map.getField(2,1));
        assertEquals(FieldType.PLAIN, map.getField(2,2));
        assertEquals(FieldType.ROCKY, map.getField(2,3));
    }

    @Test(expected = NoSuchElementException.class)
    public void testReadWrongSiteMap() throws IOException {
        String txt = "x";

        ByteArrayInputStream stream = new ByteArrayInputStream(txt.getBytes());
        SiteMap map = ui.readMap(stream);
    }
}
