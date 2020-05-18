/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exemplos;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 *
 * @author William
 */
public class SystemMemory {
        // can be white-box mocked for testing
    private final Runtime runtime = Runtime.getRuntime();

    /**
     * <b>Total allocated memory</b>: space currently reserved for the JVM heap within the process.
     * <p>
     * <i>Caution</i>: this is not the total memory, the JVM may grow the heap for new allocations.
     */
    public long getAllocatedTotal() {
        return runtime.totalMemory();
    }

    /**
     * <b>Current allocated free memory</b>: space immediately ready for new objects.
     * <p>
     * <i>Caution</i>: this is not the total free available memory,
     * the JVM may grow the heap for new allocations.
     */
    public long getAllocatedFree() {
        return runtime.freeMemory();
    }

    /**
     * <b>Used memory</b>:
     * Java heap currently used by instantiated objects. 
     * <p>
     * <i>Caution</i>: May include no longer referenced objects, soft references, etc.
     * that will be swept away by the next garbage collection.
     */
    public long getUsed() {
        return getAllocatedTotal() - getAllocatedFree();
    }

    /**
     * <b>Maximum allocation</b>: the process' allocated memory will not grow any further.
     * <p>
     * <i>Caution</i>: This may change over time, do not cache it!
     * There are some JVMs / garbage collectors that can shrink the allocated process memory.
     * <p>
     * <i>Caution</i>: If this is true, the JVM will likely run GC more often.
     */
    public boolean isAtMaximumAllocation() {
        return getAllocatedTotal() == getTotal();
        // = return getUnallocated() == 0;
    }

    /**
     * <b>Unallocated memory</b>: amount of space the process' heap can grow.
     */
    public long getUnallocated() {
        return getTotal() - getAllocatedTotal();
    }

    /**
     * <b>Total designated memory</b>: this will equal the configured {@code -Xmx} value.
     * <p>
     * <i>Caution</i>: You can never allocate more memory than this, unless you use native code.
     */
    public long getTotal() {
        return runtime.maxMemory();
    }

    /**
     * <b>Total free memory</b>: memory available for new Objects,
     * even at the cost of growing the allocated memory of the process.
     */
    public long getFree() {
        return getTotal() - getUsed();
        // = return getAllocatedFree() + getUnallocated();
    }

    /**
     * <b>Unbounded memory</b>: there is no inherent limit on free memory.
     */
    public boolean isBounded() {
        return getTotal() != Long.MAX_VALUE;
    }

    /**
     * Dump of the current state for debugging or understanding the memory divisions.
     * <p>
     * <i>Caution</i>: Numbers may not match up exactly as state may change during the call.
     */
    public String getCurrentStats() {
        StringWriter backing = new StringWriter();
        PrintWriter out = new PrintWriter(backing, false);
        out.printf("Total: allocated %,d (%.1f%%) out of possible %,d; %s, %s %,d%n",
                getAllocatedTotal(),
                (float)getAllocatedTotal() / (float)getTotal() * 100,
                getTotal(),
                isBounded()? "bounded" : "unbounded",
                isAtMaximumAllocation()? "maxed out" : "can grow",
                getUnallocated()
        );
        out.printf("Used: %,d; %.1f%% of total (%,d); %.1f%% of allocated (%,d)%n",
                getUsed(),
                (float)getUsed() / (float)getTotal() * 100,
                getTotal(),
                (float)getUsed() / (float)getAllocatedTotal() * 100,
                getAllocatedTotal()
        );
        out.printf("Free: %,d (%.1f%%) out of %,d total; %,d (%.1f%%) out of %,d allocated%n",
                getFree(),
                (float)getFree() / (float)getTotal() * 100,
                getTotal(),
                getAllocatedFree(),
                (float)getAllocatedFree() / (float)getAllocatedTotal() * 100,
                getAllocatedTotal()
        );
        out.flush();
        return backing.toString();
    }

    public static void main(String... args) {
        SystemMemory memory = new SystemMemory();
        System.out.println(memory.getCurrentStats());
    }
}
