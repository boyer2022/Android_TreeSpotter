package com.android.treespotter

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.Marker
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

// Logging TAG
private const val TAG = "TREE_MAP_FRAGMENT"

class TreeMapFragment : Fragment() {

// Variables for the floating action button
    private lateinit var addTreeButton: FloatingActionButton

    // Knowing where the user is
    private var locationPermissionGranted = false

    // Move the map to the users location, without resetting to the position if the user moves map
    private var movedMapToUsersLocation = false

    // Need to get the users location
    private var fusedLocationProvider: FusedLocationProviderClient? = null

    // Reference to the Google map
    private var map: GoogleMap? = null

    // List to keep track of and display the tree sightings
    private val treeMarkers = mutableListOf<Marker>()

    private var treeList = listOf<Tree>()

    private val treeViewModel: TreeViewModel by lazy {
        ViewModelProvider(requireActivity()).get(TreeViewModel::class.java)
    }

    // Setting up the call-back function
    private val mapReadyCallback = OnMapReadyCallback { googleMap ->
        Log.d(TAG, "Google map ready")
        map = googleMap
        updateMap()
    }

    private fun updateMap() {
       // TODO Draw Markers
        // TODO blue dot at user's location
        // TODO show no location message if location permission not granted.
        // or device does not have location enabled
    }

    private fun setAddTreeButtonEnabled(isEnabled: Boolean) {
        addTreeButton.isClickable = isEnabled
        addTreeButton.isEnabled = isEnabled

        if (isEnabled) {
            addTreeButton.backgroundTintList = AppCompatResources.getColorStateList(requireActivity(),
            android.R.color.holo_green_light)
        } else {
            addTreeButton.backgroundTintList = AppCompatResources.getColorStateList(requireActivity(),
            android.R.color.darker_gray)
        }
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG).show()
    }

    // Function to request location permission
    private fun requestLocationPermission() {
        // has user already granted permissions?
        // if yes...
        if (ContextCompat.checkSelfPermission(requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ) {

            locationPermissionGranted = true
            Log.d(TAG, "Permission already granted")
            updateMap()
        } else {
            // need to ask for permission
                // Launching general permission to Launch activity
            val requestLocationPermissionLauncher = registerForActivityResult(
                ActivityResultContracts.RequestPermission()) {granted ->
                if (granted) {
                    Log.d(TAG, "User granted permission")
                    setAddTreeButtonEnabled(true)
                    fusedLocationProvider = LocationServices.getFusedLocationProviderClient(requireActivity())
                } else {
                    Log.d(TAG, "User did not grant permission")
                    setAddTreeButtonEnabled(false)
                    showSnackbar(getString(R.string.give_permission))
                }
                updateMap()
            }
            // Requesting specific permission for location
            requestLocationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val mainView = inflater.inflate(R.layout.fragment_tree_map, container, false)

        // Initialize addTreeButton/FloatingActionButton
        addTreeButton = mainView.findViewById(R.id.add_tree)
        addTreeButton.setOnClickListener {
            // TODO add tree at user's location - if location permission granted/location available

        }

        val mapFragment = childFragmentManager.findFragmentById(R.id.map_view) as SupportMapFragment?
 // When map is ready- mapReadCallback() function will be called, updateMap() called
        mapFragment?.getMapAsync(mapReadyCallback)
        //  request user's permission to access device location
        requestLocationPermission()

        // disable add tree button until location is known
        setAddTreeButtonEnabled(false)
        // TODO draw existing trees on map

        return mainView
    }

    companion object {

        @JvmStatic
        fun newInstance() = TreeMapFragment()
    }
}