package edu.utdallas.cs4348;

import java.util.Arrays;

public abstract class MainMemory {
    protected final Frame[] frames;
    protected final TLB tlb;

    protected MainMemory(int numFrames, TLB tlb) {
        numFrames /= Util.SIZE_OF_FRAME;
        frames = new Frame[numFrames];
        for ( int i=0; i<numFrames; i++) {
            frames[i] = new Frame(i);
        }
        this.tlb = tlb;
    }

    protected MainMemory(int numFrames) {
        frames = new Frame[numFrames];
        for ( int i=0; i<numFrames; i++) {
            frames[i] = new Frame(i);
        }
        this.tlb = null;
    }
    /**
     * Turn a logical address (including the process it comes from) into a physical
     * address, if possible. Also, if there is a TLB, try it and report if there
     * was a TLB hit or not
     * @param lookupInfo What you need to look up a logical address...and what you need
     *                   to fill in about the match (if any)
     */
    public abstract void getPhysicalAddress(LookupInfo lookupInfo);

    /**
     * Add a page to main memory. It should go into the first open frame (if any), or, failing
     * that, the least-recently-ADDED frame
     * @param pageTableEntry Entry to add
     */
    public abstract void addPageToMemory(PageTableEntry pageTableEntry);

    @Override
    public String toString() {
        return "MemorySystem{" +
                "frames=" + Arrays.toString(frames) +
                '}';
    }
}