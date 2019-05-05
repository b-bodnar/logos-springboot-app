package ua.logos.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.logos.domain.UserDTO;
import ua.logos.entity.UserEntity;
import ua.logos.exceptions.NotFoundException;
import ua.logos.exceptions.ServerException;
import ua.logos.repository.UserRepository;
import ua.logos.service.UserService;
import ua.logos.utils.ObjectMapperUtils;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapperUtils modelMapper;

    @Override
    public void createUser(UserDTO userDto) {

        if (userDto.getPassword().equals(userDto.getPasswordConfirm())) {
            UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
            userRepository.save(userEntity);
        } else {
            throw new ServerException("Passwords not match");
        }
    }

    @Override
    public UserDTO getUserById(Long id) {
        return null;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return modelMapper.mapAll(userRepository.findAll(), UserDTO.class);
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("User with id [" + id + "] not found");
        }
        userRepository.deleteById(id);
    }

    @Override
    public Page<UserEntity> getUsersByPage(Pageable pageable) {
        Page<UserEntity> userEntities =
                userRepository.findAll(pageable);
        // page = 0
        // size = 10
        return userEntities;
    }

    @Override
    public void addImageToUser(Long id, String fileName) {
        UserEntity userEntity =
                userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
        userEntity.setImage(fileName);
        userRepository.save(userEntity);
    }
}
