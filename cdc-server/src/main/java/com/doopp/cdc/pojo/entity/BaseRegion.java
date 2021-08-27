package com.doopp.youlin.pojo.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BaseRegion {

    private static final long serialVersionUID = 5163L;

    @ApiModelProperty(value="ID", example = "116")
    private Integer id;

    @ApiModelProperty(value="区域的父区域ID", example = "1")
    private Integer parentId;

    @ApiModelProperty(value="区域的父区域名", example = "中国")
    private String parentName;

    @ApiModelProperty(value="区域名称", example = "湖南")
    private String regionName;

    @ApiModelProperty(value="区域类型:province city region town", example = "city")
    private String type;

    @ApiModelProperty(value="树结构的等级", example = "0")
    private Integer level = 0;
}
