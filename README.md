boomerang
=========

Boomerang : At the end, what goes out must return home. A framework for objects pooling.

Usage
-----

```java
public class Pen implements Poolable<Pen> {

    public static enum INKCOLOR { RED, GREEN, BLUE, BLACK }

    private String name;

    private INKCOLOR color ;

     public Pen(String name) {
        this.name = name;
    }

    //getter & setter omitted for brevity
}

PoolableManager<Pen> manager = new PoolableManager<Pen>() {

        private int number =0 ;

        @Override
        public Pen newInstance() {
            return new Pen("No-" + (number++ ))  ;
        }

        @Override
        public void onAcquired(Pen pen) {
            //we can initialize the pen, say to BLUE color
            pen.setColor(Pen.INKCOLOR.BLUE);
        }

        @Override
        public void onReleased(Pen pen) {
            //we can restore back the pen's default values
            pen.setColor(Pen.INKCOLOR.BLACK);
        }
} ;

Pool<Pen> penStand = new  Pool.Builder<Pen>(manager).maxSize(10).build();
```

We can now call `penStand.acquire()` to obtain a `Pen` instance for writing. 
When we are done, we can call `penStand.release(pen)` which
will return the `pen` instance back to the penStand (pool).

We can also make Pen as harvestable. When a pen is harvestable, then if you 
borrow a pen from the pen stand and have no intention to return it, then the pool
will recliam the pen back from you on its own.

```java
public class Pen extends Harvestable.Base<Pen> implements Poolable<Pen> {
   // all omitted....
   int worth = 10;
}

or 

public class Pen implements Harvestable, Poolable<Pen> {
   // all omitted....
}

PoolableManager<Pen> manager = new PoolableManager<Pen>() {

        @Override
        public void onAcquired(Pen pen) {
            //we can initialize the pen, say to BLUE color
            pen.setColor(Pen.INKCOLOR.BLUE);
            //also, we can set a worthness(priority) to this pen
            ((Harvestable)pen).setPriority(pen.getWorth());
        }

        //other methods go here ...
} ;

Pool<Pen> penStand = new  Pool.Builder<Pen>(manager).maxSize(10).harvestable(true).build();
```
License
-------

    Copyright 2009 The Android Open Source Project
    Copyright 2013 Nitu Chiring

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.