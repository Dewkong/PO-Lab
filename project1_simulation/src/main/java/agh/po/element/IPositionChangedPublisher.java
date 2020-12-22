package agh.po.element;

import agh.po.map.IPositionChangeObserver;

public interface IPositionChangedPublisher {
    void addObserver(IPositionChangeObserver observer);
    void removeObserver(IPositionChangeObserver observer);
}
