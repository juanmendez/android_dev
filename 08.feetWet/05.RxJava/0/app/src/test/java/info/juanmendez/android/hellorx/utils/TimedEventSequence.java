package info.juanmendez.android.hellorx.utils;


import java.util.ArrayList;
import java.util.List;
import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by Juan on 9/25/2015.
 */
public class TimedEventSequence<T> {

    private final PublishSubject<T> subject;
    private final ArrayList<T> eventData;
    private final long intervalMs;

    private int offset;
    private long lastTick;
    private Thread tickThread;
    private volatile boolean paused = false;

    public TimedEventSequence(List<T> eventData, long intervalMs) {
        this.eventData = new ArrayList(eventData);
        this.intervalMs = intervalMs;
        this.offset = 0;
        this.subject = (PublishSubject<T>) PublishSubject.create();
        this.tickThread = null;
    }

    public synchronized void start() {

        if (tickThread != null) {
            return; // No active thread.
        }

        lastTick = System.currentTimeMillis();

        paused = false;

        tickThread = new Thread(() -> {

            try {
                while (Thread.interrupted() == false) {
                    Thread.sleep(5);

                    // If we are paused then don't send the ticks.
                    if (paused) {
                        continue;
                    }

                    // Get the current time
                    long currentTime = System.currentTimeMillis();
                    if (currentTime - lastTick > intervalMs) {
                        lastTick = currentTime;
                        subject.onNext(nextEvent());
                    }
                }
            } catch (InterruptedException e) {
                // suppress
            } catch (Throwable t) {
                // Notify all subscribers that there has been an error.
                subject.onError(t);
            }

            // Make sure all subscribers are told that the list is complete
            subject.onCompleted();

        }, "TimedEventSequence Thread");
        tickThread.start();
    }

    public synchronized void stop() {
        if (tickThread == null) {
            return; // The ticker thread isn't running.
        }

        tickThread.interrupt();
        try {
            tickThread.join();
        } catch (InterruptedException ex) {
            // suppress
        }
        tickThread = null;
    }

    public void pause() {
        paused = true;
    }

    public void unpause() {
        paused = false;
    }

    private synchronized T nextEvent() {

        if (eventData == null) {
            return null;
        }

        int size = eventData.size();
        if (size == 0) {
            return null;
        }

        if (offset >= eventData.size()) {
            offset = 0;
        }

        return eventData.get(offset++);

    }

    public Observable<T> toObservable() {
        return subject;
    }
}
