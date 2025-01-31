package edu.utdallas.cs4348;

/**
 * Contains what you need to process a frame lookup (and what you'll store if the
 * lookup successes and if it was a TLB hit
 */
public class LookupInfo {
    private final int logicalAddress;
    private final Process process;
    private int physicalAddress = -1;
    private boolean tlbHit = false;

    public LookupInfo(int logicalAddress, Process process) {
        this.logicalAddress = logicalAddress;
        this.process = process;
    }

    /**
     * The logical address we're looking up
     * @return Logical address
     */
    public int getLogicalAddress() {
        return logicalAddress;
    }

    /**
     * The process we're concerned with
     * @return The process for this lookup
     */
    public Process getProcess() {
        return process;
    }

    /**
     * The resulting physical address; -1 if not found
     * @return Physical address
     */
    public int getPhysicalAddress() {
        return physicalAddress;
    }

    /**
     * Set the physical address, or make it -1 if the address can't be converted
     * (not a valid page or isn't in main memory)
     * @param physicalAddress Physical address for this lookup (result of the lookup); -1 if not in main memory
     */
    public void setPhysicalAddress(int physicalAddress) {
        this.physicalAddress = physicalAddress;
    }

    public boolean isTlbHit() {
        return tlbHit;
    }

    public void setTlbHit(boolean tlbHit) {
        this.tlbHit = tlbHit;
    }
}