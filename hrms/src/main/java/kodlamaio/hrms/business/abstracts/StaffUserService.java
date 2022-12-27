package kodlamaio.hrms.business.abstracts;

import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.entities.concretes.EmployerUser;
import kodlamaio.hrms.entities.concretes.JobAdvert;
import kodlamaio.hrms.entities.concretes.StaffUser;

import java.util.List;

public interface StaffUserService {
    DataResult<List<StaffUser>> getAll();
    DataResult<StaffUser> getById(int id);

    DataResult<Boolean> confirmEmployer(StaffUser staffUser, EmployerUser confirmedEmployerUser);
    DataResult<Boolean> confirmJobAdvert(StaffUser staffUser, JobAdvert jobAdvert);

    Result add(StaffUser staffUser);
    Result update(StaffUser staffUser);
}
