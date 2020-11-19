package com.github.grishberg.android.layoutinspector.ui.dialogs

import java.awt.BorderLayout
import java.awt.Frame
import javax.swing.*

class LoadingDialog(owner: Frame) : JDialog(owner, false) {

    init {
        val panel = JPanel()
        panel.layout = BorderLayout()

        val cldr = this.javaClass.classLoader
        val imageURL = cldr.getResource("icons/loading.gif")
        val imageIcon = ImageIcon(imageURL)
        val iconLabel = JLabel()
        iconLabel.setIcon(imageIcon)
        imageIcon.imageObserver = iconLabel

        val label = JLabel("Loading...")
        label.setHorizontalAlignment(JLabel.CENTER);
        iconLabel.setHorizontalAlignment(JLabel.CENTER);
        panel.add(iconLabel, BorderLayout.CENTER)
        panel.add(label, BorderLayout.PAGE_START)
        setContentPane(panel)
        defaultCloseOperation = WindowConstants.DO_NOTHING_ON_CLOSE
        pack()
    }
}