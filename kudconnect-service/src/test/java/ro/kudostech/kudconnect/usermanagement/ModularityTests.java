package ro.kudostech.kudconnect.usermanagement;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.docs.Documenter;
import ro.kudostech.kudconnect.Application;
import org.springframework.modulith.core.ApplicationModules;

/** Tests to verify the modular structure and generate documentation for the modules. */
class ModularityTests {
  ApplicationModules modules = ApplicationModules.of(Application.class);

  @Test
  void verifiesModularStructure() {
    modules.verify();
  }

  @Test
  void createModuleDocumentation() {
    ApplicationModules modules = ApplicationModules.of(Application.class);
    new Documenter(modules)
            .writeDocumentation()
            .writeModuleCanvases();
  }
}
