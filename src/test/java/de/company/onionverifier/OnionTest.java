package de.company.onionverifier;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.Architectures;
import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


@AnalyzeClasses(
        packages = "de.company.onionverifier",
        importOptions = ImportOption.DoNotIncludeTests.class
)
public class OnionTest {

    @ArchTest
    ArchRule adaptersShouldNotDependOnEachOther =
            SlicesRuleDefinition.slices().matching("..adapter.(*)..").should().notDependOnEachOther();

    @ArchTest
    ArchRule onionArchitecture =
            Architectures.layeredArchitecture()
                    .layer("infrastructure").definedBy("..infrastructure..")
                    .layer("domain").definedBy("..domain..")
                    .layer("application").definedBy("..application..")
                    .whereLayer("infrastructure").mayNotBeAccessedByAnyLayer()
                    .whereLayer("application").mayOnlyBeAccessedByLayers("infrastructure");
}
