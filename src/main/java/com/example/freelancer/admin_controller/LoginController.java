package com.example.freelancer.admin_controller;

import com.example.freelancer.dto.AccountDTO;
import com.example.freelancer.dto.CredentialDTO;
import com.example.freelancer.dto.LoginDTO;
import com.example.freelancer.entity.Account;
import com.example.freelancer.resdto.LoginAdminRes;
import com.example.freelancer.resdto.ResponseAPI;
import com.example.freelancer.service.AccountService;
import com.example.freelancer.util.APIMessage;
import com.example.freelancer.util.APIStatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class LoginController {
    @Autowired
    AccountService accountService;

    @RequestMapping(value = "/admin/login", method = RequestMethod.POST)
    public ResponseAPI<LoginAdminRes> loginAdmin(
            @RequestBody LoginDTO loginDTO
    ) {
        ResponseAPI<LoginAdminRes> responseAPI = new ResponseAPI<>();
        try {
            CredentialDTO credential = accountService.login(loginDTO);
            if (credential == null) {
                responseAPI.setMessage(APIMessage.LOGIN_FAILED);
                responseAPI.setCode(APIStatusCode.LOGIN_FAIL);
            } else {
                Account account = accountService.findById(credential.getAccountId());
//                if (account.getStatus() == Account.Status.DELETE) {
//                    responseAPI.setMessage(APIMessage.USER_LOCKED);
//                    responseAPI.setStatus(APIStatusCode.USER_LOCKED);
//                } else {
//                    if (account.getRole() == Account.Role.ADMIN) {
//                        LoginAdminRes loginAdminRes = new LoginAdminRes();
//                        loginAdminRes.setCredential(credential);
//                        loginAdminRes.setAccount(new AccountDTO(account));
//                        responseAPI.setData(loginAdminRes);
//                        responseAPI.setMessage(APIMessage.MES_SUCCESS);
//                        responseAPI.setStatus(APIStatusCode.SUCCESS);
//                    } else {
//                        responseAPI.setMessage(APIMessage.PERMISSION_DENIED);
//                        responseAPI.setStatus(APIStatusCode.PERMISSION_DENIED);
//                    }
//                }
                LoginAdminRes loginAdminRes = new LoginAdminRes();
                loginAdminRes.setCredential(credential);
                loginAdminRes.setAccount(new AccountDTO(account));
                responseAPI.setData(loginAdminRes);
                responseAPI.setMessage(APIMessage.MES_SUCCESS);
                responseAPI.setStatus(APIStatusCode.SUCCESS);
            }
        } catch (Exception e) {
            responseAPI.setMessage(e.toString());
            responseAPI.setStatus(APIStatusCode.ERROR);
        }
        return responseAPI;
    }
}
