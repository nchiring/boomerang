package org.boomerang;

/**
 *  A <code>HarvestListener</code> can be subscribed to the harvesting process.
 *
 *  @see org.boomerang.Harvestable
 */
public interface HarvestListener<T extends Harvestable> {

    void onHarvested(T item ) ;
}
