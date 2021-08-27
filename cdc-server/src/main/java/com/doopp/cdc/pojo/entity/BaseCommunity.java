package com.doopp.youlin.pojo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class BaseCommunity {

    private static final long serialVersionUID = 5163L;

    @ApiModelProperty(value="ID", example = "116")
    private Integer id;

    @ApiModelProperty(value="所在行政区ID", example = "116")
    private Integer regionId;

    @ApiModelProperty(value="社区名", example = "富力华庭院")
    private String communityName;

    @ApiModelProperty(value="社区首次录入者ID", example = "富力华庭院")
    private String creatorId;

    @ApiModelProperty(value="社区审核状态 review accept reject", example = "accept")
    private String status;

    @ApiModelProperty(value="创建时间", example = "2021-12-12 00:00:00")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date createTime;
}
