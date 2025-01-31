package edu.utdallas.cs4348;


public class Process {

    private static int nextProcessID = 0;

    private final int processID;
    private final PageTableEntry[] pages;

    public Process(int numPages) {
        processID = nextProcessID++;
        pages = new PageTableEntry[numPages];
        for ( int i=0; i<numPages; i++) {
            pages[i] = new PageTableEntry(processID, i);
        }
    }

    /**
     * Get the requested page
     * @param pageNumber Page number to retrieve
     * @return The matching page, or -1 if the page number isn't valid
     */
    public PageTableEntry getEntryAt(int pageNumber) {
        if ( pageNumber < 0 || pageNumber >= pages.length) {
            return null;
        }
        return pages[pageNumber];
    }

    /**
     * Get this process's ID
     * @return The process ID
     */
    public int getProcessID() {
        return processID;
    }

}
