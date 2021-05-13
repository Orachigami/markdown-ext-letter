package com.utp1.markdown.renderer

import com.utp1.markdown.node.UtpInfoNode
import com.utp1.markdown.node.UtpVariableNode
import com.utp1.markdown.node.UtpWarnNode
import org.commonmark.node.Node
import org.commonmark.renderer.NodeRenderer

abstract class UtpNodeRenderer : NodeRenderer {
    companion object {
        const val VARIABLE_FORMAT = "{{data.%s}}"
    }

    override fun getNodeTypes(): Set<Class<out Node>> {
        return setOf(
            UtpVariableNode::class.java,
            UtpInfoNode::class.java,
            UtpWarnNode::class.java
        )
    }

    fun render(node: UtpVariableNode): String {
        return String.format(VARIABLE_FORMAT, node.name)
    }
}
