package com.utp1.markdown

import com.utp1.markdown.internal.UtpWarnPostProcessor
import com.utp1.markdown.renderer.UtpImageTextNodeRenderer
import com.utp1.markdown.renderer.UtpLinkTextNodeRenderer
import com.utp1.markdown.renderer.UtpWarnHtmlNodeRenderer
import com.utp1.markdown.renderer.UtpWarnTextNodeRenderer
import org.commonmark.Extension
import org.commonmark.parser.Parser
import org.commonmark.renderer.html.HtmlRenderer
import org.commonmark.renderer.text.TextContentRenderer

class UtpWarnExtension private constructor() :
    Parser.ParserExtension,
    HtmlRenderer.HtmlRendererExtension,
    TextContentRenderer.TextContentRendererExtension {

    companion object {
        fun create(): Extension {
            return UtpWarnExtension()
        }
    }

    override fun extend(parserBuilder: Parser.Builder) {
        parserBuilder.postProcessor(UtpWarnPostProcessor())
    }

    override fun extend(rendererBuilder: HtmlRenderer.Builder) {
        rendererBuilder.escapeHtml(true)
        rendererBuilder.nodeRendererFactory { UtpWarnHtmlNodeRenderer(it) }
    }

    override fun extend(rendererBuilder: TextContentRenderer.Builder) {
        rendererBuilder.nodeRendererFactory { UtpWarnTextNodeRenderer(it) }
        rendererBuilder.nodeRendererFactory { UtpLinkTextNodeRenderer(it) }
        rendererBuilder.nodeRendererFactory { UtpImageTextNodeRenderer(it) }
    }
}
