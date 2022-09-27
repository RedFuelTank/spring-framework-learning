package database;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public final class ConnectionInfo {
    private String dataBaseUrl;
    private String dataBaseUser;
    private String dataBasePassword;

    private ConnectionInfo() {}

    public static ConnectionInfo of(String dataBaseUrl, String dataBaseUser, String dataBasePassword) {
        return new ConnectionInfo(dataBaseUrl, dataBaseUser, dataBasePassword);
    }
}
