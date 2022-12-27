package kodlamaio.hrms.api.controllers;

import java.io.File;
import java.util.List;

import kodlamaio.hrms.business.abstracts.ResumeService;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.entities.concretes.Resume;
import kodlamaio.hrms.entities.dtos.ResumeAddDto;
import kodlamaio.hrms.entities.dtos.ResumeUpdateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/resumes")
@CrossOrigin
public class ResumesController {
    private final ResumeService resumeService;

    @Autowired
    public ResumesController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @GetMapping("/getall")
    public DataResult<List<Resume>> getAll(){
        return this.resumeService.getAll();
    }

    @GetMapping("/getbycandidateid")
    public DataResult<Resume> getByCandidateId(@Valid @RequestParam int candidateUserId){
        return this.resumeService.getByCandidateId(candidateUserId);
    }

    @PostMapping("/add")
    public Result add(@Valid @RequestBody ResumeAddDto resumeAddDto){
        return this.resumeService.add(resumeAddDto);
    }

    @PutMapping("/update")
    public Result update(@Valid @RequestBody ResumeUpdateDto resumeUpdateDto){
        return this.resumeService.update(resumeUpdateDto);
    }

    @PutMapping("/uploadimage")
    public Result uploadImage(@RequestBody MultipartFile file, @RequestParam int resumeId){
        return this.resumeService.uploadImage(file, resumeId);
    }

}
