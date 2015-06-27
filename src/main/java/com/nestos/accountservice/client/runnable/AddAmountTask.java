package com.nestos.accountservice.client.runnable;

import com.nestos.accountservice.service.AccountService;

/**
 *  Task for call addAmount service method.
 *
 * @author Roman Osipov
 */
public class AddAmountTask implements Runnable {

    //-------------------Logger---------------------------------------------------
    //-------------------Constants------------------------------------------------
    private final Integer id;
    private final Long value;
    private final AccountService accountService;

    //-------------------Fields---------------------------------------------------
    //-------------------Constructors---------------------------------------------
    /**
     * Constructs task.
     *
     * @param accountService account service.
     * @param id balance identifier
     * @param value positive or negative value, which must be added to current balance.
     */
    public AddAmountTask(AccountService accountService, Integer id, Long value) {
        this.id = id;
        this.value = value;
        this.accountService = accountService;
    }

    //-------------------Getters and setters--------------------------------------
    //-------------------Methods--------------------------------------------------
    @Override
    public void run() {
        accountService.addAmount(id, value);
    }
}
