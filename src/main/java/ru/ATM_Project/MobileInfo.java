package ru.ATM_Project;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
public class MobileInfo {
    private final long mobileNumber;
    @Setter
    private Money balans;

    public MobileInfo(long mobileNumber, Money balans){
        this.mobileNumber = mobileNumber;
        this.balans = balans;
    }
}
