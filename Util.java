package edu.utdallas.cs4348;

public class Util {

    /**
     * Size of a frame (4 KB)
     */
    public static final int SIZE_OF_FRAME = 4096;
    /**
     * Number of bits for the offset within a page/frame
     */
    public static final int NUM_BITS_WITHIN_FRAME = 12;

    /**
     * Mask for the offset (binary & it with a logical/physical address to get the offset alone)
     */
    public static final int LOCATION_WITHIN_PAGE_OR_FRAME_MASK = 0xFFF;

    /**
     * Number of entries in a TLB
     */
    public static final int TLB_SIZE = 8;
}