package com.sist.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sist.web.entity.Chef;
import com.sist.web.entity.Recipe;
import java.util.*;

public interface ChefRepository extends JpaRepository<Chef, String> {
	
}
