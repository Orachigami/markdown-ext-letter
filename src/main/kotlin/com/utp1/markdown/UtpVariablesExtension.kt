package com.utp1.markdown

import com.utp1.markdown.internal.UtpAttributeProvider
import com.utp1.markdown.internal.UtpParagraphPostProcessor
import com.utp1.markdown.internal.UtpVariablePostProcessor
import com.utp1.markdown.renderer.UtpHtmlNodeRenderer
import com.utp1.markdown.renderer.UtpImageTextNodeRenderer
import com.utp1.markdown.renderer.UtpLinkTextNodeRenderer
import com.utp1.markdown.renderer.UtpTextNodeRenderer
import org.commonmark.Extension
import org.commonmark.parser.Parser
import org.commonmark.renderer.html.HtmlRenderer
import org.commonmark.renderer.text.TextContentRenderer

class UtpVariablesExtension private constructor(
    private val defaultValues: HashMap<String, String>
) :
    Parser.ParserExtension,
    HtmlRenderer.HtmlRendererExtension,
    TextContentRenderer.TextContentRendererExtension {

    companion object {
        fun create(defaultValues: HashMap<String, String>): Extension {
            return UtpVariablesExtension(defaultValues)
        }
    }

    override fun extend(parserBuilder: Parser.Builder) {
        parserBuilder.postProcessor(UtpVariablePostProcessor(defaultValues))
        parserBuilder.postProcessor(UtpParagraphPostProcessor())
    }

    override fun extend(rendererBuilder: HtmlRenderer.Builder) {
        rendererBuilder.escapeHtml(true)
        rendererBuilder.attributeProviderFactory { UtpAttributeProvider(defaultValues, it) }
        rendererBuilder.nodeRendererFactory { UtpHtmlNodeRenderer(it) }
    }

    override fun extend(rendererBuilder: TextContentRenderer.Builder) {
        rendererBuilder.nodeRendererFactory { UtpTextNodeRenderer(it) }
        rendererBuilder.nodeRendererFactory { UtpLinkTextNodeRenderer(it) }
        rendererBuilder.nodeRendererFactory { UtpImageTextNodeRenderer(it) }
    }
}
