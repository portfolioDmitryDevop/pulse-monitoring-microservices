package telran.pulse.monitoring.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor @AllArgsConstructor
public class LoginResponse {
    public String authToken;
    public String role;
}
