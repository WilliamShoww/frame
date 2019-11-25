package com.xcf.util.net;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class HttpResponse {
    /**
     * http 的状态码
     */
    private Integer status;
    /**
     * 状态码的描述 200 success 302 redirect 其他 fail
     */
    private String msg;
    /**
     * success   响应的数据实体
     * redirect  重定向的url地址
     * fail      为null
     */
    private String body;
}
