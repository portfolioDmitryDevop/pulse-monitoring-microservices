package telran.pulse.monitoring.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @AllArgsConstructor
public class Account {
    private final String username;
    @Setter
    private String hashPassword;
    @Setter
    private String role;
}
