package telran.pulse.monitoring;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import telran.pulse.monitoring.dto.LoginData;
import telran.pulse.monitoring.dto.LoginResponse;
import telran.pulse.monitoring.security.Account;
import telran.pulse.monitoring.security.AccountingManagement;

import java.util.Base64;


@SpringBootApplication
@RestController
@Log4j2
public class PulseMonitoringGatewayApplication {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AccountingManagement accountingManagement;

    public static void main(String[] args) {
        SpringApplication.run(PulseMonitoringGatewayApplication.class, args);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginData loginData) {
        log.info(loginData);
        Account account = accountingManagement.getAccount(loginData.email);
        if (account != null && passwordEncoder.matches(loginData.password,
                account.getHashPassword())) {
            log.info("ResponseEntity.ok");

            return ResponseEntity.ok(getToken(loginData, account));
        }
        return ResponseEntity.badRequest().body("Wrong Credentials");

    }

    private LoginResponse getToken(LoginData loginData, Account account) {
        byte[] code = String.format("%s:%s", loginData.email, loginData.password).getBytes();
        return new LoginResponse("Basic " + Base64.getEncoder().encodeToString(code), account.getRole());
    }

}
