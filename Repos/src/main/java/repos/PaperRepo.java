package repos;

import model.Paper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PaperRepo implements IPaperRepo{
    static SessionFactory sessionFactory;
    private JdbcUtils jdbcUtils=new JdbcUtils();

    public PaperRepo() {
        System.out.println("Initializing PaperRepo... ");
        sessionFactory=jdbcUtils.getSessionFactory();
    }

    @Override
    public void update(Paper paper) {
        try(Session session = sessionFactory.openSession()){
            Transaction tx=null;
            try{
                tx = session.beginTransaction();
                Paper oldPaper =
                        (Paper) session.load( Paper.class, paper.getId());
                oldPaper.setGrade1(paper.getGrade1());
                oldPaper.setGrade2(paper.getGrade2());
                oldPaper.setFinal_grade(paper.getFinal_grade());
                oldPaper.setRecheck(paper.getRecheck());
                System.err.println("We've updated paper "+paper.getId().toString()+ " to the final grade: "+paper.getFinal_grade().toString());
                tx.commit();

            } catch(RuntimeException ex){
                if (tx!=null)
                    tx.rollback();
            }
        }
    }

    @Override
    public Iterable<Paper> getAllByTeacher(String username) {
        try(Session session=sessionFactory.openSession()){
            Transaction tx=null;
            try{
                tx=session.beginTransaction();
                List<Paper> papers =
                        session.createQuery("from Paper as p where p.teacher1 = ? or p.teacher2 = ?", Paper.class).setParameter(0, username)
                                .setParameter(1, username)
                                .setFirstResult(0).setMaxResults(20).list();
                tx.commit();
                return papers;
            }catch (Exception e){
                if (tx != null)
                    tx.rollback();
                System.out.println("No papers found for teacher "+ username);
                e.printStackTrace();
                return null;
            }
        }
    }

    public Iterable<Paper> getAllByTeacherRecheck(String username) {
        try(Session session=sessionFactory.openSession()){
            Transaction tx=null;
            try{
                tx=session.beginTransaction();
                List<Paper> papers =
                        session.createQuery("from Paper as p where (p.teacher1 = ? or p.teacher2 = ?) and p.recheck='RECORECTARE'", Paper.class).setParameter(0, username)
                                .setParameter(1, username)
                                .setFirstResult(0).setMaxResults(20).list();
                tx.commit();
                return papers;
            }catch (Exception e){
                if (tx != null)
                    tx.rollback();
                System.out.println("No papers found for teacher "+ username);
                e.printStackTrace();
                return null;
            }
        }
    }

    public Paper getById(Integer id) {
        try(Session session=sessionFactory.openSession()){
            Transaction tx=null;
            try{
                tx=session.beginTransaction();
                List<Paper> papers =
                        session.createQuery("from Paper as p where p.id= ? ", Paper.class).setParameter(0, id)
                                .setFirstResult(0).setMaxResults(20).list();
                tx.commit();
                if (papers.size()==0){
                    return null;
                }
                else{
                return papers.get(0);}
            }catch (Exception e){
                if (tx != null)
                    tx.rollback();
                System.out.println("No papers found for id "+ id.toString());
                e.printStackTrace();
                return null;
            }
        }
    }

    @Override
    public Iterable<Paper> getAll() {
        try(Session session=sessionFactory.openSession()){
            Transaction tx=null;
            try{
                tx=session.beginTransaction();
                List<Paper> papers =
                        session.createQuery("from Paper", Paper.class)
                                .setFirstResult(0).setMaxResults(20).list();
                tx.commit();
                return papers;
            }catch (Exception e){
                if (tx != null)
                    tx.rollback();
                System.out.println("No papers found... ");
                e.printStackTrace();
                return null;
            }
        }
    }
}
