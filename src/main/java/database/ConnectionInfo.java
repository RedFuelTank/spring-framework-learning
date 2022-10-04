package database;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class ConnectionInfo {
    private String dataBaseUrl;
    private String dataBaseUser;
    private String dataBasePassword;

    public static ConnectionInfo ofData(String dataBaseUrl, String dataBaseUser, String dataBasePassword) {
        return new ConnectionInfo(dataBaseUrl, dataBaseUser, dataBasePassword);
    }
}
