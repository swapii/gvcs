package gvcs

import org.gradle.api.initialization.dsl.VersionCatalogBuilder

interface CatalogGroupContext {

    fun group(
        prefix: String,
        setup: CatalogGroupContext.() -> Unit,
    )

    fun group(
        prefix: String,
        version: String,
        setup: CatalogGroupContextWithVersion.() -> Unit,
    )

    fun version(
        alias: String,
        version: String,
    )

    fun plugin(
        id: String,
        version: String? = null,
    )

    fun plugin(
        alias: String,
        id: String,
        version: String? = null,
    )

    fun library(
        groupArtifactVersion: String,
    )

    fun library(
        groupArtifact: String,
        build: VersionCatalogBuilder.LibraryAliasBuilder.() -> Unit,
    )

    fun library(
        alias: String,
        groupArtifactVersion: String,
    )

    fun library(
        alias: String,
        groupArtifact: String,
        build: VersionCatalogBuilder.LibraryAliasBuilder.() -> Unit,
    )

}
