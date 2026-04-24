package com.backend.backend_server.dto;

import com.backend.backend_server.entity.Role;
import com.backend.backend_server.entity.User;

/*
 * This applies to every DTO in here, but these are record classes, so they are immutable.
 * JSON requests from curl/frontend are parsed by jackson and converted to any of these objects,
 * depending on the type of request body the particular API endpoint wants. Record classes are immutable,
 * because they don't have any setters defined. But they do have getters auto defined for every property.
 * For example, the username property of UserDTO can be retrieved by userDtoObject.username().
 */

public record UserDTO (
    // Normally, info on the user will be encoded in the jwt. But fuck jwt auth, we ball
    String username,
    Role role,
    String personnelID,

    String accessToken,
    String refreshToken
) {}