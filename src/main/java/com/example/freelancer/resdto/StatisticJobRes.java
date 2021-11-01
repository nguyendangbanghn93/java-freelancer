package com.example.freelancer.resdto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StatisticJobRes {
    private int totalJob;
    private int totalJobClosed;
    private int totalJobPending;
    private int totalJobDoing;
    private int totalJobDone;
}
