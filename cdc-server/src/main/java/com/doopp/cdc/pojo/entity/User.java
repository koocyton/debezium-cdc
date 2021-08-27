package com.doopp.youlin.pojo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class User implements Serializable {

    private static final long serialVersionUID = 5163L;

    @ApiModelProperty(value="用户ID", example = "1234125")
    private Long id;

    @ApiModelProperty(value="用户ID", example = "1234125")
    private Long openId;

    @ApiModelProperty(value="用户昵称", example = "刘毅")
    private String nickname;

    @ApiModelProperty(value="性别", example = "男")
    private String gender;

    @ApiModelProperty(value="年龄", example = "42")
    private Integer age;

    @ApiModelProperty(value="邮箱", example = "liuyi@me.dom")
    private String email;

    @ApiModelProperty(value="手机", example = "13500000000")
    private String mobile;

    @ApiModelProperty(value="当前住宅 ID", example = "123456")
    private Integer estateId;

    @ApiModelProperty(value="拥有的住宅 ID", example = "[123123,123123,123213]")
    private Integer[] estateIds;

    @ApiModelProperty(value="创建时间", example = "2021-12-12 00:00:00")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date createTime;

    public byte[] cacheId() {
        return ("user_id_" + this.getId().toString()).getBytes();
    }
}
