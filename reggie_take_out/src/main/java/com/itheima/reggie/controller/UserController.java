package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.itheima.reggie.common.BaseContext;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.User;
import com.itheima.reggie.service.UserService;
import com.itheima.reggie.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author:luosheng
 * @Date:2023/3/15 19:49
 * @Description:
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 发送手机短信验证码
     * ---其实这个模块不用开发的我们的验证码，直接就前端随机生成了，但是在使用的时候这一块代码可能会使用到
     *
     * @param user
     * @return
     */
    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session) {
        //获取手机号
        String phone = user.getPhone();
        if (StringUtils.isNotEmpty(phone)) {
            //生成随机的4位验证码
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            log.info("code={}", code);
            //其实这一块要使用一个手机验证码功能的，但是他那个要注册什么小程序执照xx,注册不来所以我们直接将验证码信息发到控制台中
            //需要将生成的验证码保存到Session
//            session.setAttribute(phone, code);
            //这里我们不需要再将code存到sessin中了
            //我们直接存到redis中的缓存数据当中，然后我们还可以设置五分钟删除
            redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);

            return R.success("手机验证码短信发送成功");
        }
        return R.error("短信发送失败");
    }

    /**
     * @param map
     * @param session
     * @return
     */
    @PostMapping("/login")
    public R<User> login(@RequestBody Map map, HttpSession session) {
        log.info("手机端登录数据,{}", map.toString());
        //获取手机号
        String phone = map.get("phone").toString();
        //获取验证码
        String code = map.get("code").toString();

        //从Session中获取保存的验证码
        //  Object attribute = session.getAttribute(phone);
        //进行验证码的比对(页面提交的验证码和Session中保存的验证码进行对比)
        //这一块我们现在直接就可以在redis中取它的数据，
        Object attribute = redisTemplate.opsForValue().get(phone);

        if (code != null && code.equals(attribute)) {
            //如果能够比对成功，说明登录成功
            LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
            lqw.eq(User::getPhone, phone);
            User user = userService.getOne(lqw);
            //判断当前手机号对应的用户是否为新用户，如果使新用户就能自动完成注册
            if (user == null) {
                user = new User();
                user.setPhone(phone);
                user.setStatus(1);//默认状态为1启用
                userService.save(user);
            }
            session.setAttribute("user", user.getId());
            //这一块我们走到这里肯定是已经登录成功了所以，直接可以把存到redis中的数据直接删除
            redisTemplate.delete(phone);
            return R.success(user);
        }

        return R.error("短信发送失败");
    }

    /**
     * 实现手机端退出登录
     *
     * @param session
     * @return
     */
    @PostMapping("/loginout")
    public R<String> loginout(HttpSession session) {
        BaseContext.setCurrentId(null);
        session.removeAttribute("user");
        return R.success("退出登录成功");
    }
}
