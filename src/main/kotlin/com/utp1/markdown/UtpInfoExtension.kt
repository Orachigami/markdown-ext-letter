package com.utp1.markdown

import com.utp1.markdown.renderer.UtpInfoHtmlNodeRenderer
import com.utp1.markdown.renderer.UtpInfoTextNodeRenderer
import org.commonmark.Extension
import org.commonmark.renderer.html.HtmlRenderer
import org.commonmark.renderer.text.TextContentRenderer

class UtpInfoExtension private constructor() :
    HtmlRenderer.HtmlRendererExtension,
    TextContentRenderer.TextContentRendererExtension {

    companion object {
        fun create(): Extension {
            return UtpInfoExtension()
        }
    }

    override fun extend(rendererBuilder: HtmlRenderer.Builder) {
        rendererBuilder.escapeHtml(true)
        rendererBuilder.nodeRendererFactory { UtpInfoHtmlNodeRenderer(it) }
    }

    override fun extend(rendererBuilder: TextContentRenderer.Builder) {
        rendererBuilder.nodeRendererFactory { UtpInfoTextNodeRenderer(it) }
    }
}
