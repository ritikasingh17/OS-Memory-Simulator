package edu.utdallas.cs4348;

public class RitikaTLB extends TLB {

    /**
     * Add an entry to the TLB. This should only be done AFTER a failed lookup.
     * The entry can go in any empty slot. If all slots are full, evict the
     * page table entry that was least recently ACCESSED (based on
     * PageTableEntry.getLastAccess()).
     * ...
     * Be sure to call access() on this page to make it the newest entry
     * in the TLB....
     *
     * @param entry PageTableEntry to add
     */
    public void addEntry(PageTableEntry entry) {
        int leastRecentlyUsedIndex = 0;
        for (int i = 0; i < entries.length; i++) {
            if (entries[i] == null) {
                entries[i] = entry;
                entry.access();
                return;
            }

            if (entries[i].getLastAccessed() < entries[leastRecentlyUsedIndex].getLastAccessed()) {
                leastRecentlyUsedIndex = i;
            }
        }
        entries[leastRecentlyUsedIndex] = entry;
        entry.access();
    }

    /**
     * Look up a page in the TLB. If the page is found, update its access
     * time (call access() on it) and return its frame assignment.
     * If the page isn't found, return -1
     *
     * @param pageInTable The page number to look up
     * @param process The process the page belongs to
     * @return The matching frame; -1 if none found
     */
    public int lookup(int pageInTable, Process process) {
        for (int i = 0; i < entries.length; i++) {
            if (entries[i] != null && entries[i].getPageNumber() == pageInTable && entries[i].getProcessID() == process.getProcessID()) {
                entries[i].access();
                return entries[i].getFrameNumber();
            }
        }
        return -1;
    }
}
