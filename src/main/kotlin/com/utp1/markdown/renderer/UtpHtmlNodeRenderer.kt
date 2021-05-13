package com.utp1.markdown.renderer

import com.utp1.markdown.node.UtpInfoNode
import com.utp1.markdown.node.UtpVariableNode
import com.utp1.markdown.node.UtpWarnNode
import org.commonmark.node.Node
import org.commonmark.renderer.html.HtmlNodeRendererContext
import org.commonmark.renderer.html.HtmlWriter

class UtpHtmlNodeRenderer(context: HtmlNodeRendererContext) : UtpNodeRenderer() {
    private val writer: HtmlWriter = context.writer

    override fun render(node: Node) {
        when (node) {
            is UtpWarnNode -> renderWarnNode()
            is UtpInfoNode -> renderInfoNode()
            is UtpVariableNode -> writer.text(render(node))
            else -> return
        }
    }

    private fun renderWarnNode() {
        writer.raw("{{warnHtml}}")
    }

    private fun renderInfoNode() {
        writer.raw("{{infoHtml}}")
    }
}
