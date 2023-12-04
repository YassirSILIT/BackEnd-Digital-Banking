package com.banking.ensak.Digitalbanking.dtos;

import com.banking.ensak.Digitalbanking.enums.OperationType;
import lombok.Data;
import java.util.Date;

@Data
public class AccountOperationDTO {
    private Long id;
    private Date operationDate;
    private Double amount;
    private OperationType type;
    private String description;

}
