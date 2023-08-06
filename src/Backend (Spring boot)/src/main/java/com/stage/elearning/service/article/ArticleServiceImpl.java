package com.stage.elearning.service.article;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stage.elearning.dto.article.ArticleDTO;
import com.stage.elearning.dto.article.ArticleDTOMapper;
import com.stage.elearning.exceptions.ResourceNotFoundException;
import com.stage.elearning.model.acticle.Article;
import com.stage.elearning.model.categories.Categorie;
import com.stage.elearning.model.file.FileData;
import com.stage.elearning.repository.ArticleRepository;
import com.stage.elearning.service.categorie.CategorieServiceImpl;
import com.stage.elearning.service.file.FileServiceImpl;
import com.stage.elearning.utility.CustomResponseEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleDTOMapper articleDTOMapper;
    private final FileServiceImpl fileServiceImpl;
    private final CategorieServiceImpl categorieServiceImpl;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository, FileServiceImpl fileServiceImpl, CategorieServiceImpl categorieServiceImpl, ArticleDTOMapper articleDTOMapper)
    {
        this.articleRepository = articleRepository;
        this.fileServiceImpl = fileServiceImpl;
        this.categorieServiceImpl = categorieServiceImpl;
        this.articleDTOMapper = articleDTOMapper;
    }


    @Override
    public ResponseEntity<CustomResponseEntity<ArticleDTO>> createArticle(@NotNull final MultipartFile image, @NotNull final String jsonArticle , final long categorieId) throws IOException {

        final Article article = new ObjectMapper().readValue(jsonArticle,Article.class);
        final Categorie currentCategorie = categorieServiceImpl.getCategorieById(categorieId);
        final FileData fileData = fileServiceImpl.processUploadedFile(image);
        article.setImage(fileData);
        article.setCategorie(currentCategorie);
        final ArticleDTO savedArticleDTO = articleDTOMapper.apply(articleRepository.save(article));
        final CustomResponseEntity<ArticleDTO> customResponseEntity = new CustomResponseEntity<>(HttpStatus.CREATED,savedArticleDTO);
        return new ResponseEntity<>(customResponseEntity, HttpStatus.CREATED);
    }
    @Override
    public ResponseEntity<byte[]> fetchArticleImage(final long articleId) throws IOException {

        final Article article = getArticleById(articleId);
        final FileData fileData = fileServiceImpl.getFileDataById(article.getImage().getId());
        return fileServiceImpl.downloadFile(fileData);
    }
    @Override
    public ResponseEntity<CustomResponseEntity<ArticleDTO>> fetchArticleById(final long articleId)
    {
        final ArticleDTO articleDto = articleDTOMapper.apply(getArticleById(articleId));
        final CustomResponseEntity<ArticleDTO> customResponseEntity = new CustomResponseEntity<>(HttpStatus.OK,articleDto);
        return new ResponseEntity<>(customResponseEntity , HttpStatus.OK);
    }
    @Override
    public ResponseEntity<CustomResponseEntity<List<ArticleDTO>>> fetchAllArticles(final long pageNumber)
    {
        final int itemsPerPage = 10;
        final long  startIndex = (pageNumber - 1) * itemsPerPage;

        final List<Article> articleList = articleRepository.fetchAllArticles();
        final List<ArticleDTO> articleDTOList = articleList.stream().skip(startIndex).limit(itemsPerPage).map(articleDTOMapper).toList();
        if(articleDTOList.isEmpty() && pageNumber > 1)
        {
            return fetchAllArticles(1);
        }
        final CustomResponseEntity<List<ArticleDTO>> customResponseEntityList = new CustomResponseEntity<>(HttpStatus.OK,articleDTOList);
        return new ResponseEntity<>(customResponseEntityList, HttpStatus.OK);
    }
    @Override
    public ResponseEntity<CustomResponseEntity<String>> modifyArticleById(final MultipartFile image, final long articleId, @NotNull final String  jsonArticle, final long categorieId) throws IOException {
        final Article currentArticle = getArticleById(articleId);
        final FileData prevImage = currentArticle.getImage();
        final Article articleDetails = new ObjectMapper().readValue(jsonArticle,Article.class);
        final Categorie currentCategorie = categorieServiceImpl.getCategorieById(categorieId);

        articleDetails.setId(currentArticle.getId());
        articleDetails.setCategorie(currentCategorie);

        if(image != null)
        {
            final FileData fileData = fileServiceImpl.processUploadedFile(image);
            articleDetails.setImage(fileData);
            articleRepository.save(articleDetails);
            fileServiceImpl.deleteFileFromFileSystem(currentArticle.getImage());
        }else {
            articleRepository.save(articleDetails);
        }



        final String successResponse = "The Article has been updated successfully.";
        final CustomResponseEntity<String> customResponseEntity = new CustomResponseEntity<>(HttpStatus.OK, successResponse);
        return new ResponseEntity<>(customResponseEntity , HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CustomResponseEntity<List<ArticleDTO>>>fetchArticlesByCategoryById(final long categorieId) {
        final Categorie categorie = categorieServiceImpl.getCategorieById(categorieId);
        final List<ArticleDTO> articleDTOList = categorie.getArticles().stream().map(articleDTOMapper).toList();
        final CustomResponseEntity<List<ArticleDTO>> customResponseEntityList = new CustomResponseEntity<>(HttpStatus.OK,articleDTOList);
        return new ResponseEntity<>(customResponseEntityList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CustomResponseEntity<List<ArticleDTO>>> fetchArticlesByCategoryByName(final String categorieName) {
        final Categorie categorie = categorieServiceImpl.getCategorieByName(categorieName);
        List<ArticleDTO> articleDTOList = categorie.getArticles().stream().map(articleDTOMapper).toList();
        final CustomResponseEntity<List<ArticleDTO>> customResponseEntityList = new CustomResponseEntity<>(HttpStatus.OK,articleDTOList);
        return new ResponseEntity<>(customResponseEntityList, HttpStatus.OK);
    }
    @Override
    @Transactional
    public ResponseEntity<CustomResponseEntity<String>> deleteArticleById(final long articleId) throws IOException {
        final Article currentArticle = getArticleById(articleId);
        articleRepository.deleteArticleById(articleId);
        fileServiceImpl.deleteFileFromFileSystem(currentArticle.getImage());
        final String successResponse = "The Article has been deleted successfully.";
        final CustomResponseEntity<String> customResponseEntity = new CustomResponseEntity<>(HttpStatus.OK,successResponse);
        return new ResponseEntity<>(customResponseEntity , HttpStatus.OK);
    }
    @Override
    @Transactional
    public ResponseEntity<CustomResponseEntity<String>> deleteArticleByName(final String articleName) throws IOException {
        final Article currentArticle = getArticleByName(articleName);
        articleRepository.deleteArticleByName(articleName);
        fileServiceImpl.deleteFileFromFileSystem(currentArticle.getImage());
        final String successResponse = "The Article has been deleted successfully.";
        final CustomResponseEntity<String> customResponseEntity = new CustomResponseEntity<>(HttpStatus.OK,successResponse);
        return new ResponseEntity<>(customResponseEntity , HttpStatus.OK);
    }
    private Article getArticleById(final long articleId)
    {
        return articleRepository.fetchArticleById(articleId).orElseThrow(
                ()-> new ResourceNotFoundException(String.format("The Article with ID : %d could not be found in our system",articleId))
        );
    }
    private Article getArticleByName(final String articleName)
    {
        return articleRepository.fetchArticleByName(articleName).orElseThrow(
                ()-> new ResourceNotFoundException(String.format("The Article with NAME : %s could not be found in our system",articleName))
        );
    }

}
