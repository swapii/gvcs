import gvcs.CatalogGroupContext
import gvcs.CatalogGroupContextImpl
import org.gradle.api.initialization.resolve.MutableVersionCatalogContainer

@Suppress("unused")
fun MutableVersionCatalogContainer.catalog(
    name: String,
    setup: CatalogGroupContext.() -> Unit,
) {
    CatalogGroupContextImpl(
        prefix = "",
        builder = create(name),
    )
        .setup()
}
