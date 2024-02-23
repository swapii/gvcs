package gvcs

import org.gradle.api.initialization.dsl.VersionCatalogBuilder

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

    override fun plugin(id: String, version: String?) {
        require(id.isNotBlank())
        builder.plugin(prefix, id)
            .apply {
                if (!version.isNullOrBlank()) {
                    version(version)
                }
            }
    }

    override fun plugin(alias: String, id: String, version: String?) {
        require(alias.isNotBlank())
        require(id.isNotBlank())
        builder.plugin("$prefix.$alias".removePrefix("."), id)
            .apply {
                if (!version.isNullOrBlank()) {
                    version(version)
                }
            }
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
