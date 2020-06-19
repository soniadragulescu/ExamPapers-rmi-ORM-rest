package services;

import model.Paper;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IObserver extends Remote {
    void paperGraded(List<Paper> papers) throws RemoteException;
}
