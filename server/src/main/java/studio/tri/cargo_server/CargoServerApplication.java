package studio.tri.cargo_server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"studio.tri.cargo_server.mapper"})
public class CargoServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CargoServerApplication.class, args);
    }

}
