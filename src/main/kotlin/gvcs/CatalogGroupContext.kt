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
        alias: String,
        groupArtifactVersion: String,
    )

}
