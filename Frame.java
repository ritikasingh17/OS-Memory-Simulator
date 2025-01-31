package edu.utdallas.cs4348;

public class Frame {
    private final int frameNumber;
    private PageTableEntry pageTableEntry;

    public Frame(int frameNumber) {
        this.frameNumber = frameNumber;
    }

    public void assignFrame(PageTableEntry pageTableEntry) {
        this.pageTableEntry = pageTableEntry;
        pageTableEntry.assignToFrame(frameNumber);
    }

    public void clearFrame() {
        pageTableEntry.assignToFrame(-1);
        this.pageTableEntry = null;
    }

    public boolean isEmpty() {
        return (pageTableEntry == null);
    }


}