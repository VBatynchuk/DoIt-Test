package com.doitua.doittest.util;

import io.reactivex.Scheduler;

/**
 * Created by batynchuk on 1/24/18.
 */

public class SchedulerHelper {

    private Scheduler workerScheduler;
    private Scheduler mainScheduler;

    public SchedulerHelper(Scheduler workerScheduler, Scheduler mainScheduler) {
        this.workerScheduler = workerScheduler;
        this.mainScheduler = mainScheduler;
    }

    public Scheduler getWorkerScheduler() {
        return workerScheduler;
    }

    public Scheduler getMainScheduler() {
        return mainScheduler;
    }

}
