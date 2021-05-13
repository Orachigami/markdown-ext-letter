package com.utp1.markdown.renderer

import com.utp1.markdown.node.UtpInfoNode
import com.utp1.markdown.node.UtpVariableNode
import com.utp1.markdown.node.UtpWarnNode
import org.commonmark.node.Node
import org.commonmark.renderer.text.TextContentNodeRendererContext
import org.commonmark.renderer.text.TextContentWriter

class UtpTextNodeRenderer(context: TextContentNodeRendererContext) : UtpNodeRenderer() {
    private val writer: TextContentWriter = context.writer

    override fun render(node: Node) {
        when (node) {
            is UtpWarnNode -> renderWarnNode()
            is UtpInfoNode -> renderInfoNode()
            is UtpVariableNode -> writer.write(render(node))
            else -> return
        }
    }

    private fun renderWarnNode() {
        writer.write("{{warnText}}")
    }


    private fun renderInfoNode() {
        writer.write("{{infoText}}")
    }
}
