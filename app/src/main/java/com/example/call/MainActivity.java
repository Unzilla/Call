package com.example.call;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.ContentProviderOperation;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import android.net.Uri;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import static android.Manifest.permission_group.CALENDAR;
import static android.provider.ContactsContract.CommonDataKinds.Phone.*;
import static android.provider.ContactsContract.Data.CONTENT_URI;

public class MainActivity extends AppCompatActivity implements LocationListener {
    private TextToSpeech tts;
    private int req_Code;
    private final int REQUEST_SPEECH_RECOGNIZER1 = 3000;
    private final int REQUEST_SPEECH_RECOGNIZER2 = 4000;
    private final int REQUEST_SPEECH_RECOGNIZER_COMMANDS = 1000;
    private TextView mTextView;
    private final String mQuestion = "Which company is the largest online retailer on the planet?";
    private String mAnswer = "";
    Button b, b2;
    String phoneNumber;
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    String number;
    FusedLocationProviderClient fusedLocationProviderClient;
    LocationManager locationManager;
    Button b1;
    ListView listitem;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1 = findViewById(R.id.button1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                listitem=(ListView)findViewById(R.id.lv);
                if (ContextCompat.checkSelfPermission(getApplicationContext(), (Manifest.permission.READ_SMS)) != PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(getApplicationContext(), "hello permission", Toast.LENGTH_LONG).show();
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_SMS}, 200);


                } else {
//                    Uri mSmsQueryUri = Uri.parse("content://sms/inbox");
//                    List<String> messages = new ArrayList<String>();
//                    Cursor cursor = null;
//                    try {
//                        cursor = getContentResolver().query(mSmsQueryUri, null, null, null, null);
//                        if (cursor == null) {
//                            Log.i("ERROR:Log.e", "cursor is null. uri: " + mSmsQueryUri);
//
//                        }
//                        for (boolean hasData = cursor.moveToFirst(); hasData; hasData = cursor.moveToNext()) {
//                            final String body = cursor.getString(cursor.getColumnIndexOrThrow("body"));
//                            messages.add(body);
//                        }
//                    } catch (Exception e) {
//                        Log.e("ERROR:Log.e", e.getMessage());
//                    } finally {
//                        cursor.close();
//                    }
//                    listitem.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1,messages));
//                }
//                    Toast.makeText(getApplicationContext(),"hey else",Toast.LENGTH_LONG).show();
                    Uri my_uri = Uri.parse("content://sms/inbox");
                    Cursor readFstSms = v.getContext().getContentResolver().query(my_uri, null, null, null, null);
                    if (readFstSms.moveToFirst()) {
                        String msg_body = readFstSms.getString(readFstSms.getColumnIndexOrThrow("body")).toString();
                        String sender_no=readFstSms.getString(readFstSms.getColumnIndexOrThrow("address")).toString();
                        String date=readFstSms.getString(readFstSms.getColumnIndexOrThrow("date")).toString();
                        Toast.makeText(getApplicationContext(),"msgbody:"+msg_body,Toast.LENGTH_LONG).show();
                    } readFstSms.close();

                }
            }


        });
    }


//        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this);
//        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) +
//                ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)+
//                ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_NETWORK_STATE)+
//                ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.INTERNET)!=PackageManager.PERMISSION_GRANTED)
//         {
//
//             ActivityCompat.requestPermissions(new String[]{
//                             Manifest.permission.ACCESS_COARSE_LOCATION,
//                             Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_NETWORK_STATE,
//                     },
//                     ASK_MULTIPLE_PERMISSION_REQUEST_CODE);
//
//        } else {
//            try {
//
//
//                gps_loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//                network_loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            if (gps_loc != null) {
//                final_loc = gps_loc;
//                longitude = final_loc.getLongitude();
//                latitude = final_loc.getLatitude();
//
//            } else if (network_loc != null) {
//                final_loc = network_loc;
//                longitude = final_loc.getLongitude();
//                latitude = final_loc.getLatitude();
//
//            } else {
//                latitude = 0.0;
//                longitude = 0.0;
//            }
//            try {
//                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
//                List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
//                if (addresses != null && addresses.size() > 0) {
//                    String address = addresses.get(0).getAddressLine(0);
//                    String city = addresses.get(0).getLocality();
//                    String state = addresses.get(0).getAdminArea();
//                    String country = addresses.get(0).getCountryName();
//                    String postalCode = addresses.get(0).getPostalCode();
//                    String KnownName = addresses.get(0).getFeatureName();
//                    mTextView.setText("Address:" + address + "\ncity:" + city + "\nState:" + state + "\nCountry:" + country + "PostalCode:" + postalCode + "Known name:" + KnownName);
//
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        }
//        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
//            @Override
//            public void onInit(int status) {
//                if (status == TextToSpeech.SUCCESS) {
//                    int result = tts.setLanguage(Locale.ENGLISH);
//                    if (result == TextToSpeech.LANG_MISSING_DATA) {
//                        Log.e("TTS", "Lang not supported");
//                    } else {
//                        Log.e("TTS", "Initialization succeed");
//                    }
//                }
//                ;
//            }
//        }
//
//        );


//        tts.speak("hello",TextToSpeech.QUEUE_FLUSH,null);
//        sendSmsMsgFnc("03020039165","hello");


    private void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(locationManager.NETWORK_PROVIDER, 0, 10, (LocationListener) this);

        } catch (SecurityException e) {
            e.printStackTrace();

        }
    }

    private void requestPermission() {

        if ((ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 400);


        }
    }

    private void isLocationEnabledOrNot() {
        LocationManager ln = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean gpsEnabled = false;
        boolean networkEnabled = false;
        try {
            gpsEnabled = ln.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            networkEnabled = ln.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!gpsEnabled && !networkEnabled) {
            new AlertDialog.Builder(this).setTitle("Enable GPS Service:").setCancelable(false).setPositiveButton("Enable", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                }
            }).setNegativeButton("Cancel", null)
                    .show();
        }


    }

//    public void onClick(View v) {
//        String DisplayName = "Jani";
//        String MobileNumber = "123456";
//        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
//        ops.add(ContentProviderOperation.newInsert(
//                ContactsContract.RawContacts.CONTENT_URI)
//                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
//                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
//                .build());
//
//        //------------------------------------------------------ Names
//        if (DisplayName != null) {
//            ops.add(ContentProviderOperation.newInsert(
//                    ContactsContract.Data.CONTENT_URI)
//                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
//                    .withValue(ContactsContract.Data.MIMETYPE,
//                            ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
//                    .withValue(
//                            ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
//                            DisplayName).build());
//        }
//
//        //------------------------------------------------------ Mobile Number
//        if (MobileNumber != null) {
//            ops.add(ContentProviderOperation.
//                    newInsert(ContactsContract.Data.CONTENT_URI)
//                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
//                    .withValue(ContactsContract.Data.MIMETYPE,
//                            ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
//                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, MobileNumber)
//                    .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
//                            ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
//                    .build());
//        }
//
//        //------------------------------------------------------ Home Numbers
//
//        // Asking the Contact provider to create a new contact
//        try {
//            getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
//        } catch (Exception e) {
//            e.printStackTrace();
//            Toast.makeText(getApplicationContext(), "Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//        }
//
//    }

    public void hello(View v) {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, mQuestion);
        startActivityForResult(intent, REQUEST_SPEECH_RECOGNIZER1);


    }


//    private void startSpeechRecognizer() {
//    tts.speak("hello",TextToSpeech.QUEUE_FLUSH,null);
//
//        Intent intent = new Intent
//                (RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
//        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
//                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
//        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, mQuestion);
//        startActivityForResult(intent, REQUEST_SPEECH_RECOGNIZER);
//    }


//    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode,
//                                    Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//
//        if (requestCode == REQUEST_SPEECH_RECOGNIZER1) {
//            if (resultCode == RESULT_OK) {
//                List<String> results = data.getStringArrayListExtra
//                        (RecognizerIntent.EXTRA_RESULTS);
//                mAnswer = results.get(0);
//                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 0);
//
//                } else {
//
//
//                    Uri uri = ContactsContract.CommonDataKinds.Contactables.CONTENT_URI;
//                    String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
//                            ContactsContract.CommonDataKinds.Phone.NUMBER};
//                    String selection = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " like'%" + mAnswer + "%'";
//                    Cursor people = getContentResolver().query(uri, projection, selection, null, ContactsContract.Contacts.SORT_KEY_PRIMARY);
//                    int indexNumber = people.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
//                    people.moveToFirst();
//                    try {
//                        do {
//                            number = people.getString(indexNumber);
//                            if (number.contains("+91")) {
//                                number = number.replace("+91", "");
//                            }
//
//                        } while (people.moveToNext() && people.getPosition() != 1);
//                    } catch (Exception ex) {
//                        Toast.makeText(this, "Sorry no such contact", Toast.LENGTH_SHORT);
//                    }
//
//                    Toast.makeText(getApplicationContext(), "people:" + people, Toast.LENGTH_LONG).show();
//
//                    Toast.makeText(getApplicationContext(), "phone:" + selection, Toast.LENGTH_LONG).show();
//
//                }
//                Intent callIntent = new Intent(Intent.ACTION_CALL);
//                callIntent.setData(Uri.parse("tel:" + number));
//                if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CALL_PHONE}, 0);
//
//                } else
//                    startActivity(callIntent);
//            }
//
//
//        }
//
//
//    }


    @Override
    protected void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }


//    @Override
//    public void onClick(View v) {
//        if(ActivityCompat.checkSelfPermission(MainActivity.this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
//            Log.e("if","fused location");
//            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
//                @Override
//                public void onComplete(@NonNull Task<Location> task) {
//                    Location location=task.getResult();
//                    if(location!=null){
//                        Geocoder geocoder=new Geocoder(MainActivity.this,Locale.getDefault());
//                        try {
//                            List<Address>addresses=geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
////                            Toast.makeText(getApplicationContext(),"Latitude:"+addresses.get(0).getLatitude(),Toast.LENGTH_LONG).show();
////                            Toast.makeText(getApplicationContext(),"Longitude:"+addresses.get(0).getLongitude(),Toast.LENGTH_LONG).show();
////                            Toast.makeText(getApplicationContext(),"Country:"+addresses.get(0).getCountryName(),Toast.LENGTH_LONG).show();
//                            Toast.makeText(getApplicationContext(),"Address"+addresses.get(0).getAddressLine(0),Toast.LENGTH_LONG).show();
////                            Toast.makeText(getApplicationContext(),"Locality:"+addresses.get(0).getLocality(),Toast.LENGTH_LONG).show();
//
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                }
//            });
//        }
//        else{
//            Log.e("else","request permissions");
//            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
//        }


    @Override
    public void onLocationChanged(Location location) {

        try {
            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            String address = addresses.get(0).getAddressLine(0);
            String city = addresses.get(0).getLocality();
            double longitude = addresses.get(0).getLongitude();
            double latitude = addresses.get(0).getLatitude();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String KnownName = addresses.get(0).getFeatureName();
            mTextView.setText("\nLongitude:" + longitude + "\nLatitude:" + latitude + "Address:" + address + "\ncity:" + city + "\nState:" + state + "\nCountry:" + country + "PostalCode:" + postalCode + "Known name:" + KnownName);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}


//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//    public void alarm(View view) {
//
//
//        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
//        intent.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
//        intent.putExtra(AlarmClock.EXTRA_HOUR, 14);
//        intent.putExtra(AlarmClock.EXTRA_MINUTES, 31);
//        intent.putExtra(AlarmClock.EXTRA_DAYS, 0);
//        startActivity(intent);
//
//
//    }


//    public void sendSmsMsgFnc(String mblNumVar, String smsMsgVar) {
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
//            try {
//                SmsManager smsMgrVar = SmsManager.getDefault();
//                smsMgrVar.sendTextMessage(mblNumVar, null, smsMsgVar, null, null);
//                Toast.makeText(getApplicationContext(), "Message Sent",
//                        Toast.LENGTH_LONG).show();
//            } catch (Exception ErrVar) {
//                Toast.makeText(getApplicationContext(), ErrVar.getMessage().toString(),
//                        Toast.LENGTH_LONG).show();
//                ErrVar.printStackTrace();
//            }
//        } else {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                requestPermissions(new String[]{Manifest.permission.SEND_SMS}, 10);
//            }
//        }
//
//    }


//
//    public String get_Number(String name,Context context)
//
//    {
//
//        String number="";
//        Uri uri = CONTENT_URI;
//        String[] projection    = new String[] {DISPLAY_NAME,
//                NUMBER};
//        Cursor people = context.getContentResolver().query(uri, projection, null, null, null);
//        int indexName = people.getColumnIndex(DISPLAY_NAME);
//        int indexNumber = people.getColumnIndex(NUMBER);
//        people.moveToFirst();
//        do {
//            String Name   = people.getString(indexName);
//            String Number = people.getString(indexNumber);
//            if(Name.equalsIgnoreCase(name)){return Number.replace("-", "");
//            }
//            // Do work...
//        } while (people.moveToNext());
//
//        if(!number.equalsIgnoreCase("")){
//            return number.replace("-", "");}
//        else return number;
//    }
//private String retrieveContactNumber(String contactName) {
//
//    Log.d(contactName, "Contact Name: " + contactName);
//
//    Cursor cursorPhone = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//            new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},
//
//            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " = ? AND " +
//                    ContactsContract.CommonDataKinds.Phone.TYPE + " = " +
//                    ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE,
//
//            new String[]{contactName},
//            null);
//
//    if (cursorPhone.moveToFirst()) {
//        phoneNumber = cursorPhone.getString(cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//    }
//
//    cursorPhone.close();
//
//    Log.d(contactName, "Contact Phone Number: " + phoneNumber);
//
//    return phoneNumber;
//}















    
















