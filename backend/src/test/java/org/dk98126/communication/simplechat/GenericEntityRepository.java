package org.dk98126.communication.simplechat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = GenericEntity.class, idClass = Long.class)
public interface GenericEntityRepository extends JpaRepository<GenericEntity, Long> {

}
