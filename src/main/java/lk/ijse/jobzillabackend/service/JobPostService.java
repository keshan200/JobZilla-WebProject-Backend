package lk.ijse.jobzillabackend.service;

import java.util.UUID;

public interface JobPostService {
    int postJob(UUID companyId, UUID jobId);
}
