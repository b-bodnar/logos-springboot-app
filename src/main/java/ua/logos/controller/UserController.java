package ua.logos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.logos.domain.UserDTO;
import ua.logos.service.FileStorageService;
import ua.logos.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping
    public ResponseEntity<?> createUser(
            @RequestBody @Valid UserDTO userDTO/*, BindingResult bindingResult*/) {
            // BindingResult bindingResult

/*        if (bindingResult.hasErrors()) {
            System.out.println("Error happened");
            return new ResponseEntity<>("Error happened",
                    HttpStatus.BAD_REQUEST);
        }*/

        userService.createUser(userDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        return new ResponseEntity<>(
                userService.getAllUsers(), HttpStatus.OK
        );
    }

    @DeleteMapping("{userId}")
    public ResponseEntity<?> deleteUserById(@PathVariable("userId") Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("page") // /users/page?page=0&size=20&sort=
    public ResponseEntity<?> getUsersByPage(@PageableDefault Pageable pageable) {
        return new ResponseEntity<>(userService.getUsersByPage(pageable), HttpStatus.OK);
    }

    @PostMapping("{userId}/image")
    public ResponseEntity<?> uploadImage(
            @PathVariable("userId") Long id,
            @RequestParam("imageFile")MultipartFile file
            ) {
        System.out.println(file.getOriginalFilename());

        fileStorageService.storeFile(file);
        userService.addImageToUser(id, file.getOriginalFilename());
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("image")
    public ResponseEntity<?> getImage(
            @RequestParam("imageName") String name,
            HttpServletRequest servletRequest
    ) {

        Resource resource = fileStorageService.loadFile(name);

        String contentType = null;

        try {
            contentType = servletRequest
                            .getServletContext()
                                .getMimeType(
                                        resource.getFile().getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
