package studio.tri.cargo_server.mapper;

import studio.tri.cargo_server.entity.PackageEntity;
import studio.tri.cargo_server.entity.UserEntity;

import java.util.List;

public interface PackageMapper {
    void insertPackage(PackageEntity e);
    List<PackageEntity> findUserPackage(String uname);

    void updatePackage(PackageEntity e);

    PackageEntity findPackage(String id);
}
