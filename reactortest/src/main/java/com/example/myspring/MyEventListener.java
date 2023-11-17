package com.example.myspring;

import java.util.List;

public interface MyEventListener<T> {

    void onDataChunk(List<T> chunk);

    void processComplete();

    void processError(Throwable e);
}