package com.shinom.blogging.helper;

import com.shinom.blogging.dto.ForgotPassword;

public record ForgotPasswordResponse(ForgotPassword data, AuthStatus status) {

}
