package studio.tri.cargo_server.mapper;

import org.apache.catalina.User;
import org.apache.ibatis.annotations.Select;
import studio.tri.cargo_server.entity.UserEntity;

import java.util.List;

public interface UserMapper {
    /// 输出用户表
    List<UserEntity> selectAllUser();
    /// 由 ID 找到用户
    UserEntity findUser(String uname);
    /// 插入用户
    void insertUser(UserEntity i);
    /// 更新用户
    void updateUser(UserEntity i);
}
