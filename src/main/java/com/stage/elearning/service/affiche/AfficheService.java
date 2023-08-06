package com.stage.elearning.service.affiche;

import com.stage.elearning.dto.affiche.AfficheDTO;
import com.stage.elearning.model.affiche.Affiche;
import com.stage.elearning.utility.CustomResponseEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AfficheService {
    public ResponseEntity<CustomResponseEntity<AfficheDTO>> createAffiche(@org.jetbrains.annotations.NotNull final MultipartFile image , @org.jetbrains.annotations.NotNull final String jsonAffiche) throws IOException;
    public ResponseEntity<byte[]> fetchAfficheImage(final long afficheId) throws IOException ;
    public ResponseEntity<CustomResponseEntity<String>> modifyAfficheById(final MultipartFile image,final long afficheId , @NotNull final String jsonAfficheDetails) throws IOException ;
    ResponseEntity<CustomResponseEntity<AfficheDTO>> fetchAfficheById(final long afficheId);
    ResponseEntity<CustomResponseEntity<List<AfficheDTO>>> fetchAllAffiche(final long pageNumber);
    ResponseEntity<CustomResponseEntity<String>> deleteAfficheById(final long afficheId) throws IOException;
}
