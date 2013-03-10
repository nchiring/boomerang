
/* Repackaged android.util.PoolableManager.java as org.boomerang.PoolableManager.java
 *
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.boomerang;

/**
 * represents a manufacturing and repairing unit for <code>Poolable</code> objects
 */
public interface PoolableManager<T extends Poolable<T>> {

    /**
     *
     * @return  a new <code>Poolable</code> object
     */
    T newInstance();

    /**
     *  called from {@link org.boomerang.Pool#acquire()}  }  once an item selected for the borrower
     *  can be used to initialize certain attributes/states
     */
    void onAcquired(T item);

    /**
     *  called from {@link org.boomerang.Pool#release(Poolable)} }
     *  can be used to unset certain attributes/states set earlier by {@link #onAcquired(Poolable)}
     */
    void onReleased(T item);
}
