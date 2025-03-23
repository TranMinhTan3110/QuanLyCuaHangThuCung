package respository;

import model.entity.User;
import model.request.LoginRequest;

public interface userRespositorty {
    User getUserWithUserNameAndPassWord(LoginRequest loginRequest);
}
