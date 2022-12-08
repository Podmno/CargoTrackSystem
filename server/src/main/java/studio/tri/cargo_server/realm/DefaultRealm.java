package studio.tri.cargo_server.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import studio.tri.cargo_server.entity.UserEntity;
import studio.tri.cargo_server.mapper.UserMapper;

import javax.annotation.Resource;

public class DefaultRealm extends AuthorizingRealm {
    @Resource
    private UserMapper mapper_user;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 认证操作
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        UserEntity user = mapper_user.findUser(token.getUsername());
        if(user!= null) {
            return new SimpleAuthenticationInfo(user,user.getPasswd(),getName());
        } else {
            throw new UnknownAccountException("Account not existed.");
        }

    }
}
