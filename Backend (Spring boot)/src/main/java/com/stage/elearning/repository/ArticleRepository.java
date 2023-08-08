package com.stage.elearning.repository;

import com.stage.elearning.model.acticle.Article;
import com.stage.elearning.model.categories.Categorie;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface ArticleRepository  extends JpaRepository<Article , Integer> {


    @Query(value = "SELECT A FROM Article A WHERE A.Id = :articleId")
    Optional<Article> fetchArticleById(@Param("articleId") final long articleId);
    @Query(value = "SELECT A FROM Article A WHERE A.name = :articleName")
    Optional<Article> fetchArticleByName(@Param("articleName") final String articleName);

    @Query(value = "SELECT A FROM Article  A")
    List<Article> fetchAllArticles();

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Article A WHERE A.name = :articleName")
    void deleteArticleByName(@Param("articleName") final String articleName);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Article A WHERE A.id = :articleId")
    void deleteArticleById(@Param("articleId") final long articleId);

}
