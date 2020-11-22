package agh.po.lab7;

public interface IPositionChangedPublisher {
    void addObserver(IPositionChangeObserver observer);
    void removeObserver(IPositionChangeObserver observer);
}
