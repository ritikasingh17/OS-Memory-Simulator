package edu.utdallas.cs4348;

import org.junit.jupiter.api.Test;

public class TestAddressConversion {

    @Test
    public void testSimpleConversion() {
        Process process = new Process(16);
        MainMemory mainMemory = StudentSubmissionFactory.createMainMemory(TestUtil.SIXTY_FOUR_KB);
        mainMemory.addPageToMemory(process.getEntryAt(1));


        TestUtil.testLookup(4096, process, mainMemory, 0);
        TestUtil.testLookup(4098, process, mainMemory, 2);

        // Doesn't exist
        TestUtil.testLookup(0, process, mainMemory, -1);
    }

}
