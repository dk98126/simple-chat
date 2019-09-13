package org.dk98126.communication.simplechat.repository;

import org.dk98126.communication.simplechat.user.WebUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.List;

@RepositoryDefinition(idClass = Long.class, domainClass = WebUser.class)
public interface WebUserRepo extends JpaRepository<WebUser, Long> {
    List<WebUser> findByUsername(String login);
    List<WebUser> findByEmail(String email);
}
