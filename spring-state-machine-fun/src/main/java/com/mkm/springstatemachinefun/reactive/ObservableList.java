package com.mkm.springstatemachinefun.reactive;

import lombok.extern.slf4j.Slf4j;
import rx.Observable;
import rx.subjects.PublishSubject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Do przepisania na reaktywną javę 9 (natywna, bez rxjava)
 * <p>
 * remove -> Usuwa wszystkie instancje podanego obiektu
 * add -> Dodaje, jeśli nie ma już istniejącej instancji podanego obiektu
 *
 * @param <T>
 */
@Slf4j
public class ObservableList<T> {

    protected final ArrayList<T> list;
    protected final PublishSubject<T> onAdd;

    public synchronized void log() {
        //https://stackoverflow.com/questions/262367/type-safety-unchecked-cast
        List<T> stateList = (List<T>) list.clone();
        stateList.forEach(s -> log.warn("State: {}", s));
    }

    public synchronized void clear() {
        list.clear();
    }

    public synchronized ArrayList<T> getList() {
        return list;
    }

    public synchronized ArrayList<T> clone() {
        return (ArrayList<T>) list.clone();
    }

    public synchronized void removeIf(Predicate<? super T> p){
        list.removeIf(p);
    }

    public synchronized void remove(Object o) {
        list.removeIf(object -> object.equals(o));
    }

    public ObservableList() {
        this.list = new ArrayList<>();
        this.onAdd = PublishSubject.create();
    }

    public synchronized void completed() {
        onAdd.onCompleted();
    }

    public synchronized void add(T value) {
        if (list.contains(value))
            log.trace("Not adding {}, already contains.", value);
        else {
            log.trace("Adding value to ObservableList: {} of type {}", value, this.getClass().getSimpleName());
            list.add(value);
        }
        onAdd.onNext(value);
    }

    public synchronized void addAll(List<T> values) {
        for (T value : values)
            add(value);
    }

    public synchronized Observable<T> getObservable() {
        return onAdd;
    }

    public synchronized boolean contains(Object s) {
        for (Object l : list) {
            if (l.equals(s))
                return true;
        }
        return false;
    }
}