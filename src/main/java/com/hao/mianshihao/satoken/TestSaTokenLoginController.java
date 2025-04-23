package com.hao.mianshihao.satoken;

import cn.dev33.satoken.annotation.*;
import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @SaCheckLogin: 登录校验 —— 只有登录之后才能进入该方法。
 * @SaCheckRole("admin"): 角色校验 —— 必须具有指定角色标识才能进入该方法。
 * @SaCheckPermission("user:add"): 权限校验 —— 必须具有指定权限才能进入该方法。
 * @SaIgnore：忽略校验 —— 表示被修饰的方法或类无需进行注解鉴权和路由拦截器鉴权。
 */
@RestController
@RequestMapping("/test/user")
public class TestSaTokenLoginController {


    @Autowired
    private StpInterface stpInterface;
    // 测试登录  ---- http://localhost:8101/api/test/user/doLogin?name=zhang&pwd=123456
    @RequestMapping("doLogin")
    public SaResult doLogin(String name, String pwd) {
        // 此处仅作模拟示例，真实项目需要从数据库中查询数据进行比对
        if("zhang".equals(name) && "123456".equals(pwd)) {
            StpUtil.login(10001);
            return SaResult.ok("登录成功");
        }
        return SaResult.error("登录失败");
    }

    // 查询登录状态  ---- http://localhost:8101/api/test/user/isLogin
    @RequestMapping("isLogin")
    public SaResult isLogin() {
        return SaResult.ok("是否登录：" + StpUtil.isLogin());
    }

    // 查询 Token 信息  ---- http://localhost:8101/api/test/user/tokenInfo
    @RequestMapping("tokenInfo")
    public SaResult tokenInfo() {
        return SaResult.data(StpUtil.getTokenInfo());
    }

    // 测试注销  ---- http://localhost:8101/api/test/user/logout
    @RequestMapping("logout")
    public SaResult logout() {
        StpUtil.logout();
        return SaResult.ok();
    }

    @RequestMapping("Auth")
    public SaResult Auth() {
        // 获取：当前账号所拥有的权限集合
        System.out.println(StpUtil.getPermissionList());

        // 判断：当前账号是否含有指定权限, 返回 true 或 false
        System.out.println(StpUtil.hasPermission("user.add"));

        // 校验：当前账号是否含有指定权限, 如果验证未通过，则抛出异常: NotPermissionException
        StpUtil.checkPermission("user.add");

        // 校验：当前账号是否含有指定权限 [指定多个，必须全部验证通过]
        StpUtil.checkPermissionAnd("user.add", "user.delete", "user.get");

        // 校验：当前账号是否含有指定权限 [指定多个，只要其一验证通过即可]
        StpUtil.checkPermissionOr("user.add", "user.delete", "user.get");

        return SaResult.ok();
    }

    // 登录校验：只有登录之后才能进入该方法
    @SaCheckLogin
    @RequestMapping("info")
    public String info() {
        StpUtil.getSession();
        return "查询用户信息";
    }

    // 角色校验：必须具有指定角色才能进入该方法
    @SaCheckRole("super-admin")
    @RequestMapping("add1")
    public String add1() {
        return "用户增加";
    }

    // 权限校验：必须具有指定权限才能进入该方法
    @SaCheckPermission("user-add")
    @RequestMapping("add2")
    public String add2() {
        return "用户增加";
    }

    // 二级认证校验：必须二级认证之后才能进入该方法
    @SaCheckSafe()
    @RequestMapping("add3")
    public String add3() {
        return "用户增加";
    }



    // 校验当前账号是否被封禁 comment 服务，如果已被封禁会抛出异常，无法进入方法
    @SaCheckDisable("comment")
    @RequestMapping("send")
    public String send() {
        return "查询用户信息";
    }

    @RequestMapping("store")
    public String store(){
        StpUtil.getSession().set("userId", "555");
        return "ok";
    }

}
