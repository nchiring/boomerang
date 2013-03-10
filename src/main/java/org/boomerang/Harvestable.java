package org.boomerang;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** When a <code>Pool</code> reaches the max capacity and no <code>Poolable</code> object is available for
 * lending to a borrower, then a Harvestable <code>Poolable</code> object can be reclaimed(harvested) back to pool from the
 * possession of an earlier borrower and lends to this new borrower.
 *
 * The object with the lowest priority among the all borrowed  objects is reclaimed.
 *
 * Useful where a <code>Poolable</code> is cached by a borrower for long duration
 *
 *  @see org.boomerang.Pool.Builder#harvestable(boolean)
 *  @see org.boomerang.Pool.Builder#maxSize(int)
 *
 */
public interface Harvestable {

         void setPriority(int priority );
         int getPriority();
         void addHarvestListener( HarvestListener listener);
         List<HarvestListener> getListeners();
         void reset( );

    //TODO -- revisit if a priority queue  can be used instead of TreeSet

    /**
     * Serves as a base class to extend from for implementing <code>Harvestable</code>
     * <pre>
     *     Example,
     *
     *        public class HeavyItem extends Harvestable.Base<HeavyItem> implements Poolable<HeavyItem> {
     *            .....
     *        }
     *
     * </pre>
     * @param <T>
     */
    public static class Base<T extends Poolable<T> > implements  Poolable<T>, Harvestable, Comparable<Base<T>> {

        //private T item ;

        private volatile boolean  prioritySet=false;   //TODO-- use atomic boolean

        private List<HarvestListener> listeners = new ArrayList<HarvestListener>()   ;
        private  int priority= Integer.MAX_VALUE;

        /*Base(T item) {

            this.item = item;
        }  */

        @Override
        public void setPriority(int priority) {
            if(prioritySet){
                throw new IllegalStateException("priority already set")  ;
            }
            this.priority = priority;
            prioritySet=true;
        }

        public void addHarvestListener(HarvestListener listener) {
            listeners.add(listener);
        }

        /*
        T getItem( ){
            return item;
        }

*/
        @Override
        public List<HarvestListener> getListeners() {
            return Collections.unmodifiableList(listeners);
        }

        @Override
        public int getPriority() {
            return priority;
        }

        @Override
        public void reset( ){
            prioritySet=false;
            priority= Integer.MAX_VALUE;
            listeners.clear();
        }

        @Override
        public int compareTo(Base<T> o) {

           return   this.priority - o.priority  ;
        }
    }
}
