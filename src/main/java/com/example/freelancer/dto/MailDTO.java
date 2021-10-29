package com.example.freelancer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MailDTO {
    private String title;
    private String body;//html
    private String receiver;

    @Override
    public String toString() {
        return "MailDTO{" +
                "title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", receiver='" + receiver + '\'' +
                '}';
    }
}
