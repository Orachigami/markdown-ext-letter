package com.utp1.markdown

import com.utp1.markdown.renderer.UtpInfoTitleHtmlNodeRenderer
import org.commonmark.Extension
import org.commonmark.renderer.html.HtmlRenderer

class UtpInfoTitleDecoratorExtension private constructor() : HtmlRenderer.HtmlRendererExtension {
    companion object {
        fun create(): Extension {
            return UtpInfoTitleDecoratorExtension()
        }
    }

    override fun extend(rendererBuilder: HtmlRenderer.Builder) {
        rendererBuilder.escapeHtml(true)
        rendererBuilder.nodeRendererFactory { UtpInfoTitleHtmlNodeRenderer(it) }
    }
}
