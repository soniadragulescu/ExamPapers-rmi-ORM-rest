package services;

import model.Paper;
import model.User;
import repos.PaperRepo;
import repos.UserRepo;

import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Service implements IService{
    private UserRepo userRepo;
    private PaperRepo paperRepo;
    private HashMap<String,IObserver> observers;

    public Service(UserRepo userRepo, PaperRepo paperRepo) {
        this.userRepo = userRepo;
        this.paperRepo = paperRepo;
        observers=new HashMap<String, IObserver>();
    }

    @Override
    public synchronized User login(IObserver client, String username, String password) {
        User user=userRepo.findOne(username, password);
        if(user!=null){
            observers.put(username,client);
        }
        return user;
    }

    @Override
    public void logout(IObserver client, String teacher) {
        observers.remove(teacher);

    }

    @Override
    public void updatePaper(Paper paper, String teacher) {
        paperRepo.update(paper);
        notifyTeachers(teacher);
    }

    @Override
    public Iterable<Paper> getPaperByTeacher(String username) {
        return paperRepo.getAllByTeacher(username);
    }

    private final int defaultThreadsNo=5;
    private void notifyTeachers(String teacher){
        ExecutorService executor= Executors.newFixedThreadPool(defaultThreadsNo);

        executor.execute(()->{
            try{
                System.out.println("notifying users...");
                List<Paper> papers=new ArrayList<>();
                for (Paper paper:paperRepo.getAllByTeacher(teacher)
                ) {
                    papers.add(paper);
                }
                observers.get(teacher).paperGraded(papers);
            }catch (Exception e){
                System.out.println("error notifying users...");
            }
        });

        executor.shutdown();
    }
}
