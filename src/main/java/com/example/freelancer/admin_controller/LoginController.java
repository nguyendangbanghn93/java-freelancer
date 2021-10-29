package com.example.freelancer.admin_controller;

import com.example.freelancer.dto.CredentialDTO;
import com.example.freelancer.dto.LoginDTO;
import com.example.freelancer.entity.Account;
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
    public ResponseAPI<CredentialDTO> loginAdmin(
            @RequestBody LoginDTO loginDTO
    ) {
        ResponseAPI<CredentialDTO> responseAPI = new ResponseAPI<>();
        try {
            CredentialDTO credential = accountService.login(loginDTO);
            if (credential == null) {
                responseAPI.setMessage(APIMessage.LOGIN_FAILED);
                responseAPI.setCode(APIStatusCode.LOGIN_FAIL);
            } else {
                Account account = accountService.findById(credential.getAccountId());
                if (account.getRole() == Account.Role.ADMIN) {
                    responseAPI.setData(credential);
                    responseAPI.setMessage(APIMessage.MES_SUCCESS);
                    responseAPI.setCode(APIStatusCode.SUCCESS);
                } else {
                    responseAPI.setMessage(APIMessage.PERMISSION_DENIED);
                    responseAPI.setCode(APIStatusCode.PERMISSION_DENIED);
                }
            }
        } catch (Exception e) {
            responseAPI.setMessage(e.toString());
            responseAPI.setCode(APIStatusCode.ERROR);
        }
        return responseAPI;
    }
}
