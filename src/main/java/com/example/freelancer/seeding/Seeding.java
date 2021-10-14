package com.example.freelancer.seeding;

import com.example.freelancer.entity.Account;
import com.example.freelancer.entity.Freelancer;
import com.example.freelancer.service.AccountService;
import com.example.freelancer.service.FreelancerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Seeding implements CommandLineRunner {


    @Autowired
    AccountService accountService;

    @Autowired
    FreelancerService freelancerService;

    @Override
    public void run(String... args) throws Exception {
        loadUserData();
    }

    private void loadUserData() {
        if (accountService.count() == 0) {
            Account account1 = new Account("hongluyen",  "Hồng luyến","$2a$10$g8.Tln.HnH7rfzN4GXQz1.BAWTSA6KXB6dyljyxVKdBnBrbPuwfPO", Account.Status.ACTIVATE, Account.Role.USER);
            Account account2 = new Account("hongduc",  "Hồng Đức","$2a$10$g8.Tln.HnH7rfzN4GXQz1.BAWTSA6KXB6dyljyxVKdBnBrbPuwfPO", Account.Status.ACTIVATE, Account.Role.USER);
            Account account3 = new Account("hongtruong",  "Hồng Trường","$2a$10$g8.Tln.HnH7rfzN4GXQz1.BAWTSA6KXB6dyljyxVKdBnBrbPuwfPO", Account.Status.ACTIVATE, Account.Role.USER);
            Account account4 = new Account("honghung",  "Hồng Hưng","$2a$10$g8.Tln.HnH7rfzN4GXQz1.BAWTSA6KXB6dyljyxVKdBnBrbPuwfPO", Account.Status.ACTIVATE, Account.Role.USER);
            accountService.createAcount2(account1);
            accountService.createAcount2(account2);
            accountService.createAcount2(account3);
            accountService.createAcount2(account4);

            Freelancer freelancer1 = new Freelancer("Hồng luyến", "123 Hai Bà Trưng", Freelancer.Gender.MALE, "0987654321", "2years", "Full Stack", "Devloper Full Staff", 1000, "PHP, Java, C#", 4.5, "https://www.upwork.com/profile-portraits/c1nNoIgLYy_IVyPXMjKQx9nSJA7UzeLfybprDSPtFhTDhlveUbw49WuZRKvEmaOkzu", Freelancer.Status.ACTIVATE, 1);
            Freelancer freelancer2 = new Freelancer("Hồng Đức", "40 Hoàng Quốc Việt", Freelancer.Gender.MALE, "09876123321", "1years", "Full Stack", "Devloper Full Staff", 1000, "PHP, Java, C#", 4.5, "https://www.upwork.com/profile-portraits/c14TVZr7y4krxUcBJOPAurKZofggOSuclgEK8CCSpzzn_4ZUcde4A4Wq3Jc9lQK5PE", Freelancer.Status.ACTIVATE, 2);
            Freelancer freelancer3 = new Freelancer("Hồng Trường", "29 Nhật Triêu", Freelancer.Gender.MALE, "0987654345", "4-5years", "Full Stack", "Devloper Full Staff", 1000, "PHP, Java, C#", 4.5, "https://www.upwork.com/profile-portraits/c1LhX6BY5-esM44EEqV6kIpMiL_KPdUaG5_vk5G1q5500mJrOro0TPqypwiZDkL6wM", Freelancer.Status.ACTIVATE, 3);
            Freelancer freelancer4 = new Freelancer("Hồng Hưng", "1090 Đường Láng", Freelancer.Gender.MALE, "0986543876", "10years", "Full Stack", "Devloper Full Staff", 1000, "PHP, Java, C#", 4.5, "https://www.upwork.com/profile-portraits/c1n5OKi-Z4EgL64PtroNy_Yd4UWo2Ga6Xl6uA4NiR_PbvNqUNvOkRqCC2SWexqYNBp", Freelancer.Status.ACTIVATE, 4);
            freelancerService.create(freelancer1);
            freelancerService.create(freelancer2);
            freelancerService.create(freelancer3);
            freelancerService.create(freelancer4);
        }
    }
}
