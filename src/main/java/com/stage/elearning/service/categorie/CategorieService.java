package com.stage.elearning.service.categorie;

import com.stage.elearning.dto.categorie.CategorieDTO;
import com.stage.elearning.model.categories.Categorie;
import com.stage.elearning.utility.CustomResponseEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CategorieService {
    public  ResponseEntity<CustomResponseEntity<CategorieDTO>> createCategorie(@NotNull final Categorie categorie);
    public  ResponseEntity<CustomResponseEntity<CategorieDTO>> fetchCategorieById(final long categorieId);
    public  ResponseEntity<CustomResponseEntity<CategorieDTO>> fetchCategorieByName(final String categorieName);
    public ResponseEntity<CustomResponseEntity<List<CategorieDTO>>> fetchAllCategories(final long pageNumber);
    public ResponseEntity<CustomResponseEntity<String>> modifyCategorieById(final long categorieId, @NotNull Categorie categorieDetails);
    public ResponseEntity<CustomResponseEntity<String>> deleteCategorieById(final long categorieId);
}
