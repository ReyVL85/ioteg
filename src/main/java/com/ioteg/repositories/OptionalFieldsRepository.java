package com.ioteg.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ioteg.model.Field;
import com.ioteg.model.OptionalFields;
import com.ioteg.model.User;

@Repository
public interface OptionalFieldsRepository extends CrudRepository<OptionalFields, Long>{
	@Query("SELECT o.owner FROM OptionalFields o WHERE o.id = :id")
	public Optional<User> findOwner(@Param("id") Long id);
	
	@Query("SELECT o.fields FROM OptionalFields o WHERE o.id = :id")
	public List<Field> findAllFieldsOf(@Param("id") Long id);
	
	@Query("SELECT o FROM OptionalFields o LEFT JOIN FETCH o.fields WHERE o.id = :id")
	public Optional<OptionalFields> findByIdWithFields(@Param("id") Long id);
}
