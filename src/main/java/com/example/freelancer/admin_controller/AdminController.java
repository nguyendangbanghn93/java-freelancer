package com.example.freelancer.admin_controller;

import com.example.freelancer.dto.AccountDTO;
import com.example.freelancer.dto.FreelancerDTO;
import com.example.freelancer.dto.JobDTO;
import com.example.freelancer.dto.TransactionHistoryDTO;
import com.example.freelancer.entity.Account;
import com.example.freelancer.entity.Freelancer;
import com.example.freelancer.entity.Job;
import com.example.freelancer.entity.TransactionHistory;
import com.example.freelancer.resdto.*;
import com.example.freelancer.service.AccountService;
import com.example.freelancer.service.FreelancerService;
import com.example.freelancer.service.JobService;
import com.example.freelancer.service.TransactionService;
import com.example.freelancer.util.APIMessage;
import com.example.freelancer.util.APIStatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "api/admin")
@CrossOrigin
public class AdminController {
    @Autowired
    AccountService accountService;

    @Autowired
    FreelancerService freelancerService;

    @Autowired
    JobService jobService;

    @Autowired
    TransactionService transactionService;

    // region account
    @RequestMapping(method = RequestMethod.GET, value = "/account")
    public ResponseAPI<AccountRes> getListAccount(
            @RequestParam @Nullable Integer currentPage,
            @RequestParam @Nullable Integer pageSize,
            @RequestParam @Nullable Integer typeUser
    ) {
        ResponseAPI<AccountRes> responseAPI = new ResponseAPI<>();

        try {
            Page<Account> listPage = accountService.getListAccount(currentPage, pageSize, typeUser);
            AccountRes accountRes = new AccountRes();
            accountRes.setList(listPage.stream().map(x -> new AccountDTO(x)).collect(Collectors.toList()));
            accountRes.setTotalPage(listPage.getTotalPages());

            responseAPI.setData(accountRes);
            responseAPI.setMessage(APIMessage.MES_SUCCESS);
            responseAPI.setStatus(APIStatusCode.SUCCESS);
        } catch (Exception e) {
            responseAPI.setStatus(APIStatusCode.ERROR);
            responseAPI.setMessage(e.toString());
        }
        return responseAPI;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/account/create")
    public ResponseAPI createAccount(
            @RequestBody AccountDTO accountDTO
    ) {
        ResponseAPI responseAPI = new ResponseAPI();
        try {
            accountService.createAccount(accountDTO);
            responseAPI.setMessage(APIMessage.MES_SUCCESS);
            responseAPI.setStatus(APIStatusCode.SUCCESS);
        } catch (Exception e) {
            responseAPI.setMessage(e.toString());
            responseAPI.setStatus(APIStatusCode.ERROR);
        }
        return responseAPI;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/account/detail")
    public ResponseAPI<AccountDTO> detailAccount(
            @RequestParam int accountId
    ) {
        ResponseAPI<AccountDTO> responseAPI = new ResponseAPI<>();
        try {
            Account account = accountService.findById(accountId);
            responseAPI.setData(new AccountDTO(account));
            responseAPI.setMessage(APIMessage.MES_SUCCESS);
            responseAPI.setStatus(APIStatusCode.SUCCESS);
        } catch (Exception e) {
            responseAPI.setMessage(e.toString());
            responseAPI.setStatus(APIStatusCode.ERROR);
        }
        return responseAPI;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/account/update")
    public ResponseAPI updateAccount(
            @RequestBody AccountDTO accountDTO
    ) {
        ResponseAPI responseAPI = new ResponseAPI();
        try {
            if (accountService.updateAccount(accountDTO)) {
                responseAPI.setMessage(APIMessage.MES_SUCCESS);
                responseAPI.setStatus(APIStatusCode.SUCCESS);
            } else {
                responseAPI.setMessage(APIMessage.ACCOUNT_NOT_FOUND);
                responseAPI.setStatus(APIStatusCode.ERROR);
            }
        } catch (Exception e) {
            responseAPI.setMessage(e.toString());
            responseAPI.setStatus(APIStatusCode.ERROR);
        }
        return responseAPI;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/account/delete")
    public ResponseAPI deleteAccount(
            @RequestBody int accountId
    ) {
        ResponseAPI responseAPI = new ResponseAPI();
        try {
            if (accountService.deleteAccount(accountId)) {
                responseAPI.setMessage(APIMessage.MES_SUCCESS);
                responseAPI.setStatus(APIStatusCode.SUCCESS);
            } else {
                responseAPI.setMessage(APIMessage.ACCOUNT_NOT_FOUND);
                responseAPI.setStatus(APIStatusCode.ERROR);
            }
        } catch (Exception e) {
            responseAPI.setMessage(e.toString());
            responseAPI.setStatus(APIStatusCode.ERROR);
        }
        return responseAPI;
    }
    // endregion account

    // region freelancer
    @RequestMapping(method = RequestMethod.GET, value = "/freelancer")
    public ResponseAPI<FreelancerRes> getListFreelancer(
            @RequestParam @Nullable Integer currentPage,
            @RequestParam @Nullable Integer pageSize
    ) {
        ResponseAPI<FreelancerRes> responseAPI = new ResponseAPI<>();

        try {
            Page<Freelancer> listPage = freelancerService.getListFreelancerPagination(currentPage, pageSize);
            FreelancerRes freelancerRes = new FreelancerRes();

            List<FreelancerDTO> list = new ArrayList<>();

            listPage.forEach((el) -> {
                FreelancerDTO freelancerDTO = el.toFreelancerDTO();
                freelancerDTO.setTotalJobDone(jobService.getTotalJobDone(freelancerDTO.getId()));
                freelancerDTO.setTotalEarning(jobService.getTotalEarning(freelancerDTO.getId()));
                list.add(freelancerDTO);
            });

            freelancerRes.setList(list);
            freelancerRes.setTotalPage(listPage.getTotalPages());

            responseAPI.setData(freelancerRes);
            responseAPI.setMessage(APIMessage.MES_SUCCESS);
            responseAPI.setStatus(APIStatusCode.SUCCESS);
        } catch (Exception e) {
            responseAPI.setStatus(APIStatusCode.ERROR);
            responseAPI.setMessage(e.toString());
        }
        return responseAPI;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/freelancer/create")
    public ResponseAPI createFreelancer(
            @RequestBody FreelancerDTO freelancerDTO
    ) {
        ResponseAPI responseAPI = new ResponseAPI();
        try {
            Account account = accountService.findById(freelancerDTO.getAccountId());
            freelancerService.register(freelancerDTO, account);
            responseAPI.setMessage(APIMessage.MES_SUCCESS);
            responseAPI.setStatus(APIStatusCode.SUCCESS);
        } catch (Exception e) {
            responseAPI.setMessage(e.toString());
            responseAPI.setStatus(APIStatusCode.ERROR);
        }
        return responseAPI;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/freelancer/detail")
    public ResponseAPI<FreelancerDTO> detailFreelancer(
            @RequestParam int freelancerId
    ) {
        ResponseAPI<FreelancerDTO> responseAPI = new ResponseAPI<>();
        try {
            Freelancer freelancer = freelancerService.findById(freelancerId);
            FreelancerDTO freelancerDTO = freelancer.toFreelancerDTO();
            freelancerDTO.setTotalJobDone(jobService.getTotalJobDone(freelancer.getId()));
            freelancerDTO.setTotalEarning(jobService.getTotalEarning(freelancer.getId()));

            responseAPI.setData(freelancerDTO);
            responseAPI.setMessage(APIMessage.MES_SUCCESS);
            responseAPI.setStatus(APIStatusCode.SUCCESS);
        } catch (Exception e) {
            responseAPI.setMessage(e.toString());
            responseAPI.setStatus(APIStatusCode.ERROR);
        }
        return responseAPI;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/freelancer/update")
    public ResponseAPI updateFreelancer(
            @RequestBody FreelancerDTO freelancerDTO
    ) {
        ResponseAPI responseAPI = new ResponseAPI();
        try {
            if (freelancerService.updateFreelancer(freelancerDTO)) {
                responseAPI.setMessage(APIMessage.MES_SUCCESS);
                responseAPI.setStatus(APIStatusCode.SUCCESS);
            } else {
                responseAPI.setMessage(APIMessage.ACCOUNT_NOT_FOUND);
                responseAPI.setStatus(APIStatusCode.ERROR);
            }
        } catch (Exception e) {
            responseAPI.setMessage(e.toString());
            responseAPI.setStatus(APIStatusCode.ERROR);
        }
        return responseAPI;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/freelancer/delete")
    public ResponseAPI deleteFreelancer(
            @RequestBody int freelancerId
    ) {
        ResponseAPI responseAPI = new ResponseAPI();
        try {
            if (freelancerService.deleteFreelancer(freelancerId)) {
                responseAPI.setMessage(APIMessage.MES_SUCCESS);
                responseAPI.setStatus(APIStatusCode.SUCCESS);
            } else {
                responseAPI.setMessage(APIMessage.ACCOUNT_NOT_FOUND);
                responseAPI.setStatus(APIStatusCode.ERROR);
            }
        } catch (Exception e) {
            responseAPI.setMessage(e.toString());
            responseAPI.setStatus(APIStatusCode.ERROR);
        }
        return responseAPI;
    }
    // endregion freelancer

    // region job
    @RequestMapping(method = RequestMethod.GET, value = "/job")
    public ResponseAPI<JobRes> getListJob(
            @RequestParam @Nullable Integer currentPage,
            @RequestParam @Nullable Integer pageSize
    ) {
        ResponseAPI<JobRes> responseAPI = new ResponseAPI<>();

        try {
            Page<Job> listPage = jobService.getListJobPagination(currentPage, pageSize);
            JobRes jobRes = new JobRes();

            List<JobDTO> list = new ArrayList<>();

            listPage.forEach((el) -> {
                JobDTO jobDTO = el.toJobDTO();
                jobDTO.setAccount(new AccountDTO(accountService.findById(jobDTO.getAccountId())));
                jobDTO.setFreelancer(freelancerService.findById(jobDTO.getFreelancerId()).toFreelancerDTO());
                list.add(jobDTO);
            });
            jobRes.setList(list);
            jobRes.setTotalPage(listPage.getTotalPages());

            responseAPI.setData(jobRes);
            responseAPI.setMessage(APIMessage.MES_SUCCESS);
            responseAPI.setStatus(APIStatusCode.SUCCESS);
        } catch (Exception e) {
            responseAPI.setStatus(APIStatusCode.ERROR);
            responseAPI.setMessage(e.toString());
        }
        return responseAPI;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/job/create")
    public ResponseAPI createJob(
            @RequestBody JobDTO jobDTO
    ) {
        ResponseAPI responseAPI = new ResponseAPI();
        try {
            jobService.createJob(jobDTO);
            responseAPI.setMessage(APIMessage.MES_SUCCESS);
            responseAPI.setStatus(APIStatusCode.SUCCESS);
        } catch (Exception e) {
            responseAPI.setMessage(e.toString());
            responseAPI.setStatus(APIStatusCode.ERROR);
        }
        return responseAPI;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/job/detail")
    public ResponseAPI<JobDTO> detailJob(
            @RequestParam int jobId
    ) {
        ResponseAPI<JobDTO> responseAPI = new ResponseAPI<>();
        try {
            Job job = jobService.getDetailJob(jobId);
            responseAPI.setData(job.toJobDTO2());
            responseAPI.setMessage(APIMessage.MES_SUCCESS);
            responseAPI.setStatus(APIStatusCode.SUCCESS);
        } catch (Exception e) {
            responseAPI.setMessage(e.toString());
            responseAPI.setStatus(APIStatusCode.ERROR);
        }
        return responseAPI;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/job/update")
    public ResponseAPI updateJob(
            @RequestBody JobDTO jobDTO
    ) {
        ResponseAPI responseAPI = new ResponseAPI();
        try {
            if (jobService.updateJob(jobDTO) != null) {
                responseAPI.setMessage(APIMessage.MES_SUCCESS);
                responseAPI.setStatus(APIStatusCode.SUCCESS);
            } else {
                responseAPI.setMessage(APIMessage.JOB_NOT_FOUND);
                responseAPI.setStatus(APIStatusCode.ERROR);
            }
        } catch (Exception e) {
            responseAPI.setMessage(e.toString());
            responseAPI.setStatus(APIStatusCode.ERROR);
        }
        return responseAPI;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/job/delete")
    public ResponseAPI deleteJob(
            @RequestBody int jobId
    ) {
        ResponseAPI responseAPI = new ResponseAPI();
        try {
            if (jobService.deleteJob(jobId)) {
                responseAPI.setMessage(APIMessage.MES_SUCCESS);
                responseAPI.setStatus(APIStatusCode.SUCCESS);
            } else {
                responseAPI.setMessage(APIMessage.ACCOUNT_NOT_FOUND);
                responseAPI.setStatus(APIStatusCode.ERROR);
            }
        } catch (Exception e) {
            responseAPI.setMessage(e.toString());
            responseAPI.setStatus(APIStatusCode.ERROR);
        }
        return responseAPI;
    }
    // endregion account

    // region transaction
    @RequestMapping(method = RequestMethod.GET, value = "/transaction")
    public ResponseAPI<TransactionRes> getListAccount(
            @RequestParam @Nullable Integer currentPage,
            @RequestParam @Nullable Integer pageSize
    ) {
        ResponseAPI<TransactionRes> responseAPI = new ResponseAPI<>();

        try {
            Page<TransactionHistory> listPage =
                    transactionService.getListTransactionPagination(currentPage, pageSize);
            TransactionRes transactionRes = new TransactionRes();

            List<TransactionHistoryDTO> list = new ArrayList<>();

            listPage.forEach((el) -> {
                TransactionHistoryDTO tranDTO = el.toTransactionHistoryDTO();
                AccountDTO accountDTO = tranDTO.getAccount();
                accountDTO.setFreelancerDTO(freelancerService.findByAccountId(accountDTO.getId()).toFreelancerDTO2());
                list.add(tranDTO);
            });

            transactionRes.setList(list);
            transactionRes.setTotalPage(listPage.getTotalPages());

            responseAPI.setData(transactionRes);
            responseAPI.setMessage(APIMessage.MES_SUCCESS);
            responseAPI.setStatus(APIStatusCode.SUCCESS);
        } catch (Exception e) {
            responseAPI.setStatus(APIStatusCode.ERROR);
            responseAPI.setMessage(e.toString());
        }
        return responseAPI;
    }
    // endregion transaction

    // region statistical
    @RequestMapping(method = RequestMethod.GET, value = "/statistic/account")
    public ResponseAPI<StatisticAccount> getStatisticAccount(
    ) {
        ResponseAPI<StatisticAccount> responseAPI = new ResponseAPI<>();

        try {
            StatisticAccount statisticAccount = new StatisticAccount();
            int totalAccount = accountService.findAll().size();
            int totalFreelancer = freelancerService.findAll().size();
            statisticAccount.setTotalUserNormal(totalAccount - totalFreelancer);
            statisticAccount.setTotalFreelancer(totalFreelancer);

            responseAPI.setData(statisticAccount);
            responseAPI.setMessage(APIMessage.MES_SUCCESS);
            responseAPI.setStatus(APIStatusCode.SUCCESS);
        } catch (Exception e) {
            responseAPI.setStatus(APIStatusCode.ERROR);
            responseAPI.setMessage(e.toString());
        }
        return responseAPI;
    }
    // endregion statistical
}
