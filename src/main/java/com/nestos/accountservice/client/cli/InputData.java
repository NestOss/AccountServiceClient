package com.nestos.accountservice.client.cli;

import com.lexicalscope.jewel.cli.CliFactory;
import java.util.Random;
import org.apache.commons.lang3.Range;
import static org.apache.commons.lang3.Validate.inclusiveBetween;

/**
 * Data model for command line arguments.
 *
 * @author Roman Osipov
 */
public class InputData {
    //-------------------Logger---------------------------------------------------
    //-------------------Constants------------------------------------------------

    private final int MIN_VALUE = -15000;
    private final int MAX_VALUE = 15000;
    //-------------------Fields---------------------------------------------------
    private final int rCount; // Reader (getAmount) thread number.
    private final int wCount; // Writer (addAmount) thread number.
    private final Range<Integer> idRange; // Id range.
    private final Random rand = new Random();

    //-------------------Constructors---------------------------------------------
    /**
     * Creates data model for command line arguments.
     *
     * @param args command line arguments.
     */
    public InputData(String[] args) {
        ClientCli clientCli = CliFactory.parseArguments(ClientCli.class, args);
        rCount = clientCli.getRCount();
        inclusiveBetween(1, ClientCli.MAX_R_THREAD_NUMBER, rCount,
                String.format("Invlalid argument: %d %s", rCount, ClientCli.READER_HELP_MESSAGE));
        wCount = clientCli.getWCount();
        inclusiveBetween(1, ClientCli.MAX_W_THREAD_NUMBER, wCount,
                String.format("Invlalid argument: %d %s", wCount, ClientCli.WRITER_HELP_MESSAGE));
        Integer idMin = clientCli.getIdList().get(0);
        inclusiveBetween(0, Integer.MAX_VALUE, idMin,
                String.format("Invlalid argument: %d %s", idMin, ClientCli.ID_LIST_HELP_MESSAGE));
        Integer idMax = clientCli.getIdList().get(1);
        inclusiveBetween(0, Integer.MAX_VALUE, idMax,
                String.format("Invlalid argument: %d %s", idMax, ClientCli.ID_LIST_HELP_MESSAGE));
        idRange = Range.between(idMin, idMax);
    }

    //-------------------Getters and setters--------------------------------------
    /**
     * Returns reader (getAmount) thread number.
     *
     * @return reader (getAmount) thread number.
     */
    public int getrCount() {
        return rCount;
    }

    /**
     * Returns writer (addAmount) thread number.
     *
     * @return writer (addAmount) thread number.
     */
    public int getwCount() {
        return wCount;
    }

    /**
     * Returns id range.
     *
     * @return id range (inclusive).
     */
    public Range<Integer> getIdRange() {
        return idRange;
    }
    //-------------------Methods--------------------------------------------------

    /**
     * Returns random id from id range.
     *
     * @return random id from id range.
     */
    public int getRandomId() {
        return rand.nextInt((idRange.getMaximum() - idRange.getMinimum()) + 1)
                + idRange.getMinimum();
    }

    /**
     * Returns random value from MIN_VALUE to MAX_VALUE.
     *
     * @return random value from MIN_VALUE to MAX_VALUE.
     */
    public Long getRandomValue() {
        return Long.valueOf(rand.nextInt((MAX_VALUE - MIN_VALUE) + 1) + MIN_VALUE);
    }
}
