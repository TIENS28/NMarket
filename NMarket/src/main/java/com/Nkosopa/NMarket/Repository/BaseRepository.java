package com.Nkosopa.NMarket.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;


@NoRepositoryBean
public interface BaseRepository<T,ID> extends JpaRepository<T, ID>{

	T findByIDMandatory(ID id) throws IllegalArgumentException;
	
	
}
