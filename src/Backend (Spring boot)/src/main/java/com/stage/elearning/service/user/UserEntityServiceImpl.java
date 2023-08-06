package com.stage.elearning.service.user;

import com.stage.elearning.dto.user.UserEntityDTO;
import com.stage.elearning.dto.user.UserEntityDTOMapper;
import com.stage.elearning.exceptions.ResourceNotFoundException;
import com.stage.elearning.model.user.UserEntity;
import com.stage.elearning.repository.UserEntityRepository;
import com.stage.elearning.utility.CustomResponseEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

@Service
public class UserEntityServiceImpl implements UserEntityService {
    private final UserEntityRepository userEntityRepository;
    private final UserEntityDTOMapper userEntityDTOMapper;

    public UserEntityServiceImpl(UserEntityRepository userEntityRepository,UserEntityDTOMapper userEntityDTOMapper)
    {
        this.userEntityRepository = userEntityRepository;
        this.userEntityDTOMapper = userEntityDTOMapper;
    }

    @Override
    public ResponseEntity<CustomResponseEntity<UserEntityDTO>> fetchUserById(final UUID userId) {
        final UserEntity user = getUserEntityById(userId);

        final UserEntityDTO userEntityDto = userEntityDTOMapper.apply(user);
        final CustomResponseEntity<UserEntityDTO> customResponseEntity = new CustomResponseEntity<>(HttpStatus.OK,userEntityDto);
        return new ResponseEntity<>(customResponseEntity,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CustomResponseEntity<List<UserEntityDTO>>> searchUser (String fullName, UUID id, String email, String phoneNumber) {
        final List<UserEntity> userEntityList = userEntityRepository.searchUsers(fullName, id , email , phoneNumber);
        final List<UserEntityDTO> userEntityDTOList = userEntityList.stream().map(userEntityDTOMapper).toList();
        final CustomResponseEntity<List<UserEntityDTO>> customResponseEntity = new CustomResponseEntity<>(HttpStatus.OK,userEntityDTOList);
        return new ResponseEntity<>(customResponseEntity , HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CustomResponseEntity<List<UserEntityDTO>>> fetchAllUsers(final long pageNumber)
    {
        final int itemsPerPage = 10;
        final long startIndex = (pageNumber - 1) * itemsPerPage;

        final List<UserEntityDTO> userEntityDTOList = userEntityRepository.fetchAllUsers().stream().skip(startIndex).limit(itemsPerPage).map(userEntityDTOMapper).toList();

        if(userEntityDTOList.isEmpty() && pageNumber > 1)
        {
            return fetchAllUsers(1);
        }
        final CustomResponseEntity<List<UserEntityDTO>> customResponseEntity = new CustomResponseEntity<>(HttpStatus.OK,userEntityDTOList);
        return new ResponseEntity<>(customResponseEntity, HttpStatus.OK);
    }
    @Override
    public ResponseEntity<CustomResponseEntity<UserEntityDTO>> fetchCurrentUser(@NotNull final UserDetails userDetails)
    {
        final UserEntity currentUser = getUserEntityByEmail(userDetails.getUsername());
        final UserEntityDTO currentUserDto = userEntityDTOMapper.apply(currentUser);
        final CustomResponseEntity<UserEntityDTO> customResponseEntity = new CustomResponseEntity<>(HttpStatus.OK,currentUserDto);
        return new ResponseEntity<>(customResponseEntity , HttpStatus.OK);

    }
    @Override
    public void enableUserById(final UUID userId)
    {
        UserEntity userEntity = getUserEntityById(userId);
        userEntity.setEnabled(true);
        userEntityRepository.save(userEntity);
    }

    @Override
    public boolean isFullNameRegistered(final String fullName)
    {
        return userEntityRepository.isFullNameRegistered(fullName);
    }
    @Override
    public boolean isEmailRegistered(final String email)
    {
        return userEntityRepository.isEmailRegistered(email);
    }
    @Override
    public boolean isPhoneNumberRegistered(final String phoneNumber)
    {
        return userEntityRepository.isPhoneNumberRegistered(phoneNumber);
    }
    @Override
    public UserEntity saveUser(@NotNull final UserEntity userEntity)
    {
        return userEntityRepository.save(userEntity);
    }

    @Override
    public UserEntity getUserEntityById(final UUID userId)
    {
        return userEntityRepository.fetchUserWithId(userId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("The user with ID : %s could not be found.", userId)));
    }

    @Override
    public UserEntity getUserEntityByEmail(@NotNull final String userEmail)
    {
        return userEntityRepository.fetchUserWithEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("The user with email : %s could not be found.", userEmail)));
    }

}
