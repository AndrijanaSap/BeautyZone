package com.beautyzone.beautysalonapp.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

@Data
@NoArgsConstructor
public class IpgResponseDto {
    @NotNull
    private String orderNumber;

    @NotNull
    private String language;

    @NotNull
    private String approvalCode;

    @NotNull
    private String signature;

    private Integer accountId;

    private String subscriptionExpirationDate;

    private Boolean discountUsed;


    public IpgResponseDto(String order_number, String language, String approval_code, String signature,
                          Integer account_id, String subscription_exp_date, Boolean discount_used) {
        this.orderNumber = order_number;
        this.language = language;
        this.approvalCode = approval_code;
        this.signature = signature;
        this.accountId = account_id;
        this.subscriptionExpirationDate = subscription_exp_date;
        this.discountUsed = discount_used;
    }
}
