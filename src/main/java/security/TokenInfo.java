package security;

import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
public class TokenInfo {
    private String name;
    private List<String> roles;

    public TokenInfo(String name, String roles) {
        this.name = name;
        this.roles = Arrays.stream(roles.split(", ")).toList();
    }

    public TokenInfo(String name, List<String> roles) {
        this.name = name;
        this.roles = roles;
    }

    public String getRolesAsString() {
        return String.join(", ", roles);
    }
}
