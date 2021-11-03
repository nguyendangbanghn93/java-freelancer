package com.example.freelancer.controller;

import com.example.freelancer.dto.JobDTO;
import com.example.freelancer.dto.MailDTO;
import com.example.freelancer.entity.Account;
import com.example.freelancer.entity.Freelancer;
import com.example.freelancer.entity.Job;
import com.example.freelancer.resdto.ResponseAPI;
import com.example.freelancer.service.AccountService;
import com.example.freelancer.service.FreelancerService;
import com.example.freelancer.service.JobService;
import com.example.freelancer.util.APIMessage;
import com.example.freelancer.util.APIStatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
public class JobController {
    private String domainName = "http://localhost:8080/job/";

    @Autowired
    public JavaMailSender emailSender;

    @Autowired
    JobService jobService;

    @Autowired
    FreelancerService freelancerService;

    @Autowired
    AccountService accountService;

    @RequestMapping(method = RequestMethod.GET, value = "/api/job/{id}")
    public JobDTO getDetailJob(
            @PathVariable(value = "id") @Nullable Integer id
    ) {
        System.out.println(id);
        return jobService.getDetailJob(id).toJobDTO();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/api/job")
    public ResponseAPI<JobDTO> createJob(
            @RequestBody JobDTO jobDTO
    ) {
        Job job = jobService.createJob(jobDTO);
        if (job != null) {
            Freelancer freelancer = freelancerService.findById(job.toJobDTO().getFreelancerId());
            Account account = accountService.findById(freelancer.getAccountId());
            try {
                MailDTO mailDTO = new MailDTO();
                mailDTO.setTitle("You received a job offer");
                String bodyContent = "<table class=\"wrapper\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; -premailer-cellpadding: 0; -premailer-cellspacing: 0; -premailer-width: 100%; background-color: #edf2f7; margin: 0; padding: 0; width: 100%;\"><tr>\n" +
                        "        <td align=\"center\" style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative;\">\n" +
                        "            <table class=\"content\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; -premailer-cellpadding: 0; -premailer-cellspacing: 0; -premailer-width: 100%; margin: 0; padding: 0; width: 100%;\">\n" +
                        "                <tr>\n" +
                        "                    <td class=\"header\" style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; padding: 25px 0; text-align: center;\">\n" +
                        "                        <a href=\"http://localhost\" style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; color: #3d4852; font-size: 19px; font-weight: bold; text-decoration: none; display: inline-block;\">\n" +
                        "                            <img src=\"https://e7.pngegg.com/pngimages/624/199/png-clipart-sticker-galaxy-unicorn-picsart-studio-horn-galaxy-of-multicolored-horse-purple.png\" class=\"logo\" alt=\"Laravel Logo\" style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; max-width: 100%; border: none; height: 75px; max-height: 75px; width: 75px;\"></a>\n" +
                        "                    </td>\n" +
                        "                </tr>\n" +
                        "                <!-- Email Body --><tr>\n" +
                        "                    <td class=\"body\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; -premailer-cellpadding: 0; -premailer-cellspacing: 0; -premailer-width: 100%; background-color: #edf2f7; border-bottom: 1px solid #edf2f7; border-top: 1px solid #edf2f7; margin: 0; padding: 0; width: 100%;\">\n" +
                        "                        <table class=\"inner-body\" align=\"center\" width=\"570\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; -premailer-cellpadding: 0; -premailer-cellspacing: 0; -premailer-width: 570px; background-color: #ffffff; border-color: #e8e5ef; border-radius: 2px; border-width: 1px; box-shadow: 0 2px 0 rgba(0, 0, 150, 0.025), 2px 4px 0 rgba(0, 0, 150, 0.015); margin: 0 auto; padding: 0; width: 570px;\">\n" +
                        "                            <!-- Body content --><tr>\n" +
                        "                                <td class=\"content-cell\" style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; max-width: 100vw; padding: 32px;\">\n" +
                        "                                    <h1 style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; color: #3d4852; font-size: 18px; font-weight: bold; margin-top: 0; text-align: left;\">Hello!</h1>\n" +
                        "                                    <p style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; font-size: 16px; line-height: 1.5em; margin-top: 0; text-align: left;\">You received a job offer from {accountName}, log in to FFlance to view job details</p>\n" +
                        "<div style=\"text-align-last: center;margin-top: 20px;\">\n" +
                        "    <a href=\"#{jobPage}\" style=\"text-decoration: none;line-height: 16px;letter-spacing: .6px;color: #fff;background-color: #14a800;border-radius: 160px; box-sizing: border-box;vertical-align: middle;white-space: nowrap;padding: 10px 30px;font-size: 15px;font-stretch: 100%;\">Go to job</a>\n" +
                        "</div>" +
                        "                                    <p style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; font-size: 16px; line-height: 1.5em; margin-top: 0; text-align: left;\">Regards,<br>\n" +
                        "                                        FFlance</p>\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "                                </td>\n" +
                        "                            </tr>\n" +
                        "                        </table>\n" +
                        "                    </td>\n" +
                        "                </tr>\n" +
                        "                <tr>\n" +
                        "                    <td style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative;\">\n" +
                        "                        <table class=\"footer\" align=\"center\" width=\"570\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; -premailer-cellpadding: 0; -premailer-cellspacing: 0; -premailer-width: 570px; margin: 0 auto; padding: 0; text-align: center; width: 570px;\"><tr>\n" +
                        "                                <td class=\"content-cell\" align=\"center\" style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; max-width: 100vw; padding: 32px;\">\n" +
                        "                                    <p style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; line-height: 1.5em; margin-top: 0; color: #b0adc5; font-size: 12px; text-align: center;\">© 2021 FFlance. All rights reserved.</p>\n" +
                        "\n" +
                        "                                </td>\n" +
                        "                            </tr></table>\n" +
                        "                    </td>\n" +
                        "                </tr>\n" +
                        "            </table>\n" +
                        "        </td>\n" +
                        "    </tr>\n" +
                        "</table>";
                bodyContent = bodyContent.replace("#{jobPage}", domainName + job.getId());
                bodyContent = bodyContent.replace("{accountName}", job.getAccount().getUsername());
                mailDTO.setBody(bodyContent);
                mailDTO.setReceiver(account.getEmail());

                MailController mailController = new MailController();
                mailController.sendHtmlEmail(mailDTO, this.emailSender);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            return new ResponseAPI(job.toJobDTO(), APIMessage.MES_SUCCESS, APIStatusCode.SUCCESS);
        }
        return new ResponseAPI(APIMessage.MES_ERROR, APIStatusCode.ERROR);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/api/job/update")
    public ResponseAPI<JobDTO> updateJob(
            @RequestBody JobDTO jobDTO
    ) {
        Job job = jobService.updateJob(jobDTO);
        if (job != null) {
            MailDTO mailDTO = new MailDTO();
            //freelancer ko nhan job
            if (job.toJobDTO().getStatus() == 0) {
                Account account = accountService.findById(job.toJobDTO().getAccountId());
                Freelancer freelancer = freelancerService.findById(job.toJobDTO().getFreelancerId());
                mailDTO.setTitle("Freelancer refuse your job this one");
                String bodyContent = "<table class=\"wrapper\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; -premailer-cellpadding: 0; -premailer-cellspacing: 0; -premailer-width: 100%; background-color: #edf2f7; margin: 0; padding: 0; width: 100%;\"><tr>\n" +
                        "        <td align=\"center\" style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative;\">\n" +
                        "            <table class=\"content\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; -premailer-cellpadding: 0; -premailer-cellspacing: 0; -premailer-width: 100%; margin: 0; padding: 0; width: 100%;\">\n" +
                        "                <tr>\n" +
                        "                    <td class=\"header\" style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; padding: 25px 0; text-align: center;\">\n" +
                        "                        <a href=\"http://localhost\" style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; color: #3d4852; font-size: 19px; font-weight: bold; text-decoration: none; display: inline-block;\">\n" +
                        "                            <img src=\"https://e7.pngegg.com/pngimages/624/199/png-clipart-sticker-galaxy-unicorn-picsart-studio-horn-galaxy-of-multicolored-horse-purple.png\" class=\"logo\" alt=\"Laravel Logo\" style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; max-width: 100%; border: none; height: 75px; max-height: 75px; width: 75px;\"></a>\n" +
                        "                    </td>\n" +
                        "                </tr>\n" +
                        "                <!-- Email Body --><tr>\n" +
                        "                    <td class=\"body\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; -premailer-cellpadding: 0; -premailer-cellspacing: 0; -premailer-width: 100%; background-color: #edf2f7; border-bottom: 1px solid #edf2f7; border-top: 1px solid #edf2f7; margin: 0; padding: 0; width: 100%;\">\n" +
                        "                        <table class=\"inner-body\" align=\"center\" width=\"570\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; -premailer-cellpadding: 0; -premailer-cellspacing: 0; -premailer-width: 570px; background-color: #ffffff; border-color: #e8e5ef; border-radius: 2px; border-width: 1px; box-shadow: 0 2px 0 rgba(0, 0, 150, 0.025), 2px 4px 0 rgba(0, 0, 150, 0.015); margin: 0 auto; padding: 0; width: 570px;\">\n" +
                        "                            <!-- Body content --><tr>\n" +
                        "                                <td class=\"content-cell\" style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; max-width: 100vw; padding: 32px;\">\n" +
                        "                                    <h1 style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; color: #3d4852; font-size: 18px; font-weight: bold; margin-top: 0; text-align: left;\">Hello!</h1>\n" +
                        "                                    <p style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; font-size: 16px; line-height: 1.5em; margin-top: 0; text-align: left;\">{FreelancerName} refuse your \"{JobSubject}\" job, log in to FFlance to find another</p>\n" +
                        "                                    <p style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; font-size: 16px; line-height: 1.5em; margin-top: 0; text-align: left;\">Regards,<br>\n" +
                        "                                        FFlance</p>\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "                                </td>\n" +
                        "                            </tr>\n" +
                        "                        </table>\n" +
                        "                    </td>\n" +
                        "                </tr>\n" +
                        "                <tr>\n" +
                        "                    <td style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative;\">\n" +
                        "                        <table class=\"footer\" align=\"center\" width=\"570\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; -premailer-cellpadding: 0; -premailer-cellspacing: 0; -premailer-width: 570px; margin: 0 auto; padding: 0; text-align: center; width: 570px;\"><tr>\n" +
                        "                                <td class=\"content-cell\" align=\"center\" style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; max-width: 100vw; padding: 32px;\">\n" +
                        "                                    <p style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; line-height: 1.5em; margin-top: 0; color: #b0adc5; font-size: 12px; text-align: center;\">© 2021 FFlance. All rights reserved.</p>\n" +
                        "\n" +
                        "                                </td>\n" +
                        "                            </tr></table>\n" +
                        "                    </td>\n" +
                        "                </tr>\n" +
                        "            </table>\n" +
                        "        </td>\n" +
                        "    </tr>\n" +
                        "</table>";
                bodyContent = bodyContent.replace("#{jobPage}", domainName + jobDTO.getId());
                bodyContent = bodyContent.replace("{FreelancerName}", freelancer.getName());
                bodyContent = bodyContent.replace("{JobSubject}", jobDTO.getSubject());
                mailDTO.setBody(bodyContent);
                mailDTO.setReceiver(account.getEmail());
            }
            //user update money
            if (job.toJobDTO().getStatus() == 1) {
                Freelancer freelancer = freelancerService.findById(job.toJobDTO().getFreelancerId());
                Account account = accountService.findById(freelancer.getAccountId());
                mailDTO.setTitle("Salary has been updated");
                String bodyContent = "<table class=\"wrapper\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; -premailer-cellpadding: 0; -premailer-cellspacing: 0; -premailer-width: 100%; background-color: #edf2f7; margin: 0; padding: 0; width: 100%;\"><tr>\n" +
                        "        <td align=\"center\" style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative;\">\n" +
                        "            <table class=\"content\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; -premailer-cellpadding: 0; -premailer-cellspacing: 0; -premailer-width: 100%; margin: 0; padding: 0; width: 100%;\">\n" +
                        "                <tr>\n" +
                        "                    <td class=\"header\" style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; padding: 25px 0; text-align: center;\">\n" +
                        "                        <a href=\"http://localhost\" style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; color: #3d4852; font-size: 19px; font-weight: bold; text-decoration: none; display: inline-block;\">\n" +
                        "                            <img src=\"https://e7.pngegg.com/pngimages/624/199/png-clipart-sticker-galaxy-unicorn-picsart-studio-horn-galaxy-of-multicolored-horse-purple.png\" class=\"logo\" alt=\"Laravel Logo\" style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; max-width: 100%; border: none; height: 75px; max-height: 75px; width: 75px;\"></a>\n" +
                        "                    </td>\n" +
                        "                </tr>\n" +
                        "                <!-- Email Body --><tr>\n" +
                        "                    <td class=\"body\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; -premailer-cellpadding: 0; -premailer-cellspacing: 0; -premailer-width: 100%; background-color: #edf2f7; border-bottom: 1px solid #edf2f7; border-top: 1px solid #edf2f7; margin: 0; padding: 0; width: 100%;\">\n" +
                        "                        <table class=\"inner-body\" align=\"center\" width=\"570\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; -premailer-cellpadding: 0; -premailer-cellspacing: 0; -premailer-width: 570px; background-color: #ffffff; border-color: #e8e5ef; border-radius: 2px; border-width: 1px; box-shadow: 0 2px 0 rgba(0, 0, 150, 0.025), 2px 4px 0 rgba(0, 0, 150, 0.015); margin: 0 auto; padding: 0; width: 570px;\">\n" +
                        "                            <!-- Body content --><tr>\n" +
                        "                                <td class=\"content-cell\" style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; max-width: 100vw; padding: 32px;\">\n" +
                        "                                    <h1 style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; color: #3d4852; font-size: 18px; font-weight: bold; margin-top: 0; text-align: left;\">Hello!</h1>\n" +
                        "                                    <p style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; font-size: 16px; line-height: 1.5em; margin-top: 0; text-align: left;\">Salary of \"{JobSubject}\" has been updated, log in to FFlance to view details</p>\n" +
                        "<div style=\"text-align-last: center;margin-top: 20px;\">\n" +
                        "    <a href=\"#{jobPage}\" style=\"text-decoration: none;line-height: 16px;letter-spacing: .6px;color: #fff;background-color: #14a800;border-radius: 160px; box-sizing: border-box;vertical-align: middle;white-space: nowrap;padding: 10px 30px;font-size: 15px;font-stretch: 100%;\">Go to job</a>\n" +
                        "</div>" +
                        "                                    <p style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; font-size: 16px; line-height: 1.5em; margin-top: 0; text-align: left;\">Regards,<br>\n" +
                        "                                        FFlance</p>\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "                                </td>\n" +
                        "                            </tr>\n" +
                        "                        </table>\n" +
                        "                    </td>\n" +
                        "                </tr>\n" +
                        "                <tr>\n" +
                        "                    <td style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative;\">\n" +
                        "                        <table class=\"footer\" align=\"center\" width=\"570\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; -premailer-cellpadding: 0; -premailer-cellspacing: 0; -premailer-width: 570px; margin: 0 auto; padding: 0; text-align: center; width: 570px;\"><tr>\n" +
                        "                                <td class=\"content-cell\" align=\"center\" style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; max-width: 100vw; padding: 32px;\">\n" +
                        "                                    <p style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; line-height: 1.5em; margin-top: 0; color: #b0adc5; font-size: 12px; text-align: center;\">© 2021 FFlance. All rights reserved.</p>\n" +
                        "\n" +
                        "                                </td>\n" +
                        "                            </tr></table>\n" +
                        "                    </td>\n" +
                        "                </tr>\n" +
                        "            </table>\n" +
                        "        </td>\n" +
                        "    </tr>\n" +
                        "</table>";
                bodyContent = bodyContent.replace("#{jobPage}", domainName + jobDTO.getId());
                bodyContent = bodyContent.replace("{JobSubject}", jobDTO.getSubject());
                mailDTO.setBody(bodyContent);
                mailDTO.setReceiver(account.getEmail());
            }
            //freelancer nhân job
            if (job.toJobDTO().getStatus() == 2 && job.toJobDTO().getResult() == null) {
                Account account = accountService.findById(job.toJobDTO().getAccountId());
                Freelancer freelancer = freelancerService.findById(job.toJobDTO().getFreelancerId());
                String title = "{FreelancerName} was accepted your job";
                title = title.replace("{FreelancerName}", freelancer.getName());
                mailDTO.setTitle(title);
                String bodyContent = "<table class=\"wrapper\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; -premailer-cellpadding: 0; -premailer-cellspacing: 0; -premailer-width: 100%; background-color: #edf2f7; margin: 0; padding: 0; width: 100%;\"><tr>\n" +
                        "        <td align=\"center\" style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative;\">\n" +
                        "            <table class=\"content\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; -premailer-cellpadding: 0; -premailer-cellspacing: 0; -premailer-width: 100%; margin: 0; padding: 0; width: 100%;\">\n" +
                        "                <tr>\n" +
                        "                    <td class=\"header\" style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; padding: 25px 0; text-align: center;\">\n" +
                        "                        <a href=\"http://localhost\" style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; color: #3d4852; font-size: 19px; font-weight: bold; text-decoration: none; display: inline-block;\">\n" +
                        "                            <img src=\"https://e7.pngegg.com/pngimages/624/199/png-clipart-sticker-galaxy-unicorn-picsart-studio-horn-galaxy-of-multicolored-horse-purple.png\" class=\"logo\" alt=\"Laravel Logo\" style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; max-width: 100%; border: none; height: 75px; max-height: 75px; width: 75px;\"></a>\n" +
                        "                    </td>\n" +
                        "                </tr>\n" +
                        "                <!-- Email Body --><tr>\n" +
                        "                    <td class=\"body\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; -premailer-cellpadding: 0; -premailer-cellspacing: 0; -premailer-width: 100%; background-color: #edf2f7; border-bottom: 1px solid #edf2f7; border-top: 1px solid #edf2f7; margin: 0; padding: 0; width: 100%;\">\n" +
                        "                        <table class=\"inner-body\" align=\"center\" width=\"570\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; -premailer-cellpadding: 0; -premailer-cellspacing: 0; -premailer-width: 570px; background-color: #ffffff; border-color: #e8e5ef; border-radius: 2px; border-width: 1px; box-shadow: 0 2px 0 rgba(0, 0, 150, 0.025), 2px 4px 0 rgba(0, 0, 150, 0.015); margin: 0 auto; padding: 0; width: 570px;\">\n" +
                        "                            <!-- Body content --><tr>\n" +
                        "                                <td class=\"content-cell\" style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; max-width: 100vw; padding: 32px;\">\n" +
                        "                                    <h1 style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; color: #3d4852; font-size: 18px; font-weight: bold; margin-top: 0; text-align: left;\">Hello!</h1>\n" +
                        "                                    <p style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; font-size: 16px; line-height: 1.5em; margin-top: 0; text-align: left;\">Freelancer accept your job, log in to FFlance to view details</p>\n" +
                        "                                   <div style=\"\ntext-align-last: center;\n\"> <img src=\"{FreelancerImage}\" style=\"border-radius: 50%;width: 100px;height: 100px;\"></div>" +
                        "<div style=\"text-align-last: center;margin-top: 20px;\">\n" +
                        "    <a href=\"#{jobPage}\" style=\"text-decoration: none;line-height: 16px;letter-spacing: .6px;color: #fff;background-color: #14a800;border-radius: 160px; box-sizing: border-box;vertical-align: middle;white-space: nowrap;padding: 10px 30px;font-size: 15px;font-stretch: 100%;\">Go to job</a>\n" +
                        "</div>" +
                        "                                    <p style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; font-size: 16px; line-height: 1.5em; margin-top: 0; text-align: left;\">Regards,<br>\n" +
                        "                                        FFlance</p>\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "                                </td>\n" +
                        "                            </tr>\n" +
                        "                        </table>\n" +
                        "                    </td>\n" +
                        "                </tr>\n" +
                        "                <tr>\n" +
                        "                    <td style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative;\">\n" +
                        "                        <table class=\"footer\" align=\"center\" width=\"570\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; -premailer-cellpadding: 0; -premailer-cellspacing: 0; -premailer-width: 570px; margin: 0 auto; padding: 0; text-align: center; width: 570px;\"><tr>\n" +
                        "                                <td class=\"content-cell\" align=\"center\" style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; max-width: 100vw; padding: 32px;\">\n" +
                        "                                    <p style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; line-height: 1.5em; margin-top: 0; color: #b0adc5; font-size: 12px; text-align: center;\">© 2021 FFlance. All rights reserved.</p>\n" +
                        "\n" +
                        "                                </td>\n" +
                        "                            </tr></table>\n" +
                        "                    </td>\n" +
                        "                </tr>\n" +
                        "            </table>\n" +
                        "        </td>\n" +
                        "    </tr>\n" +
                        "</table>";
                bodyContent = bodyContent.replace("#{jobPage}", domainName + jobDTO.getId());
                bodyContent = bodyContent.replace("{FreelancerImage}", freelancer.getThumbnail());
                bodyContent = bodyContent.replace("{FreelancerName}", freelancer.getName());
                bodyContent = bodyContent.replace("{JobSubject}", jobDTO.getSubject());
                mailDTO.setBody(bodyContent);
                mailDTO.setReceiver(account.getEmail());
            }
            //freelancer hand in job
            if (job.toJobDTO().getStatus() == 3) {
                Account account = accountService.findById(job.toJobDTO().getAccountId());
                Freelancer freelancer = freelancerService.findById(job.toJobDTO().getFreelancerId());
                String title = "{FreelancerName} hand in product";
                title = title.replace("{FreelancerName}", freelancer.getName());
                mailDTO.setTitle(title);
                String bodyContent = "<table class=\"wrapper\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; -premailer-cellpadding: 0; -premailer-cellspacing: 0; -premailer-width: 100%; background-color: #edf2f7; margin: 0; padding: 0; width: 100%;\"><tr>\n" +
                        "        <td align=\"center\" style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative;\">\n" +
                        "            <table class=\"content\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; -premailer-cellpadding: 0; -premailer-cellspacing: 0; -premailer-width: 100%; margin: 0; padding: 0; width: 100%;\">\n" +
                        "                <tr>\n" +
                        "                    <td class=\"header\" style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; padding: 25px 0; text-align: center;\">\n" +
                        "                        <a href=\"http://localhost\" style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; color: #3d4852; font-size: 19px; font-weight: bold; text-decoration: none; display: inline-block;\">\n" +
                        "                            <img src=\"https://e7.pngegg.com/pngimages/624/199/png-clipart-sticker-galaxy-unicorn-picsart-studio-horn-galaxy-of-multicolored-horse-purple.png\" class=\"logo\" alt=\"Laravel Logo\" style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; max-width: 100%; border: none; height: 75px; max-height: 75px; width: 75px;\"></a>\n" +
                        "                    </td>\n" +
                        "                </tr>\n" +
                        "                <!-- Email Body --><tr>\n" +
                        "                    <td class=\"body\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; -premailer-cellpadding: 0; -premailer-cellspacing: 0; -premailer-width: 100%; background-color: #edf2f7; border-bottom: 1px solid #edf2f7; border-top: 1px solid #edf2f7; margin: 0; padding: 0; width: 100%;\">\n" +
                        "                        <table class=\"inner-body\" align=\"center\" width=\"570\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; -premailer-cellpadding: 0; -premailer-cellspacing: 0; -premailer-width: 570px; background-color: #ffffff; border-color: #e8e5ef; border-radius: 2px; border-width: 1px; box-shadow: 0 2px 0 rgba(0, 0, 150, 0.025), 2px 4px 0 rgba(0, 0, 150, 0.015); margin: 0 auto; padding: 0; width: 570px;\">\n" +
                        "                            <!-- Body content --><tr>\n" +
                        "                                <td class=\"content-cell\" style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; max-width: 100vw; padding: 32px;\">\n" +
                        "                                    <h1 style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; color: #3d4852; font-size: 18px; font-weight: bold; margin-top: 0; text-align: left;\">Hello!</h1>\n" +
                        "                                    <p style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; font-size: 16px; line-height: 1.5em; margin-top: 0; text-align: left;\">{FreelancerName} hand in product for your \"{jobDetail}\", log in to FFlance to view details</p>\n" +
                        "                                   <div style=\"\ntext-align-last: center;\n\"> <img src=\"{FreelancerImage}\" style=\"border-radius: 50%;width: 100px;height: 100px;\"></div>" +
                        "<div style=\"text-align-last: center;margin-top: 20px;\">\n" +
                        "    <a href=\"#{jobPage}\" style=\"text-decoration: none;line-height: 16px;letter-spacing: .6px;color: #fff;background-color: #14a800;border-radius: 160px; box-sizing: border-box;vertical-align: middle;white-space: nowrap;padding: 10px 30px;font-size: 15px;font-stretch: 100%;\">Go to job</a>\n" +
                        "</div>" +
                        "                                    <p style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; font-size: 16px; line-height: 1.5em; margin-top: 0; text-align: left;\">Regards,<br>\n" +
                        "                                        FFlance</p>\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "                                </td>\n" +
                        "                            </tr>\n" +
                        "                        </table>\n" +
                        "                    </td>\n" +
                        "                </tr>\n" +
                        "                <tr>\n" +
                        "                    <td style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative;\">\n" +
                        "                        <table class=\"footer\" align=\"center\" width=\"570\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; -premailer-cellpadding: 0; -premailer-cellspacing: 0; -premailer-width: 570px; margin: 0 auto; padding: 0; text-align: center; width: 570px;\"><tr>\n" +
                        "                                <td class=\"content-cell\" align=\"center\" style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; max-width: 100vw; padding: 32px;\">\n" +
                        "                                    <p style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; line-height: 1.5em; margin-top: 0; color: #b0adc5; font-size: 12px; text-align: center;\">© 2021 FFlance. All rights reserved.</p>\n" +
                        "\n" +
                        "                                </td>\n" +
                        "                            </tr></table>\n" +
                        "                    </td>\n" +
                        "                </tr>\n" +
                        "            </table>\n" +
                        "        </td>\n" +
                        "    </tr>\n" +
                        "</table>";
                bodyContent = bodyContent.replace("#{jobPage}", domainName + jobDTO.getId());
                bodyContent = bodyContent.replace("{FreelancerImage}", freelancer.getThumbnail());
                bodyContent = bodyContent.replace("{FreelancerName}", freelancer.getName());
                bodyContent = bodyContent.replace("{jobDetail}", jobDTO.getSubject());
                mailDTO.setBody(bodyContent);
                mailDTO.setReceiver(account.getEmail());
            }
            //user xác nhận job done
            if (job.toJobDTO().getStatus() == 4) {
                Freelancer freelancer = freelancerService.findById(job.toJobDTO().getFreelancerId());
                Account account = accountService.findById(freelancer.getAccountId());
                mailDTO.setTitle("Your work is done!");
                String bodyContent = "<table class=\"wrapper\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; -premailer-cellpadding: 0; -premailer-cellspacing: 0; -premailer-width: 100%; background-color: #edf2f7; margin: 0; padding: 0; width: 100%;\"><tr>\n" +
                        "        <td align=\"center\" style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative;\">\n" +
                        "            <table class=\"content\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; -premailer-cellpadding: 0; -premailer-cellspacing: 0; -premailer-width: 100%; margin: 0; padding: 0; width: 100%;\">\n" +
                        "                <tr>\n" +
                        "                    <td class=\"header\" style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; padding: 25px 0; text-align: center;\">\n" +
                        "                        <a href=\"http://localhost\" style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; color: #3d4852; font-size: 19px; font-weight: bold; text-decoration: none; display: inline-block;\">\n" +
                        "                            <img src=\"https://e7.pngegg.com/pngimages/624/199/png-clipart-sticker-galaxy-unicorn-picsart-studio-horn-galaxy-of-multicolored-horse-purple.png\" class=\"logo\" alt=\"Laravel Logo\" style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; max-width: 100%; border: none; height: 75px; max-height: 75px; width: 75px;\"></a>\n" +
                        "                    </td>\n" +
                        "                </tr>\n" +
                        "                <!-- Email Body --><tr>\n" +
                        "                    <td class=\"body\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; -premailer-cellpadding: 0; -premailer-cellspacing: 0; -premailer-width: 100%; background-color: #edf2f7; border-bottom: 1px solid #edf2f7; border-top: 1px solid #edf2f7; margin: 0; padding: 0; width: 100%;\">\n" +
                        "                        <table class=\"inner-body\" align=\"center\" width=\"570\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; -premailer-cellpadding: 0; -premailer-cellspacing: 0; -premailer-width: 570px; background-color: #ffffff; border-color: #e8e5ef; border-radius: 2px; border-width: 1px; box-shadow: 0 2px 0 rgba(0, 0, 150, 0.025), 2px 4px 0 rgba(0, 0, 150, 0.015); margin: 0 auto; padding: 0; width: 570px;\">\n" +
                        "                            <!-- Body content --><tr>\n" +
                        "                                <td class=\"content-cell\" style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; max-width: 100vw; padding: 32px;\">\n" +
                        "                                    <h1 style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; color: #3d4852; font-size: 18px; font-weight: bold; margin-top: 0; text-align: left;\">Hello!</h1>\n" +
                        "                                    <p style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; font-size: 16px; line-height: 1.5em; margin-top: 0; text-align: left;\">Your work have done by {FreelancerName}, log in to FFlance to see your earnings</p>\n" +
                        "                                   <div style=\"\ntext-align-last: center;\n\"> <img src=\"{FreelancerImage}\" style=\"border-radius: 50%;width: 100px;height: 100px;\"></div>" +
                        "<div style=\"text-align-last: center;margin-top: 20px;\">\n" +
                        "    <a href=\"#{jobPage}\" style=\"text-decoration: none;line-height: 16px;letter-spacing: .6px;color: #fff;background-color: #14a800;border-radius: 160px; box-sizing: border-box;vertical-align: middle;white-space: nowrap;padding: 10px 30px;font-size: 15px;font-stretch: 100%;\">Go to job</a>\n" +
                        "</div>" +
                        "                                    <p style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; font-size: 16px; line-height: 1.5em; margin-top: 0; text-align: left;\">Regards,<br>\n" +
                        "                                        FFlance</p>\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "                                </td>\n" +
                        "                            </tr>\n" +
                        "                        </table>\n" +
                        "                    </td>\n" +
                        "                </tr>\n" +
                        "                <tr>\n" +
                        "                    <td style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative;\">\n" +
                        "                        <table class=\"footer\" align=\"center\" width=\"570\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; -premailer-cellpadding: 0; -premailer-cellspacing: 0; -premailer-width: 570px; margin: 0 auto; padding: 0; text-align: center; width: 570px;\"><tr>\n" +
                        "                                <td class=\"content-cell\" align=\"center\" style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; max-width: 100vw; padding: 32px;\">\n" +
                        "                                    <p style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; line-height: 1.5em; margin-top: 0; color: #b0adc5; font-size: 12px; text-align: center;\">© 2021 FFlance. All rights reserved.</p>\n" +
                        "\n" +
                        "                                </td>\n" +
                        "                            </tr></table>\n" +
                        "                    </td>\n" +
                        "                </tr>\n" +
                        "            </table>\n" +
                        "        </td>\n" +
                        "    </tr>\n" +
                        "</table>";
                bodyContent = bodyContent.replace("#{jobPage}", domainName + jobDTO.getId());
                bodyContent = bodyContent.replace("{FreelancerImage}", freelancer.getThumbnail());
                bodyContent = bodyContent.replace("{FreelancerName}", freelancer.getName());
                mailDTO.setBody(bodyContent);
                mailDTO.setReceiver(account.getEmail());
            }
            //user reject job
            if (job.toJobDTO().getStatus() == 2 && job.toJobDTO().getResult() != null) {
                Freelancer freelancer = freelancerService.findById(job.toJobDTO().getFreelancerId());
                Account account = accountService.findById(freelancer.getAccountId());
                mailDTO.setTitle("Your job was rejected");
                String bodyContent = "<table class=\"wrapper\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; -premailer-cellpadding: 0; -premailer-cellspacing: 0; -premailer-width: 100%; background-color: #edf2f7; margin: 0; padding: 0; width: 100%;\"><tr>\n" +
                        "        <td align=\"center\" style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative;\">\n" +
                        "            <table class=\"content\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; -premailer-cellpadding: 0; -premailer-cellspacing: 0; -premailer-width: 100%; margin: 0; padding: 0; width: 100%;\">\n" +
                        "                <tr>\n" +
                        "                    <td class=\"header\" style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; padding: 25px 0; text-align: center;\">\n" +
                        "                        <a href=\"http://localhost\" style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; color: #3d4852; font-size: 19px; font-weight: bold; text-decoration: none; display: inline-block;\">\n" +
                        "                            <img src=\"https://e7.pngegg.com/pngimages/624/199/png-clipart-sticker-galaxy-unicorn-picsart-studio-horn-galaxy-of-multicolored-horse-purple.png\" class=\"logo\" alt=\"Laravel Logo\" style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; max-width: 100%; border: none; height: 75px; max-height: 75px; width: 75px;\"></a>\n" +
                        "                    </td>\n" +
                        "                </tr>\n" +
                        "                <!-- Email Body --><tr>\n" +
                        "                    <td class=\"body\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; -premailer-cellpadding: 0; -premailer-cellspacing: 0; -premailer-width: 100%; background-color: #edf2f7; border-bottom: 1px solid #edf2f7; border-top: 1px solid #edf2f7; margin: 0; padding: 0; width: 100%;\">\n" +
                        "                        <table class=\"inner-body\" align=\"center\" width=\"570\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; -premailer-cellpadding: 0; -premailer-cellspacing: 0; -premailer-width: 570px; background-color: #ffffff; border-color: #e8e5ef; border-radius: 2px; border-width: 1px; box-shadow: 0 2px 0 rgba(0, 0, 150, 0.025), 2px 4px 0 rgba(0, 0, 150, 0.015); margin: 0 auto; padding: 0; width: 570px;\">\n" +
                        "                            <!-- Body content --><tr>\n" +
                        "                                <td class=\"content-cell\" style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; max-width: 100vw; padding: 32px;\">\n" +
                        "                                    <h1 style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; color: #3d4852; font-size: 18px; font-weight: bold; margin-top: 0; text-align: left;\">Hello!</h1>\n" +
                        "                                    <p style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; font-size: 16px; line-height: 1.5em; margin-top: 0; text-align: left;\">Your job was rejected by {FreelancerName}, log in to FFlance to view details</p>\n" +
                        "                                   <div style=\"\ntext-align-last: center;\n\"> <img src=\"{FreelancerImage}\" style=\"border-radius: 50%;width: 100px;height: 100px;\"></div>" +
                        "<div style=\"text-align-last: center;margin-top: 20px;\">\n" +
                        "    <a href=\"#{jobPage}\" style=\"text-decoration: none;line-height: 16px;letter-spacing: .6px;color: #fff;background-color: #14a800;border-radius: 160px; box-sizing: border-box;vertical-align: middle;white-space: nowrap;padding: 10px 30px;font-size: 15px;font-stretch: 100%;\">Go to job</a>\n" +
                        "</div>" +
                        "                                    <p style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; font-size: 16px; line-height: 1.5em; margin-top: 0; text-align: left;\">Regards,<br>\n" +
                        "                                        FFlance</p>\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "                                </td>\n" +
                        "                            </tr>\n" +
                        "                        </table>\n" +
                        "                    </td>\n" +
                        "                </tr>\n" +
                        "                <tr>\n" +
                        "                    <td style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative;\">\n" +
                        "                        <table class=\"footer\" align=\"center\" width=\"570\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; -premailer-cellpadding: 0; -premailer-cellspacing: 0; -premailer-width: 570px; margin: 0 auto; padding: 0; text-align: center; width: 570px;\"><tr>\n" +
                        "                                <td class=\"content-cell\" align=\"center\" style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; max-width: 100vw; padding: 32px;\">\n" +
                        "                                    <p style=\"box-sizing: border-box; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'; position: relative; line-height: 1.5em; margin-top: 0; color: #b0adc5; font-size: 12px; text-align: center;\">© 2021 FFlance. All rights reserved.</p>\n" +
                        "\n" +
                        "                                </td>\n" +
                        "                            </tr></table>\n" +
                        "                    </td>\n" +
                        "                </tr>\n" +
                        "            </table>\n" +
                        "        </td>\n" +
                        "    </tr>\n" +
                        "</table>";
                bodyContent = bodyContent.replace("#{jobPage}", domainName + jobDTO.getId());
                bodyContent = bodyContent.replace("{FreelancerImage}", freelancer.getThumbnail());
                bodyContent = bodyContent.replace("{FreelancerName}", freelancer.getName());
                mailDTO.setBody(bodyContent);
                mailDTO.setReceiver(account.getEmail());
            }
            try {
                MailController mailController = new MailController();
                mailController.sendHtmlEmail(mailDTO, this.emailSender);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            return new ResponseAPI(job.toJobDTO(), APIMessage.MES_SUCCESS, APIStatusCode.SUCCESS);
        }
        return new ResponseAPI(APIMessage.MES_ERROR, APIStatusCode.ERROR);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/job/list")
    public ResponseAPI<List<JobDTO>> listJob(
            @RequestParam @Nullable Integer freelancerId,
            @RequestParam @Nullable Integer accountId
    ) {
        List<JobDTO> list = jobService.getListJob().stream().map(x -> x.toJobDTO()).collect(Collectors.toList());
        if (freelancerId != null) {
            list = jobService.getListJobByFreelancerId(freelancerId).stream().map(x -> x.toJobDTO()).collect(Collectors.toList());
        }
        if (accountId != null) {
            list = jobService.getListJobByAccountId(accountId).stream().map(x -> x.toJobDTO()).collect(Collectors.toList());
        }
        if (freelancerId != null && accountId != null) {
            list = jobService.getListJobByAccountIdAndFreelancerId(accountId, freelancerId).stream().map(x -> x.toJobDTO()).collect(Collectors.toList());
        }

        return new ResponseAPI<List<JobDTO>>(list, APIMessage.MES_SUCCESS, APIStatusCode.SUCCESS);
    }
}
