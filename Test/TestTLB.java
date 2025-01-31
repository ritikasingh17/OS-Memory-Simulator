package edu.utdallas.cs4348;

import org.junit.jupiter.api.Test;

public class TestTLB {

    @Test
    public void simpleTLBTest() {
        TLB tlb = StudentSubmissionFactory.createTLB();
        MainMemory mainMemory = StudentSubmissionFactory.createMainMemory(TestUtil.THIRTY_TWO_KB, tlb);
        Process process = new Process(64);
        mainMemory.addPageToMemory(process.getEntryAt(1));

        // Get something into the TLB, then verify that it's there
        TestUtil.testLookup(5000, process, mainMemory, 904, false);
        TestUtil.testLookup(5000, process, mainMemory, 904, true);

    }

    @Test
    public void complexTLBTest() {
        TLB tlb = StudentSubmissionFactory.createTLB();
        MainMemory mainMemory = StudentSubmissionFactory.createMainMemory(TestUtil.SIXTY_FOUR_KB, tlb);
        Process process = new Process(64);
        Process process2 = new Process(64);

        // Add enough pages to memory so we can overfill the TLB
        mainMemory.addPageToMemory(process.getEntryAt(1));
        mainMemory.addPageToMemory(process.getEntryAt(2));
        mainMemory.addPageToMemory(process.getEntryAt(3));
        mainMemory.addPageToMemory(process.getEntryAt(4));
        mainMemory.addPageToMemory(process.getEntryAt(5));
        mainMemory.addPageToMemory(process2.getEntryAt(1));
        mainMemory.addPageToMemory(process2.getEntryAt(2));
        mainMemory.addPageToMemory(process2.getEntryAt(3));
        mainMemory.addPageToMemory(process2.getEntryAt(4));
        mainMemory.addPageToMemory(process2.getEntryAt(5));

        // Load up the TLB
        TestUtil.testLookup(5000, process, mainMemory, 904, false);
        TestUtil.testLookup(9000, process, mainMemory, 4904, false);
        TestUtil.testLookup(13000, process, mainMemory, 8904, false);
        TestUtil.testLookup(17000, process, mainMemory, 12904, false);
        TestUtil.testLookup(5000, process2, mainMemory, 904+TestUtil.TWENTY_KB, false);
        TestUtil.testLookup(9000, process2, mainMemory, 4904+TestUtil.TWENTY_KB, false);
        TestUtil.testLookup(13000, process2, mainMemory, 8904+TestUtil.TWENTY_KB, false);
        TestUtil.testLookup(17000, process2, mainMemory, 12904+TestUtil.TWENTY_KB, false);

        // Verify all are now in the TLB
        TestUtil.testLookup(5000, process, mainMemory, 904, true);
        TestUtil.testLookup(9000, process, mainMemory, 4904, true);
        TestUtil.testLookup(13000, process, mainMemory, 8904, true);
        TestUtil.testLookup(17000, process, mainMemory, 12904, true);
        TestUtil.testLookup(5000, process2, mainMemory, 904+TestUtil.TWENTY_KB, true);
        TestUtil.testLookup(9000, process2, mainMemory, 4904+TestUtil.TWENTY_KB, true);
        TestUtil.testLookup(13000, process2, mainMemory, 8904+TestUtil.TWENTY_KB, true);
        TestUtil.testLookup(17000, process2, mainMemory, 12904+TestUtil.TWENTY_KB, true);

        // Knock out page 1 with process 2's page 5, verify the pages are correct now (2-4, 1-5), then knock out page 2 with page 1
        TestUtil.testLookup(21000, process2, mainMemory, 16904+TestUtil.TWENTY_KB, false);

        TestUtil.testLookup(9000, process, mainMemory, 4904, true);
        TestUtil.testLookup(13000, process, mainMemory, 8904, true);
        TestUtil.testLookup(17000, process, mainMemory, 12904, true);
        TestUtil.testLookup(5000, process2, mainMemory, 904+TestUtil.TWENTY_KB, true);
        TestUtil.testLookup(9000, process2, mainMemory, 4904+TestUtil.TWENTY_KB, true);
        TestUtil.testLookup(13000, process2, mainMemory, 8904+TestUtil.TWENTY_KB, true);
        TestUtil.testLookup(17000, process2, mainMemory, 12904+TestUtil.TWENTY_KB, true);
        TestUtil.testLookup(21000, process2, mainMemory, 16904+TestUtil.TWENTY_KB, true);

        // Knock out page 2 with page 1 (both process 1)
        TestUtil.testLookup(5000, process, mainMemory, 904, false);
        TestUtil.testLookup(5000, process, mainMemory, 904, true);
        TestUtil.testLookup(13000, process, mainMemory, 8904, true);
        TestUtil.testLookup(17000, process, mainMemory, 12904, true);
        TestUtil.testLookup(5000, process2, mainMemory, 904+TestUtil.TWENTY_KB, true);
        TestUtil.testLookup(9000, process2, mainMemory, 4904+TestUtil.TWENTY_KB, true);
        TestUtil.testLookup(13000, process2, mainMemory, 8904+TestUtil.TWENTY_KB, true);
        TestUtil.testLookup(17000, process2, mainMemory, 12904+TestUtil.TWENTY_KB, true);
        // NOTE: If this one fails, you probably aren't updating the "last accessed" field
        // in the PageTableEntry when you have a TLB hit
        TestUtil.testLookup(21000, process2, mainMemory, 16904+TestUtil.TWENTY_KB, true);

        // Here we're adding process 1, page 2 (1,2), and skipping  2,2
        // This will cascade out process 1, pages 3-4 and process 2, page 1
        // because on each lookup we have to toss out a page, and the "next"
        // one is the oldest at that point.
        TestUtil.testLookup(5000, process, mainMemory, 904, true);
        // This is the one we're adding, which will kick out 1,3, then 1,3 will kick out 1,4, etc.
        TestUtil.testLookup(9000, process, mainMemory, 4904, false);
        TestUtil.testLookup(13000, process, mainMemory, 8904, false);
        TestUtil.testLookup(17000, process, mainMemory, 12904, false);
        TestUtil.testLookup(5000, process2, mainMemory, 904+TestUtil.TWENTY_KB, false);
        // We skipped 2,2, so these last 3 will be in the TLB (because we stopped the "cascade")
        TestUtil.testLookup(13000, process2, mainMemory, 8904+TestUtil.TWENTY_KB, true);
        TestUtil.testLookup(17000, process2, mainMemory, 12904+TestUtil.TWENTY_KB, true);
        TestUtil.testLookup(21000, process2, mainMemory, 16904+TestUtil.TWENTY_KB, true);

        // Verify 2,2 was kicked out
        TestUtil.testLookup(9000, process2, mainMemory, 4904+TestUtil.TWENTY_KB, false);
    }
}
