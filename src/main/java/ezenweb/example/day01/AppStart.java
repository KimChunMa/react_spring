package ezenweb.example.day01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppStart {

    private static Testmember2Repository testmember2Repository;

    public static void main(String[] args) {
        SpringApplication.run(AppStart.class);

        LombokDto dto = LombokDto.builder()
                .mid("qwe")
                .mpassword("qwe")
                .build();

        testmember2Repository.save(dto.toEntity());
    }
}

/*
    Description:

        application.

Failed to configure a DataSource: 'url' attribute is not specified and no embedded datasource could be configured.

Reason: Failed to determine a suitable driver class


*/
