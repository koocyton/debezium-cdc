package com.doopp.youlin.pojo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class Moment implements Serializable {

    private static final long serialVersionUID = 5163L;

    @ApiModelProperty(value="新闻ID", example = "1234125")
    private Long id;

    @ApiModelProperty(value="发新闻的用户", example = "1234125")
    private Long userId;

    @ApiModelProperty(value="新闻内容", example = "新闻内容...")
    private String content;

    @ApiModelProperty(value="新闻类型 album text music visual-music long-video", example = "album")
    private String type;

    @ApiModelProperty(value="新闻素材", example = "[1234142123, 1234231312]")
    private Long[] fileIds;

    @ApiModelProperty(value="创建时间", example = "2021-12-12 00:00:00")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date createTime;
}
