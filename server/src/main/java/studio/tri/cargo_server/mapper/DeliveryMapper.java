package studio.tri.cargo_server.mapper;

import studio.tri.cargo_server.entity.DeliveryEntity;
import studio.tri.cargo_server.entity.UserEntity;

import java.util.List;

public interface DeliveryMapper {
    DeliveryEntity getDelivery(Integer id);
    void updateDelivery(DeliveryEntity e);
}
