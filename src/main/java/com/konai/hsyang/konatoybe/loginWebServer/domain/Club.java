package com.konai.hsyang.konatoybe.loginWebServer.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@NoArgsConstructor
@Getter
@Entity
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clubID;
    private String clubname;
    private String clubinfo;

    @Builder
    public Club(String clubname, String clubinfo){
        this.clubname = clubname;
        this.clubinfo = clubinfo;
    }
}
