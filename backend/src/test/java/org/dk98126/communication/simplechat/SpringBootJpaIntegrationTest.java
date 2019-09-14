package org.dk98126.communication.simplechat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SimpleChatApplication.class)
public class SpringBootJpaIntegrationTest {
    @Autowired
    private GenericEntityRepository genericEntityRepository;

    /**
     * Тест заносит в таблицу новый объект, получает оттуда этот же объект и сравнивает значения.
     * Тем самым проверяется доступность БД, и что спринг правильно инжектит репозитории.
     */
    @Test
    public void givenGenericEntityRepositoryWhenSaveAndRetrieveEntityThenOK() {
        GenericEntity genericEntity = genericEntityRepository.save(new GenericEntity("test"));
        GenericEntity foundEntity = genericEntityRepository.findById(genericEntity.getId()).orElse(null);
        assertNotNull(foundEntity);
        assertEquals(genericEntity.getValue(), genericEntity.getValue());
    }
}
