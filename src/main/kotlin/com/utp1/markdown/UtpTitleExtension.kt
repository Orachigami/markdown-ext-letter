package com.utp1.markdown

import com.utp1.markdown.renderer.UtpTitleHtmlNodeRenderer
import com.utp1.markdown.renderer.UtpTitleTextNodeRenderer
import org.commonmark.Extension
import org.commonmark.renderer.html.HtmlRenderer
import org.commonmark.renderer.text.TextContentRenderer

class UtpTitleExtension private constructor() :
    HtmlRenderer.HtmlRendererExtension,
    TextContentRenderer.TextContentRendererExtension {
    companion object {
        fun create(): Extension {
            return UtpTitleExtension()
        }
    }

    override fun extend(rendererBuilder: HtmlRenderer.Builder) {
        rendererBuilder.escapeHtml(true)
        rendererBuilder.nodeRendererFactory { UtpTitleHtmlNodeRenderer(it) }
    }

    override fun extend(rendererBuilder: TextContentRenderer.Builder) {
        rendererBuilder.nodeRendererFactory { UtpTitleTextNodeRenderer(it) }
    }
}
