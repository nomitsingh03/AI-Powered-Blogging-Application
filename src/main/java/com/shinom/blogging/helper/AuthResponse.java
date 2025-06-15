package com.shinom.blogging.helper;

import com.shinom.blogging.dto.UserDto;
import com.shinom.blogging.entities.User;

public record AuthResponse(UserDto user, String token, AuthStatus authStatus) {

}
