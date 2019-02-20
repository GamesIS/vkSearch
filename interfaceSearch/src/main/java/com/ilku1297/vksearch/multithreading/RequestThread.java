package com.ilku1297.vksearch.multithreading;

import lombok.Data;

@Data
public class RequestThread {
    public RequestThread(String name, Thread thread) {
        this.thread = thread;
        this.name = name;
    }

    private Thread thread;
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RequestThread that = (RequestThread) o;

        return thread.getId() == that.thread.getId();
    }

    @Override
    public int hashCode() {
        return thread.hashCode();
    }
}
