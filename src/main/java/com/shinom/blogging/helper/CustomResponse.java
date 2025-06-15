package com.shinom.blogging.helper;

import lombok.Builder;

@Builder
public record CustomResponse(String message, boolean success) {

}
