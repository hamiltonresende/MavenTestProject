package br.edu.maventestproject;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;

import org.junit.Test;

import br.edu.maventestproject.persistence.DAO;

// As classes estaticas devem ser importadas manualmente
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

public class FooArchitectTest {
    // Define os pacotes sujeitos a realizacao dos testes
    JavaClasses importedClasses = new ClassFileImporter().importPackages("br.edu.maventestproject");
    
    // Define que é um metodo de teste, logo significa que é uma classe que pode rodar testes
    @Test
    public void verificarDependenciaParaCamadaPersistencia(){
        /* ArchRule serve para definir as regras */

        /* Para as classes que estao no pacote persistencia, devem apenas ter dependencias 
           que residem nos pacotes persistencia e servicos */
        ArchRule rule = classes().that().resideInAPackage("..persistence..")
        .should().onlyHaveDependentClassesThat().resideInAnyPackage("..persistence..", "..service..");

        // Faz o check nas classes selecionadas do projeto de acordo com a regra criada
        rule.check(importedClasses);
    }

    @Test
    public void verificarDependenciaDaCamadaPersistencia(){

        /* Nenhuma classe que esteja no pacote de persistencia pode depender
           de outra classe que esteja no pacote de servicos */
           ArchRule rule = noClasses().that().resideInAPackage("..persistence..")
           .should().dependOnClassesThat().resideInAnyPackage("..service..");

        rule.check(importedClasses);
    }

    @Test
    public void verificarNomesClassesCamadasPersistencia(){

        /* Classes que tem o nome terminando com DAO devem residir no pacote de persistencia */
        ArchRule rule = classes().that().haveSimpleNameEndingWith("DAO")
        .should().resideInAPackage("..persistence..");

        rule.check(importedClasses);
    }

    @Test
    public void verificarImplementacaoInterfaceDAO(){

        /* Classes que implementam a interface DAO devem ter o nome terminando em DAO */
        ArchRule rule = classes().that().implement(DAO.class)
        .should().haveSimpleNameEndingWith("DAO");

        rule.check(importedClasses);
    }

    @Test
    public void verificarDependenciasCiclicas(){

        /* Qualquer classe dentro dessas fatias devem ser livres de dependencias ciclicas */
        ArchRule rule = slices().matching("br.edu.maventestproject.(*)..").should().beFreeOfCycles();

        rule.check(importedClasses);
    }

}
