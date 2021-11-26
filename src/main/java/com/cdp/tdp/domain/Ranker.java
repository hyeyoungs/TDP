package com.cdp.tdp.domain;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

public class Ranker {

    @OneToMany
    @JoinColumn(name="TIL_ID",nullable = false)
    private Til til;

    @ManyToOne
    @JoinColumn(name="User_ID",nullable = false)
    private User user;

    public Ranker(Til til,User user) {
        this.user=user;
        this.til=til;
    }
}
