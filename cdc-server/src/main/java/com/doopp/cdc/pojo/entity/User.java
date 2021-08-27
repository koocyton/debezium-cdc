package com.doopp.cdc.pojo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class User implements Serializable {

    private static final long serialVersionUID = 5163L;

    private Long id;

    private Long openId;

    private String nickname;

    private String gender;

    private Integer age;

    private String email;

    private String mobile;

    private Integer estateId;

    private Integer[] estateIds;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date createTime;

    public byte[] cacheId() {
        return ("user_id_" + this.getId().toString()).getBytes();
    }
}
