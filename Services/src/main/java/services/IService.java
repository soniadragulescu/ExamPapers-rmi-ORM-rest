package services;

import model.Paper;
import model.User;

public interface IService {
    User login(IObserver client, String username, String password);

    void logout(IObserver client, String teacher);

    void updatePaper(Paper paper, String teacher);

    Iterable<Paper> getPaperByTeacher(String username);

}
