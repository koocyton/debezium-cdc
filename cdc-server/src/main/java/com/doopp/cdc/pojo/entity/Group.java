package com.doopp.youlin.pojo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class Group implements Serializable {

    private static final long serialVersionUID = 5163L;

    @ApiModelProperty(value="频道ID", example = "123456")
    private Long id;

    @ApiModelProperty(value="频道名", example = "业主大群")
    private String name;

    @ApiModelProperty(value="频道类型", example = "public private")
    // 公共频道社区人员可以随意退出加入，并在频道列表内
    // 私密频道需邀请可加入，不在社区频道列表内列出
    private String type;

    @ApiModelProperty(value="频道 TOKEN", example = "AWEf34aFVgE%^y#%twefhRY&7uiR")
    private String token;

    @ApiModelProperty(value="当日热度", example = "12341")
    private Integer hot;

    @ApiModelProperty(value="所在住宅", example = "123456")
    private Long estateId;

    @ApiModelProperty(value="创建者ID", example = "123456")
    private Long creatorId;

    @ApiModelProperty(value="管理员", example = "[123,1234,1235]")
    private Long[] managerIds;

    @ApiModelProperty(value="频道 简介", example = "这个大群")
    private String intro;

    @ApiModelProperty(value="频道内 用户", example = "[123,1234,1235]")
    private Long[] userIds;

    @ApiModelProperty(value="活跃用户", example = "[123,1234,1235]")
    private Long[] activeUserIds;

    @ApiModelProperty(value="创建时间", example = "2021-12-12 00:00:00")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date createTime;
}
