package com.nestos.accountservice.client.cli;

import com.lexicalscope.jewel.cli.ArgumentValidationException;
import java.util.Arrays;
import java.util.List;
import junitparams.JUnitParamsRunner;
import static junitparams.JUnitParamsRunner.$;
import junitparams.Parameters;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Tests of InputData.
 *
 * @author Roman Osipov
 */
@RunWith(JUnitParamsRunner.class)
public class InputDataTest {

    public InputDataTest() {
    }

    private static Object getValidArgs() {
        return $(
                Arrays.asList("--rCount", "10", "--wCount", "20", "--idList", "45", "60"),
                Arrays.asList("--rCount", "1", "--wCount", "1", "--idList", "0", "0"),
                Arrays.asList("--rCount", "" + ClientCli.MAX_R_THREAD_NUMBER, "--wCount",
                "" + ClientCli.MAX_W_THREAD_NUMBER, "--idList", "" + Integer.MAX_VALUE, "" + Integer.MAX_VALUE));
    }

    private static Object getInsufficientArgs() {
        return $(
                Arrays.asList("--rCount", "10", "--wCount", "10"),
                Arrays.asList("--rCount", "10", "--idList", "10", "20"),
                Arrays.asList("--wCount", "10", "--idList", "10", "20"));
    }

    private static Object getInvalidArgs() {
        return $(
                Arrays.asList("--rCount", "-1", "--wCount", "20", "--idList", "45", "60"),
                Arrays.asList("--rCount", "0.1", "--wCount", "20", "--idList", "45", "60"),
                Arrays.asList("--rCount", "10", "--wCount", "0.1", "--idList", "45", "60"),
                Arrays.asList("--rCount", "10", "--wCount", "-1", "--idList", "45", "60"),
                Arrays.asList("--rCount", "1", "--wCount", "1", "--idList", "0.1", "0"),
                Arrays.asList("--rCount", "1", "--wCount", "1", "--idList", "0", "0.1"),
                Arrays.asList("--rCount", "1", "--wCount", "1", "--idList", "-1", "0"),
                Arrays.asList("--rCount", "1", "--wCount", "1", "--idList", "0", "-1"));
    }

    private static Object getIllegalArgs() {
        return $(
                Arrays.asList("--rCount", "" + (ClientCli.MAX_R_THREAD_NUMBER + 1), "--wCount", "20",
                "--idList", "45", "60"),
                Arrays.asList("--rCount", "10", "--wCount", "" + (ClientCli.MAX_W_THREAD_NUMBER + 1),
                "--idList", "45", "60"));
    }

    @Test
    @Parameters(method = "getValidArgs")
    public void validArgsShouldWork(List<String> argsList) {
        String[] args = (String[]) argsList.toArray();
        InputData inputData = new InputData(args);
        Assert.assertEquals(Integer.parseInt(args[1]), inputData.getrCount());
        Assert.assertEquals(Integer.parseInt(args[3]), inputData.getwCount());
        Assert.assertEquals(Integer.parseInt(args[5]), (int) inputData.getIdRange().getMinimum());
        Assert.assertEquals(Integer.parseInt(args[6]), (int) inputData.getIdRange().getMaximum());
    }

    @Test(expected = ArgumentValidationException.class)
    @Parameters(method = "getInsufficientArgs")
    public void insufficientArgsShouldThrowArgumentValidationException(List<String> argsList) {
        String[] args = (String[]) argsList.toArray();
        InputData inputData = new InputData(args);
    }

    @Test(expected = ArgumentValidationException.class)
    @Parameters(method = "getInvalidArgs")
    public void invalidArgsShouldThrowArgumentValidationException(List<String> argsList) {
        String[] args = (String[]) argsList.toArray();
        InputData inputData = new InputData(args);
    }

    @Test(expected = IllegalArgumentException.class)
    @Parameters(method = "getIllegalArgs")
    public void illegalArgsShouldThrowIllegalArgumentException(List<String> argsList) {
        String[] args = (String[]) argsList.toArray();
        InputData inputData = new InputData(args);
    }
}
