package com.stage.elearning.service.article;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.anyFloat;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.stage.elearning.dto.article.ArticleDTO;
import com.stage.elearning.dto.article.ArticleDTOMapper;
import com.stage.elearning.dto.categorie.CategorieDTO;
import com.stage.elearning.exceptions.ResourceNotFoundException;
import com.stage.elearning.model.acticle.Article;
import com.stage.elearning.model.categories.Categorie;
import com.stage.elearning.model.file.FileData;
import com.stage.elearning.repository.ArticleRepository;
import com.stage.elearning.service.categorie.CategorieServiceImpl;
import com.stage.elearning.service.file.FileServiceImpl;
import com.stage.elearning.utility.CustomResponseEntity;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

@ContextConfiguration(classes = {ArticleServiceImpl.class})
@ExtendWith(SpringExtension.class)
class ArticleServiceImplTest {
    @MockBean
    private ArticleDTOMapper articleDTOMapper;

    @MockBean
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleServiceImpl articleServiceImpl;

    @MockBean
    private CategorieServiceImpl categorieServiceImpl;

    @MockBean
    private FileServiceImpl fileServiceImpl;


    @Test
    void testCreateArticle4() throws IOException {
        assertThrows(IllegalArgumentException.class, () -> articleServiceImpl.createArticle(null, "Json Article", 1L));
    }


    @Test
    void testCreateArticle5() throws IOException {
        DataInputStream contentStream = mock(DataInputStream.class);
        when(contentStream.readAllBytes()).thenReturn("AXAXAXAX".getBytes("UTF-8"));
        doNothing().when(contentStream).close();
        assertThrows(IllegalArgumentException.class,
                () -> articleServiceImpl.createArticle(new MockMultipartFile("Name", contentStream), null, 1L));
        verify(contentStream).readAllBytes();
        verify(contentStream).close();
    }


    @Test
    void testFetchArticleImage() throws IOException {
        Categorie categorie = new Categorie();
        categorie.setArticles(new ArrayList<>());
        categorie.setDescription("The characteristics of someone or something");
        categorie.setId(1L);
        categorie.setName("Name");

        FileData image = new FileData();
        image.setFilePath("/directory/foo.txt");
        image.setId(1L);
        image.setName("Name");
        image.setType("Type");

        Article article = new Article();
        article.setCategorie(categorie);
        article.setCertification(true);
        article.setEndingDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        article.setId(1L);
        article.setImage(image);
        article.setName("Name");
        article.setPrice(10.0f);
        article.setStartingDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        article.setTimePeriod(1);
        Optional<Article> ofResult = Optional.of(article);
        when(articleRepository.fetchArticleById(anyLong())).thenReturn(ofResult);
        when(fileServiceImpl.getFileDataById(anyLong())).thenThrow(new IllegalArgumentException("foo"));
        assertThrows(IllegalArgumentException.class, () -> articleServiceImpl.fetchArticleImage(1L));
        verify(articleRepository).fetchArticleById(anyLong());
        verify(fileServiceImpl).getFileDataById(anyLong());
    }

    @Test
    void testFetchArticleImage2() throws IOException {
        Categorie categorie = new Categorie();
        categorie.setArticles(new ArrayList<>());
        categorie.setDescription("The characteristics of someone or something");
        categorie.setId(1L);
        categorie.setName("Name");

        FileData image = new FileData();
        image.setFilePath("/directory/foo.txt");
        image.setId(1L);
        image.setName("Name");
        image.setType("Type");

        Article article = new Article();
        article.setCategorie(categorie);
        article.setCertification(true);
        article.setEndingDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        article.setId(1L);
        article.setImage(image);
        article.setName("Name");
        article.setPrice(10.0f);
        article.setStartingDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        article.setTimePeriod(1);
        Optional<Article> ofResult = Optional.of(article);
        when(articleRepository.fetchArticleById(anyLong())).thenReturn(ofResult);

        FileData fileData = new FileData();
        fileData.setFilePath("/directory/foo.txt");
        fileData.setId(1L);
        fileData.setName("Name");
        fileData.setType("Type");
        when(fileServiceImpl.getFileDataById(anyLong())).thenReturn(fileData);
        when(fileServiceImpl.downloadFile(Mockito.<FileData>any())).thenReturn(null);
        assertNull(articleServiceImpl.fetchArticleImage(1L));
        verify(articleRepository).fetchArticleById(anyLong());
        verify(fileServiceImpl).getFileDataById(anyLong());
        verify(fileServiceImpl).downloadFile(Mockito.<FileData>any());
    }

    @Test
    void testFetchArticleImage3() throws IOException {
        Categorie categorie = new Categorie();
        categorie.setArticles(new ArrayList<>());
        categorie.setDescription("The characteristics of someone or something");
        categorie.setId(1L);
        categorie.setName("Name");

        FileData image = new FileData();
        image.setFilePath("/directory/foo.txt");
        image.setId(1L);
        image.setName("Name");
        image.setType("Type");
        FileData fileData = mock(FileData.class);
        when(fileData.getId()).thenReturn(1L);
        doNothing().when(fileData).setFilePath(Mockito.<String>any());
        doNothing().when(fileData).setId(anyLong());
        doNothing().when(fileData).setName(Mockito.<String>any());
        doNothing().when(fileData).setType(Mockito.<String>any());
        fileData.setFilePath("/directory/foo.txt");
        fileData.setId(1L);
        fileData.setName("Name");
        fileData.setType("Type");
        Article article = mock(Article.class);
        when(article.getImage()).thenReturn(fileData);
        doNothing().when(article).setCategorie(Mockito.<Categorie>any());
        doNothing().when(article).setCertification(anyBoolean());
        doNothing().when(article).setEndingDate(Mockito.<Date>any());
        doNothing().when(article).setId(anyLong());
        doNothing().when(article).setImage(Mockito.<FileData>any());
        doNothing().when(article).setName(Mockito.<String>any());
        doNothing().when(article).setPrice(anyFloat());
        doNothing().when(article).setStartingDate(Mockito.<Date>any());
        doNothing().when(article).setTimePeriod(anyInt());
        article.setCategorie(categorie);
        article.setCertification(true);
        article.setEndingDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        article.setId(1L);
        article.setImage(image);
        article.setName("Name");
        article.setPrice(10.0f);
        article.setStartingDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        article.setTimePeriod(1);
        Optional<Article> ofResult = Optional.of(article);
        when(articleRepository.fetchArticleById(anyLong())).thenReturn(ofResult);
        when(fileServiceImpl.getFileDataById(anyLong())).thenThrow(new IllegalArgumentException("foo"));
        assertThrows(IllegalArgumentException.class, () -> articleServiceImpl.fetchArticleImage(1L));
        verify(articleRepository).fetchArticleById(anyLong());
        verify(article).getImage();
        verify(article).setCategorie(Mockito.<Categorie>any());
        verify(article).setCertification(anyBoolean());
        verify(article).setEndingDate(Mockito.<Date>any());
        verify(article).setId(anyLong());
        verify(article).setImage(Mockito.<FileData>any());
        verify(article).setName(Mockito.<String>any());
        verify(article).setPrice(anyFloat());
        verify(article).setStartingDate(Mockito.<Date>any());
        verify(article).setTimePeriod(anyInt());
        verify(fileData).getId();
        verify(fileData).setFilePath(Mockito.<String>any());
        verify(fileData).setId(anyLong());
        verify(fileData).setName(Mockito.<String>any());
        verify(fileData).setType(Mockito.<String>any());
        verify(fileServiceImpl).getFileDataById(anyLong());
    }

    @Test
    void testFetchArticleById() {
        Categorie categorie = new Categorie();
        categorie.setArticles(new ArrayList<>());
        categorie.setDescription("The characteristics of someone or something");
        categorie.setId(1L);
        categorie.setName("Name");

        FileData image = new FileData();
        image.setFilePath("/directory/foo.txt");
        image.setId(1L);
        image.setName("Name");
        image.setType("Type");

        Article article = new Article();
        article.setCategorie(categorie);
        article.setCertification(true);
        article.setEndingDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        article.setId(1L);
        article.setImage(image);
        article.setName("Name");
        article.setPrice(10.0f);
        article.setStartingDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        article.setTimePeriod(1);
        Optional<Article> ofResult = Optional.of(article);
        when(articleRepository.fetchArticleById(anyLong())).thenReturn(ofResult);
        Date statingDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        Date endingDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        CategorieDTO categorieDTO = new CategorieDTO(1L, "Name", "The characteristics of someone or something");

        ArticleDTO articleDTO = new ArticleDTO(1L, "Name", statingDate, endingDate, true, 1, categorieDTO,
                new FileData());

        when(articleDTOMapper.apply(Mockito.<Article>any())).thenReturn(articleDTO);
        ResponseEntity<CustomResponseEntity<ArticleDTO>> actualFetchArticleByIdResult = articleServiceImpl
                .fetchArticleById(1L);
        assertTrue(actualFetchArticleByIdResult.hasBody());
        assertTrue(actualFetchArticleByIdResult.getHeaders().isEmpty());
        assertEquals(200, actualFetchArticleByIdResult.getStatusCodeValue());
        CustomResponseEntity<ArticleDTO> body = actualFetchArticleByIdResult.getBody();
        assertTrue(body.isStatusString());
        assertEquals(HttpStatus.OK, body.getStatus());
        assertSame(articleDTO, body.getData());
        verify(articleRepository).fetchArticleById(anyLong());
        verify(articleDTOMapper).apply(Mockito.<Article>any());
    }

    @Test
    void testFetchArticleById2() {
        Categorie categorie = new Categorie();
        categorie.setArticles(new ArrayList<>());
        categorie.setDescription("The characteristics of someone or something");
        categorie.setId(1L);
        categorie.setName("Name");

        FileData image = new FileData();
        image.setFilePath("/directory/foo.txt");
        image.setId(1L);
        image.setName("Name");
        image.setType("Type");

        Article article = new Article();
        article.setCategorie(categorie);
        article.setCertification(true);
        article.setEndingDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        article.setId(1L);
        article.setImage(image);
        article.setName("Name");
        article.setPrice(10.0f);
        article.setStartingDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        article.setTimePeriod(1);
        Optional<Article> ofResult = Optional.of(article);
        when(articleRepository.fetchArticleById(anyLong())).thenReturn(ofResult);
        when(articleDTOMapper.apply(Mockito.<Article>any()))
                .thenThrow(new ResourceNotFoundException("An error occurred"));
        assertThrows(ResourceNotFoundException.class, () -> articleServiceImpl.fetchArticleById(1L));
        verify(articleRepository).fetchArticleById(anyLong());
        verify(articleDTOMapper).apply(Mockito.<Article>any());
    }


    @Test
    void testFetchArticleById3() {
        when(articleRepository.fetchArticleById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> articleServiceImpl.fetchArticleById(1L));
        verify(articleRepository).fetchArticleById(anyLong());
    }

    @Test
    void testFetchAllArticles() {
        when(articleRepository.fetchAllArticles()).thenReturn(new ArrayList<>());
        ResponseEntity<CustomResponseEntity<List<ArticleDTO>>> actualFetchAllArticlesResult = articleServiceImpl
                .fetchAllArticles(1L);
        assertTrue(actualFetchAllArticlesResult.hasBody());
        assertTrue(actualFetchAllArticlesResult.getHeaders().isEmpty());
        assertEquals(200, actualFetchAllArticlesResult.getStatusCodeValue());
        CustomResponseEntity<List<ArticleDTO>> body = actualFetchAllArticlesResult.getBody();
        assertTrue(body.isStatusString());
        assertEquals(HttpStatus.OK, body.getStatus());
        assertTrue(body.getData().isEmpty());
        verify(articleRepository).fetchAllArticles();
    }

    @Test
    void testFetchAllArticles2() {
        Categorie categorie = new Categorie();
        categorie.setArticles(new ArrayList<>());
        categorie.setDescription("The characteristics of someone or something");
        categorie.setId(1L);
        categorie.setName("Name");

        FileData image = new FileData();
        image.setFilePath("/directory/foo.txt");
        image.setId(1L);
        image.setName("Name");
        image.setType("Type");

        Article article = new Article();
        article.setCategorie(categorie);
        article.setCertification(true);
        article.setEndingDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        article.setId(1L);
        article.setImage(image);
        article.setName("Name");
        article.setPrice(10.0f);
        article.setStartingDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        article.setTimePeriod(10);

        ArrayList<Article> articleList = new ArrayList<>();
        articleList.add(article);
        when(articleRepository.fetchAllArticles()).thenReturn(articleList);
        Date statingDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        Date endingDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        CategorieDTO categorieDTO = new CategorieDTO(1L, "Name", "The characteristics of someone or something");

        when(articleDTOMapper.apply(Mockito.<Article>any()))
                .thenReturn(new ArticleDTO(1L, "Name", statingDate, endingDate, true, 1, categorieDTO, new FileData()));
        ResponseEntity<CustomResponseEntity<List<ArticleDTO>>> actualFetchAllArticlesResult = articleServiceImpl
                .fetchAllArticles(1L);
        assertTrue(actualFetchAllArticlesResult.hasBody());
        assertTrue(actualFetchAllArticlesResult.getHeaders().isEmpty());
        assertEquals(200, actualFetchAllArticlesResult.getStatusCodeValue());
        CustomResponseEntity<List<ArticleDTO>> body = actualFetchAllArticlesResult.getBody();
        assertTrue(body.isStatusString());
        assertEquals(HttpStatus.OK, body.getStatus());
        assertEquals(1, body.getData().size());
        verify(articleRepository).fetchAllArticles();
        verify(articleDTOMapper).apply(Mockito.<Article>any());
    }

    @Test
    void testFetchAllArticles3() {
        Categorie categorie = new Categorie();
        categorie.setArticles(new ArrayList<>());
        categorie.setDescription("The characteristics of someone or something");
        categorie.setId(1L);
        categorie.setName("Name");

        FileData image = new FileData();
        image.setFilePath("/directory/foo.txt");
        image.setId(1L);
        image.setName("Name");
        image.setType("Type");

        Article article = new Article();
        article.setCategorie(categorie);
        article.setCertification(true);
        article.setEndingDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        article.setId(1L);
        article.setImage(image);
        article.setName("Name");
        article.setPrice(10.0f);
        article.setStartingDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        article.setTimePeriod(10);

        ArrayList<Article> articleList = new ArrayList<>();
        articleList.add(article);
        when(articleRepository.fetchAllArticles()).thenReturn(articleList);
        Date statingDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        Date endingDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        CategorieDTO categorieDTO = new CategorieDTO(1L, "Name", "The characteristics of someone or something");

        when(articleDTOMapper.apply(Mockito.<Article>any()))
                .thenReturn(new ArticleDTO(1L, "Name", statingDate, endingDate, true, 1, categorieDTO, new FileData()));
        ResponseEntity<CustomResponseEntity<List<ArticleDTO>>> actualFetchAllArticlesResult = articleServiceImpl
                .fetchAllArticles(10L);
        assertTrue(actualFetchAllArticlesResult.hasBody());
        assertTrue(actualFetchAllArticlesResult.getHeaders().isEmpty());
        assertEquals(200, actualFetchAllArticlesResult.getStatusCodeValue());
        CustomResponseEntity<List<ArticleDTO>> body = actualFetchAllArticlesResult.getBody();
        assertTrue(body.isStatusString());
        assertEquals(HttpStatus.OK, body.getStatus());
        assertEquals(1, body.getData().size());
        verify(articleRepository, atLeast(1)).fetchAllArticles();
        verify(articleDTOMapper).apply(Mockito.<Article>any());
    }


    @Test
    void testModifyArticleById3() throws IOException {
        when(articleRepository.fetchArticleById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class,
                () -> articleServiceImpl.modifyArticleById(
                        new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))), 1L, "Json Article",
                        1L));
        verify(articleRepository).fetchArticleById(anyLong());
    }

    @Test
    void testModifyArticleById4() throws IOException {
        assertThrows(IllegalArgumentException.class, () -> articleServiceImpl.modifyArticleById(
                new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))), 1L, null, 1L));
    }

    @Test
    void testFetchArticlesByCategoryById() {
        Categorie categorie = new Categorie();
        categorie.setArticles(new ArrayList<>());
        categorie.setDescription("The characteristics of someone or something");
        categorie.setId(1L);
        categorie.setName("Name");
        when(categorieServiceImpl.getCategorieById(anyLong())).thenReturn(categorie);
        ResponseEntity<CustomResponseEntity<List<ArticleDTO>>> actualFetchArticlesByCategoryByIdResult = articleServiceImpl
                .fetchArticlesByCategoryById(1L);
        assertTrue(actualFetchArticlesByCategoryByIdResult.hasBody());
        assertTrue(actualFetchArticlesByCategoryByIdResult.getHeaders().isEmpty());
        assertEquals(200, actualFetchArticlesByCategoryByIdResult.getStatusCodeValue());
        CustomResponseEntity<List<ArticleDTO>> body = actualFetchArticlesByCategoryByIdResult.getBody();
        assertTrue(body.isStatusString());
        assertEquals(HttpStatus.OK, body.getStatus());
        assertTrue(body.getData().isEmpty());
        verify(categorieServiceImpl).getCategorieById(anyLong());
    }

    @Test
    void testFetchArticlesByCategoryByName() {
        Categorie categorie = new Categorie();
        categorie.setArticles(new ArrayList<>());
        categorie.setDescription("The characteristics of someone or something");
        categorie.setId(1L);
        categorie.setName("Name");
        when(categorieServiceImpl.getCategorieByName(Mockito.<String>any())).thenReturn(categorie);
        ResponseEntity<CustomResponseEntity<List<ArticleDTO>>> actualFetchArticlesByCategoryByNameResult = articleServiceImpl
                .fetchArticlesByCategoryByName("Categorie Name");
        assertTrue(actualFetchArticlesByCategoryByNameResult.hasBody());
        assertTrue(actualFetchArticlesByCategoryByNameResult.getHeaders().isEmpty());
        assertEquals(200, actualFetchArticlesByCategoryByNameResult.getStatusCodeValue());
        CustomResponseEntity<List<ArticleDTO>> body = actualFetchArticlesByCategoryByNameResult.getBody();
        assertTrue(body.isStatusString());
        assertEquals(HttpStatus.OK, body.getStatus());
        assertTrue(body.getData().isEmpty());
        verify(categorieServiceImpl).getCategorieByName(Mockito.<String>any());
    }

    @Test
    void testDeleteArticleById2() throws IOException {
        Categorie categorie = new Categorie();
        categorie.setArticles(new ArrayList<>());
        categorie.setDescription("The characteristics of someone or something");
        categorie.setId(1L);
        categorie.setName("Name");

        FileData image = new FileData();
        image.setFilePath("/directory/foo.txt");
        image.setId(1L);
        image.setName("Name");
        image.setType("Type");

        Article article = new Article();
        article.setCategorie(categorie);
        article.setCertification(true);
        article.setEndingDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        article.setId(1L);
        article.setImage(image);
        article.setName("Name");
        article.setPrice(10.0f);
        article.setStartingDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        article.setTimePeriod(1);
        Optional<Article> ofResult = Optional.of(article);
        doNothing().when(articleRepository).deleteArticleById(anyLong());
        when(articleRepository.fetchArticleById(anyLong())).thenReturn(ofResult);
        doThrow(new ResourceNotFoundException("An error occurred")).when(fileServiceImpl)
                .deleteFileFromFileSystem(Mockito.<FileData>any());
        assertThrows(ResourceNotFoundException.class, () -> articleServiceImpl.deleteArticleById(1L));
        verify(articleRepository).fetchArticleById(anyLong());
        verify(articleRepository).deleteArticleById(anyLong());
        verify(fileServiceImpl).deleteFileFromFileSystem(Mockito.<FileData>any());
    }

    @Test
    void testDeleteArticleById3() throws IOException {
        when(articleRepository.fetchArticleById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> articleServiceImpl.deleteArticleById(1L));
        verify(articleRepository).fetchArticleById(anyLong());
    }

    @Test
    void testDeleteArticleByName() throws IOException {
        Categorie categorie = new Categorie();
        categorie.setArticles(new ArrayList<>());
        categorie.setDescription("The characteristics of someone or something");
        categorie.setId(1L);
        categorie.setName("Name");

        FileData image = new FileData();
        image.setFilePath("/directory/foo.txt");
        image.setId(1L);
        image.setName("Name");
        image.setType("Type");

        Article article = new Article();
        article.setCategorie(categorie);
        article.setCertification(true);
        article.setEndingDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        article.setId(1L);
        article.setImage(image);
        article.setName("Name");
        article.setPrice(10.0f);
        article.setStartingDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        article.setTimePeriod(1);
        Optional<Article> ofResult = Optional.of(article);
        doNothing().when(articleRepository).deleteArticleByName(Mockito.<String>any());
        when(articleRepository.fetchArticleByName(Mockito.<String>any())).thenReturn(ofResult);
        doNothing().when(fileServiceImpl).deleteFileFromFileSystem(Mockito.<FileData>any());
        ResponseEntity<CustomResponseEntity<String>> actualDeleteArticleByNameResult = articleServiceImpl
                .deleteArticleByName("Article Name");
        assertTrue(actualDeleteArticleByNameResult.hasBody());
        assertTrue(actualDeleteArticleByNameResult.getHeaders().isEmpty());
        assertEquals(200, actualDeleteArticleByNameResult.getStatusCodeValue());
        CustomResponseEntity<String> body = actualDeleteArticleByNameResult.getBody();
        assertTrue(body.isStatusString());
        assertEquals(HttpStatus.OK, body.getStatus());
        assertEquals("The Article has been deleted successfully.", body.getData());
        verify(articleRepository).fetchArticleByName(Mockito.<String>any());
        verify(articleRepository).deleteArticleByName(Mockito.<String>any());
        verify(fileServiceImpl).deleteFileFromFileSystem(Mockito.<FileData>any());
    }

    @Test
    void testDeleteArticleByName2() throws IOException {
        Categorie categorie = new Categorie();
        categorie.setArticles(new ArrayList<>());
        categorie.setDescription("The characteristics of someone or something");
        categorie.setId(1L);
        categorie.setName("Name");

        FileData image = new FileData();
        image.setFilePath("/directory/foo.txt");
        image.setId(1L);
        image.setName("Name");
        image.setType("Type");

        Article article = new Article();
        article.setCategorie(categorie);
        article.setCertification(true);
        article.setEndingDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        article.setId(1L);
        article.setImage(image);
        article.setName("Name");
        article.setPrice(10.0f);
        article.setStartingDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        article.setTimePeriod(1);
        Optional<Article> ofResult = Optional.of(article);
        doNothing().when(articleRepository).deleteArticleByName(Mockito.<String>any());
        when(articleRepository.fetchArticleByName(Mockito.<String>any())).thenReturn(ofResult);
        doThrow(new ResourceNotFoundException("An error occurred")).when(fileServiceImpl)
                .deleteFileFromFileSystem(Mockito.<FileData>any());
        assertThrows(ResourceNotFoundException.class, () -> articleServiceImpl.deleteArticleByName("Article Name"));
        verify(articleRepository).fetchArticleByName(Mockito.<String>any());
        verify(articleRepository).deleteArticleByName(Mockito.<String>any());
        verify(fileServiceImpl).deleteFileFromFileSystem(Mockito.<FileData>any());
    }

    @Test
    void testDeleteArticleByName3() throws IOException {
        when(articleRepository.fetchArticleByName(Mockito.<String>any())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> articleServiceImpl.deleteArticleByName("Article Name"));
        verify(articleRepository).fetchArticleByName(Mockito.<String>any());
    }
}

