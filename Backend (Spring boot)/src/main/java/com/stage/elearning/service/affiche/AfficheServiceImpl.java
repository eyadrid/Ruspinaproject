package com.stage.elearning.service.affiche;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stage.elearning.dto.affiche.AfficheDTO;
import com.stage.elearning.dto.affiche.AfficheDTOMapper;
import com.stage.elearning.exceptions.ResourceNotFoundException;
import com.stage.elearning.model.affiche.Affiche;
import com.stage.elearning.model.file.FileData;
import com.stage.elearning.repository.AfficheRepository;
import com.stage.elearning.service.file.FileService;
import com.stage.elearning.service.file.FileServiceImpl;
import com.stage.elearning.utility.CustomResponseEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class AfficheServiceImpl implements  AfficheService{

    private final AfficheRepository afficheRepository;
    private final AfficheDTOMapper afficheDTOMapper;
    private final FileService fileService;


    public AfficheServiceImpl(AfficheRepository afficheRepository, AfficheDTOMapper afficheDTOMapper, FileService fileService)
    {
        this.afficheRepository = afficheRepository;
        this.afficheDTOMapper = afficheDTOMapper;
        this.fileService = fileService;
    }


    private  int maxAfficheNumber = 4 , currentAfficheNumber = 0;

    @Override
    public ResponseEntity<CustomResponseEntity<AfficheDTO>> createAffiche(@NotNull final MultipartFile image , @NotNull final String jsonAffiche) throws IOException {
        if (currentAfficheNumber >= maxAfficheNumber) {
            throw new IllegalArgumentException("The Affiche number has exceeded its limit.");
        }
        currentAfficheNumber++;
        final Affiche currentAffiche = new ObjectMapper().readValue(jsonAffiche , Affiche.class);
        final FileData fileData = fileService.processUploadedFile(image);
        currentAffiche.setImage(fileData);
        final AfficheDTO savedAfficheDTO = afficheDTOMapper.apply(afficheRepository.save(currentAffiche));
        final CustomResponseEntity<AfficheDTO> customResponseEntity = new CustomResponseEntity<>(HttpStatus.OK,savedAfficheDTO);
        return new ResponseEntity<>(customResponseEntity, HttpStatus.OK);

    }

    public ResponseEntity<byte[]> fetchAfficheImage(final long afficheId) throws IOException {
        final Affiche affiche = getAfficheById(afficheId);
        final FileData fileData = fileService.getFileDataById(affiche.getImage().getId());
        return fileService.downloadFile(fileData);
    }

    @Override
    public ResponseEntity<CustomResponseEntity<String>> modifyAfficheById(final MultipartFile image,final long afficheId , @NotNull final String jsonAfficheDetails) throws IOException {
        final Affiche currentAffiche = getAfficheById(afficheId);
        final FileData prevImage = currentAffiche.getImage();
        final Affiche afficheDetails =new ObjectMapper().readValue(jsonAfficheDetails, Affiche.class);
        afficheDetails.setId(currentAffiche.getId());

        if (image != null) {
            final FileData fileData = fileService.processUploadedFile(image);
            afficheDetails.setImage(fileData);
            afficheRepository.save(afficheDetails);
            fileService.deleteFileFromFileSystem(prevImage);
        } else {
            afficheRepository.save(afficheDetails);
        }

        final  String successResponse = String.format("The Affiche with ID : %d is Updated successfully.",afficheId);
        final CustomResponseEntity<String> customResponseEntity = new CustomResponseEntity<>(HttpStatus.OK , successResponse);
        return new ResponseEntity<>(customResponseEntity , HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CustomResponseEntity<AfficheDTO>> fetchAfficheById(final long afficheId)
    {
        final AfficheDTO afficheDTO = afficheDTOMapper.apply(getAfficheById(afficheId));
        final CustomResponseEntity<AfficheDTO> customResponseEntity = new CustomResponseEntity<>(HttpStatus.OK, afficheDTO);
        return new ResponseEntity<>(customResponseEntity , HttpStatus.OK);
    }
    @Override
    public ResponseEntity<CustomResponseEntity<List<AfficheDTO>>> fetchAllAffiche(final long pageNumber)
    {
        final int itemsPerPage = 10;
        final long startIndex = (pageNumber - 1) * itemsPerPage;

        final List<AfficheDTO> afficheDTOList =
                afficheRepository.fetchAllAffiche()
                        .stream()
                        .skip(startIndex)
                        .limit(itemsPerPage)
                        .map(afficheDTOMapper)
                        .toList();

        if(afficheDTOList.isEmpty() && pageNumber > 1)
        {
            return fetchAllAffiche(1);
        }
        final  CustomResponseEntity<List<AfficheDTO>> customResponseEntity  = new CustomResponseEntity<>(HttpStatus.OK , afficheDTOList);
        return new ResponseEntity<>(customResponseEntity , HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<CustomResponseEntity<String>> deleteAfficheById(final long afficheId) throws IOException {
        final Affiche currentAffiche = getAfficheById(afficheId);
        afficheRepository.deleteAfficheById(afficheId);
        fileService.deleteFileFromFileSystem(currentAffiche.getImage());
        currentAfficheNumber--;

        final String successResponse = String.format("The Affiche with ID: %d has been deleted successfully.", afficheId);
        final CustomResponseEntity<String> customResponseEntity = new CustomResponseEntity<>(HttpStatus.OK , successResponse);
        return new ResponseEntity<>(customResponseEntity , HttpStatus.OK);
    }


    private Affiche getAfficheById(final long afficheId)
    {
        return afficheRepository.fetchAfficheById(afficheId).orElseThrow(
                ()-> new ResourceNotFoundException(String.format("The Affiche with ID : %d could not be found in our system.",afficheId))
        );
    }
}
