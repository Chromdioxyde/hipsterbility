package de.hsosnabrueck.hipsterbility.rest.api;

import de.hsosnabrueck.hipsterbility.entities.GroupEntity;
import de.hsosnabrueck.hipsterbility.entities.UserEntity;
import de.hsosnabrueck.hipsterbility.rest.api.UserResource;
import de.hsosnabrueck.hipsterbility.rest.data.CreatedId;
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
public class UserResource {

    private final UserService userService;

    @Inject
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @Context
    SecurityContext securityContext;

    @RolesAllowed("ADMIN")
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") int id) {
        UserEntity user = userService.read(id);
        return user != null ? Response.ok(user).build() : Response.status(Response.Status.NOT_FOUND).build();
    }

    @RolesAllowed({"ADMIN","USER"})
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response list(){
        if(securityContext.isUserInRole("ADMIN")) {
            return Response.ok(userService.list()).build();
        } else {
            UserEntity user = getUser();
            return Response.ok(user).build();
        }
    }

    @RolesAllowed({"ADMIN"})
    @DELETE
    public Response delete(@PathParam("id") int id){
        return Response.notModified().build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(UserEntity userEntity){
        // Validate the UserEntity before checks in database
        Validator v = Validation.buildDefaultValidatorFactory().getValidator();
        // Constraints for bean validation, read from class annotations
        Set<ConstraintViolation<UserEntity>> constraintViolations;
        constraintViolations = v.validate(userEntity);
        // StringBuilder to store violation messages
        StringBuilder sb = new StringBuilder();
        for ( ConstraintViolation<UserEntity> violation : constraintViolations ) {
            // Java Bean validation
            sb.append(violation.getPropertyPath()).append(" ").append(violation.getMessage()).append("\n");
        }
        if(constraintViolations.size() > 0) return Response.status(Response.Status.BAD_REQUEST).entity(sb.toString()).build();
        // check code validity or admin role before further processing
        if (userService.checkValidInvite(userEntity.getInviteCode())) {
            if (null != userService.findByName(userEntity.getUsername())) {
            // check if username already exists
                return Response.notModified("username already exists" +
                        "ts").build();
            } else if (null != userService.findByEmail(userEntity.getEmail().toLowerCase())) { // check for unique email
                return Response.notModified("email already exists").build();
            } else {
            // create new user
                userEntity = userService.create(userEntity);
                GroupEntity groupEntity = new GroupEntity();
                groupEntity.setName(GroupEntity.USER);
                userService.addGroup(userEntity, groupEntity);
                if (null == userEntity) return Response.notModified("could not create user").build();
                return Response.status(Response.Status.CREATED).entity(userEntity.getId()).build();
            }
        } else {
        // code invalid
            return Response.status(Response.Status.BAD_REQUEST).entity("code invalid").build();
        }

    }

    @RolesAllowed({"ADMIN"})
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") int id, UserEntity userEntity){
        userService.update(id, userEntity);
        return Response.ok().build();
    }

    @RolesAllowed({"ADMIN"})
    @PUT
    @Path("{id}/group/")
    @Consumes("text/plain")
    public Response updateGroup(@PathParam("id") int id,String group){
        GroupEntity groupEntity = new GroupEntity();
        groupEntity.setName(group.toUpperCase());
        return userService.addGroup(id, groupEntity) ? Response.ok().build() : Response.notModified().build();
    }

    @RolesAllowed({"ADMIN","USER"})
    @PUT
    @Produces("text/plain")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(UserEntity userEntity) {
        if(securityContext.isUserInRole(GroupEntity.ADMIN)){
            return null != userService.create(userEntity) ?
                    Response.status(Response.Status.CREATED).entity(userEntity.getId()).build()
                    : Response.notModified().build();
        }
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
