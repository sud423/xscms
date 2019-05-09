package com.susd.domain.activities;

import com.susd.domain.Entity;

public class Discount implements Entity<Discount> {


    @Override
    public boolean sameIdentityAs(Discount other) {
        return false;
    }


}
