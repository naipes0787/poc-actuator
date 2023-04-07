package com.example.demo.actuator;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class ActuatorTest {

  private final ApplicationContextRunner contextRunner = new ApplicationContextRunner();

  @Autowired
  private MockMvc mockMvc;

  @Test
  void givenLegacySecurity_whenListActuatorEndpoints_thenUnauthorized() throws Exception {
    setLegacySecurityMethod();
    checkResultFromListActuatorEndpointsIsUnauthorized();
  }

  @Test
  void givenSupportedSecurity_whenListActuatorEndpoints_thenUnauthorized() throws Exception {
    setSupportedSecurityMethod();
    checkResultFromListActuatorEndpointsIsUnauthorized();
  }

  @Test
  void givenLegacySecurity_whenGetPrometheusInformation_thenNotFound() throws Exception {
    setLegacySecurityMethod();
    checkResultFromPrometheusEndpointIsNotFound();
  }

  @Test
  void givenSupportedSecurity_whenGetPrometheusInformation_thenNotFound() throws Exception {
    setSupportedSecurityMethod();
    checkResultFromPrometheusEndpointIsNotFound();
  }

  @Test
  void givenLegacySecurity_whenGetHealth_thenOk() throws Exception {
    setLegacySecurityMethod();
    checkResultFromHealthEndpointIsOk();
  }

  @Test
  void givenSupportedSecurity_whenGetHealth_thenOk() throws Exception {
    setSupportedSecurityMethod();
    checkResultFromHealthEndpointIsOk();
  }

  private void setLegacySecurityMethod() {
    contextRunner.withPropertyValues("security.method=legacy");
  }

  private void setSupportedSecurityMethod() {
    contextRunner.withPropertyValues("security.method=supported");
  }

  private void checkResultFromListActuatorEndpointsIsUnauthorized() throws Exception {
    mockMvc.perform(get("/actuator")).andExpect(status().isUnauthorized());
  }

  private void checkResultFromPrometheusEndpointIsNotFound() throws Exception {
    // Not found because there is no Prometheus configured
    mockMvc.perform(get("/actuator/prometheus")).andExpect(status().isNotFound());
  }

  private void checkResultFromHealthEndpointIsOk() throws Exception {
    mockMvc.perform(get("/actuator/health")).andExpect(status().isOk());
  }

}
