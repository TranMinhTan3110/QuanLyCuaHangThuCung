package respository;

import model.entity.User;
import model.request.LoginRequest;

public interface userResponsitorty {
    User getUserWithUserNameAndPassWord(LoginRequest loginRequest);
}
