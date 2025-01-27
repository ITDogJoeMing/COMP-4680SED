package com.comp4680.webportal.models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CustomException extends Exception {
    private String errId;
    private String errCode;
    private String errDtm;
    private String errMsg;
    private String rtnUrl;

    public CustomException(String errCode, String errMsg, String rtnUrl) {
        this.errId = UUID.randomUUID().toString();
        this.errCode = errCode;
        this.errDtm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        this.errMsg = errMsg;
        this.rtnUrl = rtnUrl;
    }
}