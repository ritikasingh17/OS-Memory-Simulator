package edu.utdallas.cs4348;

public class StudentSubmissionFactory {

    /**
     * Create a memory system implementation
     *
     * @param maxMemory Size of main memory (in bytes)
     * @return MemorySystem to test with
     */
    public static MainMemory createMainMemory(int maxMemory) {
        return createMainMemory(maxMemory, null);
    }

    /**
     * Return a memory system implementation of type {LastName}MainMemory
     * (you will create that class)
     *
     * @param maxMemory Size of main memory (in bytes)
     * @param tlb TLB; can be null
     * @return {LastName}MainMemory
     */
    public static MainMemory createMainMemory(int maxMemory, TLB tlb) {
        // TODO: fill in for TestAddressConversion and TestVictimFrame (and TestTLB, too)
        return new SinghMainMemory(maxMemory, tlb);
    }

    /**
     * Return a TLB implmentation of type {FirstName}TLB
     * (you will create that class
     *
     * @return {FirstName}TLB
     */
    public static TLB createTLB() {
        // TODO: fill in for TestTLB...do after the MemorySystem above
        return new RitikaTLB();
    }
}