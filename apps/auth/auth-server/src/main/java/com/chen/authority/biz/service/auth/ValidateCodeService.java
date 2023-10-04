package com.chen.authority.biz.service.auth;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * 验证码
 */
public interface ValidateCodeService {
    /**
     * 生成验证码
     * @param response HttpServletResponse
     * @throws IOException
     */
    void create(HttpServletResponse response) throws IOException;
    /**
     * 校验验证码
     * @param key   前端上送 key
     * @param value 前端上送待校验值
     */
    boolean check(String key, String value);
}
