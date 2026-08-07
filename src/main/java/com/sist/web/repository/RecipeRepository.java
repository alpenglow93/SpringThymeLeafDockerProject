package com.sist.web.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sist.web.entity.Recipe;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
	public Page<Recipe> findByTitleContains(String title, Pageable page);
	/*
	 * 	SELECT *
	 * 	FROM recipe
	 * 	WHERE title LIKE '%데이터%'
	 * 	OFFSET ? ROWS FETCH NEXT ? ROWS ONLY
	 * 
	 */
	public Page<Recipe> findByChefContains(String chef, Pageable page);
	public long countByTitleContains(String title);
	/*
	 * 	SELECT COUNT(*)
	 * 	FROM recipe
	 * 	WHERE title LIKE '%데이터%'
	 */
	public long countByChefContains(String chef);
	/*
	 * 	findBy컬럼명연산자
	 * 	findByName(String name)
	 * 	=> WHERE name=? =======> equals
	 * 	findByTitleStartsWith(String title)
	 * 		=> WHERE title LIKE 'title%'
	 * 	findByTitleEndsWith(String title)
	 * 		=> WHERE title LIKE '%title'
	 * 	findByTitleContainsWith(String title)
	 * 		=> WHERE title LIKE '%title%'
	 * 	findByOrderByTitleDesc()
	 * 		=> ORDER BY title DESC
	 * 
	 * 	findAll(Pageable, Sort)
	 * 	count()
	 * 	save() / delete()
	 */
	// 상세보기가 존재하는걸 가져오기 위한 쿼리 => 교집합(intersect) 사용
	@Query(value="""
			SELECT *
			FROM recipe
			WHERE no IN(SELECT no FROM recipe
						INTERSECT
						SELECT no FROM recipeDetail)
			ORDER BY no DESC
			OFFSET :start ROWS FETCH NEXT 12 ROWS ONLY
			""", nativeQuery=true)
	public List<Recipe> recipeListData(@Param("start") int start);
	
	@Query(value="""
			SELECT count(*)
			FROM recipe
			WHERE no IN(SELECT no FROM recipe
						INTERSECT
						SELECT no FROM recipeDetail)
			
			""", nativeQuery=true)
	public int recipeCount();
	
}
