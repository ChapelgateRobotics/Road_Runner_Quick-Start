package com.acmerobotics.splinelib

import com.acmerobotics.splinelib.trajectory.DriveConstraints
import java.awt.GridLayout
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextField
import javax.swing.SwingConstants

class ConstraintsPanel : JPanel() {
    private class MutableDriveConstraints(
            var maximumVelocity: Double,
            var maximumAcceleration: Double,
            var maximumAngularVelocity: Double,
            var maximumAngularAcceleration: Double,
            var maximumCentripetalAcceleration: Double
    ) {
        constructor(constraints: DriveConstraints): this(
                constraints.maximumVelocity,
                constraints.maximumAcceleration,
                constraints.maximumAngularVelocity,
                constraints.maximumAngularAcceleration,
                constraints.maximumCentripetalAcceleration
        )

        fun immutable(): DriveConstraints = DriveConstraints(
                maximumVelocity,
                maximumAcceleration,
                maximumAngularVelocity,
                maximumAngularAcceleration,
                maximumCentripetalAcceleration
        )
    }

    private val maxVelTextField: JTextField
    private val maxAccelTextField: JTextField
    private val maxAngVelTextField: JTextField
    private val maxAngAccelTextField: JTextField
    private val maxCentripetalAccelTextField: JTextField

    private var mutableConstraints: MutableDriveConstraints = MutableDriveConstraints(0.0, 0.0, 0.0, 0.0, 0.0)

    var onConstraintsUpdateListener: ((DriveConstraints) -> Unit)? = null

    init {
        val panel = JPanel()
        panel.layout = GridLayout(0, 2, 5, 5)

        panel.add(JLabel("Max Velocity", SwingConstants.RIGHT))
        maxVelTextField = JTextField()
        maxVelTextField.addChangeListener {
            mutableConstraints.maximumVelocity = maxVelTextField.text.toDoubleOrNull() ?: mutableConstraints.maximumVelocity
            fireUpdate()
        }
        panel.add(maxVelTextField)

        panel.add(JLabel("Max Accel", SwingConstants.RIGHT))
        maxAccelTextField = JTextField()
        maxAccelTextField.addChangeListener {
            mutableConstraints.maximumAcceleration = maxAccelTextField.text.toDoubleOrNull() ?: mutableConstraints.maximumAcceleration
            fireUpdate()
        }
        panel.add(maxAccelTextField)

        panel.add(JLabel("Max Ang Velocity", SwingConstants.RIGHT))
        maxAngVelTextField = JTextField()
        maxAngVelTextField.addChangeListener {
            mutableConstraints.maximumAngularVelocity = maxAngVelTextField.text.toDoubleOrNull()?.toRadians() ?: mutableConstraints.maximumAngularVelocity
            fireUpdate()
        }
        panel.add(maxAngVelTextField)

        panel.add(JLabel("Max Ang Accel", SwingConstants.RIGHT))
        maxAngAccelTextField = JTextField()
        maxAngAccelTextField.addChangeListener {
            mutableConstraints.maximumAngularAcceleration = maxAngAccelTextField.text.toDoubleOrNull()?.toRadians() ?: mutableConstraints.maximumAngularAcceleration
            fireUpdate()
        }
        panel.add(maxAngAccelTextField)

        panel.add(JLabel("Max Centripetal Accel", SwingConstants.RIGHT))
        maxCentripetalAccelTextField = JTextField()
        maxCentripetalAccelTextField.addChangeListener {
            mutableConstraints.maximumCentripetalAcceleration = maxCentripetalAccelTextField.text.toDoubleOrNull() ?: mutableConstraints.maximumCentripetalAcceleration
            fireUpdate()
        }
        panel.add(maxCentripetalAccelTextField)

        add(panel)
    }

    fun fireUpdate() {
        onConstraintsUpdateListener?.invoke(mutableConstraints.immutable())
    }

    fun updateConstraints(constraints: DriveConstraints) {
        this.mutableConstraints = MutableDriveConstraints(constraints)

        maxVelTextField.text = String.format("%.2f", constraints.maximumVelocity)
        maxAccelTextField.text = String.format("%.2f", constraints.maximumAcceleration)
        maxAngVelTextField.text = String.format("%.2f", constraints.maximumAngularVelocity.toDegrees())
        maxAngAccelTextField.text = String.format("%.2f", constraints.maximumAngularAcceleration.toDegrees())
        maxCentripetalAccelTextField.text = String.format("%.2f", constraints.maximumCentripetalAcceleration)
    }
}