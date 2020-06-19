package papers;

import model.Paper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repos.PaperRepo;

@CrossOrigin
@RestController
@RequestMapping("/model/papers")
public class PapersController {
    private static final String template="TemplatePaper";

    @Autowired
    private PaperRepo paperRepo;

    @RequestMapping(value = "/recorectare/{username}", method = RequestMethod.GET)
    public Iterable<Paper> getByTeacher(@PathVariable String username){

        return paperRepo.getAllByTeacherRecheck(username);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable Integer id){
        Paper paper=paperRepo.getById(id);
        if(paper==null){
            return new ResponseEntity<String>("Paper not found!", HttpStatus.NOT_FOUND);
        }
        else{
            return new ResponseEntity<>(paper, HttpStatus.OK);
        }
    }

}

