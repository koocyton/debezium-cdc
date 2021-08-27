package com.doopp.youlin.pojo.entity;

import com.doopp.youlin.pojo.GoodsStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class Goods implements Serializable {

    private static final long serialVersionUID = 5163L;

    @ApiModelProperty(value="商品ID", example = "123456")
    private Integer id;

    @ApiModelProperty(value="卖家ID", example = "123456")
    private Long sellerId;

    @ApiModelProperty(value="买家ID", example = "12312")
    private Long buyerId = null;

    @ApiModelProperty(value="商品名称", example = "沙发")
    private String goodsName;

    @ApiModelProperty(value="商品售价(元)", example = "123")
    private Integer price = 1;

    @ApiModelProperty(value="商品介绍", example = "你懂么 ？沙发")
    private String intro = "";

    @ApiModelProperty(value="商品状态 on-sale sold-over stop-selling", example = "on-sale")
    private String status = "";

    @ApiModelProperty(value="点赞数", example = "1000")
    private int likes = 0;

    @ApiModelProperty(value="买家留言", example = "这条短裤真结实")
    private String buyerMessage = "";

    @ApiModelProperty(value="创建时间", example = "2021-12-12 00:00:00")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date createTime = null;
}
