package repos;

import model.Paper;

public interface IPaperRepo {
    void update(Paper paper);
    Iterable<Paper> getAllByTeacher(String username);
    Iterable<Paper> getAll();
}
