package edu.utdallas.cs4348;

public class TestUtil {
    public static final int SIXTEEN_KB = (int)Math.pow(2, 14);
    public static final int TWENTY_KB = (int)Math.pow(2, 10)*20;
    public static final int THIRTY_TWO_KB = (int)Math.pow(2, 15);
    public static final int SIXTY_FOUR_KB = (int)Math.pow(2, 16);

    public static void testLookup(int logicalAddress, Process process, MainMemory mainMemory, int expected) {
        testLookup(logicalAddress, process, mainMemory, expected, false);
    }

    public static void testLookup(int logicalAddress, Process process, MainMemory mainMemory, int expected, boolean expectedTlbHit) {
        LookupInfo info = new LookupInfo(logicalAddress, process);
        mainMemory.getPhysicalAddress(info);
        if ( info.getPhysicalAddress() != expected) {
            throw new RuntimeException("Expected " + logicalAddress + " to translated to " + expected + ", not " + info.getPhysicalAddress() + " (note that -1 means the page either doesn't exist or isn't in memory)");
        }
        if ( info.isTlbHit() != expectedTlbHit) {
            throw new RuntimeException("Expected TLB hit to be " + expectedTlbHit + ", but it was " + info.isTlbHit());
        }
    }

}
