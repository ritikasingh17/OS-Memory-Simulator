package edu.utdallas.cs4348;

import org.junit.jupiter.api.Test;

public class TestVictimFrame {

    @Test
    public void testFrameVictimization() {
        Process process = new Process(16);
        Process process2 = new Process(16);
        MainMemory mainMemory = StudentSubmissionFactory.createMainMemory(TestUtil.THIRTY_TWO_KB);

        // Fill up the memory system with a mix of pages from two processes
        mainMemory.addPageToMemory(process.getEntryAt(0));
        mainMemory.addPageToMemory(process.getEntryAt(1));
        mainMemory.addPageToMemory(process.getEntryAt(2));
        mainMemory.addPageToMemory(process.getEntryAt(3));
        mainMemory.addPageToMemory(process2.getEntryAt(0));
        mainMemory.addPageToMemory(process2.getEntryAt(1));
        mainMemory.addPageToMemory(process2.getEntryAt(2));
        mainMemory.addPageToMemory(process2.getEntryAt(3));

        TestUtil.testLookup(123, process, mainMemory, 123);
        TestUtil.testLookup(5000, process, mainMemory, 5000);
        TestUtil.testLookup(10000, process, mainMemory, 10000);
        TestUtil.testLookup(14000, process, mainMemory, 14000);

        // Not in memory
        TestUtil.testLookup(17000, process, mainMemory, -1);


        TestUtil.testLookup(123, process2, mainMemory, (123 + TestUtil.SIXTEEN_KB));
        TestUtil.testLookup(5000, process2, mainMemory, (5000 + TestUtil.SIXTEEN_KB));
        TestUtil.testLookup(10000, process2, mainMemory, (10000 + TestUtil.SIXTEEN_KB));
        TestUtil.testLookup(14000, process2, mainMemory, (14000 + TestUtil.SIXTEEN_KB));

        // Doesn't exist
        TestUtil.testLookup(17000, process2, mainMemory, -1);

        // Force the first page in memory out
        mainMemory.addPageToMemory(process2.getEntryAt(4));
        TestUtil.testLookup((123 + TestUtil.SIXTEEN_KB), process2, mainMemory, 123);

        // Not in memory
        TestUtil.testLookup(100, process, mainMemory, -1);

        // Force all original pages to be kicked out, plus 1 to check the circular motion
        mainMemory.addPageToMemory(process.getEntryAt(5));
        mainMemory.addPageToMemory(process.getEntryAt(6));
        mainMemory.addPageToMemory(process.getEntryAt(7));
        mainMemory.addPageToMemory(process.getEntryAt(8));
        mainMemory.addPageToMemory(process.getEntryAt(9));
        mainMemory.addPageToMemory(process.getEntryAt(10));
        mainMemory.addPageToMemory(process.getEntryAt(11));

        TestUtil.testLookup(22000, process, mainMemory, (22000 - TestUtil.SIXTEEN_KB));
        TestUtil.testLookup(26000, process, mainMemory, (26000 - TestUtil.SIXTEEN_KB));
        TestUtil.testLookup(30000, process, mainMemory, (30000 - TestUtil.SIXTEEN_KB));
        TestUtil.testLookup(34000, process, mainMemory, (34000 - TestUtil.SIXTEEN_KB));
        TestUtil.testLookup(38000, process, mainMemory, (38000 - TestUtil.SIXTEEN_KB));
        TestUtil.testLookup(42000, process, mainMemory, (42000 - TestUtil.SIXTEEN_KB));

        mainMemory.addPageToMemory(process.getEntryAt(0));
        TestUtil.testLookup(100, process, mainMemory, 100);

        // Not in memory
        TestUtil.testLookup((123 + TestUtil.SIXTEEN_KB), process2, mainMemory, -1);
    }
}
