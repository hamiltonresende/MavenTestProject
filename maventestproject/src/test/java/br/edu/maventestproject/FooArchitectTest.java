package br.edu.maventestproject;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;

import org.junit.Test;

// A classe estatica classes deve ser importada manualmente
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

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

}
