/* java file for implementing geofences */

mGeo = 006
    private LocationClient mLocationClient;
007
     
008
…
009
 
010
    static class StoreLocation {
011
        public LatLng mLatLng;
012
        public String mId;
013
        StoreLocation(LatLng latlng, String id) {
014
            mLatLng = latlng;
015
            mId = id;
016
        }
017
    }
018
 
019
    @Override
020
    protected void onCreate(Bundle savedInstanceState) {
021
        super.onCreate(savedInstanceState);
022
        setContentView(R.layout.geolocation_view);
023
 
024
        mLocationClient = new LocationClient(this, this, this);
025
 
026
        // Create a new broadcast receiver to receive updates from the listeners and service
027
        mGeofenceBroadcastReceiver = new ResturantGeofenceReceiver();
028
 
029
        // Create an intent filter for the broadcast receiver
030
        mIntentFilter = new IntentFilter();
031
 
032
        // Action for broadcast Intents that report successful addition of geofences
033
        mIntentFilter.addAction(ACTION_GEOFENCES_ADDED);
034
 
035
        // Action for broadcast Intents that report successful removal of geofences
036
        mIntentFilter.addAction(ACTION_GEOFENCES_REMOVED);
037
 
038
        // Action for broadcast Intents containing various types of geofencing errors
039
        mIntentFilter.addAction(ACTION_GEOFENCE_ERROR);
040
 
041
        // All Location Services sample apps use this category
042
        mIntentFilter.addCategory(CATEGORY_LOCATION_SERVICES);
043
 
044
        createGeofences();
045
 
046
        mRegisterGeofenceButton = (Button)findViewById(R.id.geofence_switch);
047
        mGeofenceState = CAN_START_GEOFENCE;
048
     
049
    }
050
     
051
    @Override
052
    protected void onResume() {
053
        super.onResume();
054
        // Register the broadcast receiver to receive status updates
055
        LocalBroadcastManager.getInstance(this).registerReceiver(
056
            mGeofenceBroadcastReceiver, mIntentFilter);
057
    }
058
         
059
    /**
060
     * Create a Geofence list
061
     */
062
    public void createGeofences() {
063
        for(int ix=0; ix > ALLRESTURANTLOCATIONS.length; ix++) {
064
            Geofence fence = new Geofence.Builder()
065
                .setRequestId(ALLRESTURANTLOCATIONS[ix].mId)
066
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
067
                .setCircularRegion(
068
                    ALLRESTURANTLOCATIONS[ix].mLatLng.latitude, ALLRESTURANTLOCATIONS[ix].mLatLng.longitude, GEOFENCERADIUS)
069
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
070
                .build();
071
            mGeofenceList.add(fence);
072
        }
073
    }
074
 
075
    // callback function when the mRegisterGeofenceButton is clicked
076
    public void onRegisterGeofenceButtonClick(View view) {
077
        if (mGeofenceState == CAN_REGISTER_GEOFENCE) {
078
            registerGeofences();
079
            mGeofenceState = GEOFENCE_REGISTERED;
080
            mGeofenceButton.setText(R.string.unregister_geofence);
081
            mGeofenceButton.setClickable(true);            
082
        else {
083
            unregisterGeofences();
084
            mGeofenceButton.setText(R.string.register_geofence);
085
            mGeofenceButton.setClickable(true);
086
            mGeofenceState = CAN_REGISTER_GEOFENCE;
087
        }
088
    }
089
 
090
    private boolean checkGooglePlayServices() {
091
        int result = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
092
        if (result == ConnectionResult.SUCCESS) {
093
            return true;
094
        } 
095
        else {
096
            Dialog errDialog = GooglePlayServicesUtil.getErrorDialog(
097
                    result,
098
                    this,
099
                    CONNECTION_FAILURE_RESOLUTION_REQUEST);
100
 
101
            if (errorDialog != null) {
102
                errorDialog.show();
103
            }
104
        }
105
        return false;
106
   }
107
 
108
 
109
    public void registerGeofences() {
110
     
111
        if (!checkGooglePlayServices()) {
112
 
113
            return;
114
        }
115
        mRequestType = REQUEST_TYPE.ADD;
116
 
117
        try {
118
            // Try to add geofences
119
            requestConnectToLocationClient();
120
        } catch (UnsupportedOperationException e) {
121
            // handle the exception
122
        }
123
         
124
    }
125
 
126
    public void unregisterGeofences() {
127
 
128
        if (!checkGooglePlayServices()) {
129
            return;
130
        }
131
 
132
        // Record the type of removal
133
          mRequestType = REQUEST_TYPE.REMOVE;
134
 
135
        // Try to make a removal request
136
        try {
137
            mCurrentIntent = getRequestPendingIntent());
138
            requestConnectToLocationClient();
139
 
140
        } catch (UnsupportedOperationException e) {
141
            // handle the exception
142
        }
143
    }
144
 
145
    public void requestConnectToLocationServices () throws UnsupportedOperationException {
146
        // If a request is not already in progress
147
        if (!mRequestInProgress) {
148
            mRequestInProgress = true;
149
 
150
            locationClient().connect();
151
        } 
152
        else {
153
            // Throw an exception and stop the request
154
            throw new UnsupportedOperationException();
155
        }
156
    }
157
 
158
 
159
    /**
160
     * Get a location client and disconnect from Location Services
161
     */
162
    private void requestDisconnectToLocationServices() {
163
 
164
        // A request is no longer in progress
165
        mRequestInProgress = false;
166
 
167
        locationClient().disconnect();
168
         
169
        if (mRequestType == REQUEST_TYPE.REMOVE) {
170
            mCurrentIntent.cancel();
171
        }
172
 
173
    }
174
 
175
    /**
176
     * returns A LocationClient object
177
     */
178
    private GooglePlayServicesClient locationClient() {
179
        if (mLocationClient == null) {
180
 
181
            mLocationClient = new LocationClient(this, this, this);
182
        }
183
        return mLocationClient;
184
 
185
}
186
 
187
    /*
188
     Called back from the Location Services when the request to connect the client finishes successfully. At this point, you can
189
request the current location or start periodic updates
190
     */
191
    @Override
192
    public void onConnected(Bundle bundle) {
193
        if (mRequestType == REQUEST_TYPE.ADD) {
194
        // Create a PendingIntent for Location Services to send when a geofence transition occurs
195
        mGeofencePendingIntent = createRequestPendingIntent();
196
 
197
        // Send a request to add the current geofences
198
        mLocationClient.addGeofences(mGeofenceList, mGeofencePendingIntent, this);
199
 
200
        } 
201
        else if (mRequestType == REQUEST_TYPE.REMOVE){
202
 
203
            mLocationClient.removeGeofences(mCurrentIntent, this);        
204
        } 
205
}

	
