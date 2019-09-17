package org.dk98126.communication.simplechat.repository;

import org.dk98126.communication.simplechat.domain.Message;
import org.springframework.data.repository.CrudRepository;

public interface MessagesRepo extends CrudRepository<Message, Long> {

}
