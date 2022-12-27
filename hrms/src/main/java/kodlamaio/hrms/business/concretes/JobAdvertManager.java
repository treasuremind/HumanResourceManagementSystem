package kodlamaio.hrms.business.concretes;

import kodlamaio.hrms.business.abstracts.JobAdvertService;
import kodlamaio.hrms.business.constants.Messages;
import kodlamaio.hrms.core.utilities.converter.DtoConverterService;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.core.utilities.results.SuccessDataResult;
import kodlamaio.hrms.core.utilities.results.SuccessResult;
import kodlamaio.hrms.dataAccess.abstracts.JobAdvertDao;
import kodlamaio.hrms.entities.concretes.JobAdvert;
import kodlamaio.hrms.entities.customEntity.JobAdvertFilter;
import kodlamaio.hrms.entities.dtos.JobAdvertAddDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class JobAdvertManager implements JobAdvertService {

    private final JobAdvertDao jobAdvertDao;

    private final DtoConverterService dtoConverterService;

    @Autowired
    public JobAdvertManager(JobAdvertDao jobAdvertDao, DtoConverterService dtoConverterService) {
        this.jobAdvertDao = jobAdvertDao;
        this.dtoConverterService = dtoConverterService;
    }

    @Override
    public DataResult<List<JobAdvert>> getAll() {
        return new SuccessDataResult<>(this.jobAdvertDao.findAll(), Messages.jobAdvertsListed);
    }

    @Override
    public DataResult<Page<JobAdvert>> getByActiveIsWithPagination(int pageNo, int pageSize, JobAdvertFilter jobAdvertFilter) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        var filteredData = this.jobAdvertDao.getByActiveIs(pageable, jobAdvertFilter);

        return new SuccessDataResult<>(new PageImpl<>(filteredData.getContent(), pageable, filteredData.getTotalElements()));
    }

    @Override
    public DataResult<List<JobAdvert>> getByActiveIs() {
        return new SuccessDataResult<>(this.jobAdvertDao.getByActiveIs(), Messages.jobAdvertsListedByActivationStatus);
    }

    @Override
    public DataResult<List<JobAdvert>> getByActiveIsAndEmployer_Id(int employerId) {
        return new SuccessDataResult<>(this.jobAdvertDao.getByActiveIsAndEmployer_Id(employerId), Messages.jobAdvertsListedByActivationStatusAndByEmployerId);
    }

    @Override
    @Cacheable(value = "prominentJobAdvertsCache")
    public DataResult<List<JobAdvert>> getByActiveForProminent(int numberOfProminent) {
        Random random = new Random();

        List<JobAdvert> activeJobAdverts = this.jobAdvertDao.getByActiveIs();
        List<JobAdvert> prominentJobAdverts = new ArrayList<>();

        for (int i = 0; i < numberOfProminent; i++){

            if (i > activeJobAdverts.size() + 1){
                return new SuccessDataResult<>(prominentJobAdverts);
            }

            int randomIndex = random.nextInt(activeJobAdverts.size());

            prominentJobAdverts.add(activeJobAdverts.get(randomIndex));
            activeJobAdverts.remove(randomIndex);
        }
        return new SuccessDataResult<>(prominentJobAdverts);
    }

    @Override
    public DataResult<JobAdvert> add(JobAdvertAddDto jobAdvertAddDto) {
        var addedJobAdvert = this.jobAdvertDao.save((JobAdvert)this.dtoConverterService.dtoToBaseClassConverter(jobAdvertAddDto, JobAdvert.class));
        return new SuccessDataResult<>(addedJobAdvert, "Added");
    }

    @Override
    public Result closeJobAdvert(int jobAdvertId) {
        JobAdvert tempJobAdvert = this.jobAdvertDao.getOne(jobAdvertId);
        tempJobAdvert.setActive(false);

        this.jobAdvertDao.save(tempJobAdvert);
        return new SuccessResult(Messages.updateJobAdvertActivationStatusToDeactivate);
    }

    @Override
    public Result openJobAdvert(int jobAdvertId) {
        JobAdvert tempJobAdvert = this.jobAdvertDao.getOne(jobAdvertId);
        tempJobAdvert.setActive(true);

        this.jobAdvertDao.save(tempJobAdvert);
        return new SuccessResult(Messages.updateJobAdvertActivationStatusToActivate);
    }
}
