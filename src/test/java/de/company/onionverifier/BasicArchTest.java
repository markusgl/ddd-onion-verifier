package de.company.onionverifier;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Service;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;


public class BasicArchTest {
    private JavaClasses classes;
    private final String BASE_PACKAGE_NAME = "de.company.onionverifier";

    @BeforeEach
    public void setup() {
        classes = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages(BASE_PACKAGE_NAME);
    }

    @Test
    public void serviceClassesShouldBeAnnotatedWithService() {
        classes()
                .that().haveNameMatching(".*Service")
                .should().beAnnotatedWith(Service.class)
                .check(classes);
    }

    @Test
    public void serviceClassesShouldHaveNameEndingService() {
        classes()
                .that().areAnnotatedWith(Service.class)
                .should().haveSimpleNameEndingWith("Service")
                .check(classes);
    }
}
