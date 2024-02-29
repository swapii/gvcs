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

    override fun version(
        alias: String,
        version: String,
    ) {
        require(alias.isNotBlank())
        require(version.isNotBlank())
        builder.version("$prefix.$alias".removePrefix("."), version)
    }

    override fun plugin(
        id: String,
    ): VersionCatalogBuilder.PluginAliasBuilder {
        require(id.isNotBlank())
        return builder.plugin(prefix, id)
    }

    override fun plugin(
        alias: String,
        id: String,
    ): VersionCatalogBuilder.PluginAliasBuilder {
        require(alias.isNotBlank())
        require(id.isNotBlank())
        return builder.plugin("$prefix.$alias".removePrefix("."), id)
    }

    override fun library(
        groupArtifactVersion: String,
    ) {
        require(groupArtifactVersion.isNotBlank())
        val (group, artifact, version) =
            groupArtifactVersion.split(":")
                .let {
                    if (it.size == 3) {
                        it
                    } else {
                        it + ":"
                    }
                }
        builder.library(prefix, group, artifact)
            .apply {
                if (version.isBlank()) {
                    withoutVersion()
                } else {
                    version(version)
                }
            }
    }

    override fun library(
        groupArtifact: String,
        build: VersionCatalogBuilder.LibraryAliasBuilder.() -> Unit,
    ) {
        require(groupArtifact.isNotBlank())
        val (group, artifact) = groupArtifact.split(":")
        builder.library(prefix, group, artifact).build()
    }

    override fun library(
        alias: String,
        groupArtifactVersion: String,
    ) {
        require(alias.isNotBlank())
        require(groupArtifactVersion.isNotBlank())
        val (group, artifact, version) =
            groupArtifactVersion.split(":")
                .let {
                    if (it.size == 3) {
                        it
                    } else {
                        it + ":"
                    }
                }
        builder.library("$prefix.$alias".removePrefix("."), group, artifact)
            .apply {
                if (version.isBlank()) {
                    withoutVersion()
                } else {
                    version(version)
                }
            }
    }

    override fun library(
        alias: String,
        groupArtifact: String,
        build: VersionCatalogBuilder.LibraryAliasBuilder.() -> Unit,
    ) {
        require(alias.isNotBlank())
        require(groupArtifact.isNotBlank())
        val (group, artifact) = groupArtifact.split(":")
        builder.library("$prefix.$alias".removePrefix("."), group, artifact).build()
    }

    private fun getChildGroupPrefix(prefix: String) =
        if (this.prefix.isBlank()) {
            prefix
        } else {
            "${this.prefix}.$prefix"
        }

}
