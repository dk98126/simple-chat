package org.dk98126.communication.simplechat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.List;

@RepositoryDefinition(idClass = Long.class, domainClass = WebUser.class)
public interface WebUserRepo extends JpaRepository<WebUser, Long> {
    List<WebUser> findByLogin(String login);
}
