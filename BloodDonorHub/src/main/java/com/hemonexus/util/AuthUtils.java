package com.hemonexus.util;

import com.hemonexus.security.services.UserDetailsImpl;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthUtils {
    public static Long currentUserId() {
        return ((UserDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal()).getId();
    }
}
