package com.builder.microsoftassistant

import android.app.assist.AssistContent
import android.app.assist.AssistStructure
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.service.voice.VoiceInteractionSession
import android.util.Log

class AssistLoggerSession(context: Context?) : VoiceInteractionSession(context) {

    var fullTextBuffer = StringBuffer()
    override fun onHandleAssist(data: Bundle?, structure: AssistStructure?, content: AssistContent?) {
        super.onHandleAssist(data, structure, content)
        fullTextBuffer.delete(0, fullTextBuffer.length)
        dumpStructure(structure)
        Log.d("AssistLoggerSession", "Text = $fullTextBuffer")
        var intent = Intent(context,MainActivity::class.java)
        intent.putExtra("DATA", fullTextBuffer.toString())
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        context.startActivity(intent)
    }

    private fun dumpStructure(structure: AssistStructure?) {
        for (i in 0 until (structure?.windowNodeCount ?: 0)) {
            if (structure != null) {
                dumpStructureWindow(structure.getWindowNodeAt(i))
            }
        }
    }

    private fun dumpStructureWindow(
        window: AssistStructure.WindowNode) {
        fullTextBuffer.append("Title: " + wrap(window.title))
        dumpStructureNode(window.rootViewNode)

    }

    private fun dumpStructureNode(node: AssistStructure.ViewNode?) {
        if (node != null) {
            if (node.className.toLowerCase().contains("textview")) {
                if (node.text != null)
                    fullTextBuffer.append(node.text.toString() + "\n")
            }
        }
        if (node != null) {
            dumpStructureNodes(node)
        }
    }

    private fun dumpStructureNodes(node: AssistStructure.ViewNode) {
        for (i in 0 until node.childCount) {
            dumpStructureNode(node.getChildAt(i))
        }

    }
    private fun wrap(thingy: Any?): Any? {
        return (thingy as? CharSequence)?.toString() ?: thingy

    }
}