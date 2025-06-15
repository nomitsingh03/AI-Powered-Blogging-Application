package com.shinom.blogging.helper;

import com.shinom.blogging.dto.UserDto;

public record RegisterResponse(UserDto dto, AuthStatus status ) {

}
