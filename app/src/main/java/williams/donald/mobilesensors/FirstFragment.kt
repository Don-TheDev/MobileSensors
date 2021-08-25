package williams.donald.mobilesensors

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.ScrollView
import androidx.core.view.get
import androidx.navigation.fragment.findNavController
import williams.donald.mobilesensors.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
        populateSensorsRadioGroup()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun populateSensorsRadioGroup() {
        val sensorManager = requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        sensors.forEach {
            val radioButton = RadioButton(context)
            radioButton.setText(it.name)
            binding.radiogroupSensors.addView(radioButton)}
        if (sensors.size > 0) {
            (binding.radiogroupSensors[0] as RadioButton).isChecked = true
        }
    }
}