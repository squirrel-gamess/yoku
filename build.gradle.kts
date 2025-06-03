import korlibs.korge.gradle.*
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

plugins {
    alias(libs.plugins.korge)
}

korge {
    id = "com.sample.demo"

// To enable all targets at once

    //targetAll()

// To enable targets based on properties/environment variables
    //targetDefault()

// To selectively enable targets

    targetJvm()
    targetJs()
    targetWasm()
    targetDesktop()
    targetIos()
    targetAndroid()

    serializationJson()

    // Android está desabilitado ao não chamar targetAndroid()
}

// Adicionar referência ao módulo local korge-tiled
dependencies {
    add("commonMainApi", project(":korge-tiled"))
}

// Configuração para o source set concurrentMain
afterEvaluate {
    extensions.findByType<KotlinMultiplatformExtension>()?.let { kotlin ->
        kotlin.sourceSets.findByName("concurrentMain")?.dependsOn(
            kotlin.sourceSets.getByName("commonMain")
        )
    }
}

