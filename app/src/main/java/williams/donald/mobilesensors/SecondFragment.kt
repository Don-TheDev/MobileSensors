package williams.donald.mobilesensors

import android.content.Context
import android.hardware.*
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import williams.donald.mobilesensors.databinding.FragmentSecondBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment(), SensorEventListener {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    // Sensors
    lateinit var sensorManager: SensorManager
    var accelerationValues: FloatArray? = null
    var rotationValues: FloatArray? = null

    // Motion sensors
    lateinit var sensorLinearAcceleration: Sensor
    lateinit var sensorRotationVector: Sensor
    lateinit var sensorLight: Sensor
    //val me = "you"


    // Environmental sensors
    // Position sensors

    //Listeners
    fun addSensorListeners() {
        sensorManager.registerListener(this, sensorLinearAcceleration, SensorManager.SENSOR_DELAY_UI)
        sensorManager.registerListener(this, sensorRotationVector, 0)
        sensorManager.registerListener(this, sensorLight, SensorManager.SENSOR_DELAY_UI)
    }


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

        sensorManager = requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensorLinearAcceleration = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        sensorRotationVector = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        sensorLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        addSensorListeners()
//        updateSensorsViews()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun updateSensorsViews() {
//        binding.textviewAccelerometer.text = sensorLinearAcceleration.toString()
//        binding.textviewGyroscope.text = sensorRotationVector.toString()
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_LINEAR_ACCELERATION) {
            accelerationValues = getSensorValues(event);
//            binding.textviewAccelerometer.text = sensorLinearAcceleration.toString()
            ("Acceleration" +
                    "\nX: ${accelerationValues?.get(0)}" +
                    "\nY: ${accelerationValues?.get(1)}" +
                    "\nZ: ${accelerationValues?.get(2)}")
                .also { binding.textviewAccelerometer.text = it }
        }
        else if (event?.sensor?.type == Sensor.TYPE_ROTATION_VECTOR) {
            rotationValues = getSensorValues(event);
            ("Rotation" +
                    "\nX: ${rotationValues?.get(0)}" +
                    "\nY: ${rotationValues?.get(1)}" +
                    "\nZ: ${rotationValues?.get(2)}")
                .also { binding.textviewGyroscope.text = it }

        }
        else if (event?.sensor?.type == Sensor.TYPE_LIGHT) {
            rotationValues = getSensorValues(event);
            ("Light" +
                    "\nLx: ${rotationValues?.get(0)}")
                .also { binding.textviewPhotometer.text = it }

        }
//        updateSensorsViews();
    }

    override fun onAccuracyChanged(event: Sensor?, accuracy: Int) {
    }

    private fun getSensorValues(event: SensorEvent?): FloatArray? {
        return event?.values;
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onResume() {
        super.onResume()
        addSensorListeners()
    }
}
