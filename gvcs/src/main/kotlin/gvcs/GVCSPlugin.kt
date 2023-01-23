package gvcs

import org.gradle.api.Plugin
import org.gradle.api.initialization.Settings
import org.gradle.api.initialization.dsl.VersionCatalogBuilder
import org.gradle.api.initialization.resolve.MutableVersionCatalogContainer

class GVCSPlugin: Plugin<Settings> {

    override fun apply(settings: Settings) {
    }

}

fun MutableVersionCatalogContainer.catalog(name: String, setup: CatalogGroupContext.() -> Unit) {
    CatalogGroupContextImpl(
        prefix = "",
        builder = create(name),
    ).setup()
}

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

interface CatalogGroupContextWithVersion : CatalogGroupContext {

    val version: String

}

class CatalogGroupContextImpl(
    val prefix: String = "",
    override val version: String = "",
    private val builder: VersionCatalogBuilder,
) : CatalogGroupContextWithVersion {

    init {
        version
            .takeIf { it.isNotBlank() }
            ?.let {
                builder.version(prefix, it)
            }
    }

    override fun group(
        prefix: String,
        setup: CatalogGroupContext.() -> Unit,
    ) {
        require(prefix.isNotBlank())
        CatalogGroupContextImpl(
            prefix = getChildGroupPrefix(prefix),
            builder = builder,
        ).setup()
    }

    override fun group(
        prefix: String,
        version: String,
        setup: CatalogGroupContextWithVersion.() -> Unit
    ) {
        require(prefix.isNotBlank())
        require(version.isNotBlank())
        CatalogGroupContextImpl(
            prefix = getChildGroupPrefix(prefix),
            version = version,
            builder = builder,
        ).setup()
    }

    override fun version(alias: String, version: String) {
        require(alias.isNotBlank())
        require(version.isNotBlank())
        builder.version("$prefix.$alias".removePrefix("."), version)
    }

    override fun library(groupArtifactVersion: String) {
        require(groupArtifactVersion.isNotBlank())
        builder.library(prefix, groupArtifactVersion)
    }

    override fun library(alias: String, groupArtifactVersion: String) {
        require(alias.isNotBlank())
        require(groupArtifactVersion.isNotBlank())
        builder.library("$prefix.$alias".removePrefix("."), groupArtifactVersion)
    }

    private fun getChildGroupPrefix(prefix: String) =
        if (this.prefix.isBlank()) {
            prefix
        } else {
            "${this.prefix}.$prefix"
        }

}
