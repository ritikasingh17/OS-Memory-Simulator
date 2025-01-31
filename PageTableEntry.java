package edu.utdallas.cs4348;

public class PageTableEntry {
    private final int processID;
    private final int pageNumber;
    private int frameNumber = -1;
    private long lastAccessed = System.nanoTime();

    public PageTableEntry(int processID, int pageNumber) {
        this.processID = processID;
        this.pageNumber = pageNumber;
    }

    public void assignToFrame(int frameNumber) {
        this.frameNumber = frameNumber;
    }

    public int getProcessID() {
        return processID;
    }

    public int getFrameNumber() {
        return frameNumber;
    }

    public long getLastAccessed() {
        return lastAccessed;
    }

    public void access() {
        lastAccessed = System.nanoTime();
    }

    public int getPageNumber() {
        return pageNumber;
    }
}