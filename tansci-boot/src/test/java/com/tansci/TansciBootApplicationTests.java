package com.tansci;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.IdentityService;

@SpringBootTest(properties = {
    "spring.autoconfigure.exclude=org.flowable.spring.boot.ProcessEngineAutoConfiguration,org.flowable.spring.boot.IdmEngineAutoConfiguration,org.flowable.spring.boot.AppEngineAutoConfiguration,org.flowable.spring.boot.ProcessEngineServicesAutoConfiguration,org.flowable.spring.boot.IdmEngineServicesAutoConfiguration,org.flowable.spring.boot.AppEngineServicesAutoConfiguration"
})
@ActiveProfiles("test")
class TansciBootApplicationTests {

    @MockBean
    private RepositoryService repositoryService;

    @MockBean
    private RuntimeService runtimeService;

    @MockBean
    private TaskService taskService;

    @MockBean
    private IdentityService identityService;

    @Test
    void contextLoads() {
    }

}
