package br.edu.maventestproject;

import static org.junit.Assert.assertTrue;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;

import org.junit.Test;

public class FooArchitectTest {
    // Define os pacotes sujeitos a realizacao dos testes
    JavaClasses importedClasses = new ClassFileImporter().importPackages("br.edu.maventestproject");
    
    // Define que é um metodo de teste, logo significa que é uma classe que pode rodar testes
    @Test
    public void verificarDependenciaParaCamadaPersistencia(){
        
    }

}
