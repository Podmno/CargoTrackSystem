package studio.tri.cargo_server.controller;

import org.apache.catalina.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;
import studio.tri.cargo_server.entity.DeliveryEntity;
import studio.tri.cargo_server.entity.PackageEntity;
import studio.tri.cargo_server.entity.UserEntity;
import studio.tri.cargo_server.mapper.DeliveryMapper;
import studio.tri.cargo_server.mapper.PackageMapper;
import studio.tri.cargo_server.mapper.UserMapper;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class DefaultController {
    @Resource
    private UserMapper mapper_user;
    @Resource
    private DeliveryMapper delivery_mapper;
    @Resource
    private PackageMapper mapper_package;

    // 注册功能
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public String register(@RequestBody UserEntity params) {
        System.out.println(params.getUsername());
        System.out.println(params.getPasswd());
        System.out.println(params.getContact());

        UserEntity i = mapper_user.findUser(params.getUsername());
        //System.out.println(i.getUsername());
        if(i!=null){
            // 用户名重复
            return "failure";
        } else {
            UserEntity new_user = new UserEntity();

            new_user.setUsername(params.getUsername());
            new_user.setPasswd(params.getPasswd());
            new_user.setContact(params.getContact());
            mapper_user.insertUser(new_user);
            return "success";
        }
    }

    // 登录功能
    // TODO: 添加 Token 以增强安全性
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(@RequestBody UserEntity params) {
        System.out.println("USERNAME: "+params.getUsername());
        System.out.println("PASSWORD: "+params.getPasswd());
        String username = params.getUsername();
        String password = params.getPasswd();

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        try {
            subject.login(token);
            UserEntity e = (UserEntity) subject.getPrincipal();
            //subject.getSession().setAttribute("account",e);
            System.out.println("Login Success");
            if(e.getType()==0){
                return "admin";
            }
            return "success";
        } catch (UnknownAccountException e) {
            System.out.println("User Not Found");
            return "notfound";
        } catch (IncorrectCredentialsException e) {
            System.out.println("Login Failure");
            return "failure";
        }

    }
    @RequestMapping(value="/logout",method = RequestMethod.GET)
    public String logOut() {
        System.out.println("User logOut");
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "success";
    }

    // 修改个人资料
    @RequestMapping(value = "/api/editprofile",method = RequestMethod.POST)
    public String editProfile(@RequestBody UserEntity params) {
        System.out.println(params.getUsername());
        System.out.println(params.getPasswd());
        System.out.println(params.getContact());

        UserEntity i = mapper_user.findUser(params.getUsername());
        //System.out.println(i.getUsername());
        if(i==null){
            // 没有这个用户名，改啥资料
            return "failure";
        } else {
            UserEntity update_user = new UserEntity();
            update_user.setUsername(params.getUsername());
            update_user.setPasswd(params.getPasswd());
            update_user.setContact(params.getContact());
            update_user.setUid(i.getUid());
            mapper_user.updateUser(update_user);
            return "success";
        }
    }

    @RequestMapping(value = "/api/delivery/get",method = RequestMethod.GET)
    public DeliveryEntity packageListGet(@RequestParam("id") Integer id) {
        System.out.println("PackageListGet : "+id);
        return delivery_mapper.getDelivery(id);
    }

    @RequestMapping(value = "/api/delivery/update",method = RequestMethod.POST)
    public String packageAdd(@RequestBody DeliveryEntity params) {
        System.out.println("PackageUpdate: "+params.getId());
        // 添加快递信息
        delivery_mapper.updateDelivery(params);
        return "success";
    }



    @RequestMapping(value = "/api/package/add",method = RequestMethod.POST)
    public String packageAdd(@RequestBody PackageEntity params) {
        // 添加快递信息
        System.out.println("PackageAdd"+params.getOrderid());
        mapper_package.insertPackage(params);
        return "success";
    }

    @RequestMapping(value = "/api/package/getlist",method = RequestMethod.GET)
    public List<PackageEntity> packageListGet(@RequestParam("uname") String uname) {
        System.out.println("PackageListGet: "+uname);
        // 获取快递列表
        List<PackageEntity> r = mapper_package.findUserPackage(uname);
        return r;
    }

    @RequestMapping(value="/api/package/update",method = RequestMethod.POST)
    public String packageUpdate(@RequestBody PackageEntity params) {
        System.out.println("PackageUpdate: "+params.getId());
        mapper_package.updatePackage(params);
        return "success";
    }

    @RequestMapping(value = "/api/package/get",method = RequestMethod.GET)
    public PackageEntity packageGet(@RequestParam("id") String id) {
        System.out.println("PackageGet: "+id);
        // 获取快递列表
        return mapper_package.findPackage(id);
    }

    @RequestMapping(value = "/api/server_status",method = RequestMethod.GET)
    public String server_status() {
        return "server_ok";
    }

    @GetMapping("/unauth")
    public String unauth() {
        return "unauth";
    }
}