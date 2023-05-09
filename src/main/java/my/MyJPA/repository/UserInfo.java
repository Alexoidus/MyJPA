package my.MyJPA.repository;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class UserInfo implements Info<UserInfo> {

    private String userId;

    @Override
    public UserInfo copy() {
        return new UserInfo(userId);
    }
}
