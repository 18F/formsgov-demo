package gov.gsa.faas;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("gov.gsa.faas");

        noClasses()
            .that()
                .resideInAnyPackage("gov.gsa.faas.service..")
            .or()
                .resideInAnyPackage("gov.gsa.faas.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..gov.gsa.faas.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
