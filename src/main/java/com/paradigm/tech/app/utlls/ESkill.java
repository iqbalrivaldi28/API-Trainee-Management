package com.paradigm.tech.app.utlls;

import lombok.Getter;

@Getter
public enum ESkill {
    // Programming Languages
    JAVA("Java"),
    PYTHON("Python"),
    JAVASCRIPT("JavaScript"),
    CSHARP("C#"),
    RUBY("Ruby"),
    GOLANG("Go"),
    PHP("PHP"),
    SWIFT("Swift"),
    KOTLIN("Kotlin"),
    SCALA("Scala"),
    TYPESCRIPT("TypeScript"),
    RUST("Rust"),
    DART("Dart"),
    CPLUSPLUS("C++"),
    OBJECTIVE_C("Objective-C"),

    // Frameworks
    SPRING("Spring Framework"),
    DJANGO("Django"),
    FLASK("Flask"),
    RUBY_ON_RAILS("Ruby on Rails"),
    ANGULAR("Angular"),
    REACT("React"),
    VUE("Vue.js"),
    LARAVEL("Laravel"),
    ASP_NET("ASP.NET"),
    EXPRESS("Express.js"),
    HIBERNATE("Hibernate"),
    SYMFONY("Symfony"),
    QUARKUS("Quarkus"),
    PLAY("Play Framework"),
    NEXTJS("Next.js"),
    NESTJS("NestJS"),
    STRUTS("Struts"),
    GIN("Gin"),
    BEVY("Bevy"),
    SVELTE("Svelte"),
    BLAZOR("Blazor");

    private final String skillName;

    // Constructor
    ESkill(String skillName) {
        this.skillName = skillName;
    }


    // Override toString method
    @Override
    public String toString() {
        return skillName;
    }
}