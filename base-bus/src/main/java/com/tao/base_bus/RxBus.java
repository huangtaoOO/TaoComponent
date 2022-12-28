package com.tao.base_bus;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;

/**
 * 基于RxJava实现事件总线
 */
public class RxBus {

//    private final Subject<Object> bus;
//
//    private RxBus(){
//        bus = PublishSubject.create().toSerialized();
//    }
//
//    public static RxBus getInstance(){
//        return RxBusHolder.mInstance;
//    }
//
//    static class RxBusHolder{
//        private static final RxBus mInstance = new RxBus();
//    }
//
//    public void post(Object obj){
//        bus.onNext(obj);
//    }
//
//    public <T> Observable<T> tObservable(Class<T> eventType){
//        return bus.ofType(eventType);
//    }
}
