package com.utp1.markdown.renderer

import org.commonmark.node.AbstractVisitor
import org.commonmark.node.Image
import org.commonmark.node.Node
import org.commonmark.renderer.NodeRenderer
import org.commonmark.renderer.text.TextContentNodeRendererContext
import org.commonmark.renderer.text.TextContentWriter

class UtpImageTextNodeRenderer(context: TextContentNodeRendererContext) : AbstractVisitor(), NodeRenderer {
    private val writer: TextContentWriter = context.writer

    override fun getNodeTypes(): Set<Class<out Node>> {
        return setOf(
            Image::class.java
        )
    }

    override fun render(node: Node) {
        node.accept(this)
    }

    override fun visit(image: Image) {
        writer.write("[${image.destination}]")
    }
}
