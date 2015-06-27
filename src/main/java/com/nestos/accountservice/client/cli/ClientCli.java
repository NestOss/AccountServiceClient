package com.nestos.accountservice.client.cli;

import com.lexicalscope.jewel.cli.Option;
import java.util.List;

/**
 * Command line interface.
 *
 * @author Roman Osipov
 */
public interface ClientCli {

    /**
     * Max number of threads, which calls getAmount service method.
     */
    int MAX_R_THREAD_NUMBER = 10000;
    /**
     * Max number of threads, which calls addAmount service method.
     */
    int MAX_W_THREAD_NUMBER = 10000;
    String READER_HELP_MESSAGE = "reader threads number, <positive integer, lesser then "
            + MAX_R_THREAD_NUMBER + ">";
    String WRITER_HELP_MESSAGE = "writer threads number, <positive integer, lesser then "
            + MAX_W_THREAD_NUMBER + ">";
    String ID_LIST_HELP_MESSAGE =
            "range of key ids, <min_inclusive(positive integer) max_inclusive(positive integer)>";

    @Option(description = READER_HELP_MESSAGE)
    int getRCount();

    @Option(description = WRITER_HELP_MESSAGE)
    int getWCount();

    @Option(description = ID_LIST_HELP_MESSAGE, exactly = 2)
    List<Integer> getIdList();
}
