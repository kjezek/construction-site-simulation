package au.kjezek.consite;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * This is a UI service.
 */
public interface UIService {


    /**
     * Print the map
     * @param map the map
     */
    void printMap(SiteMap map);

    /**
     * Print the bill
     * @param bill the bill
     */
    void printBill(SimulationBill bill);

    /**
     * Read actions from the user.
     * @param input the input source.
     * @return actions.
     */
    List<CommandAndParam> readActions(InputStream input) throws IOException;

    /**
     * Read site maps from the input source.
     * @param input the input source.
     * @return the map.
     */
    SiteMap readMap(InputStream input) throws IOException;
}
