package kodlamaio.hrms.business.abstracts;

import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.entities.concretes.JobAdvert;
import kodlamaio.hrms.entities.customEntity.JobAdvertFilter;
import kodlamaio.hrms.entities.dtos.JobAdvertAddDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface JobAdvertService {
    DataResult<List<JobAdvert>> getAll();
    DataResult<List<JobAdvert>> getByActiveIs();

    DataResult<Page<JobAdvert>> getByActiveIsWithPagination(int pageNo, int pageSize, JobAdvertFilter jobAdvertFilter);

    DataResult<List<JobAdvert>> getByActiveIsAndEmployer_Id(int employerId);
    DataResult<List<JobAdvert>> getByActiveForProminent(int numberOfProminent);

    DataResult<JobAdvert> add(JobAdvertAddDto jobAdvertAddDto);

    Result closeJobAdvert(int jobAdvertId);
    Result openJobAdvert(int jobAdvertId);
}
