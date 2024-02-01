package com.example.sesion5;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

@Controller
public class UserController {
    @Autowired
    UserService userService;
    public List<UserDTO> readAll() {
        List<UserDTO> userDTOS;
        userDTOS = userService.readAllUsers().stream().map(UserDTO::new).toList();
        return userDTOS;
    }

    public UserDTO getUserById(Integer id) {
        return new UserDTO(userService.getUserById(id));
    }

    public UserDTO addUser(User user) {
        return new UserDTO(userService.addUser(user));

    }

    public void deleteUserById(Integer id) {
       userService.deleteUserById(id);
    }

    public UserDTO updateUser(Integer id, User user) {
        User updateUser = userService.userDAO.findById(id).orElse(null);

        updateUser.setId(user.getId());
        updateUser.setEmail(user.getEmail());
        updateUser.setPassword(user.getPassword());
        updateUser.setFullName(user.getFullName());
        return new UserDTO(userService.updateUser(updateUser));

    }
    public UserDTO modifyUser(Integer id, JsonPatch match) throws JsonPatchException, JsonProcessingException {
        User user = userService.userDAO.findById(id).orElse(null);
        User userPatched = applyPatchToCustomer(match, user);
        return new UserDTO(userService.modifyUser(userPatched));
    }
    private User applyPatchToCustomer(JsonPatch patch, User targetCustomer) throws JsonPatchException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode patched = patch.apply(objectMapper.convertValue(targetCustomer, JsonNode.class));
        return objectMapper.treeToValue(patched, User.class);
    }
}
