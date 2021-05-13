package com.utp1.markdown

import com.utp1.markdown.renderer.UtpActionButtonHtmlNodeRenderer
import com.utp1.markdown.renderer.UtpActionButtonTextNodeRenderer
import org.commonmark.Extension
import org.commonmark.renderer.html.HtmlRenderer
import org.commonmark.renderer.text.TextContentRenderer

class UtpActionButtonExtension private constructor() :
    HtmlRenderer.HtmlRendererExtension,
    TextContentRenderer.TextContentRendererExtension {

    companion object {
        fun create(): Extension {
            return UtpActionButtonExtension()
        }
    }

    override fun extend(rendererBuilder: HtmlRenderer.Builder) {
        rendererBuilder.escapeHtml(true)
        rendererBuilder.nodeRendererFactory { UtpActionButtonHtmlNodeRenderer(it) }
    }

    override fun extend(rendererBuilder: TextContentRenderer.Builder) {
        rendererBuilder.nodeRendererFactory { UtpActionButtonTextNodeRenderer(it) }
    }
}
