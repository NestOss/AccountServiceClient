package com.nestos.accountservice.client;

import com.lexicalscope.jewel.cli.ArgumentValidationException;
import com.nestos.accountservice.client.cli.InputData;
import com.nestos.accountservice.client.runnable.SubmitServiceRequestsRunnable;
import com.nestos.accountservice.client.javaconfig.ClientConfig;
import com.nestos.accountservice.service.AccountService;
import java.util.Scanner;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Program main class.
 *
 * @author Roman Osipov
 */
public class Main {
    //-------------------Logger---------------------------------------------------
    //-------------------Constants------------------------------------------------

    private static final String ACCOUNT_SERVICE_BEAN_NAME = "remoteAccountService";

    //-------------------Fields---------------------------------------------------
    //-------------------Constructors---------------------------------------------
    //-------------------Getters and setters--------------------------------------
    //-------------------Methods--------------------------------------------------
    public static void main(String[] args) throws InterruptedException {
        try {
            InputData inputData = new InputData(args);
            AnnotationConfigApplicationContext springContext = null;
            Thread submitTasksThread = null;
            try {
                System.out.println("Starting AccountServiceClient...");
                springContext = new AnnotationConfigApplicationContext();
                springContext.register(ClientConfig.class);
                springContext.refresh();
                System.out.println("AccountServiceClient started.");
                AccountService accountService = springContext.getBean(ACCOUNT_SERVICE_BEAN_NAME, AccountService.class);
                SubmitServiceRequestsRunnable submitTasksRunnable = new SubmitServiceRequestsRunnable(accountService, inputData);
                submitTasksThread = new Thread(submitTasksRunnable);
                submitTasksThread.start();
                System.out.println("Reader threads number: " + inputData.getrCount());
                System.out.println("Writer threads number: " + inputData.getwCount());
                System.out.println("Id range: " + inputData.getIdRange());
                System.out.println("AccountServiceClient working now. Type 'stop' to shutdown client.");
                Scanner scanner = new Scanner(System.in);
                while (!scanner.nextLine().equals("stop")) {
                };
            } finally {
                if (submitTasksThread != null) {
                    submitTasksThread.interrupt();
                }
                if (springContext != null) {
                    springContext.close();
                }
                System.out.println("AccountServiceClient stoped.");
                // Nothing to lost. Not use additional library to interrupt rmi calls.
                System.exit(0);
            }
        } catch (ArgumentValidationException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
