package gvcs

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

    fun library(
        groupArtifactVersion: String,
    )

    fun library(
        alias: String,
        groupArtifactVersion: String,
    )

}