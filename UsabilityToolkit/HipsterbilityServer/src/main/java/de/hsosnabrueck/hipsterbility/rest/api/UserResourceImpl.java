package de.hsosnabrueck.hipsterbility.rest.api;

import de.hsosnabrueck.hipsterbility.entities.UserEntity;
import de.hsosnabrueck.hipsterbility.rest.api.UserResource;
import de.hsosnabrueck.hipsterbility.rest.service.UserService;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.Set;

/**
 * Created by Albert on 08.09.2014.
 */
@Path("/users")
public class UserResourceImpl implements UserResource {

    private final UserService userService;

    @Inject
    public UserResourceImpl(UserService userService) {
        this.userService = userService;
    }

    @Context
    SecurityContext securityContext;

    @Override
    public Response get(int id) {
        UserEntity user = userService.read(id);
        return user != null ? Response.ok(user).build() : Response.status(Response.Status.NOT_FOUND).build();
    }

    @Override
    public Response list() {
        if(securityContext.isUserInRole("ADMIN")) {
            return Response.ok(userService.list()).build();
        } else {
            UserEntity user = getUser();
            return Response.ok(user).build();
        }
    }

    @Override
    public Response delete(int id) {
        return Response.notModified().build();
    }

    @Override
    public Response create(UserEntity userEntity) {
        // Validate the UserEntity before checks in database
        Validator v = Validation.buildDefaultValidatorFactory().getValidator();
        // Constraints for bean validation, read from class annotations
        Set<ConstraintViolation<UserEntity>> constraintViolations;
        constraintViolations = v.validate(userEntity);
        // StringBuilder to store violation messages
        StringBuilder sb = new StringBuilder();
        boolean invalid = constraintViolations.size() > 0;
        for ( ConstraintViolation<UserEntity> violation : constraintViolations ) {
            // Java Bean validation
            sb.append(violation.getPropertyPath()).append(" ").append(violation.getMessage()).append("\n");
        }
        if(invalid) return Response.status(Response.Status.BAD_REQUEST).entity(sb.toString()).build();
        // check code validity or admin role before further processing
        if (userService.checkValidInvite(userEntity.getInviteCode())) {
            if (null != userService.findByName(userEntity.getUsername())) { // check if username already exists
                return Response.notModified("username already exists" +
                        "ts").build();
            } else if (null != userService.findByEmail(userEntity.getEmail().toLowerCase())) { // check for unique email
                return Response.notModified("email already exists").build();
            } else { // create new user
                userEntity = userService.create(userEntity);
                if (null == userEntity) return Response.notModified("could not create user").build();
                return Response.status(Response.Status.CREATED).entity(userEntity.getId()).build();
            }
        } else { // code invalid
            return Response.status(Response.Status.BAD_REQUEST).entity("code invalid").build();
        }

    }

    @Override
    public Response update(int id, UserEntity object) {
        userService.update(id, object);
        return Response.ok().build();
    }

    @Override
    public Response update(UserEntity userEntity) {
        UserEntity user = getUser();
        userService.update(user.getId(), user);
        return Response.ok().build();
    }

    /**
     * Gets the UserEntity for the currently logged in user
     * @return UserEntity
     */
    private UserEntity getUser(){
        return userService.findByName(securityContext.getUserPrincipal().getName());
    }
}
