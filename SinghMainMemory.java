package edu.utdallas.cs4348;

import java.util.Arrays;
import java.util.LinkedList;

public class SinghMainMemory extends MainMemory {

    protected LinkedList<Integer> LRAQueue;

    protected SinghMainMemory(int numFrames, TLB tlb) {
        super(numFrames, tlb);
        LRAQueue = new LinkedList<>();
    }

    protected SinghMainMemory(int numFrames) {
        super(numFrames);
        LRAQueue = new LinkedList<>();
    }
    /**
     * Turn a logical address (including the process it comes from) into a physical
     * address, if possible. Also, if there is a TLB, try it and report if there
     * was a TLB hit or not
     * @param lookupInfo What you need to look up a logical address...and what you need
     *                   to fill in about the match (if any)
     */
    public void getPhysicalAddress(LookupInfo lookupInfo) {

        int logicalAddress = lookupInfo.getLogicalAddress();
        int pageNumber = logicalAddress >> Util.NUM_BITS_WITHIN_FRAME;
        Process process = lookupInfo.getProcess();
        int frameNumber;

        if (tlb != null) {
            int lookup = tlb.lookup(pageNumber, process);
            //If we find it inside the TLB
            if (lookup != -1) {
                lookupInfo.setTlbHit(true);
                frameNumber = lookup;
            }
            //If we don't find it inside the TLB
            else {
                lookupInfo.setTlbHit(false);
                PageTableEntry pageTableEntry = process.getEntryAt(pageNumber);
                tlb.addEntry(pageTableEntry);
                frameNumber = pageTableEntry.getFrameNumber();
            }
        }
        else {
            PageTableEntry pageTableEntry = process.getEntryAt(pageNumber);
            frameNumber = pageTableEntry.getFrameNumber();
        }

        //Convert frameNumber to physicalAddress and set it
        if (frameNumber == -1) {
            lookupInfo.setPhysicalAddress(-1);
        }
        else {
            int offset = logicalAddress & Util.LOCATION_WITHIN_PAGE_OR_FRAME_MASK;
            int physicalAddress = (frameNumber << Util.NUM_BITS_WITHIN_FRAME) | offset;
            lookupInfo.setPhysicalAddress(physicalAddress);
        }
    }

    /**
     * Add a page to main memory. It should go into the first open frame (if any), or, failing
     * that, the least-recently-ADDED frame
     * @param pageTableEntry Entry to add
     */
    public void addPageToMemory(PageTableEntry pageTableEntry) {
        boolean isAdded = false;
        for (int i = 0; i < frames.length; i++) {
            if (frames[i].isEmpty()) {
                frames[i].assignFrame(pageTableEntry);
                LRAQueue.add(i);
                isAdded = true;
                break;
            }
        }
        if (!isAdded) {
            int evictedFrameIndex = LRAQueue.removeFirst();
            frames[evictedFrameIndex].clearFrame();
            frames[evictedFrameIndex].assignFrame(pageTableEntry);
            LRAQueue.add(evictedFrameIndex);
        }
    }

    @Override
    public String toString() {
        return "MemorySystem{" +
                "frames=" + Arrays.toString(frames) +
                '}';
    }
}