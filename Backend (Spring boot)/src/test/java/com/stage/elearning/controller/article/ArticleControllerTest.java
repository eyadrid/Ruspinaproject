package com.stage.elearning.controller.article;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.stage.elearning.dto.article.ArticleDTO;
import com.stage.elearning.dto.article.ArticleDTOMapper;
import com.stage.elearning.dto.categorie.CategorieDTO;
import com.stage.elearning.dto.categorie.CategorieDTOMapper;
import com.stage.elearning.model.acticle.Article;
import com.stage.elearning.model.categories.Categorie;
import com.stage.elearning.model.file.FileData;
import com.stage.elearning.repository.ArticleRepository;
import com.stage.elearning.repository.CategorieRepository;
import com.stage.elearning.repository.FileDataRepository;
import com.stage.elearning.service.article.ArticleService;
import com.stage.elearning.service.article.ArticleServiceImpl;
import com.stage.elearning.service.categorie.CategorieServiceImpl;
import com.stage.elearning.service.file.FileServiceImpl;
import com.stage.elearning.utility.CustomResponseEntity;

import java.io.ByteArrayInputStream;
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
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

@ContextConfiguration(classes = {ArticleController.class})
@ExtendWith(SpringExtension.class)
class ArticleControllerTest {
    @Autowired
    private ArticleController articleController;

    @MockBean
    private ArticleService articleService;



    @Test
    void testCreateArticle2() throws IOException {
        ArticleServiceImpl articleService = mock(ArticleServiceImpl.class);
        when(articleService.createArticle(Mockito.<MultipartFile>any(), Mockito.<String>any(), anyLong()))
                .thenReturn(null);
        ArticleController articleController = new ArticleController(articleService);
        assertNull(articleController.createArticle(
                new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))), "Json Article", 1L));
        verify(articleService).createArticle(Mockito.<MultipartFile>any(), Mockito.<String>any(), anyLong());
    }

    @Test
    void testFetchArticleById() throws IOException {
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
        Date endingDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        article.setEndingDate(endingDate);
        article.setId(1L);
        article.setImage(image);
        article.setName("Name");
        article.setPrice(10.0f);
        Date startingDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        article.setStartingDate(startingDate);
        article.setTimePeriod(1);
        ArticleRepository articleRepository = mock(ArticleRepository.class);
        when(articleRepository.fetchArticleById(anyLong())).thenReturn(Optional.of(article));
        FileServiceImpl fileServiceImpl = new FileServiceImpl(mock(FileDataRepository.class));
        CategorieRepository categorieRepository = mock(CategorieRepository.class);
        CategorieServiceImpl categorieServiceImpl = new CategorieServiceImpl(categorieRepository,
                new CategorieDTOMapper(), mock(ArticleRepository.class));

        ResponseEntity<CustomResponseEntity<ArticleDTO>> actualFetchArticleByIdResult = (new ArticleController(
                new ArticleServiceImpl(articleRepository, fileServiceImpl, categorieServiceImpl, new ArticleDTOMapper())))
                .fetchArticleById(1L);
        assertTrue(actualFetchArticleByIdResult.hasBody());
        assertTrue(actualFetchArticleByIdResult.getHeaders().isEmpty());
        assertEquals(200, actualFetchArticleByIdResult.getStatusCodeValue());
        CustomResponseEntity<ArticleDTO> body = actualFetchArticleByIdResult.getBody();
        assertTrue(body.isStatusString());
        assertEquals(HttpStatus.OK, body.getStatus());
        ArticleDTO data = body.getData();
        assertEquals(1, data.timePeriod());
        assertTrue(data.certification());
        assertEquals(1L, data.id());
        assertSame(image, data.image());
        assertSame(startingDate, data.statingDate());
        assertSame(endingDate, data.endingDate());
        assertEquals("Name", data.name());
        CategorieDTO categorieDTOResult = data.categorieDTO();
        assertEquals(1L, categorieDTOResult.id());
        assertEquals("Name", categorieDTOResult.name());
        assertEquals("The characteristics of someone or something", categorieDTOResult.description());
        verify(articleRepository).fetchArticleById(anyLong());
    }


    @Test
    void testFetchAllArticles() {
        ArticleRepository articleRepository = mock(ArticleRepository.class);
        when(articleRepository.fetchAllArticles()).thenReturn(new ArrayList<>());
        FileServiceImpl fileServiceImpl = new FileServiceImpl(mock(FileDataRepository.class));
        CategorieRepository categorieRepository = mock(CategorieRepository.class);
        CategorieServiceImpl categorieServiceImpl = new CategorieServiceImpl(categorieRepository,
                new CategorieDTOMapper(), mock(ArticleRepository.class));

        ResponseEntity<CustomResponseEntity<List<ArticleDTO>>> actualFetchAllArticlesResult = (new ArticleController(
                new ArticleServiceImpl(articleRepository, fileServiceImpl, categorieServiceImpl, new ArticleDTOMapper())))
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
    void testFetchArticlesByCategoryById() {
        Categorie categorie = new Categorie();
        categorie.setArticles(new ArrayList<>());
        categorie.setDescription("The characteristics of someone or something");
        categorie.setId(1L);
        categorie.setName("Name");
        CategorieRepository categorieRepository = mock(CategorieRepository.class);
        when(categorieRepository.fetchCategorieById(anyLong())).thenReturn(Optional.of(categorie));
        CategorieServiceImpl categorieServiceImpl = new CategorieServiceImpl(categorieRepository,
                new CategorieDTOMapper(), mock(ArticleRepository.class));

        ArticleRepository articleRepository = mock(ArticleRepository.class);
        FileServiceImpl fileServiceImpl = new FileServiceImpl(mock(FileDataRepository.class));
        ResponseEntity<CustomResponseEntity<List<ArticleDTO>>> actualFetchArticlesByCategoryByIdResult = (new ArticleController(
                new ArticleServiceImpl(articleRepository, fileServiceImpl, categorieServiceImpl, new ArticleDTOMapper())))
                .fetchArticlesByCategoryById(1L);
        assertTrue(actualFetchArticlesByCategoryByIdResult.hasBody());
        assertTrue(actualFetchArticlesByCategoryByIdResult.getHeaders().isEmpty());
        assertEquals(200, actualFetchArticlesByCategoryByIdResult.getStatusCodeValue());
        CustomResponseEntity<List<ArticleDTO>> body = actualFetchArticlesByCategoryByIdResult.getBody();
        assertTrue(body.isStatusString());
        assertEquals(HttpStatus.OK, body.getStatus());
        assertTrue(body.getData().isEmpty());
        verify(categorieRepository).fetchCategorieById(anyLong());
    }


    @Test
    void testFetchArticlesByCategoryByName() {
        Categorie categorie = new Categorie();
        categorie.setArticles(new ArrayList<>());
        categorie.setDescription("The characteristics of someone or something");
        categorie.setId(1L);
        categorie.setName("Name");
        CategorieRepository categorieRepository = mock(CategorieRepository.class);
        when(categorieRepository.fetchCategorieByName(Mockito.<String>any())).thenReturn(Optional.of(categorie));
        CategorieServiceImpl categorieServiceImpl = new CategorieServiceImpl(categorieRepository,
                new CategorieDTOMapper(), mock(ArticleRepository.class));

        ArticleRepository articleRepository = mock(ArticleRepository.class);
        FileServiceImpl fileServiceImpl = new FileServiceImpl(mock(FileDataRepository.class));
        ResponseEntity<CustomResponseEntity<List<ArticleDTO>>> actualFetchArticlesByCategoryByNameResult = (new ArticleController(
                new ArticleServiceImpl(articleRepository, fileServiceImpl, categorieServiceImpl, new ArticleDTOMapper())))
                .fetchArticlesByCategoryByName("Categorie Name");
        assertTrue(actualFetchArticlesByCategoryByNameResult.hasBody());
        assertTrue(actualFetchArticlesByCategoryByNameResult.getHeaders().isEmpty());
        assertEquals(200, actualFetchArticlesByCategoryByNameResult.getStatusCodeValue());
        CustomResponseEntity<List<ArticleDTO>> body = actualFetchArticlesByCategoryByNameResult.getBody();
        assertTrue(body.isStatusString());
        assertEquals(HttpStatus.OK, body.getStatus());
        assertTrue(body.getData().isEmpty());
        verify(categorieRepository).fetchCategorieByName(Mockito.<String>any());
    }


    @Test
    void testModifyArticleById2() throws IOException {
        ArticleService articleService = mock(ArticleService.class);
        when(articleService.modifyArticleById(Mockito.<MultipartFile>any(), anyLong(), Mockito.<String>any(), anyLong()))
                .thenReturn(null);
        ArticleController articleController = new ArticleController(articleService);
        assertNull(articleController.modifyArticleById(
                new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))), 1L, "Json Article",
                1L));
        verify(articleService).modifyArticleById(Mockito.<MultipartFile>any(), anyLong(), Mockito.<String>any(),
                anyLong());
    }

    @Test
    void testDeleteArticleById() throws Exception {
        when(articleService.deleteArticleById(anyLong())).thenThrow(new IOException("foo"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/api/v1/article/admin/delete/id/{articleId}", "Uri Variables", "Uri Variables");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(articleController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }


    @Test
    void testDeleteArticleByName() throws Exception {
        when(articleService.deleteArticleByName(Mockito.<String>any())).thenThrow(new IOException("foo"));
        SecurityMockMvcRequestBuilders.FormLoginRequestBuilder requestBuilder = SecurityMockMvcRequestBuilders
                .formLogin();
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(articleController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }


    @Test
    void testFetchArticleImageById() throws Exception {
        when(articleService.fetchArticleImage(anyLong())).thenThrow(new IOException("foo"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/article/all/get_article_image/id/{articleId}", "Uri Variables", "Uri Variables");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(articleController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }
}

