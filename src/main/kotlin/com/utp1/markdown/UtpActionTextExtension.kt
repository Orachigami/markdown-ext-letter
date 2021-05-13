package com.utp1.markdown

import com.utp1.markdown.renderer.UtpActionTextHtmlNodeRenderer
import com.utp1.markdown.renderer.UtpActionTextTextNodeRenderer
import org.commonmark.Extension
import org.commonmark.renderer.html.HtmlRenderer
import org.commonmark.renderer.text.TextContentRenderer

class UtpActionTextExtension private constructor() :
    HtmlRenderer.HtmlRendererExtension,
    TextContentRenderer.TextContentRendererExtension {

    companion object {
        fun create(): Extension {
            return UtpActionTextExtension()
        }
    }

    override fun extend(rendererBuilder: HtmlRenderer.Builder) {
        rendererBuilder.escapeHtml(true)
        rendererBuilder.nodeRendererFactory { UtpActionTextHtmlNodeRenderer(it) }
    }

    override fun extend(rendererBuilder: TextContentRenderer.Builder) {
        rendererBuilder.nodeRendererFactory { UtpActionTextTextNodeRenderer(it) }
    }
}
