package com.nestos.accountservice.client.runnable;

import com.nestos.accountservice.service.AccountService;
import java.util.concurrent.Callable;

/**
 * Task for call getAmount service method.
 * 
 * @author Roman Osipov
 */
public class GetAmountTask implements Callable<Long> {
    //-------------------Logger---------------------------------------------------

    //-------------------Constants------------------------------------------------
    private final Integer id;
    private final AccountService accountService;

    //-------------------Fields---------------------------------------------------
    //-------------------Constructors---------------------------------------------
    /**
     * Constructs getAmmount task.
     *
     * @param accountService account service.
     * @param id balance identifier.
     */
    public GetAmountTask(AccountService accountService, Integer id) {
        this.id = id;
        this.accountService = accountService;
    }
    //-------------------Getters and setters--------------------------------------

    //-------------------Methods--------------------------------------------------
    @Override
    public Long call() throws Exception {
        return accountService.getAmount(id);
    }

}
