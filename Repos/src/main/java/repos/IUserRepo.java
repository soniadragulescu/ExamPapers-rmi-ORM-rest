package repos;

import model.User;

public interface IUserRepo {
    User findOne(String username, String password);
}
