package com.nestos.accountservice.client.runnable;

import com.nestos.accountservice.client.cli.InputData;
import com.nestos.accountservice.service.AccountService;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Class for submit requests to AccountService.
 *
 * @author Roman Osipov
 */
public class SubmitServiceRequestsRunnable implements Runnable {

    //-------------------Logger---------------------------------------------------
    //-------------------Constants------------------------------------------------
    private final int WORK_QUEUE_SIZE = 1000; // Maximum tasks number in working queue.
    private static final int KEEP_ALIVE_TIME = 1; // Keep alive for minute.
    //-------------------Fields---------------------------------------------------
    private final InputData inputData; // Data model for command line arguments.
    private final AccountService accountService;
    private ThreadPoolExecutor rExecutor;
    private ThreadPoolExecutor wExecutor;

    //-------------------Constructors---------------------------------------------
    /**
     * Constructs request tasks submitter.
     *
     * @param accountService account service to submit requests.
     * @param inputData data model for command line arguments.
     */
    public SubmitServiceRequestsRunnable(AccountService accountService, InputData inputData) {
        this.inputData = inputData;
        this.accountService = accountService;
    }

    //-------------------Getters and setters--------------------------------------
    //-------------------Methods--------------------------------------------------
    /**
     * A endless loop for submit tasks to AccountService. Loop breaks after interrupt request.
     */
    @Override
    public void run() {
        // Intruduce workQueue with 'discard' policy to provide low memory usage.
        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(WORK_QUEUE_SIZE);
        rExecutor = new ThreadPoolExecutor(
                inputData.getrCount(), // Core pool size.
                inputData.getrCount(), // Maximum pool size.
                KEEP_ALIVE_TIME,
                TimeUnit.MINUTES,
                workQueue,
                new ThreadPoolExecutor.DiscardPolicy());
        workQueue = new LinkedBlockingQueue<>(WORK_QUEUE_SIZE);
        wExecutor = new ThreadPoolExecutor(
                inputData.getwCount(), // Core pool size.
                inputData.getwCount(), // Maximum pool size.
                KEEP_ALIVE_TIME,
                TimeUnit.MINUTES,
                workQueue,
                new ThreadPoolExecutor.DiscardPolicy());
        try {
            // Fill up working queues.
            while (true) {
                for (int i = 0; i < WORK_QUEUE_SIZE; i++) {
                    rExecutor.submit(new GetAmountTask(accountService, inputData.getRandomId()));
                    wExecutor.submit(new AddAmountTask(accountService, inputData.getRandomId(),
                            inputData.getRandomValue()));
                }
                Thread.sleep(0);
            }
        } catch (InterruptedException ex) {
            // Thread finished after finally block execution.  
        } finally {
            rExecutor.shutdownNow();
            wExecutor.shutdownNow();
        }
    }
}
