package repos;

import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class UserRepo implements IUserRepo{
    static SessionFactory sessionFactory;
    private JdbcUtils jdbcUtils=new JdbcUtils();

    public UserRepo() {
        System.out.println("Initializing UserRepo... ");
        sessionFactory=jdbcUtils.getSessionFactory();
    }

    @Override
    public User findOne(String username, String password) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                List<User> users =
                        session.createQuery("from User as u where u.username = ? and u.password = ?", User.class).setParameter(0, username)
                                .setParameter(1, password).
                                setFirstResult(0).setMaxResults(5).list();
                tx.commit();
                if (users.size() == 0)
                    return null;
                else return users.get(0);
            } catch (Exception e) {
                if (tx != null)
                    tx.rollback();
                System.out.println("No user found with username " + username);
                e.printStackTrace();
                return null;
            }
        }
    }
}
